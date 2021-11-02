package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.utilities.ServletUtilities;

/**
 * The main home page of the blog app. Enables the user to head to login and
 * register page and shows registered all registered users (aka authors)
 * 
 * @author Frano Rajič
 */
@WebServlet("/servleti/main")
public class Main extends HttpServlet {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<BlogUser> authors = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("authors", authors);

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");
		if (!"Login".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		List<BlogUser> authors = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("authors", authors);

		String nick = req.getParameter("nick");
		req.setAttribute("nick", nick);

		String password = req.getParameter("password");
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(nick);

		if (user == null) {
			req.setAttribute("errorMessage", "Invalid, try again");
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		try {
			if (!user.getPasswordHash().equals(ServletUtilities.generateSHA(password))) {
				req.setAttribute("errorMessage", "Invalid, try again");
				req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
				return;
			}
		} catch (NoSuchAlgorithmException error) {
			req.setAttribute("errorMessage", "Invalid, try again.");
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("user", user);

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}