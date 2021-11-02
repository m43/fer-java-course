package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that demonstrates the use of {@link BarChartComponent}. Program can
 * be run in command line and expects the path to a file with necessary
 * {@link BarChart} creation information given. That file should be of the
 * format as in the following example: <br>
 * <code>
 * Number of people in the car
 * Frequency
 * 1,8 2,20 3,22 4,10 5,4
 * 0
 * 22
 * 2
 * </code>
 * 
 * @author Frano Rajič
 */
public class BarChartDemo extends JFrame {

	/**
	 * The default serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The created {@link BarChartComponent}
	 */
	private BarChartComponent barChartComponent;

	/**
	 * String containing the path to the file that is being drawnF
	 */
	private String filePath;

	/**
	 * Construct the frame of the program
	 * 
	 * @param barChart reference to the {@link BarChart} of which to draw the
	 *                 {@link BarChartComponent}
	 * @param filePath the path to the file that is being drawn
	 */
	public BarChartDemo(BarChart barChart, String filePath) {
		this.filePath = filePath;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		barChartComponent = new BarChartComponent(barChart);
		setTitle("Bar Chart Demonstration program");
		initGUI();
		setSize(720, 720);
		// pack();
	}

	/**
	 * Help method that initializes the frame of the program
	 */
	private void initGUI() {
		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);

		//barChartComponent.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3, true));
		add(barChartComponent, BorderLayout.CENTER);

		JLabel title = new JLabel(filePath, SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 36));
		title.setForeground(Color.BLACK);
		//title.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
		add(title, BorderLayout.NORTH);
	}

	/**
	 * Entry point for program
	 * 
	 * @param args path to file with necessary information for {@link BarChart}
	 *             construction.
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println(
					"Exactly one argument should be given containing the path to the file to read the bar chart data from. Found this number of arguments: "
							+ args.length);
			return;
		}
		String filePath = args[0];

//		String filePath = "./graph.txt";

		String[] lines = new String[6];
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(filePath));
			int i = 0;
			for (String line; (line = br.readLine()) != null && i < 6; i++) {
				lines[i] = line;
			}
			br.close();

			if (i != 6) {
				System.out.println("BarChart file must have at least six lines of text.");
				return;
			}
		} catch (IOException | InvalidPathException e) {
			System.out.println("Couldnt open given file path, consider the following: " + e.getMessage());
		}

		BarChart barChart;
		try {
			String xAxisText = lines[0].trim();
			String yAxisText = lines[1].trim();

			List<XYValue> listOfValues = Arrays.asList(lines[2].split(" ")).stream().map(x -> {
				String[] parts = x.split(",");
				if (parts.length != 2)
					throw new IllegalArgumentException("Something wrong with given x,y pairs for bar chart graph.");
				return new XYValue(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			}).collect(Collectors.toList());

			Integer yMin = Integer.parseInt(lines[3]);
			Integer yMax = Integer.parseInt(lines[4]);
			Integer spacing = Integer.parseInt(lines[5]);

			barChart = new BarChart(listOfValues, xAxisText, yAxisText, yMin, yMax, spacing);
		} catch (NumberFormatException e) {
			System.out.println(
					"Where an number was expected in input file, it was not found, so the necessary int could not be parsed. Consider this: "
							+ e.getMessage());
			return;
		}

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(barChart, filePath).setVisible(true);
		});
	}

}