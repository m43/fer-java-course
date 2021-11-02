package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class provides the functionality of storing elements in a array
 * structure. Duplicate elements are allowed, but storage of null references is
 * not allowed.
 * 
 * @author Frano
 * @version 1.0
 */

public class ArrayIndexedCollection extends Collection {

	/**
	 * Stores the current size of the collection (number of elements actually stored
	 * in elements array)
	 */
	private int size;

	/**
	 * The default capacity
	 */
	private final static int DEFAULT_CAPACITY = 16;

	/**
	 * An array to store elements of the collection.
	 */
	private Object[] elements;

	/**
	 * Default constructor initializes the collection to {@value #DEFAULT_CAPACITY}
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructing a collection with elements given in other collection
	 * 
	 * @param other collection to copy elements from
	 * @throws NullPointerException if given collection is a null reference
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
	}

	/**
	 * Constructing a collection with given initial capacity of at least 1 element
	 * 
	 * @param initialCapacity of constructed collection
	 * @throws IllegalArgumentException if invalid capacity given
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		elements = new Object[initialCapacity];
	}

	/**
	 * Constructor to copy all elements of other collection into newly created one
	 * of given minimum initial capacity
	 * 
	 * @param other           collection which elements are copied into this newly
	 *                        constructed collection
	 * @param initialCapacity defines the initial capacity of the constructed
	 *                        collection
	 * @throws NullPointerException     if given collection is a null reference
	 * @throws IllegalArgumentException if given initial capacity is invalid
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		Objects.requireNonNull(other);

		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		int otherSize = other.size();
		if (otherSize > initialCapacity) {
			elements = new Object[otherSize];
		} else {
			elements = new Object[initialCapacity];
		}

		addAll(other);
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection in average complexity O(1). If
	 * full capacity is reached, the capacity of the array is doubled.
	 * 
	 * @param value Object to add into the collection
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value);

		int indexOfNextSpot = size();
		if (elements.length <= indexOfNextSpot) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}

		size++;
		elements[indexOfNextSpot] = value;
	}

	/**
	 * Returns the object that is stored in backing array at position index in O(1)
	 * complexity.
	 * 
	 * @param index of element to be retrieved. Valid indexes are 0 to size-1 (both
	 *              included).
	 * @return Returns a reference to specified object
	 * @throws IndexOutOfBoundsException if given index is invalid
	 */

	/**
	 * Inserts (does not overwrite) the given value at the given position in array
	 * by shifting other elements one place towards the end. O(n) complexity.
	 * 
	 * @param value    Object to be inserted
	 * @param position where the element needs to be placed. Valid positions are 0
	 *                 to size (both are included)
	 * @throws IndexOutOfBoundsException if given position is invalid
	 * @throws NullPointerException      if given object is a null reference
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value);

		if (position < 0 || position > size()) {
			throw new IndexOutOfBoundsException();
		}

		Object temporaryObject = value;
		Object swappingObject;

		// this for loop is bubble-sort-like
		for (int i = position; i < size(); i++) {
			// It would be easier to use this
			// (eventhough I've got a bit better complexity):
			// System.arraycopy(src, srcPos, dest, destPos, length);
			//		src − This is the source array.
			//		srcPos − This is the starting position in the source array.
			//		dest − This is the destination array.
			//		destPos − This is the starting position in the destination data.
			//		length − This is the number of array elements to be copied.
			swappingObject = elements[i];
			elements[i] = temporaryObject;
			temporaryObject = swappingObject;
			
		}
		// the last element to be added
		add(temporaryObject);
	}

	/**
	 * Searches the collection in O(n) and returns the index of the first occurrence
	 * of the given value or -1 if the value is not found.
	 * 
	 * @param value of object to search for in the collection. Null reference is
	 *              acceptable, but no element will be found
	 * @return the index of given value if found inside collection and -1 if not
	 *         found or if value equal null
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size(); i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Getting the element at specified index.
	 * 
	 * @param index of searched element
	 * @return Object at specified index
	 * @throws IndexOutOfBoundsException if index invalid aka not in range [0,size-1]
	 */
	public Object get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		return elements[index];
	}

	@Override
	/**
	 * {@inheritDoc} The search is done in O(n).
	 */
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	@Override
	/**
	 * {@inheritDoc} Operation is of linear complexity.
	 */
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		
		for (int i = 0; i < size(); i++) {
			if (elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}

		return false;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(this.elements, size());
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size(); i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * {@inheritDoc} The allocated array is left at current capacity. Complexity of
	 * O(n)
	 * 
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, null);
//		for (int i = 0; i < size; i++) {
//			elements[i] = null;
//		}

		size = 0;
	}

	/**
	 * In linear complexity removes the element at specified index.
	 * 
	 * @param index the position of the element to be removed, in the range [0,
	 *              size-1]
	 * @throws IllegalArgumentException if invalid index given
	 */
	public void remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IllegalArgumentException();
		}

		for (int i = index + 1; i < size(); i++) {
			elements[i - 1] = elements[i];
		}
		elements[size-1] = null;
		size = size - 1;
	}

}
