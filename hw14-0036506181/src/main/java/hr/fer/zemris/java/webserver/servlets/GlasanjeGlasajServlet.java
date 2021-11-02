package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * This is a servlet that takes a vote of a pool as parameter and updates the
 * results accordingly or prints an error message if invalid parameter value
 * given.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-glasaj", urlPatterns = { "/servleti/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 1869929073889106142L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long id;
		long pollID;

		try {
			id = Long.valueOf(req.getParameter("id"));
			pollID = Long.valueOf(req.getParameter("pollID"));
			DAOProvider.getDao().addVote(pollID, id);
		} catch (NumberFormatException | DAOException e) {
			req.setAttribute("message", "Invalid voting!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
