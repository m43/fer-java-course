package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet is used to return a newly created xls file showing the votes for
 * each band.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-xls", urlPatterns = { "/servleti/glasanje-xls" })
public class GlasanjeExcel extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = -3067063050116230433L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xls\"");

		long pollID;
		try {
			pollID = Long.valueOf(req.getParameter("pollID"));
		} catch (NumberFormatException | DAOException e) {
			req.setAttribute("message", "Invalid voting!");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		List<PollOption> options;
		try {
			options = DAOProvider.getDao().getPollOptions(pollID);
		} catch (DAOException e) {
			req.setAttribute("message", "Error with poll.");
			req.getRequestDispatcher("/WEB-INF/pages/writeMessage.jsp").forward(req, resp);
			return;
		}

		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting results");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Title");
		rowhead.createCell((short) 1).setCellValue("Number of votes");

		int k = 1;
		for (PollOption po : options) {
			HSSFRow row = sheet.createRow(k++);
			row.createCell(0).setCellValue(po.getTitle());
			row.createCell(1).setCellValue(po.getNumberOfVotes());
		}

		hwb.write(resp.getOutputStream());
		hwb.close();

	}

}
