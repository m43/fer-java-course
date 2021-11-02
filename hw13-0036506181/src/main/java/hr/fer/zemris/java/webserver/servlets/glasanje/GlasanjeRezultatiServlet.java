package hr.fer.zemris.java.webserver.servlets.glasanje;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a servlet that returns a generated OS image and returns as PNG
 * response
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 4797341468527983119L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Integer, String> mapIDtoName = GlasanjeServletUtilities.getMapIDtoName(req);
		Map<Integer, String> mapIDtoLink = GlasanjeServletUtilities.getMapIDtoLink(req);
		Map<Integer, Integer> mapIDtoVotesSorted = GlasanjeServletUtilities.getMapIDtoVotesSortedByVotes(req, resp);

		req.setAttribute("mapIDtoName", mapIDtoName);
		req.setAttribute("mapIDtoLink", mapIDtoLink);
		req.setAttribute("mapIDtoVotes", mapIDtoVotesSorted);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		
	}

}
