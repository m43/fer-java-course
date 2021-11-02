package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Class models an {@link JComponent} that provides the functionality of drawing
 * an {@link BarChart}.
 * 
 * @author Frano Rajič
 */
public class BarChartComponent extends JComponent {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the {@link BarChart} object.
	 */
	private BarChart barChart;

	/**
	 * Construct a new {@link BarChartComponent} with given reference to
	 * {@link BarChart}.
	 * 
	 * @param bar the {@link BarChart} object
	 */
	public BarChartComponent(BarChart bar) {
		super();
		barChart = bar;
	}

	/**
	 * The height of the reserved space for the x axis.
	 */
	private int xNameHeight = 30;

	/**
	 * The space around the line of the axis. It is the space between the marking
	 * numbers and the axis line.
	 */
	private int xSpaceAroundAxis = 5;

	/**
	 * The width of the reserved space for the y axis
	 */
	private int yNameWidth = 30;

	/**
	 * The space around the line of the axis. It is the space between the marking
	 * numbers and the axis line.
	 */
	private int ySpaceAroundAxis = 5;

	/**
	 * Number of pixels that is reserved for y arrow area
	 */
	private int yArrow = 15;

	/**
	 * Number of pixels that is reserved for x arrow area
	 */
	private int xArrow = 15;

	/**
	 * The empty space that is around every bar. Turns into number of empty pixels.
	 */
	private int spaceAroundBar = 3;

	@Override
	protected void paintComponent(Graphics g) {

		// g -> g2d
		Graphics2D g2d = (Graphics2D) g;

		// VARIABLES TO USE LATER WHEN DRAWING
		Insets ins = getInsets();
		Dimension dim = getSize();
		g.setFont(new Font("Arial", Font.BOLD, 16));
		FontMetrics metrics = g.getFontMetrics();
		int twiceStringCenteringSpace = metrics.getAscent() - metrics.getDescent();
		Rectangle r = new Rectangle(ins.left, ins.top, dim.width - ins.left - ins.right,
				dim.height - ins.top - ins.bottom);

		// clockwise and counterclockwise transformations
		AffineTransform at90cc = new AffineTransform();
		at90cc.rotate(-Math.PI / 2);
//		AffineTransform at90c = AffineTransform.getQuadrantRotateInstance(1);
		AffineTransform defaultAt = g2d.getTransform();

		// ADDING SOME COLOR TO THA DRAWING
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(r.x, r.y, r.width, r.height);
		}

		// Y AXIS NAME
//		g2d.drawRect(r.x, r.y, yNameWidth, r.height - xNameHeight);
		g2d.setTransform(at90cc);
		g2d.drawString(barChart.getyAxisText(), -r.y - (r.height + metrics.stringWidth(barChart.getyAxisText())) / 2,
				r.x + (yNameWidth + twiceStringCenteringSpace) / 2);
		g2d.setTransform(defaultAt);

		// X AXIS NAME
//		g2d.drawRect(r.x + yNameWidth, r.y + r.height - xNameHeight, r.width - yNameWidth, xNameHeight);
		g2d.drawString(barChart.getxAxisText(), r.x + (r.width - metrics.stringWidth(barChart.getxAxisText())) / 2,
				r.y + r.height + (-xNameHeight + twiceStringCenteringSpace) / 2);

		// Y and X AXIS setup of needed variables
		int spacing = barChart.getySpacing();

		int yMin = barChart.getyMin();
		int yMax = barChart.getyMax() + ((barChart.getyMax() - barChart.getyMin()) % spacing == 0 ? 0
				: spacing - (barChart.getyMax() - barChart.getyMin()) % spacing);
		int yMarkingsWidth = metrics.stringWidth(String.valueOf(yMax));

		int xMarkingsHeight = metrics.getHeight();

		Point origin = new Point(r.x + yNameWidth + yMarkingsWidth + ySpaceAroundAxis,
				r.y + r.height - xNameHeight - xMarkingsHeight - xSpaceAroundAxis);
		Rectangle rBars = new Rectangle(origin.x, r.y + yArrow, r.width - origin.x - xArrow, origin.y - r.y - yArrow);

		double yMarkingsStepHeight = rBars.height * spacing / (double) (yMax - yMin);

		List<XYValue> xValues = barChart.getListOfValues();
		int xn = xValues.size();
		double xMarkingsStepWidth = rBars.width / (double) xn;

		// CHART DRAWING AREA
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(rBars.x, rBars.y, rBars.width, rBars.height);
		g2d.setColor(Color.BLACK);

		// Y Axis markings drawn
		// g2d.drawRect(origin.x - yMarkingsWidth - ySpaceAroundAxis, rBars.y,
		// yMarkingsWidth, rBars.height);
		for (int y = yMin, i = 0; y <= yMax; y += spacing, i++) {
			// grid
			g2d.setColor(Color.GREEN);
			g2d.drawLine(origin.x, (int) (origin.y - yMarkingsStepHeight * i), origin.x + rBars.width + xArrow,
					(int) (origin.y - yMarkingsStepHeight * i));

			// markings
			String currentMarking = String.valueOf(y);
			g2d.setColor(Color.BLACK);
			g2d.drawLine(origin.x - ySpaceAroundAxis, (int) (origin.y - yMarkingsStepHeight * i), origin.x,
					(int) (origin.y - yMarkingsStepHeight * i));
			g2d.drawString(currentMarking, origin.x - metrics.stringWidth(currentMarking) - ySpaceAroundAxis,
					(int) (origin.y - yMarkingsStepHeight * i + twiceStringCenteringSpace / 2));
		}

		// X Axis markings and bars drawn
		int i = 0;
		for (XYValue value : xValues) {

			int xLastMark = (int) (origin.x + (i) * xMarkingsStepWidth);
			int xMark = (int) (origin.x + (i + 1) * xMarkingsStepWidth);

			// grid
			g2d.setColor(Color.GREEN);
			g2d.drawLine(xMark, origin.y, xMark, rBars.y - yArrow);

			// markings
			g2d.setColor(Color.BLACK);
			int yCurrent = (int) (origin.y + xSpaceAroundAxis + xMarkingsHeight / 2. + twiceStringCenteringSpace / 2.);
			g2d.drawLine(xMark, origin.y + xSpaceAroundAxis, xMark, origin.y);
			g2d.drawString(String.valueOf(value.getX()), (int) (origin.x + (i + 0.5) * xMarkingsStepWidth
					- metrics.stringWidth(String.valueOf(value.getX())) / 2.), yCurrent);

			// bars
			g2d.setColor(Color.RED);
			int yValue = (value.getY() > yMax ? yMax : value.getY());
			g2d.fillRect(xLastMark + spaceAroundBar,
					origin.y - (int) ((yValue - yMin) * rBars.height / (double) (yMax - yMin)),
					xMark - xLastMark - spaceAroundBar * 2 + 1,
					(int) ((yValue - yMin) * rBars.height / (double) (yMax - yMin)));
			g2d.setColor(Color.BLACK);
			i++;
		}

		// Y Axis
		g2d.drawLine(origin.x, origin.y + xSpaceAroundAxis, origin.x, rBars.y - yArrow * 2 / 3);
		g2d.fillPolygon(new int[] { origin.x, origin.x - yArrow / 3, origin.x + yArrow / 3, origin.x },
				new int[] { rBars.y - yArrow, rBars.y - yArrow / 3, rBars.y - yArrow / 3, rBars.y - yArrow }, 4);

		// X Axis
		g2d.drawLine(origin.x - ySpaceAroundAxis, origin.y, origin.x + rBars.width + xArrow * 2 / 3, origin.y);
		g2d.fillPolygon(
				new int[] { origin.x + rBars.width + xArrow, origin.x + rBars.width + yArrow / 3,
						origin.x + rBars.width + xArrow / 3, origin.x + rBars.width + +xArrow },
				new int[] { origin.y, origin.y - xArrow / 3, origin.y + xArrow / 3, origin.y }, 4);
	}

}
