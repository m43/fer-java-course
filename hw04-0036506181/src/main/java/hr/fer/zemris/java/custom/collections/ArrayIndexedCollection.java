package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class provides the functionality of storing elements in a array
 * structure. Duplicate elements are allowed, but storage of null references is
 * not allowed.
 * 
 * @author Frano
 * @version 1.0
 * 
 * @param <E> the element to be stored in the collection
 */

public class ArrayIndexedCollection<E> implements List<E> {

	/**
	 * Stores the current size of the collection (number of elements actually stored
	 * in elements array)
	 */
	private int size;

	/**
	 * Variable to hold number of modifications done on the collection
	 */
	private long modificationCount;

	/**
	 * The default capacity
	 */
	private final static int DEFAULT_CAPACITY = 16;

	/**
	 * An array to store elements of the collection.
	 */
	private E[] elements;

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
	public ArrayIndexedCollection(Collection<? extends E> other) {
		this(other, other.size());
	}

	/**
	 * Constructing a collection with given initial capacity of at least 1 element
	 * 
	 * @param initialCapacity of constructed collection
	 * @throws IllegalArgumentException if invalid capacity given
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		elements = (E[]) new Object[initialCapacity];
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends E> other, int initialCapacity) {
		Objects.requireNonNull(other);

		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		int otherSize = other.size();
		if (otherSize > initialCapacity) {
			elements = (E[]) new Object[otherSize];
		} else {
			elements = (E[]) new Object[initialCapacity];
		}

		addAll(other);
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection in average time complexity of
	 * O(1). If full capacity is reached, the capacity of the array is doubled.
	 * 
	 * @param value Object to add into the collection
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(E value) {
		Objects.requireNonNull(value);

		int indexOfNextSpot = size();
		if (elements.length <= indexOfNextSpot) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}

		size++;
		modificationCount++;
		elements[indexOfNextSpot] = value;
	}

	/**
	 * {@inheritDoc} O(n) time complexity.
	 */
	public void insert(E value, int position) {
		Objects.requireNonNull(value);

		if (position < 0 || position > size()) {
			throw new IndexOutOfBoundsException();
		}

		E temporaryObject = value;
		E swappingObject;

		// this for loop is bubble-sort-like
		for (int i = position; i < size(); i++) {
			// It would be easier to use this
			// (eventhough I've got a bit better time complexity):
			// System.arraycopy(src, srcPos, dest, destPos, length);
			// src − This is the source array.
			// srcPos − This is the starting position in the source array.
			// dest − This is the destination array.
			// destPos − This is the starting position in the destination data.
			// length − This is the number of array elements to be copied.
			swappingObject = elements[i];
			elements[i] = temporaryObject;
			temporaryObject = swappingObject;

		}
		// the last element to be added
		add(temporaryObject);
	}

	/**
	 * {@inheritDoc} Done in linear time complexity.
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
	 * {@inheritDoc} Operation done in O(1) time complexity
	 */
	public E get(int index) {
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
	 * {@inheritDoc} Operation is of linear time complexity.
	 */
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}

		for (int i = 0; i < size(); i++) {
			if (elements[i].equals(value)) {
				remove(i);
				modificationCount++;
				return true;
			}
		}

		return false;
	}

	@Override
	public Object[] toArray() {
		// ?? not sure if it is a good idea to just copy the private E[] array
		return Arrays.copyOf(this.elements, size());
	}

	/**
	 * {@inheritDoc} The allocated array is left at current capacity. Time
	 * complexity of O(n)
	 * 
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, null);
//		for (int i = 0; i < size; i++) {
//			elements[i] = null;
//		}

		modificationCount++;
		size = 0;
	}

	/**
	 * {@inheritDoc} Done in linear time complexity.
	 */
	public void remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IllegalArgumentException();
		}

		for (int i = index + 1; i < size(); i++) {
			elements[i - 1] = elements[i];
		}
		elements[size - 1] = null;
		modificationCount++;
		size = size - 1;
	}

	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ArrayIndexedElementsGetter<E>(this);
	}

	/**
	 * Help class providing functionality of getting elements of a
	 * {@link ArrayIndexedCollection} by implementing {@link ElementsGetter}.
	 * 
	 * @author Frano Rajič
	 * @param <T> the element stored in the collection
	 */
	private static class ArrayIndexedElementsGetter<T> implements ElementsGetter<T> {

		/**
		 * Index of element that the ElementsGetter is currently looking at which is the
		 * same element that is going to be returned next
		 */
		private int index;

		/**
		 * Modification count of collection at time of this objects construction
		 */
		private long savedModificationCount;

		/**
		 * Collection to get elements from
		 */
		ArrayIndexedCollection<T> collection;

		/**
		 * To construct an instance of this class a collection is provided
		 * 
		 * @param c collection to get elements from
		 */
		public ArrayIndexedElementsGetter(ArrayIndexedCollection<T> c) {
			index = 0;
			collection = c;
			savedModificationCount = c.modificationCount;
		}

		@Override
		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException if collection has structure
		 *                                         modifications
		 */
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return index < collection.size();
		}

		@Override
		public T getNextElement() {
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}

			return collection.get(index++);
		}

	}

}
