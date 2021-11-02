package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class models complex numbers and provides functionality of multiplying,
 * dividing, adding, subtracting and calculating power and roots of the it.
 * 
 * @author Frano Rajič
 */
public class Complex {
	/**
	 * Static complex number representing 0
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Static complex number representing 1
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Static complex number representing -1
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Static complex number representing i
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Static complex number representing -i
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * The real part of the complex number
	 */
	private final double real;

	/**
	 * The imaginary part of the complex number
	 */
	private final double imaginary;

	/**
	 * Create and initialize the complex number to default values
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor to initialize ComplexNumber with given real and imaginary parts
	 * 
	 * @param re part of complex number
	 * @param im part of complex number
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
	}

	/**
	 * Get the module of the complex number
	 * 
	 * @return the module of the complex number
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Multiply this number with given complex number and return the result as a new
	 * complex number
	 * 
	 * @param c the number to multiply with
	 * @return the resulting complex number
	 * @throws NullPointerException if null pointer given
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);
		return new Complex(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
	}

	/**
	 * Divide this number by given complex number and return the result as a new
	 * complex number
	 * 
	 * @param c the number to divide by
	 * @return the resulting complex number
	 * @throws NullPointerException if null pointer given
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);
		return multiply(new Complex(c.real / Math.pow(c.module(), 2), -c.imaginary / Math.pow(c.module(), 2)));
	}

	/**
	 * Add this number with given complex number and return the result as a new
	 * complex number
	 * 
	 * @param c the number to add with
	 * @return the resulting complex number
	 * @throws NullPointerException if null pointer given
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);
		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Subtract this number with given complex number and return the result as a new
	 * complex number
	 * 
	 * @param c the number to multiply with
	 * @return the resulting complex number
	 * @throws NullPointerException if null pointer given
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);
		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Return this complex number negated
	 * 
	 * @return the negated complex number
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Returns this^n
	 * 
	 * @param n the power to calculate, must be a non-negative integer
	 * @return this^n
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power must be an non-negative integer!");
		}

		if (n == 0) {
			return ONE;
		}

		Complex c;
		if (n % 2 == 0) {
			c = power(n / 2);
			return c.multiply(c);
		} else {
			c = power(n / 2);
			return c.multiply(c).multiply(this);
		}

	}

	/**
	 * Returns all root of this complex number, where n is positive integer and
	 * represents the root to take
	 * 
	 * @param n number represents the what n-th root to take
	 * @return list containing all n roots
	 */
	public List<Complex> root(int n) {
		List<Complex> list = new LinkedList<>();

		double magnitude = Math.pow(module(), 1 / (double) n);
		double baseAngle = Math.atan2(imaginary, real) / n;
		System.out.println(magnitude);
		System.out.println(baseAngle);

		for (int i = 0; i < n; i++) {
			list.add(new Complex(magnitude * Math.cos(baseAngle + 2 * Math.PI / n * i),
					magnitude * Math.sin(baseAngle + 2 * Math.PI / n * i)));
		}

		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Double.toString(real));
		sb.append((imaginary >= 0) ? "+" : "-");
		sb.append("i");
		sb.append(Double.toString(Math.abs(imaginary)));
		return sb.toString();
	}

	/**
	 * Scale the complex number by given factor
	 * 
	 * @param factor factor to scale by
	 * @return the new resulting scaled vector
	 */
	public Complex scaled(double factor) {
		return new Complex(real * factor, imaginary * factor);
	}

	/**
	 * Parse given String into created ComplexNumber and return a reference to it.
	 * Parser accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1",
	 * "-2.71-3.15i"). Decimal number needs to have a point, and cannot be written
	 * in exponent notation.
	 * 
	 * @param s String to parse
	 * @return Reference to created complex number
	 * @throws NullPointerException if null pointer given
	 */
	public static Complex parse(String s) {
		Objects.requireNonNull(s);
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

		return new Complex(real, imaginary);
	}

}
