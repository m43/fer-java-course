package coloring.algorithms;

/**
 * Class models pixels as objects having x and y coordinats.
 * 
 * @author Frano Rajič
 */
public class Pixel {

	/**
	 * x coordinate of pixel
	 */
	public int x;

	/**
	 * y coordinate of pixel
	 */
	public int y;

	/**
	 * Create an instance of pixel with given coordinates
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
