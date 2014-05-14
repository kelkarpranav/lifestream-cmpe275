package poke.ejb.server;




import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import poke.demo.Jab;
import poke.server.jpa.ImageEntry;
import poke.server.jpa.UserEntry;

@Stateless
@Remote(LifeStream.class)
public class LifeStreamEJB implements LifeStream {

	@Resource
	private SessionContext ctx;
	
	private static final long serialVersionUID = 308915085712926017L;
	@PersistenceContext(unitName = "LoggerDS")
	private EntityManager data;
	
	public boolean upload(int tag, byte[] img) {
		// TODO Auto-generated method stub
		
		
		ImageEntry im = new ImageEntry();
		im.setId(tag);
		//im.setData(tag);
		im.setData(img);
		//im.setUsername(username.toCharArray());
		
		try
		{	
			if (!data.contains(im)) {
				
			data.persist(im);
		
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean getImage(int id) {
		
		Query q2 = data.createNamedQuery("getImage");
		q2.setParameter("id", id);
		ImageEntry i = (ImageEntry) q2.getSingleResult();
		if(i!= null)
			return true;
		else
			return false;
			
	}


	@AroundInvoke
	public Object preCheck(InvocationContext ctx) throws Exception {
		System.out.println("LifeStream's Preflight check!");

		return ctx.proceed();
	}

	//@SuppressWarnings("unchecked")
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean deleteImage(int id) {
		
		try
		{
			Query q2 = data.createNamedQuery("removeImage");
			q2.setParameter("id", id);
			q2.executeUpdate();
			return true;
		}
		catch(Exception e)
		{	e.printStackTrace();
			return false;
		}
	}

	
	
	// We are using a message driven bean and assigning integers to
	// operations.
	// 1. add
	// 2. find
	// 3. delete
	
	public boolean addEntry(int id, byte[] img, int opn) {
		// verify parent before saving
		if (opn < 1 && opn > 3 )
		{
			//throw new RuntimeException("Invalid operation");
			return false;
		}
		else if (img == null)
			return false;

		// TODO validate author has permission before adding to the queue
		return sendMessage(id, img, opn);
	}

	private boolean sendMessage(int id, byte[] img, int opn) {
		QueueSession session = null;
		QueueSender sender = null;
		try {
			
			System.out.println("inside send message!! ");
			System.out.println(ctx);
			QueueConnection cnn = null;
			Queue queue = (Queue) ctx.lookup("queue/logger");

			System.out.println("after queue lookup ");
			QueueConnectionFactory factory = (QueueConnectionFactory) ctx
					.lookup("ConnectionFactory");
			cnn = factory.createQueueConnection();
			session = cnn.createQueueSession(false,
					QueueSession.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);

			System.out.println("after sender creation");
			MapMessage m = session.createMapMessage();
			m.setInt("id", id);
			m.setBytes("img", img);
			m.setInt("opn", opn);

			System.out.println("before sending message!");
			sender.send(m);
			return ImageAddMDB.checkStatus();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				sender.close();
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	// methods of user authentication
	
	public boolean addUser(String username, String password, String name, String emailid ) {
		// TODO Auto-generated method stub
		
		
		UserEntry us = new UserEntry();
		//im.setData(tag);
		us.setEmailid(emailid.toCharArray());
		us.setName(name.toCharArray());
		us.setPassword(password.toCharArray());
		us.setUsername(username.toCharArray());
		try
		{	
			if (!data.contains(us)) {
				
			data.persist(us);
		
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean checkUser(String username, String password) {
		
		Query q2 = data.createNamedQuery("checkUser");
		q2.setParameter("username", username.toCharArray());
		q2.setParameter("password", password.toCharArray());
		UserEntry i = (UserEntry) q2.getSingleResult();
		if(i!= null)
			return true;
		else
			return false;
			
	}

	//@SuppressWarnings("unchecked")
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean deleteUser(String username) {
		
		try
		{
			Query q2 = data.createNamedQuery("removeUser");
			q2.setParameter("username", username.toCharArray());
			q2.executeUpdate();
			return true;
		}
		catch(Exception e)
		{	e.printStackTrace();
			return false;
		}
	}

}
