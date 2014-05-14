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
package poke.server.storage.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.ejb.server.LifeStream;
import poke.server.jpa.ImageEntry;
import poke.server.storage.Storage;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.util.*;
import javax.naming.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.*;


import eye.Comm.Document;
import eye.Comm.NameSpace;

public class DatabaseStorage implements Storage {
	protected static Logger logger = LoggerFactory.getLogger("database");

	public static final String sDriver = "jdbc.driver";
	public static final String sUrl = "jdbc.url";
	public static final String sUser = "jdbc.user";
	public static final String sPass = "jdbc.password";

	protected Properties cfg;
	protected BoneCP cpool;	
	//Code by Team illuminati Starts
	@PersistenceContext(unitName = "LoggerDS")
	private EntityManager data;
	
	public DatabaseStorage() {
	}

	public DatabaseStorage(Properties cfg) {
		init(cfg);
	}

	@Override
	public void init(Properties cfg) {
		if (cpool != null)
			return;

		this.cfg = cfg;

		try {
			Class.forName(cfg.getProperty(sDriver));
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(cfg.getProperty(sUrl));
			config.setUsername(cfg.getProperty(sUser, "sa"));
			config.setPassword(cfg.getProperty(sPass, ""));
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);

			cpool = new BoneCP(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gash.jdbc.repo.Repository#release()
	 */
	@Override
	public void release() {
		if (cpool == null)
			return;

		cpool.shutdown();
		cpool = null;
	}

	@Override
	public NameSpace getNameSpaceInfo(long spaceId) {
		NameSpace space = null;

		Connection conn = null;
		try {
			conn = cpool.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			// TODO complete code to retrieve through JDBC/SQL
			// select * from space where id = spaceId
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("failed/exception on looking up space " + spaceId, ex);
			try {
				conn.rollback();
			} catch (SQLException e) {
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return space;
	}

	@Override
	public List<NameSpace> findNameSpaces(NameSpace criteria) {
		List<NameSpace> list = null;

		Connection conn = null;
		try {
			conn = cpool.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			// TODO complete code to search through JDBC/SQL
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("failed/exception on find", ex);
			try {
				conn.rollback();
			} catch (SQLException e) {
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public NameSpace createNameSpace(NameSpace space) {
		if (space == null)
			return space;

		Connection conn = null;
		try {
			conn = cpool.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			// TODO complete code to use JDBC
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("failed/exception on creating space " + space, ex);
			try {
				conn.rollback();
			} catch (SQLException e) {
			}

			// indicate failure
			return null;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return space;
	}

	@Override
	public boolean removeNameSpace(long spaceId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addDocument(String namespace, Document doc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDocument(String namespace, long docId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateDocument(String namespace, Document doc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Document> findDocuments(String namespace, Document criteria) {
		// TODO Auto-generated method stub
		return null;
	}
//Code by Team illuminati Starts
	@Override
	public boolean addImage(int tag, byte[] img) {
		// TODO Auto-generated method stub
		
		try {
			// EchoPoint ept = connectV6();
			LifeStream ept = connect();
			if (ept != null) {
				return ept.upload(tag,img);
				//return ept.addEntry(tag, img, 1);
			}
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean removeImage(int tag) {
		// TODO Auto-generated method stub
		

		try {
	
			LifeStream ept = connect();
			if (ept != null) {
				return	ept.deleteImage(tag);
				//byte[] img = {0};
				//return ept.addEntry(tag, img, 3);	
			}
			else
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean updateImage(int tag, byte[] img) {
		// TODO Auto-generated method stub
	
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private LifeStream connect() throws Exception {

			// Team illuminati
		// no jndi.properites file, instead we explicitly define what we need
		// (for demonstration)
		@SuppressWarnings("rawtypes")
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put("java.naming.factory.initial",
				"org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(InitialContext.PROVIDER_URL,
				"remote://localhost:4447");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");

		/**
		 * YOU MUST: add a user for remote access. Use
		 * $JBOSS_HOME/bin/add-user.sh
		 */
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "cmpe275");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "cmpe275user");
		
		//jndiProperties.put(Context.SECURITY_PRINCIPAL, "jboss");
		//jndiProperties.put(Context.SECURITY_CREDENTIALS, "abhijeet");
		
		LifeStream svr = null;
		try {
			final Context context = new InitialContext(jndiProperties);

			StringBuilder sb = new StringBuilder();

			// JNDI lookup
			sb.append("ejb:");

			// The app name is the application name of the deployed EJBs. This
			// is typically the ear name without the .ear suffix. However, the
			// application name could be overridden in the application.xml of
			// the EJB deployment on the server. Since we haven't deployed the
			// application as a .ear, the app name for us will be an empty
			// string

			// application name
			sb.append("").append("/");

			// This is the module name of the deployed EJBs on the server. This
			// is typically the jar name of the EJB deployment, without the .jar
			// suffix, but can be overridden via the ejb-jar.xml In this
			// example, we have deployed the EJBs in a
			// jboss-as-ejb-remote-app.jar, so the module name is
			// jboss-as-ejb-remote-app

			// module name
			sb.append("lifestream-svr").append("/");

			// AS7 allows each deployment to have an (optional) distinct name.
			// We haven't specified a distinct name for our EJB deployment, so
			// this is an empty string

			// distinct name
			sb.append("").append("/");

			// We will generally only want the name as a string - we should not
			// require the implementation class as it would be pointless to
			// require the deployment of the implementation yet invoke it
			// remotely

			// bean name
			sb.append("LifeStreamEJB").append("!");

			// fully specified (pkg+class) to the remote interface
			sb.append(LifeStream.class.getName());

			String addr = sb.toString();

			System.out.println("---> looking up: " + addr);
			svr = (LifeStream) context.lookup(addr);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return svr;
	}

	@Override
	public boolean getImage(int tag) {
		// TODO Auto-generated method stub

		try {
	
			LifeStream ept = connect();
			if (ept != null) {
			   return	ept.getImage(tag);
				//byte[] img = {0};
				//return ept.addEntry(tag, img, 2);		
			}
			else
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean addUser(String username, String password, String name,
			String emailid) {
		// TODO Auto-generated method stub
		
		try {
			// EchoPoint ept = connectV6();
			LifeStream ept = connect();
			if (ept != null) {
				return ept.addUser(username, password, name, emailid);
				//return ept.addEntry(tag, img, 1);
			}
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkUser(String username, String password) {
		// TODO Auto-generated method stub
	
		try {
			
			LifeStream ept = connect();
			if (ept != null) {
			   return	ept.checkUser(username, password);
				//byte[] img = {0};
				//return ept.addEntry(tag, img, 2);		
			}
			else
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		

		try {
	
			LifeStream ept = connect();
			if (ept != null) {
				return	ept.deleteUser(username);
				//byte[] img = {0};
				//return ept.addEntry(tag, img, 3);	
			}
			else
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
//Code by Team illuminati Ends
}
