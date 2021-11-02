package hr.fer.zemris.java.webserver.servlets.glasanje;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * This servlet is used to create a graph showing the votes for each band. The
 * graph drawn is a pie graph
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 9098188480512605072L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();

		Map<Integer, Integer> mapIDtoVotes = GlasanjeServletUtilities.getMapIDtoVotes(req, resp);
		Map<Integer, String> mapIDtoName = GlasanjeServletUtilities.getMapIDtoName(req);

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<Integer, Integer> e : mapIDtoVotes.entrySet()) {
			dataset.setValue(mapIDtoName.get(e.getKey()), e.getValue());
		}

		JFreeChart chart = ChartFactory.createPieChart3D(null, dataset, true, false, false);

		int width = 600;
		int height = 400;

		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);

	}

}
