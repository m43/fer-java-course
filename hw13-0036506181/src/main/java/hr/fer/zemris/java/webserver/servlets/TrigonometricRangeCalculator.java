package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to calculate all sin(x) and cos(x) values of range specified by
 * request context parameters a and b and call respective rendering JSP for
 * showing results in a table.
 * 
 * @author Frano Rajič
 */
@WebServlet("/trigonometric")
public class TrigonometricRangeCalculator extends HttpServlet {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = 6401432720984841210L;

	/**
	 * The default valur used for a if a has not been given as valid parameter
	 */
	private static final int DEFAULT_A = 0;

	/**
	 * The default value used for b if b was not specified or is invalid
	 */
	private static final int DEFAULT_B = 360;

	/**
	 * How much can the upper limit atmost be greater than lower limit
	 */
	private static final int LIMIT = 2 * 360;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = getIntegerOfString(req.getParameter("a"));
		a = a == null ? DEFAULT_A : a;

		Integer b = getIntegerOfString(req.getParameter("b"));
		b = b == null ? DEFAULT_B : b;

		if (a > b) {
			int c = a;
			a = b;
			b = c;
		}

		if (b > a + LIMIT) {
			b = a + LIMIT;
		}

		req.setAttribute("a", a);
		req.setAttribute("b", b);

		List<double[]> data = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			data.add(new double[] { i, Math.sin(i * Math.PI / 180), Math.cos(i * Math.PI / 180) });
		}

		req.setAttribute("data", data);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Help method to get the numeric integer value of a string.
	 * 
	 * @param s the string to parse
	 * @return the parsed integer or null if unparseable
	 */
	private Integer getIntegerOfString(String s) {
		if (s == null) {
			return null;
		}

		Integer i = null;
		try {
			i = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			// ignoreable
		}

		return i;
	}

}
