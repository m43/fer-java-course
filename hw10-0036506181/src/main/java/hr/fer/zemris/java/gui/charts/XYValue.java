package hr.fer.zemris.java.gui.charts;

/**
 * Class provides functionality of holding an x and y value as read only
 * properties.
 * 
 * @author Frano Rajič
 */
public class XYValue {

	/**
	 * Read only value of x
	 */
	private final int x;

	/**
	 * Read only value of y
	 */
	private final int y;

	/**
	 * Create a new {@link XYValue}.
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the stored x value
	 * 
	 * @return the x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the stored y value
	 * 
	 * @return the y value
	 */
	public int getY() {
		return y;
	}

}
