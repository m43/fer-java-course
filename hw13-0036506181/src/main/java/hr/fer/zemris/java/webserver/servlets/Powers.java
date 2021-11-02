package hr.fer.zemris.java.webserver.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Servlet that creates an XLS table of powers according to parameters specified
 * in request. Parameters are: a, b and n. A and b need to be integers of
 * interval [-100, 100], whereas n must be an integer in [1,5]. The powers will
 * be calculated of range [a, b] and stored in n pages of the created excel XML
 * document, where the i-th page contains the i-th powers.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "powers", description = "create xls file with of powers of given range", urlPatterns = { "/powers" })
public class Powers extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 3813089428330438934L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int a;
		int b;
		int n;

		try {
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
			n = Integer.valueOf(req.getParameter("n"));
			if (a > b || n > 5 || n < 1 || a > 100 || b > 100 || a < -100 || b < -100) {
				req.setAttribute("message",
						"Invalid parameters given - the given ranges for all three parameters are restricted!");
				req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
				return;
			}
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid parameters given - parameters must be valid integers!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");

		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Page " + i);

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Number");
			rowhead.createCell((short) 1).setCellValue(i + "-th power");

			for (int j = a, k = 1; j <= b; j++) {
				HSSFRow row = sheet.createRow(k++);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue((long) Math.pow(j, i));
			}

		}

		hwb.write(resp.getOutputStream());
		hwb.close();
	}

}
