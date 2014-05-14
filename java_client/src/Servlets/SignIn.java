package Servlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	int st = 1;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String status = " ";
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		System.out.println("In SignIn servlets");
		response.sendRedirect("Home.jsp");
		
		
				final Hashtable jndiProperties = new Hashtable();
				jndiProperties.put("java.naming.factory.initial",
						"org.jboss.naming.remote.client.InitialContextFactory");
				jndiProperties.put(InitialContext.PROVIDER_URL,
						"remote://localhost:4447");
				jndiProperties.put("jboss.naming.client.ejb.context", true);
				jndiProperties.put(Context.URL_PKG_PREFIXES,
						"org.jboss.ejb.client.naming");
				
				jndiProperties.put(Context.SECURITY_PRINCIPAL, "abhi");
				jndiProperties.put(Context.SECURITY_CREDENTIALS, "abhiuser");

				Request svr = null;
				try {
					final Context context = new InitialContext(jndiProperties);

					StringBuilder sb = new StringBuilder();
					sb.append("ejb:");
					sb.append("").append("/");
					sb.append("request-svr").append("/");
					sb.append("").append("/");
					sb.append("RequestEJB").append("!");
					sb.append(Object.class.getName());
					String addr = sb.toString();

					System.out.println("---> looking up: " + addr);
					svr = (Request) context.lookup(addr);
				} catch (NamingException e) {
					e.printStackTrace();
				}
				System.out.println("Before calling signup");
				svr.SignUp("abc","123","name","email");
		
	}

}
