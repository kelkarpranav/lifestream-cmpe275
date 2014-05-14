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
package poke.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.AdaptiveReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.conf.JsonUtil;
import poke.server.conf.ServerConf;
import poke.server.conf.ServerConf.GeneralConf;
import poke.server.conf.ServerConf.ResourceConf;
import poke.server.management.ManagementDecoderPipeline;
import poke.server.management.ManagementQueue;
import poke.server.management.ServerHeartbeat;
import poke.server.resources.ResourceFactory;
import poke.server.routing.ServerDecoderPipeline;

import poke.monitor.*; // Team illuminati

/**
 * Note high surges of messages can close down the channel if the handler cannot
 * process the messages fast enough. This design supports message surges that
 * exceed the processing capacity of the server through a second thread pool
 * (per connection or per server) that performs the work. Netty's boss and
 * worker threads only processes new connections and forwarding requests.
 * <p>
 * Reference Proactor pattern for additional information.
 * 
 * @author gash
 * 
 */

public class Server {
	protected static Logger logger = LoggerFactory.getLogger("server");

	protected static final ChannelGroup allChannels = new DefaultChannelGroup(
			"server");
	protected static HashMap<Integer, Bootstrap> bootstrap = new HashMap<Integer, Bootstrap>();
	protected ChannelFactory cf, mgmtCF;
	protected static ServerConf conf; // Team illuminati
	protected ServerHeartbeat heartbeat;
	protected static HashMap<Integer, String> serviceTable = new HashMap<Integer, String>() ; // Team illuminati
	public static List<Integer> routePool; //Team illuminati
	// The route table contains hopCount, request routing id, hostname and port
	//public static ConcurrentHashMap<Integer, ServerRequestMapper> routeTable = new ConcurrentHashMap<Integer,ServerRequestMapper>(); // Team illuminati
	public static List<ServerRequestMapper>routeTable = new ArrayList<ServerRequestMapper>();
	public static List<MonitorMapper> moniteredNeighbours = new ArrayList<MonitorMapper>() ; //Team illuminati
	public static boolean isCycle = false; // Team illuminati
	/*
	 * static because we need to get a handle to the factory from the shutdown
	 * resource
	 */
	public static void shutdown() {
		try {
			ChannelGroupFuture grp = allChannels.close();
			grp.awaitUninterruptibly(5, TimeUnit.SECONDS);
			for (Bootstrap bs : bootstrap.values())
				bs.getFactory().releaseExternalResources();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("Server shutdown");
		System.exit(0);
	}

	/**
	 * initialize the server with a configuration of it's resources
	 * 
	 * @param cfg
	 */
	public Server(File cfg) {
		init(cfg);
	}

	private void init(File cfg) {
		// resource initialization - how message are processed
		BufferedInputStream br = null;
		try {
			byte[] raw = new byte[(int) cfg.length()];
			br = new BufferedInputStream(new FileInputStream(cfg));
			br.read(raw);
			conf = JsonUtil.decode(new String(raw), ServerConf.class);
			ResourceFactory.initialize(conf);
			// code added by Team illuminati
			// Populate the routing table on initialization of conf file.
			populateRoutingTable(conf.getRouting());
			populateRoutePool(conf.getServerUpNeighbours());
		} catch (Exception e) {
		}

		// communication - external (TCP) using asynchronous communication
		cf = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		// communication - internal (UDP)
		// mgmtCF = new
		// NioDatagramChannelFactory(Executors.newCachedThreadPool(),
		// 1);

		// internal using TCP - a better option
		mgmtCF = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newFixedThreadPool(8)); // Team illuminati changed pool to 4

	}
	
	// code added by Team illuminati starts
	public void populateRoutingTable(List<ResourceConf> routing)
	{	
		for(ResourceConf entry: routing)
		{
			serviceTable.put(entry.getId(), entry.getLocation());
		}
	}
	
	public static String getServerNodeId()
	{
		return conf.findServerByid(0).getProperty("node.id");
	}
	
	public void populateRoutePool(List<GeneralConf> neighbours)
	{
		routePool = new ArrayList<Integer>();
		
		for(int i=0;i<neighbours.size();i++)
		{	logger.info("Inside routed to: "+Integer.parseInt(neighbours.get(i).getProperty("port")));
			routePool.add(new Integer(Integer.parseInt(neighbours.get(i).getProperty("port"))));
			
			//populate the monitered neighbours with upstream neighbours
			moniteredNeighbours.add(new MonitorMapper("localhost",
					Integer.parseInt(neighbours.get(i).getProperty("port")),
					Integer.parseInt(neighbours.get(i).getProperty("port.mgmt")),
					neighbours.get(i).getProperty("node.id"),
					false)	
					);
		}
		
		logger.info("routepool size"+routePool.size());
	}
	
	public static int getRandomNeighbour()
	{
		return (int)(routePool.size() * Math.random());
	}
	
	// code added by Team illuminati ends
	
	// code added by Team illuminati starts
	public static ServerConf getServerConf()
	{
		return conf;
	}
	// code added by Team illuminati ends
	
	public void release() {
		if (heartbeat != null)
			heartbeat.release();
	}
	
	// code added by Team illuminati starts
	public static String getRoutingInfo(int id)
	{
		
		return serviceTable.get(id);
	}
	// code added by Team illuminati ends
	

	private void createPublicBoot(int port) {
		// construct boss and worker threads (num threads = number of cores)

		ServerBootstrap bs = new ServerBootstrap(cf);

		// Set up the pipeline factory.
		bs.setPipelineFactory(new ServerDecoderPipeline());

		// tweak for performance
		bs.setOption("child.tcpNoDelay", true);
		bs.setOption("child.keepAlive", true);
		bs.setOption("receiveBufferSizePredictorFactory",
				new AdaptiveReceiveBufferSizePredictorFactory(1024 * 2,
						1024 * 4, 1048576));

		bootstrap.put(port, bs);

		// Bind and start to accept incoming connections.
		Channel ch = bs.bind(new InetSocketAddress(port));
		allChannels.add(ch);

		// We can also accept connections from a other ports (e.g., isolate read
		// and writes)

		logger.info("Starting server, listening on port = " + port);
	}

	private void createManagementBoot(int port) {
		// construct boss and worker threads (num threads = number of cores)

		// UDP: not a good option as the message will be dropped
		// ConnectionlessBootstrap bs = new ConnectionlessBootstrap(mgmtCF);

		// TCP
		ServerBootstrap bs = new ServerBootstrap(mgmtCF);

		// Set up the pipeline factory.
		bs.setPipelineFactory(new ManagementDecoderPipeline());

		// tweak for performance
		// bs.setOption("tcpNoDelay", true);
		bs.setOption("child.tcpNoDelay", true);
		bs.setOption("child.keepAlive", true);

		bootstrap.put(port, bs);

		// Bind and start to accept incoming connections.
		Channel ch = bs.bind(new InetSocketAddress(port));
		allChannels.add(ch);

		logger.info("Starting server, listening on port = " + port);
	}

	protected void run() {
		String str = conf.findServerByid(0).getProperty("port"); // Team illuminati
		if (str == null)
			str = "5570";

		int port = Integer.parseInt(str);

		str = conf.findServerByid(0).getProperty("port.mgmt"); // Team illuminati
		int mport = Integer.parseInt(str);

		// storage initialization
		// TODO storage init

		// start communication
		createPublicBoot(port);
		createManagementBoot(mport);

		// start management
		//ManagementQueue.startup();

		// start heartbeat
		str = conf.findServerByid(0).getProperty("node.id");
		heartbeat = ServerHeartbeat.getInstance(str);
		heartbeat.start();
		logger.info("Server ready");
		
		// We have started the local node and are passing the
		// config file to the static method of the HeartMonitor
		// class to start the monitors to monitor the servers
		// in the network topology
		
		
		// code by Team illuminati starts
		// This static method is added to be accessible from server classs
		// This method basically extracts the list of upstream and downstream
		// neighbours from the ServerConf class and starts a monitor each for 
		// a single server in the topology to monitor its heartbeat
		// getServerUpNeighbours is the method that gives the neighbours in the
		// upstream topology and getServerDownNeighbours is the method that gives
		// the neighbours in the downstream topology.
		
		// Lets try to understand upstream and downstream topology
		// Upstream topology are the group of servers to which a server
		// can forward its requests. Client data always flows in the direction
		// of upstream servers.
		
		// DownStream servers are the ones which listen to data from other servers
		// but cannot forward their requests to those servers. Such a mechanism
		// has been implemented to construct a directed graph to ease the routing
		// process so that we do not end up in cycles when we route the requests.
		
		// A server will always start monitors for his upstream servers
		// If the servers are unavialable at that moment then a management connection
		// is not formed. So we have two approaches:
		// 1.continously keep on polling till the upstream server comes up.
		// 2. When the upstream server comes up it sends an announce message
		//	  downstream. The downstream server listens to the announce message
		//    and spawns a nodejoin connection with it.
		//
		// The second approach is more desirable as we dont need to maintain a thread
		// for polling and also it is more event based.
		
		// It also effectively implements the circuit breaker pattern.
		
			// Team illuminati
		
		logger.info("Trying to setup connection with UpStream neighbouring nodes");
		
		List<GeneralConf> upNeighbours = conf.getServerUpNeighbours();
		
		
		for(int i =0;i<upNeighbours.size();i++)
		{	System.out.println(upNeighbours.get(i).getProperty("port.mgmt"));
			try
			{
				(new HeartMonitor("localhost", 
					Integer.parseInt(upNeighbours.get(i).getProperty("port.mgmt")),
					upNeighbours.get(i).getProperty("node.id"))).NodeJoin();
			}
			catch(Exception e)
			{
				System.out.println("Server "+ upNeighbours.get(i).
						getProperty("node.id")+" not available yet" );
			}
	
		}
		
		
		logger.info("Trying to setup connection with DownStream neighbouring nodes");
		
		List<GeneralConf> downNeighbours = conf.getServerDownNeighbours();
		
		for(int i =0;i<downNeighbours.size();i++)
		{	System.out.println(downNeighbours.get(i).getProperty("port.mgmt"));
			try
			{
				(new HeartMonitor("localhost", 
					Integer.parseInt(downNeighbours.get(i).getProperty("port.mgmt")),
					downNeighbours.get(i).getProperty("node.id"))).Announce();
			}
			catch(Exception e)
			{
				System.out.println("Server "+ downNeighbours.get(i).
						getProperty("node.id")+" not available yet" );
			}
	
		}
				
		// code added by Team illuminati ends
		
	}
	
	
	public static void RetryMonitor()
	{	
		Iterator<MonitorMapper> it = moniteredNeighbours.iterator();
		while(it.hasNext())
		{	MonitorMapper hmO = it.next();
			try
			{	if(!hmO.isAlive())
				{
					(new HeartMonitor("localhost",hmO.getMgmtport(),
						hmO.getNodeId())).NodeJoin();
				}
			}
			catch(Exception e)
			{
				System.out.println("Server "+hmO.getNodeId()+" on port "+hmO.getPort()+
						" not available yet");
			}
		}
	}
	
	// methods added by Team illuminati start
	public static void updateMoniteredServerStatus(String nodeId,long timeref)
	{
		Iterator<MonitorMapper> it = moniteredNeighbours.iterator();
		while(it.hasNext())
		{	MonitorMapper hmO = it.next();
				
			if(hmO.getNodeId().equals(nodeId))
			{	if(timeref < 10)
					hmO.setAlive(true);
				else
					hmO.setAlive(false);
			}
		}
	}
	
	public static void setMoniteredServerStatus(int portno)
	{
		Iterator<MonitorMapper> it = moniteredNeighbours.iterator();
		while(it.hasNext())
		{	MonitorMapper hmO = it.next();
				
			if(hmO.getMgmtport() == portno)
			{
				hmO.setAlive(false);
			}
		}
	}
	
	public static boolean checkMoniteredServerStatus(String host, int port)
	{	boolean status = false;
		Iterator<MonitorMapper> it = moniteredNeighbours.iterator();
		while(it.hasNext())
		{	MonitorMapper hmO = it.next();
				
			if(hmO.getHost().equals(host) && hmO.getPort() == port )
			{	
				status =  hmO.isAlive();
			}
		}
		
			return status;
		
	}
	// methods added by Team illuminati ends
	
	// code added by Team illuminati starts
	// This class maps port, host and request id into a single block
	// This is put as value for hop count key in the route table hash map
	
	public static final class ServerRequestMapper
	{
		private String host;
		private int port;
		private String location;
		private int hopCount;
		
		public ServerRequestMapper(String host, int port , String location,
				int hopCount)
		{
			this.host = host;
			this.port = port;
			this.location = location;
			this.hopCount = hopCount;
		}
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		
		public int getHopCount() {
			return hopCount;
		}
		public void setHopCount(int hopCount) {
			this.hopCount = hopCount;
		}
		
		public String getLocation()
		{
			return location;
		}
		
		public void setLocation(String location)
		{
			this.setLocation(location);
		}
	}
	
	public static final class MonitorMapper
	{	
		private String host;
		private int port;
		private String nodeId;
		private int mgmtport;
		private int routingId;
		private boolean alive;
		
		
		MonitorMapper(String host, int port, int mgmtport, 
				String nodeId, boolean alive)
		{
			this.host = host;
			this.port = port;
			this.mgmtport = mgmtport;
			this.nodeId = nodeId;
			this.alive = alive;
		
		}

	// Team illuminati
		public String getHost() {
			return host;
		}


		public void setHost(String host) {
			this.host = host;
		}


		public int getPort() {
			return port;
		}


		public void setPort(int port) {
			this.port = port;
		}


		public String getNodeId() {
			return nodeId;
		}


		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}


		public int getMgmtport() {
			return mgmtport;
		}


		public void setMgmtport(int mgmtport) {
			this.mgmtport = mgmtport;
		}


		public boolean isAlive() {
			return alive;
		}


		public void setAlive(boolean val) {
				this.alive = val;
		}
		
		
	}
	
	
	
	
	// code added by Team illuminati ends
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java "
					+ Server.class.getClass().getName() + " conf-file");
			System.exit(1);
		}

		File cfg = new File(args[0]);
		if (!cfg.exists()) {
			Server.logger.error("configuration file does not exist: " + cfg);
			System.exit(2);
		}

		Server svr = new Server(cfg);
		svr.run();
	}
}
