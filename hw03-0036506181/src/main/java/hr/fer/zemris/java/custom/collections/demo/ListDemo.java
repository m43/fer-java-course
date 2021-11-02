package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Program to test functionality of classes implementing interface List
 * 
 * @author Frano
 *
 */
public class ListDemo {

	/**
	 * The entry point of the program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");

		Collection col3 = col1;
		Collection col4 = col2;

		col1.get(0);
		col2.get(0);
		// col3.get(0); // neće se prevesti! Razumijete li zašto?
		// col4.get(0); // neće se prevesti! Razumijete li zašto?

		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
	}
}
