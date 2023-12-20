package org.simple.servlet;

// Begin imports

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simple.access.SimpleHttp;

// End imports

public class RootServlet extends AbstractSimpleServlet {


	// Begin declarations

	private static final long serialVersionUID = 1L;

	// End declarations

	public RootServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin HTML 

		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();
		 
		writer.println("<html>");

		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/simple/simple.css\">");
		writer.println("</head>");
		

		writer.println("<h1>Servlet root</h1>");
		writer.println("<p>"+SimpleHttp.common.getResourcesDir());

		writer.println("</html>");

		resp.setStatus(HttpServletResponse.SC_OK);

		// End HTML 

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Begin POST logic 

		super.doPost(req, resp);

		// End POST logic 

	}

	// Begin custom methods
	
	
	// End custom methods

	
}
