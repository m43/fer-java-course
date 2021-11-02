package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Frano Rajič
 */
public class BarChart {

	/**
	 * List containing pairs of x and y values modeled with {@link XYValue}.
	 */
	private List<XYValue> listOfValues;

	/**
	 * Text to show on x axis
	 */
	private String xAxisText;

	/**
	 * Text to show on y axis
	 */
	private String yAxisText;

	/**
	 * The minimal y value of drawn graph
	 */
	private int yMin;

	/**
	 * The maximal y value of drawn graph
	 */
	private int yMax;

	/**
	 * The spacing of drawn y lines on graph
	 */
	private int ySpacing;

	/**
	 * Construct a new {@link BarChart} with necessary information given.
	 * 
	 * @param listOfValues list of xy values modeled by {@link XYValue}
	 * @param xAxisText    the text of the x axis
	 * @param yAxisText    the text of the y axis
	 * @param yMin         the minimal y value of drawn graph
	 * @param yMax         the maximal y value of drawn graph
	 * @param ySpacing     the spacing between drawn y lines
	 * @throws NullPointerException     if list is null or any text Strings are null
	 * @throws IllegalArgumentException if yMax is less than yMin or if some yValues
	 *                                  in the given list are below yMin or if yMin
	 *                                  is negative
	 */
	public BarChart(List<XYValue> listOfValues, String xAxisText, String yAxisText, int yMin, int yMax, int ySpacing) {
		Objects.requireNonNull(listOfValues);
		Objects.requireNonNull(xAxisText);
		Objects.requireNonNull(yAxisText);

		if (yMin < 0) {
			throw new IllegalArgumentException("Given minimal value of y must be positive, yMin = " + yMin);
		}

		if (yMax < yMin) {
			throw new IllegalArgumentException(
					"Given maximal value is not greater than given minimal value --> max:" + yMax + " < min:" + yMin);
		}

		for (XYValue xy : listOfValues) {
			if (xy.getY() < yMin) {
				throw new IllegalArgumentException("All given y values must be above given minimal y (y was "
						+ xy.getY() + " but yMin is " + yMin + ")");
			}
		}

		this.listOfValues = listOfValues;
		this.listOfValues.sort((x, y) -> Integer.compare(x.getX(), y.getY()));
		this.xAxisText = xAxisText;
		this.yAxisText = yAxisText;
		this.yMin = yMin;
		this.yMax = yMax;
		this.ySpacing = ySpacing;
	}

	/**
	 * Get the list of values
	 * 
	 * @return the list of values
	 */
	public List<XYValue> getListOfValues() {
		return listOfValues;
	}

	/**
	 * Get the x axis text
	 * 
	 * @return the x axis text
	 */
	public String getxAxisText() {
		return xAxisText;
	}

	/**
	 * Get the y axis text
	 * 
	 * @return the y axis text
	 */
	public String getyAxisText() {
		return yAxisText;
	}

	/**
	 * Get the minimal y value
	 * 
	 * @return the minimal y value
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Get the maximal y value
	 * 
	 * @return the maximal y value
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Get the spacing on y axis
	 * 
	 * @return the spacing on y axis
	 */
	public int getySpacing() {
		return ySpacing;
	}

}
