package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class models an complex polynomial definied by its roots.
 * 
 * @author Frano Rajič
 */
public class ComplexRootedPolynomial {

	/**
	 * The constant value of the polynomial
	 */
	private Complex z0;

	/**
	 * List containing the roots by which the polynomial is defined
	 */
	private List<Complex> roots;

	/**
	 * Create an complex rooted polynomial with given factor z0 and roots z1, z2,
	 * ..., zn.
	 * 
	 * @param constant defines z0
	 * @param roots    contains the complex numbers z1, z2, ..., zn
	 * @throws NullPointerException if given constant is a null pointer
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		Objects.requireNonNull(constant);
		this.roots = new LinkedList<>();
		z0 = constant;
		if (roots != null) {
			for (Complex root : roots) {
				this.roots.add(root);
			}
		}
	}

	/**
	 * Compute the polynomial value at given point z - p(z).
	 * 
	 * @param z point to calculate the polynomial at
	 * @return the value of p(z)
	 * @throws NullPointerException if given z is a null reference
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z);

		Complex result = roots.stream().reduce(Complex.ONE, (x, y) -> x.multiply(z.sub(y)));

		return z0.multiply(result);
	}

	/**
	 * Converts this polynomial to type {@link ComplexPolynomial}
	 * 
	 * @return the {@link ComplexPolynomial} equivalent
	 */
	public ComplexPolynomial toComplexPolynom() {

		ComplexPolynomial cp = new ComplexPolynomial(z0);

		for (Complex root : roots) {
			cp = cp.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
		}

		return cp;
	}

	@Override
	public String toString() {
		if (roots.size() == 0) {
			return z0.toString();
		}

		return roots.stream().map(x -> "(" + x.toString() + ")").reduce(("(" + z0 + ")"),
				(x, y) -> x + "*(z-" + y + ")");
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold.
	 * 
	 * @param z        the given complex number
	 * @param treshold the threshold of performed search
	 * @return Index of the root if found, -1 otherwise. First root has index 0,
	 *         second index 1, etc
	 * @throws NullPointerException if an null pointer is given
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		Objects.requireNonNull(z);

		if (roots.isEmpty()) {
			return -1;
		}

		Complex closest = roots.stream().sorted((x, y) -> Double.compare(z.sub(x).module(), z.sub(y).module()))
				.findFirst().get();

		if (closest.sub(z).module() <= treshold) {
			return roots.indexOf(closest);
		}

		return -1;
	}

}
