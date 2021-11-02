package hr.fer.zemris.math;

/**
 * Class providing the functionality of manipulating with 2D vectors. A 2D
 * vector is defined with its x and y coordinates where it would point if it
 * would begin at (0,0).
 * 
 * 
 * @author Frano
 *
 */
public class Vector2D {

	/**
	 * x component of vector
	 */
	private double x;

	/**
	 * y component of vector
	 */
	private double y;

	/**
	 * Constant to be used to compare components of two vectors. If the abs value of
	 * (firstDouble - secondDoule) is less than this constant, then these two
	 * doubles are considered to be equal.
	 */
	public static final double EPSILON = 0.0000001;

	/**
	 * Construct a vector with given x and y components
	 * 
	 * @param x value of x component
	 * @param y value of y component
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the value of the x component
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the value of the y component
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translate this vector for given offset. Offset is another vector and the
	 * translation is done by adding up this vector and the given vector. This
	 * vector is changed in the process.
	 * 
	 * @param offset vector of translation
	 */
	public void translate(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}

	/**
	 * Translate this vector for given offset. Offset is another vector and the
	 * translation is done by adding up this vector and the given vector. This
	 * vector is NOT changed in the process, but a new vector with the result is
	 * returned instead
	 * 
	 * @param offset vector of translation
	 * @return vector containing the result of the translation
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}

	/**
	 * Rotate this vector for given angle.
	 * 
	 * @param angle of rotation
	 */
	public void rotate(double angle) {
		double rotatedX = x * Math.cos(angle) - y * Math.sin(angle);
		double rotatedY = y * Math.cos(angle) + x * Math.sin(angle);
		x = rotatedX;
		y = rotatedY;
	}

	/**
	 * Rotate this vector and return the result as a new vector.
	 * 
	 * @param angle of rotation
	 * @return the resulting rotated vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D rotatedVector = copy();
		rotatedVector.rotate(angle);
		return rotatedVector;
	}

	/**
	 * Scale this vector with given scaler. Scaling is equivalent to multiplying the
	 * vector with given constant.
	 * 
	 * @param scaler to scale the vector
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}

	/**
	 * Scale the vector with given scaler and return the result. Scaling is
	 * equivalent to multiplying the vector with given constant.
	 * 
	 * @param scaler to scale the vector
	 * @return the resulting vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}

	/**
	 * Return a copy of this vector.
	 * 
	 * @return reference to newly constructed copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	/**
	 * Two vectors are equal if they have same x and y components.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (!compare(x, other.x))
			return false;
		if (!compare(y, other.y))
			return false;
		return true;
	}

	/**
	 * Help function to compare two doubles. Two doubles are equal if the absolute
	 * value of their difference is less then given constant --> {@link #EPSILON}
	 * 
	 * @param a first double to compare
	 * @param b second double to compare
	 * @return true if given doubles are equal
	 */
	private boolean compare(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}

	@Override
	/**
	 * String created for a 2D vector is of form "(xComponent, yComponent)"
	 */
	public String toString() {
		return "(" + Double.toString(x) + ", " + Double.toString(y) + ")";
	}

}
