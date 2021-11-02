package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Program demonstrates the use of an elements getter
 * 
 * @author Frano
 */
public class ElementsGetterDemo4 {

	/**
	 * The entry point of the program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.forEachRemaining(System.out::println);
	}

}
