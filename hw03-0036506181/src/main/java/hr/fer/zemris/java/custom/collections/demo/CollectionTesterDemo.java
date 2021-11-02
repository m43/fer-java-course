package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Program to demonstrate the functionality of adding all elements from first
 * collection to second according to criteria defined by Tester.
 * 
 * @author Frano
 *
 */
public class CollectionTesterDemo {

	/**
	 * Entry point for program
	 * 
	 * @param args absolutely irrelevant
	 */
	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);

		// if all good, should print out 12, 2, 4, 6
	}
}
