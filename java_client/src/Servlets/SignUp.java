package Servlets;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import Connections.CustomerServiceProxy;

/**
 * Servlet implementation class SignUp
 */
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// CustomerServiceProxy proxy = new CustomerServiceProxy();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUp() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		// TODO Auto-generated method stub
		// proxy.setEndpoint("http://localhost:8080/VLMSAdmin/services/CustomerService");
		response.setContentType("text/html");
		String status = " ";
		int st = 1;
		HttpSession session = request.getSession();
		response.sendRedirect("Home.jsp");
		System.out.println("In SignUp Servlet");

		String name = request.getParameter("name");
		System.out.println(name);
		String user = request.getParameter("user");
		System.out.println(user);
		String pass = request.getParameter("pass");
		System.out.println(pass);
		String email = request.getParameter("email");
		System.out.println(email);

				
			
	}

}
