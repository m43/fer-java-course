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
 * @param <E> The element type to get
 */
public interface ElementsGetter<E> {

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
	E getNextElement();

	/**
	 * Process the remaining elements using given processor
	 * 
	 * @param p processor that defines how to process the remaining elements
	 */
	default void processRemaining(Processor<E> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

	/**
	 * Process the remaining elements using given processor
	 * 
	 * @param p processor that defines how to process the remaining elements
	 */
	default void forEachRemaining(Processor<E> p) {
		processRemaining(p);
	}
}
