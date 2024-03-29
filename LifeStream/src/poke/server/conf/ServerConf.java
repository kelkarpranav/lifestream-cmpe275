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
package poke.server.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Routing information for the server - internal use only
 * 
 * TODO refactor StorageEntry to be neutral for cache, file, and db
 * 
 * @author gash
 * 
 */
@XmlRootElement(name = "conf")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServerConf {
	private List<GeneralConf> server;
	private List<ResourceConf> routing;

	private volatile HashMap<Integer, ResourceConf> idToRsc;

	private HashMap<Integer, ResourceConf> asMap() {
		if (idToRsc != null)
			return idToRsc;

		if (idToRsc == null) {
			synchronized (this) {
				if (idToRsc == null) {
					idToRsc = new HashMap<Integer, ResourceConf>();
					if (routing != null) {
						for (ResourceConf entry : routing) {
							System.out.println("In routing map");
							idToRsc.put(entry.id, entry);
						}
					}
				}
			}
		}

		return idToRsc;
	}
	
	// code added by Team illuminati starts
	// WE are maintaining a hashmap which has the ids of all the servers and
	// its neighbours.
	private volatile HashMap<Integer, GeneralConf> idToTM;

	private HashMap<Integer, GeneralConf> asTMMap() {
		if (idToTM != null)
			return idToTM;

		if (idToTM == null) {
			synchronized (this) {
				if (idToTM == null) {
					idToTM = new HashMap<Integer, GeneralConf>();
					if (server != null) {
						int cnt =0;
						for (GeneralConf entry : server) {
							System.out.println("In topology map");
							idToTM.put(new Integer(cnt++), entry);
						}
					}
				}
			}
		}

		return idToTM;
	}
	
	
	public void addGeneral(GeneralConf entry) {
		if (entry == null)
			return;
		else if(server == null)
			server = new ArrayList<GeneralConf>();

		server.add(entry);
	}

	public List<GeneralConf> getServer() {
		return server;
	}
	
	// We have structured the config file in such a way that the
	// first element in the GeneralConf list will always be the
	// server node itself and the remaining elements in the list will
	// the servers in the topology
	// Thus we remove the first element that is the local node
	// and return the neighbours in the topology
	public List<GeneralConf> getServerUpNeighbours() {
		List<GeneralConf> neighbour = new ArrayList<GeneralConf>();
		neighbour.addAll(server);
		neighbour.remove(0);
		
		// We remove those servers from the list of neighbours
		// who have dataflow in the downward direction
			// Team illuminati
		
		Iterator<GeneralConf> it = neighbour.iterator();
		while(it.hasNext())
		{ GeneralConf ser = it.next();
			if(ser.getProperty("dataflow").equals("down"))
			{	
				it.remove();
			}
		}
		return neighbour;
	}
	
	public List<GeneralConf> getServerDownNeighbours() {
		List<GeneralConf> neighbour = new ArrayList<GeneralConf>();
		neighbour.addAll(server);
		neighbour.remove(0);
		
		// We remove those servers from the list of neighbours
		// who have dataflow in the downward direction
		// Team illuminati
		
		Iterator<GeneralConf> it = neighbour.iterator();
		while(it.hasNext())
		{ GeneralConf ser = it.next();
			if(ser.getProperty("dataflow").equals("up"))
			{	
				it.remove();
			}
		}
		return neighbour;
	}

	public void setServer(List<GeneralConf> server) {
		this.server = server;
	}
	
	public GeneralConf findServerByid(int id)
	{
		return asTMMap().get(id);
	}

	//  code added by Team illuminati ends
	public void addResource(ResourceConf entry) {
		if (entry == null)
			return;
		else if (routing == null)
			routing = new ArrayList<ResourceConf>();

		routing.add(entry);
	}

	public ResourceConf findById(int id) {
		return asMap().get(id);
	}

	public List<ResourceConf> getRouting() {
		return routing;
	}

	public void setRouting(List<ResourceConf> conf) {
		this.routing = conf;
	}

	/**
	 * storage setup and configuration
	 * 
	 * @author gash1
	 * 
	 */
	@XmlRootElement(name = "general")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class GeneralConf {
		private TreeMap<String, String> general;

		public String getProperty(String name) {
			return general.get(name);
		}

		public void add(String name, String value) {
			if (name == null)
				return;
			else if (general == null)
				general = new TreeMap<String, String>();

			general.put(name, value);
		}

		public TreeMap<String, String> getGeneral() {
			return general;
		}

		public void setGeneral(TreeMap<String, String> general) {
			this.general = general;
		}
	}

	/**
	 * command (request) delegation
	 * 
	 * @author gash1
	 * 
	 */
	@XmlRootElement(name = "entry")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static final class ResourceConf {
		private int id;
		private String name;
		private String clazz;
		private boolean enabled;
		private String location;

		public ResourceConf() {
		}

		public ResourceConf(int id, String name, String clazz) {
			this.id = id;
			this.name = name;
			this.clazz = clazz;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getClazz() {
			return clazz;
		}

		public void setClazz(String clazz) {
			this.clazz = clazz;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
 //code by Team illuminati starts
		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
 //code by team illuminati ends
		
	}
}
