/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.server.management;

import java.lang.Thread.State;
import java.net.SocketAddress;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.Server;


import eye.Comm.Management;
import eye.Comm.Request;
import eye.Comm.Response;

/**
 * The management queue exists as an instance per process (node)
 * 
 * @author gash
 * 
 */
public class ManagementQueue implements ChannelQueue {
	protected static Logger logger = LoggerFactory.getLogger("management");

	protected static LinkedBlockingDeque<ManagementQueueEntry> inbound = new LinkedBlockingDeque<ManagementQueueEntry>();
	protected static LinkedBlockingDeque<ManagementQueueEntry> outbound = new LinkedBlockingDeque<ManagementQueueEntry>();

	// TODO static is problematic
	private static OutboundMgmtWorker oworker;
	private static InboundMgmtWorker iworker;
	
	private Channel channel; // abhi
	private String nodeId; // abhi

	// not the best method to ensure uniqueness
	private static ThreadGroup tgroup = new ThreadGroup("ManagementQueue-"
			+ System.nanoTime());
	
	//  code added Team illuminati starts
	public ManagementQueue(Channel channel,String nodeId)
	{
		this.channel = channel;
		this.nodeId = nodeId; 
		startup();
	}
	
	// code added by Team illuminati ends
	
	public  void startup() {
		if (iworker != null)
			return;

		iworker = new InboundMgmtWorker(tgroup, 1);
		iworker.start();
		oworker = new OutboundMgmtWorker(tgroup, 1);
		oworker.start();
	}

	public  void shutdown(boolean hard) {
		// TODO shutdon workers
		logger.info("server is shutting down");
		
		
		channel = null;

		if (hard) {
			// drain queues, don't allow graceful completion
			inbound.clear();
			outbound.clear();
		}

		if (iworker != null) {
			iworker.forever = false;
			if (iworker.getState() == State.BLOCKED
					|| iworker.getState() == State.WAITING)
				iworker.interrupt();
			iworker = null;
		}

		if (oworker != null) {
			oworker.forever = false;
			if (oworker.getState() == State.BLOCKED
					|| oworker.getState() == State.WAITING)
				oworker.interrupt();
			oworker = null;
		}
	}

	public  void enqueueRequest(Management req,
			SocketAddress sa) {
		try {
			ManagementQueueEntry entry = new ManagementQueueEntry(req, this.channel, sa);
			inbound.put(entry);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for processing", e);
		}
	}

	public  void enqueueResponse(Management reply) {
		try {
			ManagementQueueEntry entry = new ManagementQueueEntry(reply, this.channel,
					null);
			outbound.put(entry);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for reply", e);
		}
	}

	public static class ManagementQueueEntry {
		public ManagementQueueEntry(Management req, Channel ch, SocketAddress sa) {
			this.req = req;
			this.channel = ch;
			this.sa = sa;
		}

		public Management req;
		public Channel channel;
		SocketAddress sa;
	}


}
