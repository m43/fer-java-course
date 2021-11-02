package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Program demonstrates the basic functionality of class ComplexNumber.
 * 
 * @author Frano
 *
 */

public class ComplexDemo {

	/**
	 * Program to demonstrate the basic usage of class ComplexNumber
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(12, 23d);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(3d, Math.PI+1)).div(c2).power(5).root(3)[2];

		System.out.println(c3);
	}

}
