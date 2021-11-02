package hr.fer.zemris.java.custom.collections;

/**
 * The class represents some general collection of objects and provides basic
 * functionality that other collections should implement. That functionality
 * includes adding and removing elements, checking the size of the collection,
 * and returning an array of all the elements stored. The implementations in
 * this class are non functional.
 * 
 * @author Frano
 * @version 1.0
 */
public class Collection {

	/**
	 * Create a new and empty collection
	 */
	protected Collection() {
		// empty
	}

	/**
	 * Provides the functionality of testing whether the collection is empty
	 * 
	 * @return Returns true if collection contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Provides functionality of counting the number of elements stored in the
	 * collection.
	 * 
	 * @return Returns the number of currently stored objects in this collections.
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into the collection.
	 * 
	 * @param value Object to add into the collection
	 */
	public void add(Object value) {

	}

	/**
	 * Returns true only if given value is in collection. The equality check is done
	 * with object's equals method
	 * 
	 * @param value Object to look for in the collection
	 * @return true only if the collection contains given value
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it.
	 * 
	 * @param value Object to be removed from collection
	 * @return true if collection contains the specified element
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array. This method never returns
	 * null.
	 * 
	 * @return an array containing all of the elements in the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process(.) for each element of this collection. The
	 * order in which elements will be sent is undefined in this class.
	 * 
	 * @param processor is the Processor instance to be used to process the
	 *                  collection
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection. The other collection remains unchanged.
	 * 
	 * @param other collection containing elements to be added to this collection
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor {
			public void process(Object value) {
				add(value);
			}
		}

		LocalProcessor p = new LocalProcessor();
		other.forEach(p);
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {

	}

}
