package hr.fer.zemris.java.tecaj_13.web.servlets.utilities;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.Author;

/**
 * Class enables the functionality of adding a new {@link BlogEntry}. It offers
 * static methods that get used by {@link Author}.
 * 
 * @author Frano Rajič
 */
public class NewEntry {

	/**
	 * Static method that builds the necessary form for the user to submit a new
	 * blog entry if the user has the privilege to do so.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	public static void getNew(HttpServletRequest req, HttpServletResponse resp, String[] parts)
			throws ServletException, IOException {
		DAO dao = DAOProvider.getDAO();
		String author = parts[0];

		if (dao.getBlogUserByNick(author) == null) {
			req.setAttribute("message", "No author with given nickname found.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		BlogUser user = (BlogUser) req.getSession().getAttribute("user");
		if (user == null || !author.equals(user.getNick())) {
			req.setAttribute("message", "Forbidden!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		req.setAttribute("author", author);
		req.getRequestDispatcher("/WEB-INF/pages/newentry.jsp").forward(req, resp);
	}

	/**
	 * Static method that reads the form data and tries to create a new form if all
	 * data is valid and the user has the privilege to do so blog entry.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	public static void postNew(HttpServletRequest req, HttpServletResponse resp, String[] parts)
			throws ServletException, IOException {
		String method = req.getParameter("method");
		String author = parts[0];

		if ("Cancel".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + author);
			return;
		}

		if (!"Save".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		DAO dao = DAOProvider.getDAO();
		if (dao.getBlogUserByNick(author) == null) {
			req.setAttribute("message", "No author with given nickname found.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		BlogUser user = (BlogUser) req.getSession().getAttribute("user");
		if (user == null || !author.equals(user.getNick())) {
			req.setAttribute("message", "Forbidden!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		String title = req.getParameter("title");
		String text = req.getParameter("text");

		if (title == null || text == null || title.isEmpty() || text.isEmpty()) {
			req.setAttribute("error", "Entered data is of invalid format.");
			req.getRequestDispatcher("/WEB-INF/pages/newentry.jsp").forward(req, resp);
			return;
		}

		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		blogEntry.setCreator(user);

		dao.saveBlogEntry(blogEntry);

		resp.sendRedirect(blogEntry.getId().toString());
	}

}
