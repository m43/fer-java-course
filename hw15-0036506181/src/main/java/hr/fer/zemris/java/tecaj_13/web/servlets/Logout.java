package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used to logout user and redirect to main blog page
 * 
 * @author Frano Rajič
 */
@WebServlet("/servleti/logout")
public class Logout extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 8170180513814741436L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
