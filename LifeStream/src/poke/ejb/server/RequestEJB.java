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
@Remote(Request.class)
public class RequestEJB implements Request {

	@Resource
	private SessionContext ctx;
	
	private static final long serialVersionUID = 308915085712926017L;

	@Override
	public boolean upload(int tag, byte[] im) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getImage(int tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteImage(int tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEntry(int id, byte[] img, int opn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void SignIn(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SignUp(String username, String password, String name,
			String emailid) {
		System.out.println("In Request Stateless EJB");
		Jab jab = new Jab("jab");
		jab.SignUp(username, password, name, emailid);
	}

	
	

}
