package hr.fer.zemris.java.custom.collections;

/**
 * The class represents some general collection of elements and provides basic
 * functionality that other collections should implement. That functionality
 * includes adding and removing elements, checking the size of the collection,
 * and returning an array of all the elements stored.
 * 
 * @param <E> the type of elements in this collection
 * 
 * @author Frano Rajič
 * @version 1.0
 */
public interface Collection<E> {

//	protected Collection() {
//		// empty
//	}

	/**
	 * Provides the functionality of testing whether the collection is empty
	 * 
	 * @return Returns true if collection contains no elements and false otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Provides functionality of counting the number of elements stored in the
	 * collection.
	 * 
	 * @return Returns the number of currently stored elements in this collections.
	 */
	int size();

	/**
	 * Adds the given Element into the collection.
	 * 
	 * @param value element to add into the collection
	 */
	void add(E value);

	/**
	 * Returns true only if given value is in collection. The equality check is done
	 * with element's equals method
	 * 
	 * @param value Object to look for in the collection
	 * @return true only if the collection contains given value
	 */
	boolean contains(Object value);

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it.
	 * 
	 * @param value Object to be removed from collection
	 * @return true if collect ion contains the specified element
	 */
	boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return an array containing all of the elements in the collection
	 */
	Object[] toArray();

	/**
	 * Method calls processor.process(.) for each element of this collection. The
	 * order in which elements will be sent is undefined in this class.
	 * 
	 * @param processor is the Processor instance to be used to process the
	 *                  collection
	 */
	public default void forEach(Processor<? super E> processor) {
		for (ElementsGetter<E> e = createElementsGetter(); e.hasNextElement();) {
			processor.process(e.getNextElement());
		}
	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection. The other collection remains unchanged.
	 * 
	 * @param other collection containing elements to be added to this collection.
	 *              elements of collection should extend type of parameterization
	 */
	default void addAll(Collection<? extends E> other) {
		class LocalProcessor implements Processor<E> {
			public void process(E value) {
				add(value);
			}
		}

		LocalProcessor p = new LocalProcessor();
		other.forEach(p);

//		alternative:
//		other.forEach(new Processor<E>() {
//			@Override
//			public void process(E value) {
//				add(value);
//
//			}
//		});
	}

	/**
	 * Method adds into current collection all elements from the given collection
	 * that satisfy given Tester.
	 * 
	 * @param col    collection containing elements to be tested and added to this
	 *               collection
	 * @param tester reference to the tester that has necessary tests
	 */
	default void addAllSatisfying(Collection<E> col, Tester<E> tester) {

		E currentElement;
		for (ElementsGetter<E> e = col.createElementsGetter(); e.hasNextElement();) {
			currentElement = e.getNextElement();
			if (tester.test(currentElement)) {
				add(currentElement);
			}
		}
	}

	/**
	 * Removes all elements from this collection.
	 */
	void clear();

	/**
	 * Return an ElementsGetter that is able to return elements of this collection
	 * 
	 * @return an ElementsGetter for this collection
	 */
	ElementsGetter<E> createElementsGetter();

}
