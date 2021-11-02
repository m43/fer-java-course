package hr.fer.zemris.java.webserver.servlets.glasanje;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet is used to return a newly created xls file showing the votes for
 * each band.
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-xls", urlPatterns = { "/glasanje-xls" })
public class GlasanjeExcel extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = -3067063050116230433L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"votes.xls\"");

		HSSFWorkbook hwb = new HSSFWorkbook();

		Map<Integer, Integer> mapIDtoVotes = GlasanjeServletUtilities.getMapIDtoVotes(req, resp);
		Map<Integer, String> mapIDtoName = GlasanjeServletUtilities.getMapIDtoName(req);

		HSSFSheet sheet = hwb.createSheet("Voting results");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Band name");
		rowhead.createCell((short) 1).setCellValue("Number of votes");

		int k = 1;
		for (Integer i : mapIDtoVotes.keySet()) {
			HSSFRow row = sheet.createRow(k++);
			row.createCell(0).setCellValue(mapIDtoName.get(i));
			row.createCell(1).setCellValue(mapIDtoVotes.get(i));
		}

		hwb.write(resp.getOutputStream());
		hwb.close();

	}

}
