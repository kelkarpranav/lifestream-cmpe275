package Servlets;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public test() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static Context initialContext;

	private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";

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
		try {

			response.setContentType("text/html");
			HttpSession session = request.getSession();

			String a = "test" + request.getParameter("submitf1");
			System.out.println("button pressed : " + a);

			String a1 = "test" + request.getParameter("submitf2");
			System.out.println("button pressed : " + a1);

			String a2 = "test" + request.getParameter("submitf3");
			System.out.println("button pressed : " + a2);

			if (a.equals("testSignout")) {
				System.out.println("In Signout loop");
				System.out.println("button pressed : " + a);
				session.invalidate();
				response.sendRedirect("SignIn.jsp");
			}

			if (a1.equals("testUpload")) {
				System.out.println("In upload loop");
				System.out.println("button pressed : " + a1);
				String fname = request.getParameter("filename");
				System.out.println(fname);
				// fname="d://test.jpg";

				File fnew = new File(fname);
				// BufferedImage bImage = ImageIO.read(fnew);

				BufferedImage originalImage = ImageIO.read(fnew);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(originalImage, "jpg", baos);
				byte[] imageInByte = baos.toByteArray();
				System.out.println(imageInByte);
				


			}

			if (a2.equals("testSearch")) {
				System.out.println("In Search loop");
				System.out.println("button pressed : " + a2);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}