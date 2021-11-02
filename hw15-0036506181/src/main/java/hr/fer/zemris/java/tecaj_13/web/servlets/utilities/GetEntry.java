package hr.fer.zemris.java.tecaj_13.web.servlets.utilities;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.Author;

/**
 * Class enables the functionality of showing a {@link BlogEntry}. It offers
 * static methods that get used by {@link Author}. These static methods can
 * display an entry, its comments and enable to add further comments. The static
 * methods are further described here:
 * <li>{@link #getEntry(HttpServletRequest, HttpServletResponse, String[])}
 * <li>{@link #postComment(HttpServletRequest, HttpServletResponse, String[])}
 * 
 * @author Frano Rajič
 */
public class GetEntry {

	/**
	 * Static method that shows the entry title and text, enables the supervised to
	 * head to the entry editing link, displays comments and offers an form to
	 * submit new comments. New comments can sumbit everyone, but if it is an
	 * unregistered user, he chooses between giving an email or staying anonymous.
	 * Registered users (if logged in successfully) do not need to enter the email
	 * because it gets automatically fetched for them.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 *              and entry id
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	public static void getEntry(HttpServletRequest req, HttpServletResponse resp, String[] parts)
			throws ServletException, IOException {
		DAO dao = DAOProvider.getDAO();

		String author = parts[0];

		if (dao.getBlogUserByNick(author) == null) {
			req.setAttribute("message", "No author with given nickname found.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		Long eid;
		try {
			eid = Long.valueOf(parts[1]);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid blog entry id.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		BlogEntry be = dao.getBlogUserEntryById(author, eid);

		if (be == null) {
			req.setAttribute("message", "No such blog entry found");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		List<BlogComment> comments = dao.getBlogEntryComments(eid);

		req.setAttribute("comments", comments);
		req.setAttribute("author", author);
		req.setAttribute("eid", eid);
		req.setAttribute("entry", be);
		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
	}

	/**
	 * Static method updates the entry with a new comment if the comment form was
	 * filled up correctly or shows an respective message under the form as a hint
	 * in order to fix the invalid data.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 *              and entry id
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	public static void postComment(HttpServletRequest req, HttpServletResponse resp, String[] parts)
			throws ServletException, IOException {
		DAO dao = DAOProvider.getDAO();

		String author = parts[0];

		if (dao.getBlogUserByNick(author) == null) {
			req.setAttribute("message", "No author with given nickname found.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		Long eid;
		try {
			eid = Long.valueOf(req.getParameter("eid"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid blog entry id.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		boolean valid = true;
		String email = req.getParameter("email");
		BlogUser user = (BlogUser) req.getSession().getAttribute("user");
		if (user != null) {
			email = user.getEmail();
		} else if ("".equals(email)) {
			email = "Anonymous";
		} else if (email == null || ServletUtilities.isEmailInvalid(email) || email.length() > 100) {
			req.setAttribute("emailError", "Invalid email entered");
			valid = false;
		}

		String message = req.getParameter("message");
		if ("".equals(message)) {
			req.setAttribute("messageError", "Message must containt some text!");
			valid = false;
		}

		List<BlogComment> comments = dao.getBlogEntryComments(eid);

		BlogEntry be = dao.getBlogUserEntryById(author, eid);
		if (be == null) {
			req.setAttribute("message", "No such blog entry found");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		if (!valid) {
			req.setAttribute("comments", comments);
			req.setAttribute("author", author);
			req.setAttribute("eid", eid);
			req.setAttribute("entry", be);
			req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
			return;
		}

		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEmail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(be);

		dao.saveBlogComment(blogComment);

		resp.sendRedirect(eid.toString());
	}

}
