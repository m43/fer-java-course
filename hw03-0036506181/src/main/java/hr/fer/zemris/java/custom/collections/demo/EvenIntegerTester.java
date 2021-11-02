package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * A demonstration of how the class Tester could be used. The class implements
 * an even parity of Integer tester and runs a short demonstration of it in the
 * main method
 * 
 * @author Frano
 *
 */
public class EvenIntegerTester implements Tester {

	@Override
	/**
	 * {@inheritDoc} The criteria for this test is that the object is an Integer and
	 * that the integer is of even parity
	 */
	public boolean test(Object obj) {
		if (!(obj instanceof Integer)) return false;
		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

	/**
	 * Demonstration
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
		
		// syso: false, true, false.
	}

}
