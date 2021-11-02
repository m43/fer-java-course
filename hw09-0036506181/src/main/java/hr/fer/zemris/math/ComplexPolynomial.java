package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class models an complex polynomial of form
 * Zn*z^n+Z(n-1)*z^(n-1)+...+Z2*z^2+Z1*z+Z0
 * 
 * @author Frano Rajič
 */
public class ComplexPolynomial {

	/**
	 * List containing the roots by which the polynomial is defined
	 */
	private List<Complex> coeficients;

	/**
	 * Initialize the polynomial with given array of complex numbers that are all
	 * roots.
	 * 
	 * @param factors the roots of the complex number
	 * @throws NullPointerException if null pointer is given
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors);

		coeficients = new ArrayList<>();
		if (factors.length == 0) {
			coeficients.add(new Complex(0, 0));
		}
		for (Complex factor : factors) {
			coeficients.add(factor);
		}
	}

	/**
	 * Initialize the polynomial with given list collection of complex numbers that
	 * are all roots.
	 * 
	 * @param roots the roots of the complex number
	 * @throws NullPointerException if null pointer is givenF
	 */
	public ComplexPolynomial(List<Complex> roots) {
		Objects.requireNonNull(roots);

		this.coeficients = new LinkedList<>();
		this.coeficients.addAll(roots);
	}

	/**
	 * Returns the order of this polynomial. For example (7+2i)z^3+2z^2+5z+1 returns
	 * 3
	 * 
	 * @return the order of the polynomialF
	 */
	public short order() {
		return (short) (coeficients.size() - 1);
	}

	/**
	 * Return the polynomial resulting in multiplying this one with given one.
	 * 
	 * @param p the given polynomial to multiply by
	 * @return the resulting polynomial
	 * @throws NullPointerException if null pointer is given
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p);

		Complex[] result = new Complex[order() + p.order() + 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = Complex.ZERO;
		}

		for (int i = 0; i < coeficients.size(); i++) {
			for (int j = 0; j < p.coeficients.size(); j++) {
				result[i + j] = result[i + j].add(coeficients.get(i).multiply(p.coeficients.get(j)));
			}
		}

		return new ComplexPolynomial(result);
	}

	/**
	 * Method to compute the first derivative of this polynomial. For example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return the derived polynomial
	 */
	public ComplexPolynomial derive() {
		List<Complex> derivedCoeficients = new LinkedList<Complex>();
		if (coeficients.size() <= 1) {
			derivedCoeficients.add(new Complex(0, 0));
		}

		int i = 0;
		for (Complex root : coeficients) {
			if (i == 0) {
				i++;
				continue;
			} else {
				derivedCoeficients.add(root.scaled(i));
				i++;
			}
		}
		return new ComplexPolynomial(derivedCoeficients);
	}

	/**
	 * Computes polynomial value at given point z
	 * 
	 * @param z the point to calculate at
	 * @return the calculated value
	 * @throws NullPointerException if given z is a null pointer
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);

		Complex zPowered = Complex.ONE;
		Complex result = new Complex(0, 0);

		for (Complex coef : coeficients) {
			result = result.add(coef.multiply(zPowered));
			zPowered = zPowered.multiply(z);
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		int last = coeficients.size() - 1;
		for (Complex coef : coeficients) {
			sb.append("(" + coef + ")");
			if (i != 0) {
				sb.append("*z^" + i);
			}
			if (i != last) {
				sb.append("+");
			}
			i++;
		}

		return sb.toString();
	}

}
