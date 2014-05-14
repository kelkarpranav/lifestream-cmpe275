package poke.ejb.client;

import poke.ejb.server.LifeStream;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.BasicConfigurator;


public class Client extends Thread {
	// private static Logger log = Logger.getLogger(Client.class);
	String endpoint = "localhost:1099";

	public Client() {
	}

	public Client(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * Retrieval using JNDI (JBoss 7)
	 * 
	 * NOTE: This method is needed when connecting to an EJB from _outside_ of
	 * the application server (e.g., jboss 7.x). If we are running from within
	 * jboss; jboss has a context that you do not need to do this (use @EJB
	 * instead)
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private LifeStream connect() throws Exception {

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

	/**
	 * Using older (JBoss 6)
	 * 
	 * @return
	 * @throws Exception
	 */
	public LifeStream connectV6() throws Exception {
		// These properties specify the implementation to use, as well as the
		// location of the server
		Properties jndi = new Properties();
		jndi.setProperty(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		jndi.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099/");
		jndi.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");

		LifeStream svr = null;
		try {
			InitialContext context = new InitialContext(jndi);

			Object ref = context.lookup("LifeStreamEJB/remote");
			svr = (LifeStream) PortableRemoteObject.narrow(ref, LifeStream.class);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return svr;
	}

	public void uploadImage() {

		try {
			// EchoPoint ept = connectV6();
			LifeStream ept = connect();
			if (ept != null) {
				
				
				String location = "San Jose";
		//		ept.upload(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();

		for (int n = 0, N = 1; n < N; n++) {
			Client clt = new Client();
			// Client clt = new Client("192.168.56.30:1099");
			// Client clt = new Client("centosvr:1099");

			clt.uploadImage();
			clt.run();
		}

	}
}