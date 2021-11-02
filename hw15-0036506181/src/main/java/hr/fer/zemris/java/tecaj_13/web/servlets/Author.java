package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.web.servlets.utilities.GetEdit;
import hr.fer.zemris.java.tecaj_13.web.servlets.utilities.GetEntry;
import hr.fer.zemris.java.tecaj_13.web.servlets.utilities.NewEntry;
import hr.fer.zemris.java.tecaj_13.web.servlets.utilities.ServletUtilities;

/**
 * This servlet is used to list all authors blog entries and to handle the
 * rooting to other paths that are under /servleti/author/... It uses therefore
 * this classes: {@link GetEdit}, {@link GetEntry} and {@link NewEntry}.
 * 
 * @author Frano Rajič
 */
@WebServlet(urlPatterns = { "/servleti/author/*" })
public class Author extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 8170180513814741436L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] parts = ServletUtilities.getInfoPathParts(req);
		req.setAttribute("parts", parts);

		if (parts.length == 1) {
			getAuthor(req, resp, parts);
			return;
		}

		if (parts.length == 2) {
			if ("new".equals(parts[1])) {
				NewEntry.getNew(req, resp, parts);
			} else if ("edit".equals(parts[1])) {
				GetEdit.getEdit(req, resp, parts);
			} else {
				GetEntry.getEntry(req, resp, parts);
			}
			return;
		}

		req.setAttribute("message", "404 not found");
		req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String[] parts = ServletUtilities.getInfoPathParts(req);

		if (parts.length == 2 && "new".equals(parts[1])) {
			NewEntry.postNew(req, resp, parts);
			return;
		}

		if (parts.length == 2 && "edit".equals(parts[1])) {
			GetEdit.postEdit(req, resp, parts);
			return;
		}

		if (parts.length == 2 && "addcomment".equals(parts[1])) {
			GetEntry.postComment(req, resp, parts);
			return;
		}

		req.setAttribute("message", "404 not found");
		req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);

	}

	/**
	 * Help method that shows the blog entries of the author specified in the given
	 * string parts.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	private void getAuthor(HttpServletRequest req, HttpServletResponse resp, String[] parts)
			throws ServletException, IOException {
		DAO dao = DAOProvider.getDAO();
		String author = parts[0];

		if (dao.getBlogUserByNick(author) == null) {
			req.setAttribute("message", "No author with given nickname found.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		req.setAttribute("author", author);
		req.setAttribute("entries", dao.getBlogUserEntries(author));
		req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
	}

}
