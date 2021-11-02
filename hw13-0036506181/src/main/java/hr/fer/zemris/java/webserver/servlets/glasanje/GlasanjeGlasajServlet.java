package hr.fer.zemris.java.webserver.servlets.glasanje;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a servlet that takes a vote as parameter and updates the results
 * accordingly or prints an error message if invalid parameter value given.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-glasaj", urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 1869929073889106142L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Integer, Integer> mapIDtoVotes = GlasanjeServletUtilities.getVotesAfterUpdating(req, resp);
		if (mapIDtoVotes == null) {
			req.setAttribute("message", "Invalid voting!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
