package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Program demonstrates the use of an elements getter
 * 
 * @author Frano
 */
public class ElementsGetterDemo3 {

	/**
	 * The entry point of the program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		Collection col = new LinkedListIndexedCollection(); // or ArrayIndexedCollection
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		System.out.println("Jedan element: " + getter.getNextElement());
	}

}
