package hr.fer.zemris.java.webserver.servlets;

import java.io.IOException;
import java.io.OutputStream;

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
 * This is a servlet that returns a generated OS image and returns as PNG
 * response
 * 
 * @author Frano Rajič
 */
@WebServlet(name = "osUsage", description = "get the os usage pie chart", urlPatterns = { "/reportImage" })
public class ReportImage extends HttpServlet {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = -3383660772652439852L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("image/png");

		OutputStream outputStream = response.getOutputStream();

		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);

	}

	/**
	 * Help method to create and return a chart of OS usage with data from may 2019.
	 * 
	 * @return the created chart
	 */
	public JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		// may 2019 data
		dataset.setValue("Windows", 78.94);
		dataset.setValue("OS X", 13.89);
		dataset.setValue("Unknown", 4.17);
		dataset.setValue("Linux", 1.56);
		dataset.setValue("Chrome OS", 1.43);
		dataset.setValue("Other", 0.01);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;
		JFreeChart chart = ChartFactory.createPieChart3D(null, dataset, legend, tooltips, urls);

		return chart;
	}

}
