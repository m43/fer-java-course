package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet is used to show all the options of a pool that the client can
 * vote for and enables the user to select for what option to vote for.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje", urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 7452059179918405282L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<PollOption> options = null;
		Poll poll = null;
		try {
			long pollID = Long.valueOf(req.getParameter("pollID"));

			poll = DAOProvider.getDao().getPoll(pollID);
			options = DAOProvider.getDao().getPollOptions(pollID);
		} catch (NumberFormatException | DAOException e) {
			req.setAttribute("message", "Invalid pollID given.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}
		
		if(poll == null) {
			req.setAttribute("message", "No such pool.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
