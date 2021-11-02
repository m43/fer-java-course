package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This servlet is used to create a graph showing the votes for each option of a
 * specified pool. The graph drawn is a pie graph
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = { "/servleti/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 9098188480512605072L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();

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

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (PollOption po : options) {
			dataset.setValue(po.getTitle(), po.getNumberOfVotes());
		}

		JFreeChart chart = ChartFactory.createPieChart3D(null, dataset, true, false, false);

		int width = 600;
		int height = 400;

		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);

	}

}
