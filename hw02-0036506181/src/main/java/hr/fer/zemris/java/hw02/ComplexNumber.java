package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * This class represents an unmodifiable complex number. Each method which
 * performs some kind of modification returns a new instance which represents
 * modified number. The class provides ComplexNumer arithmetics and various ways
 * of creating new instances.
 * 
 * @author Frano
 *
 */

public class ComplexNumber {

	/**
	 * Variable stores the real part of the complex number
	 */
	private final double real;

	/**
	 * Variable stores the imaginary part of the complex number
	 */
	private final double imaginary;

	/**
	 * Constructor to initialize ComplexNumber with given real and imaginary parts
	 * 
	 * @param real      part of complex number
	 * @param imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Method to create complex numbers with only real part and no imaginary.
	 * 
	 * @param real part of complex number
	 * @return an reference to the created complex number object
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Method to create complex numbers with only imaginary and no real part.
	 * 
	 * @param imaginary part of complex number
	 * @return an reference to the created complex number object
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates an complex number with provided magnitude aka radius and angle in
	 * radians
	 * 
	 * @param magnitude the magnitude aka radius of the created complex number
	 * @param angle     the angle in radians of the created complex number
	 * @return a reference to the crated complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Get the value of the real part of this complex number
	 * 
	 * @return the real value
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Get the value of the imaginary part of this complex number
	 * 
	 * @return the imaginary value
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Get the equivalent radius of this complex number
	 * 
	 * @return the magnitude of this complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Get the angle of this complex number
	 * 
	 * @return the angle in radians, in range [0, 2*pi]
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		if (angle < 0d) {
			angle += 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Parse given String into created ComplexNumber and return a reference to it.
	 * Parser accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1",
	 * "-2.71-3.15i"). Decimal number needs to have a point, and cannot be written
	 * in exponent notation.
	 * 
	 * @param s String to parse
	 * @return Reference to created ComplexNumber
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		// With the assumption that numbers with whitespace inside are valid, I wrote
		// the next line
		s = s.replaceAll("\\s", "");

		double real;
		double imaginary;

		if (s.endsWith("i")) {
			int positionOfSign = Math.max(s.lastIndexOf("-"), s.lastIndexOf("+"));

			if (positionOfSign == -1) {
				real = 0;
				String imaginaryString = s.substring(0, s.length() - 1);
				if (imaginaryString.equals("")) {
					imaginary = 1;
				} else {
					imaginary = Double.parseDouble(imaginaryString);
				}
			} else {
				String realString = s.substring(0, positionOfSign);
				if (realString.equals("")) {
					real = 0;
				} else {
					real = Double.parseDouble(realString);
				}

				String imaginaryString = s.substring(positionOfSign, s.length() - 1);
				if (imaginaryString.equals("+")) {
					imaginary = 1;
				} else if (imaginaryString.equals("-")) {
					imaginary = -1;
				} else {
					imaginary = Double.parseDouble(imaginaryString);
				}

			}
		} else {
			real = Double.parseDouble(s);
			imaginary = 0;
		}

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Create a new Complex number equal to the some of this one and the given one.
	 * 
	 * @param c the complex number to be added with this one
	 * @return the created ComplexNumber that is the sum
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Create a new Complex number equal to the difference of this one and the given
	 * one.
	 * 
	 * @param c the complex number to be used in the calculation
	 * @return the created ComplexNumber that is the resulting difference
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Create a new Complex number equal this one and the given one multiplied.
	 * 
	 * @param c the complex number to be used in the multiplication
	 * @return the created ComplexNumber as the result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double realResult = real * c.getReal() - imaginary * c.getImaginary();
		double imaginaryResult = real * c.getImaginary() + imaginary * c.getReal();
		return new ComplexNumber(realResult, imaginaryResult);
	}

	/**
	 * Create a new Complex number equal to the this number divided by the given
	 * number.
	 * 
	 * @param c the complex number to be used to divide by
	 * @return the created ComplexNumber that is the result of the division
	 */
	public ComplexNumber div(ComplexNumber c) {
		double magnitude = getMagnitude() / c.getMagnitude();
		double angle = getAngle() - c.getAngle();
		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Raise the complex number to the power n and store the result in a newly
	 * created complex number that is returned. N needs to be positive or zero
	 * (n>=0).
	 * 
	 * @param n The power the complex number should be raised up to
	 * @return the created complex number
	 * @throws IllegalArgumentException if invalid value given
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		if (n == 0) {
			return new ComplexNumber(1, 0);
		}

		double magnitude = Math.pow(getMagnitude(), n);
		double angle = (getAngle() * n);

		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Calculate the roots of this complex number and return them in the form of an
	 * array of complex numbers. The given number n should satisfy n>0 for the root
	 * calculation to be possible.
	 * 
	 * @param n how many roots
	 * @return an array containing all the given roots of this number
	 * @throws IllegalArgumentException if given number of roots is not positive.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		double magnitude = Math.pow(getMagnitude(), 1d / n);
		double firstAngle = getAngle() / n;
		double angle; // formula firstAngle + 2*k*PI/n

		ComplexNumber[] results = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			angle = firstAngle + 2 * i * Math.PI / n;
			results[i] = ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
		}
		return results;
	}

	/**
	 * Method to convert complex number string in the form of a+bi
	 * 
	 * @return the created string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Double.toString(real));
		sb.append((imaginary >= 0) ? "+" : "");
		sb.append(Double.toString(imaginary));
		sb.append("i");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return Double.doubleToLongBits(imaginary) == Double.doubleToLongBits(other.imaginary)
				&& Double.doubleToLongBits(real) == Double.doubleToLongBits(other.real);
	}

}
