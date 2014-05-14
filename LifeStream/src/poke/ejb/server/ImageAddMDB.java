package poke.ejb.server;


import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.print.attribute.standard.Destination;

import poke.server.jpa.ImageEntry;

/**
 * Demonstration of the JPA persistence - this EJB is the recorder of messages,
 * it uses a queue to increase client responsiveness and manage high/peak load
 * writes.
 * <p>
 * Key Points:
 * <ol>
 * <li>Message queue setup (maxSession is a JBoss specific property)
 * <li>JPA merge with OneToMany cascade property set to ALL
 * </ol>
 * Notes:
 * <ol>
 * <li>A race condition exists if a entry is deleted before it is dequeued.
 * <li>Write order is not guaranteed if maxSession is not set to 1
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/logger"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "10") })
public class ImageAddMDB implements MessageListener {
	@Resource
	private MessageDrivenContext mctx;

	@PersistenceContext(unitName = "LoggerDS")
	private EntityManager data;

	static boolean  status = false;
	/**
	 * messages forwarded to this method are assumed to be validated
	 * 
	 * @param msg
	 */
	
	public static boolean checkStatus()
	{
		return status;
	}
	
	public void onMessage(Message msg) {
		try {
			javax.jms.MapMessage om = (MapMessage) msg;

		
			ImageEntry entry = new ImageEntry();
			entry.setId(om.getInt("id"));
			entry.setData(om.getBytes("img"));


			System.out.println("onMessage() got a message ("
					+ "Performing "+om.getInt("opn")+ " operation on" + entry.getId()+ "):") ;
			
			// Performing Queries as per operations
			
			//Add
			if(om.getInt("opn") == 1)
			{

				try
				{	
					if (!data.contains(entry)) {
						
					data.persist(entry);
					status = true;
					}
				}
				catch(Exception e)
				{
					status = false;
				}
			}
			// Find
			else if(om.getInt("opn") == 2)
			{

				Query q2 = data.createNamedQuery("getImage");
				q2.setParameter("id", om.getInt("id"));
				ImageEntry i = (ImageEntry) q2.getSingleResult();
				if(i!=null)
					status = true;
				else
					status = false;
			}
			// Delete
			else if(om.getInt("opn") == 3)
			{
				try
				{
					Query q2 = data.createNamedQuery("removeImage");
					q2.setParameter("id", om.getInt("id"));
					q2.executeUpdate();
					status = true;
				}
				catch(Exception e)
				{	e.printStackTrace();
					status = false;
				}
			}

			
			// iff we want to see a delay in adding and viewing
			// Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("*************************************************************************************************************************88");
			e.printStackTrace();
		}
	}
	
}