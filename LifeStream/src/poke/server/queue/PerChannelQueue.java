/*
cd so
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
package poke.server.queue;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.client.ClientConnection;
import poke.server.Server;
import poke.server.conf.ServerConf.GeneralConf;
import poke.server.resources.Resource;
import poke.server.resources.ResourceFactory;
import poke.server.resources.ResourceUtil;

import com.google.protobuf.GeneratedMessage;

import eye.Comm.Header.ReplyStatus;
import eye.Comm.Payload;
import eye.Comm.Request;
import eye.Comm.Response;

/**
 * A server queue exists for each connection (channel).
 * 
 * @author gash
 * 
 */
public class PerChannelQueue implements ChannelQueue {
	protected static Logger logger = LoggerFactory.getLogger("server");

	private Channel channel;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> inbound;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;
	private LinkedBlockingDeque<eye.Comm.Request> routebound; // Team illuminati
	private OutboundWorker oworker;
	private InboundWorker iworker;
	private RouteWorker rworker; // Team illuminati
	private static int roundRobin; //Team illuminati
	private static int reqCount ; // Team illuminati
	
	// not the best method to ensure uniqueness
	private ThreadGroup tgroup = new ThreadGroup("ServerQueue-"
			+ System.nanoTime());

	protected PerChannelQueue(Channel channel) {
		this.channel = channel;
		init();
	}
	

	protected void init() {
		inbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();
		outbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();
		routebound = new LinkedBlockingDeque<eye.Comm.Request>(); // Team illuminati
				
		iworker = new InboundWorker(tgroup, 1, this);
		iworker.start();

		oworker = new OutboundWorker(tgroup, 1, this);
		oworker.start();
		
		// code added by Team illuminati starts
		rworker = new RouteWorker(tgroup, 1, this);
		rworker.start();
		// code added by Team illuminati ends
		
		
		// let the handler manage the queue's shutdown
		// register listener to receive closing of channel
		// channel.getCloseFuture().addListener(new CloseListener(this));
	}

	protected Channel getChannel() {
		return channel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#shutdown(boolean)
	 */
	@Override
	public void shutdown(boolean hard) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#enqueueRequest(eye.Comm.Finger)
	 */
	@Override
	public void enqueueRequest(Request req) {
		try {
			inbound.put(req);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for processing", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#enqueueResponse(eye.Comm.Response)
	 */
	@Override
	public void enqueueResponse(Response reply) {
		try {
			outbound.put(reply);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for reply", e);
		}
	}
	// code added by Team illuminati starts
	// We have created an a seperate queue for routing and a seperate thread
	// handles the routing queue. We first check the routing table to see if
	// there is any server entry for routing the request. If there is no entry
	// in the routing table then we randomly choose a server and forward the 
	// request to it. When the response to the client handler comes we populate
	// the routing tables. If an entry is found in the routing table then that
	// entry is chosen and the request is forwarded to that server
	
	public void enqueueRouteRequest(Request req) {
		try {
			routebound.put(req);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for processing", e);
		}
	}
	
	protected class RouteWorker extends Thread {
		int workerId;
		PerChannelQueue sq;
		boolean forever = true;

		public RouteWorker(ThreadGroup tgrp, int workerId, PerChannelQueue sq) {
			super(tgrp, "routebound-" + workerId);
			this.workerId = workerId;
			this.sq = sq;

			if (routebound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			

			while (true) {
				if (!forever && sq.routebound.size() == 0)
					break;

				try {
					reqCount++;
					
					// block until a message is enqueued
					eye.Comm.Request req = sq.routebound.take();
						
					int port = 0;
					String host = "";
					
					logger.info("Searching entry in routing table");
					
					// Start by checking with the minimum keys in the
					// routing table which is a hashmap having hop count
					// as the key
					
					// We will also check if the server is available
					// If the server is available then we will forward
					// the client request to that server
					boolean forever = true;
					boolean hit = false; // if match in routing table
					boolean strike = false; //if its safe to forward req
					int i=0;
					while(i<12 && forever)
					{i++;
					  for(int j=0;j<Server.routeTable.size();j++)
					  {
							if(Server.routeTable.get(j).getHopCount() == i)
							{
								if(Server.routeTable.get(j).getLocation().equals( 
										req.getBody().getImage().getTag()))
								{
									if(Server.checkMoniteredServerStatus(Server.routeTable.get(j).getHost(),
											Server.routeTable.get(j).getPort()))
									{
										port = Server.routeTable.get(j).getPort();
										host = Server.routeTable.get(j).getHost();
										forever = false;
										hit = true ;
										break;
									}
								}
							}
					  }
					}
				
						
						ClientConnection cc=null;
						
						//if(Server.routeTable.size() == 0 || (port ==0 && host == ""))
						if(!hit)
						{	// Search for servers in roundrobin order
							int sizeCount=0;
							// Check if the request is returning from a cycle
						if(!(req.getBody().getHopPath().contains(Server.getServerNodeId())))
						{	
						  while(sizeCount < Server.routePool.size())
						  {
							roundRobin = reqCount % Server.routePool.size()  ;  	
						
								if(Server.checkMoniteredServerStatus("localhost", 
										Server.routePool.get(roundRobin)))
								{
									logger.info("Entry not found in routing table");
									logger.info("Request routed to: "+Server.routePool.get(roundRobin));
									logger.info("Hop path"+ req.getBody().getHopPath());
									cc = ClientConnection
									.initConnection("localhost", Server.routePool.get(roundRobin));
									strike = true;
									
									//test code by Team illuminati
									/*
									
									List<GeneralConf> upneighbours = Server.getServerConf().getServerUpNeighbours();
									String remNodeId="";
									
									for(GeneralConf gen : upneighbours )
									{
										if(gen.getProperty("port").equals( Server.routePool.get(roundRobin)))
										{	logger.info("Match******************");
											remNodeId = gen.getProperty("port");
											
										}
									}
									
									if(req.getBody().getHopPath().contains(remNodeId))
									{
										Server.isCycle = true;
									}
									else
									{
										Server.isCycle = false;
									}
									
									// ends
									*/
									
									//Server.isForwardingReq = true;
									// code added by Team illuminati
									// We will add the hop path in the request payload.
									// This helps us to keep track of cycles
									
									Request.Builder r = Request.newBuilder();
									eye.Comm.Payload.Builder p = Payload.newBuilder();
									p.setHopPath(req.getBody().getHopPath()+Server.getServerNodeId());
									p.setImage(req.getBody().getImage());
									r.setBody(p.build());
									r.setHeader(req.getHeader());
									req = r.build();
									Server.isCycle = false;
									
									break;
								}
								else
								{
									++reqCount;
									++sizeCount;
								}							
						  }
						}
						// We have a cycle in graph
						else
						{
							// The below code routes the acyclic requests to the concerned node
							// But it has a limitation
							// It cannot route the requests backs to client
							// This is becuase our original back channel gets overwritten when we
							// enter a cycle. Thus we lose the track of our client node and are
							// unable to retrace our path back to the client.
							
							// As for now we will not be routing the client requests causing cycles
							// to the destination servers.
							
							// The impact will be a loss of few initial requests. The later requests
							// will be using routing tables.
							
							
							
							/*
							logger.info("Acyclic Entry");
							// capture node which creates cycle
							ArrayList<Integer> acyclic = new ArrayList<Integer>();
							acyclic.addAll(Server.routePool);
							
							// capture the node id creating cycle
							
							logger.info("Hop Path: "+req.getBody().getHopPath());
							String delNodeId = ""+req.getBody().getHopPath().charAt(1);
							logger.info("delNode: "+delNodeId);
							Integer delNodePort = 0;
							
							List<GeneralConf> upneighbours = Server.getServerConf().getServerUpNeighbours();
							
							for(GeneralConf gen : upneighbours )
							{
								if(gen.getProperty("node.id").equals(delNodeId))
								{	logger.info("Match******************");
									delNodePort = Integer.parseInt(gen.getProperty("port"));
									logger.info("deleted port"+ delNodePort);
								}
							}
							
							logger.info("Remove status"+acyclic.remove(delNodePort));
							logger.info("Remove val"+acyclic.get(0));
							
							while(sizeCount < acyclic.size())
							  { 
								roundRobin = reqCount % acyclic.size()  ;  	
								logger.info("roundrobin:"+ roundRobin);
									if(Server.checkMoniteredServerStatus("localhost", 
											acyclic.get(roundRobin)))
									{
									
										logger.info("Entry not found in routing table");
										logger.info("Request routed to: "+acyclic.get(roundRobin));
										
										cc = ClientConnection
										.initConnection("localhost", acyclic.get(roundRobin));
										strike = true;
										//Server.isForwardingReq = true;
										// code added by Team illuminati
										// We will add the hop path in the request payload.
										// This helps us to keep track of cycles
										
										Request.Builder r = Request.newBuilder();
										eye.Comm.Payload.Builder p = Payload.newBuilder();
										p.setHopPath(Server.getServerNodeId());
										p.setImage(req.getBody().getImage());
										r.setBody(p.build());
										r.setHeader(req.getHeader());
										req = r.build();
										logger.info("Hop path"+ req.getBody().getHopPath());
										
										
										Server.isCycle = true;
										//++reqCount;
										break;
									}
									else
									{
										++reqCount;
										++sizeCount;
									}
								
							  }
							*/
						}
						}
						else
						{	
							logger.info("Entry found in routing table");
							logger.info("Hop path"+ req.getBody().getHopPath());
							logger.info("Request routed to: "+host+":"+port);
							cc = ClientConnection
									.initConnection(host, port);
							strike = true;
						}
						
						if(strike)
							cc.forwardRequest(req,channel,Server.isCycle);
						else
							logger.info("Service nodes are down or may cause a cycle");
					
					
				} catch (Exception e) {
					PerChannelQueue.logger.error(
							"Unexpected communcation failure", e);
					break;
				}
			}

			if (!forever) {
				PerChannelQueue.logger.info("connection queue closing");
			}
		}
	}

	// code added by Team illuminati ends

	protected class OutboundWorker extends Thread {
		int workerId;
		PerChannelQueue sq;
		boolean forever = true;

		public OutboundWorker(ThreadGroup tgrp, int workerId, PerChannelQueue sq) {
			super(tgrp, "outbound-" + workerId);
			this.workerId = workerId;
			this.sq = sq;

			if (outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			Channel conn = sq.channel;
			if (conn == null || !conn.isOpen()) {
				PerChannelQueue.logger
						.error("connection missing, no outbound communication");
				return;
			}

			while (true) {
				if (!forever && sq.outbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = sq.outbound.take();
					if (conn.isWritable()) {
						boolean rtn = false;
						if (channel != null && channel.isOpen()
								&& channel.isWritable()) {
							ChannelFuture cf = channel.write(msg);

							// blocks on write - use listener to be async
							cf.awaitUninterruptibly();
							rtn = cf.isSuccess();
							if (!rtn)
								sq.outbound.putFirst(msg);
						}

					} else
						sq.outbound.putFirst(msg);
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					PerChannelQueue.logger.error(
							"Unexpected communcation failure", e);
					break;
				}
			}

			if (!forever) {
				PerChannelQueue.logger.info("connection queue closing");
			}
		}
	}
	
	
	protected class InboundWorker extends Thread {
		int workerId;
		PerChannelQueue sq;
		boolean forever = true;

		public InboundWorker(ThreadGroup tgrp, int workerId, PerChannelQueue sq) {
			super(tgrp, "inbound-" + workerId);
			this.workerId = workerId;
			this.sq = sq;

			if (outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			Channel conn = sq.channel;
			if (conn == null || !conn.isOpen()) {
				PerChannelQueue.logger
						.error("connection missing, no inbound communication");
				return;
			}

			while (true) {
				if (!forever && sq.inbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = sq.inbound.take();

					// process request and enqueue response
					if (msg instanceof Request) {
						Request req = ((Request) msg);
						Resource rsc = ResourceFactory.getInstance()
								.resourceInstance(
										req.getHeader().getRoutingId());

						Response reply = null;
						if (rsc == null) {
							logger.error("failed to obtain resource for " + req);
							reply = ResourceUtil.buildError(req.getHeader(),
									ReplyStatus.FAILURE,
									"Request not processed");
						} else
							reply = rsc.process(req);

						sq.enqueueResponse(reply);
					}

				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					PerChannelQueue.logger.error(
							"Unexpected processing failure", e);
					break;
				}
			}

			if (!forever) {
				PerChannelQueue.logger.info("connection queue closing");
			}
		}
	}

	public class CloseListener implements ChannelFutureListener {
		private ChannelQueue sq;

		public CloseListener(ChannelQueue sq) {
			this.sq = sq;
		}

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			sq.shutdown(true);
		}
	}
}
