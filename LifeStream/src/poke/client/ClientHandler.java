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
package poke.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.util.PrintNode;

import com.google.protobuf.GeneratedMessage;

import eye.Comm.Document;
import eye.Comm.Header;
import eye.Comm.NameValueSet;
import eye.Comm.PayloadReply;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;
import poke.server.Server;
import poke.server.queue.QueueFactory;
import poke.server.resources.ResourceUtil;


public class ClientHandler extends SimpleChannelUpstreamHandler {
	protected static Logger logger = LoggerFactory.getLogger("client");

	private volatile Channel channel;

	public ClientHandler() {
	}

	public boolean send(GeneratedMessage msg) {
		// TODO a queue is needed to prevent overloading of the socket
		// connection. For the demonstration, we don't need it
		ChannelFuture cf = channel.write(msg);
		if (cf.isDone() && !cf.isSuccess()) {
			logger.error("failed to poke!");
			return false;
		}

		return true;
	}

	public void handleMessage(eye.Comm.Response msg) {
		if (msg.getHeader().getRoutingId() == Header.Routing.FINGER) {
			System.out.println("Finger response: ");
			System.out.println(" - Tag : " + msg.getHeader().getTag());
			System.out.println(" - Time : " + msg.getHeader().getTime());
			System.out.println(" - Status : " + msg.getHeader().getReplyCode());
				// Code changed by illuminati starts
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("Image operation Successful");
			}
			else
			{
				System.out.println("Image operation failed");
			}
			System.out.println("\nInfo:");
			printDocument(msg.getBody().getFinger());
		}
		else if(msg.getHeader().getRoutingId() == Header.Routing.DOCADD)
		{
			System.out.println("Image Add Response");
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("Image operation Successful");
			}
			else
			{
				System.out.println("Image operation failed");
			}
		}
		else if(msg.getHeader().getRoutingId() == Header.Routing.DOCREMOVE)
		{
			System.out.println("Image Remove Response");
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("Image operation Successful");
			}
			else
			{
				System.out.println("Image operation failed");
			}
		}
		else if(msg.getHeader().getRoutingId() == Header.Routing.DOCFIND)
		{
			System.out.println("Image Find Response");
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("Image operation Successful");
			}
			else
			{
				System.out.println("Image operation failed");
			}
		}
		else if(msg.getHeader().getRoutingId() == Header.Routing.USERADD)
		{
			System.out.println("User Add Response");
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("User operation Successful");
			}
			else
			{
				System.out.println("User operation failed");
			}
		}
		else if(msg.getHeader().getRoutingId() == Header.Routing.USERCHECK)
		{
			System.out.println("User Check Response");
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("User operation Successful");
			}
			else
			{
				System.out.println("User operation failed");
			}
		}
		else if(msg.getHeader().getRoutingId() == Header.Routing.USERREMOVE)
		{
			System.out.println("User Delete Response");
			if(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS)
			{
				System.out.println("User operation Successful");
			}
			else
			{
				System.out.println("User operation failed");
			} 	// Code changed by illuminati ends
		}
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		channel = e.getChannel();
		super.channelOpen(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		if (channel.isConnected())
			channel.write(ChannelBuffers.EMPTY_BUFFER).addListener(
					ChannelFutureListener.CLOSE);
	}

	@Override
	public void channelInterestChanged(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		if (e.getState() == ChannelState.INTEREST_OPS
				&& ((Integer) e.getValue() == Channel.OP_WRITE)
				|| (Integer) e.getValue() == Channel.OP_READ_WRITE)
			logger.warn("channel is not writable! <--------------------------------------------");
	}
	
	
	// Code changed by illuminati starts
	// This method has been changed so that it can route requests to the
	// original client
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		if(!ClientConnection.isRequestRouted()) // Team illuminati
		{	logger.info("Normal client received a response from Remote Server: "+e.getRemoteAddress().toString());
			//logger.info("Number of hops: "+ ClientConnection.getHopCount());
			handleMessage((eye.Comm.Response) e.getMessage());
		}
		// code added by Team illuminati starts
		else
		{	
			// increment the hopCount in the google protobuff response message
			// for this we need to rebuild the google protobuff response message
			// We extract hostname and port using e.getRemoteAddress of the server
			// who gave the response and then we put it into the route table
			// which is a hashmap
			
			Response.Builder r = Response.newBuilder();
        	r.setHeader(((eye.Comm.Response) e.getMessage()).getHeader());
        	PayloadReply.Builder p = PayloadReply.newBuilder();
        	int hopCount = ((eye.Comm.Response) e.getMessage()).getBody().getHopCount();
        	p.setHopCount(++hopCount);
        	r.setBody(p.build());
            
            Response newReply = r.build();
            
			logger.info("Server acting as a client received response from Remote Server: "+e.getRemoteAddress().toString());
			logger.info("Request Hop Count "+ newReply.getBody().getHopCount());
			QueueFactory.getInstance(ClientConnection.getResonseChannel()).enqueueResponse(newReply);
			//update the server routing table;
			InetSocketAddress remAddress = (InetSocketAddress) e.getRemoteAddress();
			
			//int routeId = ((eye.Comm.Response) e.getMessage()).getHeader().getRoutingId().getNumber();
			String location = ((eye.Comm.Response) e.getMessage()).getBody().getImage().getTag();
			//Server.routeTable.put(hopCount,new Server.ServerRequestMapper(remAddress.getHostString(),remAddress.getPort(),routeId));
			Server.routeTable.add(new Server.ServerRequestMapper(remAddress.getHostString(),
					remAddress.getPort(),location,hopCount));
			
		}
		
		// code added by Team illuminati ends
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		System.out.println("ERROR: " + e.getCause());

		// TODO do we really want to do this? try to re-connect?
		e.getChannel().close();
	}

	private void printDocument(Document doc) {
		if (doc == null) {
			System.out.println("document is null");
			return;
		}

		if (doc.hasNameSpace())
			System.out.println("NameSpace: " + doc.getNameSpace());

		if (doc.hasDocument()) {
			NameValueSet nvs = doc.getDocument();
			PrintNode.print(nvs);
		}
	}

}
