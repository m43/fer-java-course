package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.forms.BlogUserForm;

/**
 * Class models an servlet that handles user registration.
 * 
 * @author Frano Rajič
 */
@WebServlet("/servleti/register")
public class Register extends HttpServlet {

	/**
	 * serialisation
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getParameter("form") == null) {
			BlogUserForm f = new BlogUserForm();
			req.setAttribute("form", f);
		}

		req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");
		if (!"Save".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}

		BlogUserForm f = new BlogUserForm();
		f.loadFromHttpRequest(req);
		f.validate(DAOProvider.getDAO());

		if (f.hasInconsistencies()) {
			req.setAttribute("form", f);
			req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp);
			return;
		}

		BlogUser bu = f.createBlogUser();
		DAOProvider.getDAO().saveBlogUser(bu);
		
		req.getSession().setAttribute("user", bu);
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}