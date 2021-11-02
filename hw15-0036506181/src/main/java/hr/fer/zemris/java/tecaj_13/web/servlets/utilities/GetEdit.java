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
 * Class enables the functionality of editing an existing {@link BlogEntry} for
 * the user that owns the respective entry. It offers static methods that get
 * used by {@link Author}. These static methods provide a form to enter new blog
 * entry data, as well updating the data in the original blog entry. The static
 * methods are further described by themselves:
 * <li>{@link #getEdit(HttpServletRequest, HttpServletResponse, String[])}
 * <li>{@link #postEdit(HttpServletRequest, HttpServletResponse, String[])}
 * 
 * @author Frano Rajič
 */
public class GetEdit {

	/**
	 * Static method that shows the offers an form to edit the existing data of a
	 * blog entry. Only the owner of all users has the privilege to do so.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 *              and entry id
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	public static void getEdit(HttpServletRequest req, HttpServletResponse resp, String[] parts)
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

		Long eid;
		try {
			eid = Long.valueOf(req.getParameter("eid"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid blog entry id.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		String text = req.getParameter("text");
		if (text == null) {

		}

		BlogEntry be = dao.getBlogUserEntryById(author, eid);

		if (be == null) {
			req.setAttribute("message", "No such blog entry found");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		req.setAttribute("author", author);
		req.setAttribute("eid", eid);
		req.setAttribute("entry", be);
		req.getRequestDispatcher("/WEB-INF/pages/editentry.jsp").forward(req, resp);
	}

	/**
	 * Static method that updates the blog entry if valid data was given, or
	 * notifies the user with hint to invalid data written next to the form.
	 * 
	 * @param req   the request
	 * @param resp  the response
	 * @param parts the parts of the info path necessary to get author information
	 *              and entry id
	 * @throws ServletException thrown if an servlet error occurs
	 * @throws IOException      thrown if any IO error occurs
	 */
	public static void postEdit(HttpServletRequest req, HttpServletResponse resp, String[] parts)
			throws ServletException, IOException {
		String method = req.getParameter("method");
		Long eid;
		try {
			eid = Long.valueOf(req.getParameter("eid"));
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid blog entry id.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		if ("Cancel".equals(method)) {
			resp.sendRedirect(eid.toString());
			return;
		}

		if (!"Save".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

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

		BlogEntry be = dao.getBlogUserEntryById(author, eid);
		if (be == null) {
			req.setAttribute("message", "No such blog entry found");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		String text = req.getParameter("text");
		if (text == null) {
			req.setAttribute("message", "Invalid edit.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}
		
		if (text.isEmpty()) {
			req.setAttribute("author", author);
			req.setAttribute("eid", eid);
			req.setAttribute("entry", be);
			req.setAttribute("error", "Text cannot be empty!");
			req.getRequestDispatcher("/WEB-INF/pages/editentry.jsp").forward(req, resp);
			return;
		}

		be.setText(text);
		be.setLastModifiedAt(new Date());
		dao.saveBlogEntry(be);

		resp.sendRedirect(eid.toString());
	}

}
