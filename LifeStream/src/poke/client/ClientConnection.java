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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import javax.imageio.ImageIO;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.ByteString;

import eye.Comm;
import eye.Comm.Document;
import eye.Comm.Finger;
import eye.Comm.Header;
import eye.Comm.Image;
import eye.Comm.Payload;
import eye.Comm.Request;

/**
 * provides an abstraction of the communication to the remote server.
 * 
 * @author gash
 * 
 */
public class ClientConnection {
	protected static Logger logger = LoggerFactory.getLogger("client");

	private String host;
	private int port;
	private ChannelFuture channel; // do not use directly call connect()!
	private ClientBootstrap bootstrap;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;
	private OutboundWorker worker;
	private static boolean routedRequest = false; // Team illuminati
	private static Channel responseChannel ; //Team illuminati
	private static boolean isCycle = false; // Team illuminati
	
	protected ClientConnection(String host, int port) {
		this.host = host;
		this.port = port;

		init();
	}

	/**
	 * release all resources
	 */
	public void release() {
		bootstrap.releaseExternalResources();
	}
	
	//code added by Team illuminati starts
	// This method tells if a request is made from a genuine client or 
	// from a routed server acting as a clinet
	public static boolean isRequestRouted()
	{
		return routedRequest;
	}
	// code added by Team illuminati ends
	
	
	
	public static ClientConnection initConnection(String host, int port) {

		ClientConnection rtn = new ClientConnection(host, port);
		return rtn;
	}
	
	// code added by Team illuminati starts
	// This method returns the back channel of the preceeding server requests
	
	public static Channel getResonseChannel()
	{		
		return responseChannel;
	}
	
	// code added by Team illuminati ends
	
	
	// code added by Team illuminati starts
	// This method is called when a server acts as a client
	// This basically forwards requests between clients
	// we also set routedRequest flag to true
	
	public void forwardRequest(eye.Comm.Request req, Channel backChannel, boolean cyclecheck)
	{
		isCycle = cyclecheck;
		
		try {
			// comments added by Team illuminati
			// The messages produced by clients will be added to the
			//outbound queue and will be processed by the worker thread
			// enqueue message
			outbound.put(req);
			
			responseChannel = backChannel;
			routedRequest= true; 
			
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	// code added by Team illuminati ends
	
	public void poke(String tag, int num, String location) {
		// data to send
		Finger.Builder f = eye.Comm.Finger.newBuilder();
		f.setTag(tag);
		f.setNumber(num);
		
		File fnew=new File("/home/Team illuminatijeet/softwares/core-netty/smiley.jpeg");
		//File f1=new File("e:/test1.jpg");
		BufferedImage originalImage=null;
		try {
			originalImage = ImageIO.read(fnew);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			ImageIO.write(originalImage, "jpg", baos );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] imageInByte = baos.toByteArray();
		

		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		// Team illuminati starts
		eye.Comm.Image.Builder i = Image.newBuilder() ;
		ByteString b = ByteString.copyFrom(imageInByte);
		i.setContent(b);
		i.setTag(location);
		i.build();
		// Team illuminati ends
		
		p.setFinger(f.build());
		p.setImage(i);
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test finger");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.FINGER);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// comments added by Team illuminati
			// The messages produced by clients will be added to the
			//outbound queue and will be processed by the worker thread
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	// code added by Team illuminati to upload images
	public void upload(int id,String tag,  String location) {
		// data to send
		
	
		File fnew=new File("/home/rupesh/Downloads/smiley.jpg");
		
		//File f1=new File("e:/test1.jpg");
		BufferedImage originalImage=null;
		try {
			
		originalImage=ImageIO.read(fnew);
		
			//originalImage = ImageIO.read(this.getClass().getResource("/home/rupesh/Downloads/smiley.jpg"));
			
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			ImageIO.write(originalImage, "jpg", baos );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] imageInByte = baos.toByteArray();
		

		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		// Team illuminati starts
		
		
		
		eye.Comm.Image.Builder i = Image.newBuilder() ;
		ByteString b = ByteString.copyFrom(imageInByte);
		i.setId(id);
		i.setContent(b);
		i.setTag(location);
		i.build();
		// Team illuminati ends
		
		p.setImage(i);
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test Image Add");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.DOCADD);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// comments added by Team illuminati
			// The messages produced by clients will be added to the
			//outbound queue and will be processed by the worker thread
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	// code added by Team illuminati to upload images
		public void findImage(int id,String tag, int num, String location) {
				

			// payload containing data
			Request.Builder r = Request.newBuilder();
			eye.Comm.Payload.Builder p = Payload.newBuilder();
			// Team illuminati starts
			eye.Comm.Image.Builder i = Image.newBuilder() ;
			byte[] imageInByte = {0};
			ByteString b = ByteString.copyFrom(imageInByte);
			i.setId(id);
			i.setContent(b);
			i.setTag(location);
			i.build();
			// Team illuminati ends
			
			p.setImage(i);
			r.setBody(p.build());

			// header with routing info
			eye.Comm.Header.Builder h = Header.newBuilder();
			h.setOriginator("client");
			h.setTag("test Image Find");
			h.setTime(System.currentTimeMillis());
			h.setRoutingId(eye.Comm.Header.Routing.DOCFIND);
			r.setHeader(h.build());

			eye.Comm.Request req = r.build();

			try {
				// comments added by Team illuminati
				// The messages produced by clients will be added to the
				//outbound queue and will be processed by the worker thread
				// enqueue message
				outbound.put(req);
			} catch (InterruptedException e) {
				logger.warn("Unable to deliver message, queuing");
			}
		}

	
	
	//code added by Team illuminatijeet to delete images
	public void DeleteImage(int id,String tag, int num, String location) {
		// data to send
		
			// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		// Team illuminati starts
		eye.Comm.Image.Builder i = Image.newBuilder() ;
		i.setId(id);
		
		byte[] imageInByte = {0};
		ByteString b = ByteString.copyFrom(imageInByte);
		i.setContent(b);
		i.setTag(location);
		i.build();
		// Team illuminati ends
		
		p.setImage(i);
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test Image Delete");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.DOCREMOVE);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// comments added by Team illuminati
			// The messages produced by clients will be added to the
			//outbound queue and will be processed by the worker thread
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	// code added by Team illuminatijeet to add, delete and check users
	
	// code added by Team illuminati to upload images
	public void addUser(String username, String password, String name, String emailId, String location) {
		// data to send
		

		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		// Team illuminati starts
		eye.Comm.User.Builder u = Comm.User.newBuilder();
		
		u.setUsername(username);
		u.setPassword(password);
		u.setName(name);
		u.setEmailId(emailId);
		u.setTag(location);
		
		p.setUser(u.build());
		
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test User Add");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.USERADD);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// comments added by Team illuminati
			// The messages produced by clients will be added to the
			//outbound queue and will be processed by the worker thread
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	// code added by Team illuminati to upload images
		public void checkUser(String username, String password, String location) {
				

			// payload containing data
			Request.Builder r = Request.newBuilder();
			eye.Comm.Payload.Builder p = Payload.newBuilder();
			// Team illuminati starts
			eye.Comm.User.Builder u = Comm.User.newBuilder();
			
			u.setUsername(username);
			u.setPassword(password);
			u.setTag(location);
			
			p.setUser(u.build());
			
			r.setBody(p.build());

			// Team illuminati ends
			
			p.setUser(u);
			r.setBody(p.build());

			// header with routing info
			eye.Comm.Header.Builder h = Header.newBuilder();
			h.setOriginator("client");
			h.setTag("test User Authenticate");
			h.setTime(System.currentTimeMillis());
			h.setRoutingId(eye.Comm.Header.Routing.USERCHECK);
			r.setHeader(h.build());

			eye.Comm.Request req = r.build();

			try {
				// comments added by Team illuminati
				// The messages produced by clients will be added to the
				//outbound queue and will be processed by the worker thread
				// enqueue message
				outbound.put(req);
			} catch (InterruptedException e) {
				logger.warn("Unable to deliver message, queuing");
			}
		}

	
	
	//code added by Team illuminatijeet to delete images
	public void DeleteUser(String username, String location) {
		// data to send
		
			// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		// Team illuminati starts
		eye.Comm.User.Builder u = Comm.User.newBuilder();
		
		u.setUsername(username);
		u.setTag(location);
		
		p.setUser(u.build());
		
		
		r.setBody(p.build());

		// Team illuminati ends
		
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator("client");
		h.setTag("test User Delete");
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.USERREMOVE);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// comments added by Team illuminati
			// The messages produced by clients will be added to the
			//outbound queue and will be processed by the worker thread
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	private void init() {
		// the queue to support client-side surging
		outbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();

		// Configure the client.
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new ClientDecoderPipeline());

		// comments by Team illuminati
		// This is a worker thread which monitors the Queue outbound
		// as soon as a new messages come to this queue they are
		// dequeued by the worker thread
		// start outbound message processor
		worker = new OutboundWorker(this);
		worker.start();
		
	}

	/**
	 * create connection to remote server
	 * 
	 * @return
	 */
	protected Channel connect() {
		// Start the connection attempt.
		if (channel == null) {
			// System.out.println("---> connecting");
			channel = bootstrap.connect(new InetSocketAddress(host, port));
			
			// cleanup on lost connection

		}

		// wait for the connection to establish
		channel.awaitUninterruptibly();

		if (channel.isDone() && channel.isSuccess())
			return channel.getChannel();
		else
			throw new RuntimeException(
					"Not able to establish connection to server");
	}

	/**
	 * queues outgoing messages - this provides surge protection if the client
	 * creates large numbers of messages.
	 * 
	 * @author gash
	 * 
	 */
	protected class OutboundWorker extends Thread {
		ClientConnection conn;
		boolean forever = true;

		public OutboundWorker(ClientConnection conn) {
			this.conn = conn;

			if (conn.outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			Channel ch = conn.connect();
			if (ch == null || !ch.isOpen()) {
				ClientConnection.logger
						.error("connection missing, no outbound communication");
				return;
			}

			while (true) {
				if (!forever && conn.outbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = conn.outbound.take();
					if (ch.isWritable()) {
						ClientHandler handler = conn.connect().getPipeline()
								.get(ClientHandler.class);

						if (!handler.send(msg))
							conn.outbound.putFirst(msg);

					} else
						conn.outbound.putFirst(msg);
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					ClientConnection.logger.error(
							"Unexpected communcation failure", e);
					break;
				}
			}

			if (!forever) {
				ClientConnection.logger.info("connection queue closing");
			}
		}
	}
}
