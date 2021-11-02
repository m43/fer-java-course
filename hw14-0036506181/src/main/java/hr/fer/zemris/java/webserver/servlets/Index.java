package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used to redirect from home page right away to
 * /servleti/index.html
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "index", urlPatterns = { "/index.html" })
public class Index extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = -3067063050116230433L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("servleti/index.html");
	}

}
