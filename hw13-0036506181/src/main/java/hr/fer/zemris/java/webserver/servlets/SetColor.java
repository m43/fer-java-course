package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to set the color given by parameter as default page background
 * color. That it returns to index page.
 * 
 * @author Frano Rajič
 */
public class SetColor extends HttpServlet {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = 6401432720984841210L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");

		if (color != null && (color.length() != 3 || color.length() != 6)) {
			boolean isHex = color.matches("^[0-9a-fA-F]+$");
			if (isHex) {
				req.getSession().setAttribute("pickedBgCol", color);
			}
		}

		resp.sendRedirect("index.jsp");
	}

}
