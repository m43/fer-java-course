package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program demonstrates the use of {@link ComplexPolynomial} and
 * {@link ComplexRootedPolynomial} including the transformation from rooted to
 * normal polynomial.
 * 
 * @author Frano Rajič
 */
public class ComplexPolynomialDemo {

	/**
	 * Entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		// System.out.println("(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))
		// is expected");
		System.out.println(crp);

		// System.out.println("(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)E
		// is expected");
		System.out.println(cp);

		// System.out.println("(8.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(0.0+i0.0)
		// is expected");
		System.out.println(cp.derive());

		// System.out.println("~ (4z^8 - 8z^4 + 4) is expected");
		System.out.println(cp.multiply(cp));
	}

}
