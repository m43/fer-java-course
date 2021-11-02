package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class models an three dimensional vector, providing the basic functionality
 * of vector manipulation. The included functionality is adding, subtracting,
 * normalizing, scaling vectors, determining the product and dot product of two
 * vectors.
 * 
 * @author Frano Rajič
 */
public class Vector3 {

	/**
	 * The x component of the vector
	 */
	private double x;

	/**
	 * The y component of the vector
	 */
	private double y;

	/**
	 * The z component of the vector
	 */
	private double z;

	/**
	 * Construct the vector with given x, y and z components.
	 * 
	 * @param x value of x component
	 * @param y value of y component
	 * @param z value of z component
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculate the norm (length) of the this vector.
	 * 
	 * @return the norm of this vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Return a new vector resulting in the normalization of this vector
	 * 
	 * @return the normalized vector
	 */
	public Vector3 normalized() {
		return scale(1 / norm());
	}

	/**
	 * Return the result of adding this vector with given vector. This vector is not
	 * changed, only the result is returned
	 * 
	 * @param other vector to add with
	 * @return the vector resulting with the addition
	 * @throws NullPointerException if null pointer given
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Return a new vector resulting in subtracting this vector with given vector.
	 * 
	 * @param other vector to subtract with
	 * @return the new resulting vector
	 * @throws NullPointerException if null pointer given
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Return the scalar product of this vector and the given vector.
	 * 
	 * @param other second vector in scalar product calculation
	 * @return the scalar product
	 * @throws NullPointerException if null pointer given
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Return the product of this vector and the given vector
	 * 
	 * @param other the vector to calculate the product with
	 * @return the cross product
	 * @throws NullPointerException if null pointer given
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		double newX = y * other.z - z * other.y;
		double newY = x * other.z - z * other.x;
		double newZ = x * other.y - y * other.x;
		return new Vector3(newX, newY, newZ);
	}

	/**
	 * Return the vector resulting in scaling this vector with given scale factor
	 * 
	 * @param s the factor to scale with
	 * @return the new scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}

	/**
	 * Calculate the cosines of the angle between this vector and the given vector
	 * 
	 * @param other the second vector to calculate the angle with
	 * @return the cosines of the angle
	 * @throws NullPointerException if null pointer given
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other);
		return dot(other) / (norm() * other.norm());
	}

	/**
	 * Get the x component of the vector
	 * 
	 * @return the x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y component of the vector
	 * 
	 * @return the y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the z component of the vector
	 * 
	 * @return the z component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Create and return an array of three elements containing the values of the x,
	 * y and z components
	 * 
	 * @return an array with values of the components
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

}
