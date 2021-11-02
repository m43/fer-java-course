package hr.fer.zemris.java.webserver.servlets.glasanje;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used to show all bands that the client can vote for and
 * enables the user to select for what team to vote for.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 7452059179918405282L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<Integer, String> mapIDtoName = GlasanjeServletUtilities.getMapIDtoName(req);

		req.setAttribute("mapIDtoName", mapIDtoName);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
