package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * An interface to define basic functionality for an object to be an elements
 * getter. An elements getter class is working with some storage structure in
 * the background (like a collection) and is always able to tell if there are
 * more elements to return in that structure and if so to return the next
 * element.
 * 
 * @author Frano
 *
 */
public interface ElementsGetter {

	/**
	 * Tell if the storing structure has elements that have not ben returned yet.
	 * 
	 * @return true if there are unreturned elements, false otherwise
	 */
	boolean hasNextElement();

	/**
	 * Return the next element
	 * 
	 * @return the next element
	 * @throws NoSuchElementException if no elements left to be returned
	 */
	Object getNextElement();

	/**
	 * Process the remaining elements using given processor
	 * 
	 * @param p the processor to do the processing
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

	/**
	 * Process each of the remaining elements using given processor
	 * 
	 * @param p the processor to process by
	 */
	default void forEachRemaining(Processor p) {
		processRemaining(p);
	}
}
