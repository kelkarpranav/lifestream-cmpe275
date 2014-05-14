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

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.routing.ServerHandler.ConnectionClosedListener;

public class ManagementHandler extends SimpleChannelUpstreamHandler {
	protected static Logger logger = LoggerFactory.getLogger("management");

	private ChannelQueue queue;
	
	public ManagementHandler() {
		// logger.info("** HeartbeatHandler created **");
	}

	/**
	 * override this method to provide processing behavior
	 * 
	 * @param msg
	 */
	public void handleMessage(eye.Comm.Management req, Channel channel) {
		if (req == null) {
			logger.error("ERROR: Unexpected content - null");
			return;
		}

		logger.info("ManagementHandler got messsage");
		// ManagementQueue.enqueueRequest(req, channel);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		logger.info("message rcv: " + e.getRemoteAddress());
		queueInstance(e.getChannel(),((eye.Comm.Management) e.getMessage()).getGraph().getNodeId())
		.enqueueRequest((eye.Comm.Management) e.getMessage(),
				 e.getRemoteAddress());

		// handleMessage((eye.Comm.Management) e.getMessage(), e.getChannel());
	}

	// code added by Team illuminati starts
	 private ChannelQueue queueInstance(Channel channel, String nodeId) {
		// if a single queue is needed, this is where we would obtain a
		// handle to it.

		if (queue != null)
			return queue;
		else {
			queue = QueueFactory.getInstance(channel, nodeId);

			// on close remove from queue
			channel.getCloseFuture().addListener(
					new ManagementClosedListener(queue));
		}

		return queue;
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.error(
				"ManagementHandler error, closing channel, reason: "
						+ e.getCause(), e);
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

	/**
	 * usage:
	 * 
	 * <pre>
	 * channel.getCloseFuture().addListener(new ManagementClosedListener(queue));
	 * </pre>
	 * 
	 * @author gash
	 * 
	 */
	public static class ManagementClosedListener implements
			ChannelFutureListener {
		 private ChannelQueue sq;

		public ManagementClosedListener(ChannelQueue sq) {
			 this.sq = sq;
		}

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			 if (sq != null)
			 sq.shutdown(true);
			 sq = null;
		}

	}
}
