package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * This servlet is used to show all polls that the client can vote for and
 * enables the user to select a pool.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "voting-index", urlPatterns = { "/servleti/index.html" })
public class VotingIndex extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 7452059179918405282L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/votingIndex.jsp").forward(req, resp);
	}

}
