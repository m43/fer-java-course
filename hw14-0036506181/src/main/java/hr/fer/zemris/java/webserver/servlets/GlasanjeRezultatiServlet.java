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
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This is a servlet that returns a generated OS image and returns as PNG
 * response
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 4797341468527983119L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long pollID;
		try {
			pollID = Long.valueOf(req.getParameter("pollID"));
		} catch (NumberFormatException | DAOException e) {
			req.setAttribute("message", "Invalid voting!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		List<PollOption> options;
		try {
			options = DAOProvider.getDao().getPollOptions(pollID);
		} catch (DAOException e) {
			req.setAttribute("message", "Error with poll.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		if (options.isEmpty()) {
			req.setAttribute("message", "No such pool.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("pollID", pollID);
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

}
