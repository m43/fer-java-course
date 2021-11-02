package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class stores elements in a linked list like structure not visible to client.
 * The general contract of this collection is that duplicate elements are
 * allowed (each of those elements will be held in different list node) and
 * storage of null references are not allowed. The class provides the
 * functionality of accessing elements, searching, removing in linear time
 * complexity and adding in constant time.
 * 
 * @author Frano
 * 
 * @param <E> The element type to be stored in the collection
 */

public class LinkedListIndexedCollection<E> implements List<E> {

	/**
	 * Class to be used to store node of constructed structure in collection
	 * 
	 * @author Frano
	 * @param <E> The element type to be stored in the node
	 *
	 */
	private static class ListNode<E> {
		/**
		 * Reference to previous element
		 */
		public ListNode<E> previous;
		/**
		 * Reference to next element
		 */
		public ListNode<E> next;

		/**
		 * Value of stored object in node
		 */
		public E value;

		/**
		 * Basic node constructor initializing the value of stored object right away
		 * 
		 * @param value the value to initialize with
		 */
		public ListNode(E value) {
			this.value = value;
		}

//		public ListNode(ListNode<E> previous, ListNode<E> next,E value) {
//			this.previous = previous;
//			this.next = next;
//			this.value = value;
//		}

	}

	/**
	 * The current size of collection aka number of elements actually stored aka
	 * number of elements in linked list
	 */
	private int size;

	/**
	 * Reference to the first node of the linked list,
	 */
	private ListNode<E> first;

	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode<E> last;

	/**
	 * Variable to hold number of modifications done on the collection
	 */
	private long modificationCount;

	/**
	 * Constructor to initialize an empty linked list structure
	 */
	public LinkedListIndexedCollection() {
		// first=last=null is done automatically in java
	}

	/**
	 * Constructor to add all elements of given structure right away to created
	 * structure
	 * 
	 * @param other Collection to copy elements from
	 * @throws NullPointerException if given collection is a null reference
	 */
	public LinkedListIndexedCollection(Collection<? extends E> other) {
		if (other == null) {
			throw new NullPointerException();
		}

		addAll(other);
	}

	@Override
	/**
	 * Method to return current number of elements stored in the collection
	 */
	public int size() {
		return size;
	}

	@Override
	/**
	 * Adds the given object into this collection at the end of the collection;
	 * newly added element becomes the element at the biggest index. Done in time
	 * complexity O(1). The method refuses to add null as element
	 * 
	 * @param value Object to add into collection
	 * @throws NullPointerException if given value is a null reference
	 */
	public void add(E value) {
		Objects.requireNonNull(value);

		ListNode<E> newNode = new ListNode<>(value);
		if (size == 0) {
			first = last = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}

		modificationCount++;
		size++;
	}

	// just a help method
	/**
	 * Return the object with given index in linear time complexity. A valid index
	 * is in range [0, size-1]
	 * 
	 * @param index the index of the object that is being asked for
	 * @return the reference to the object that is being looked up
	 * @throws IndexOutOfBoundsException if given invalid index
	 */
	private ListNode<E> nodeAtIndex(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode<E> startNode;
		if (index <= size() / 2) {
			startNode = first;
			for (int i = 0; i != index; i++) {
				startNode = startNode.next;
			}
		} else {
			startNode = last;
			for (int i = size() - 1; i != index; i--) {
				startNode = startNode.previous;
			}
		}
		return startNode;
	}

	/**
	 * {@inheritDoc} O(n) time complexity.
	 */
	public void insert(E value, int position) {
		Objects.requireNonNull(value);

		if (position < 0 || position > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (position == size()) {
			add(value);
			return;
		}

		ListNode<E> nodeAtPosition = nodeAtIndex(position);
		ListNode<E> newNode = new ListNode<>(value);
		if (position == 0) {
			first = newNode;
		}

		newNode.previous = nodeAtPosition.previous;
		newNode.next = nodeAtPosition;

		if (nodeAtPosition.previous != null) {
			nodeAtPosition.previous.next = newNode;
		}
		nodeAtPosition.previous = newNode;

		modificationCount++;
		size++;
	}

	/**
	 * {@inheritDoc} Done in linear time complexity.
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		return nodeAtIndex(index).value;
	}

	/**
	 * {@inheritDoc} Done in linear time complexity.
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		ListNode<E> iteratingNode = first;
		for (int i = 0; i < size(); i++) {
			if (iteratingNode.value.equals(value)) {
				return i;
			}
			iteratingNode = iteratingNode.next;
		}

		return -1;
	}

	@Override
	/**
	 * {@inheritDoc} The search is done in O(n).
	 */
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}

	/**
	 * {@inheritDoc} Operation done in linear time complexity.
	 */
	public Object[] toArray() {
		Object[] newArray = new Object[size()];

		int i = 0;
		for (ListNode<E> node = first; node != null; node = node.next) {
			newArray[i++] = node.value;
		}
		return Arrays.copyOf(newArray, size());
	}

	@Override

	/**
	 * {@inheritDoc} Operation done in linear time complexity
	 */
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index == -1) {
			return false;
		}

		remove(index);
		return true;
	}

	/**
	 * {@inheritDoc} Done in linear time complexity
	 */
	public void remove(int index) {
		ListNode<E> removedNode = nodeAtIndex(index);

		if (index == 0) {
			first = removedNode.next;
		}

		if (index == size() - 1) {
			last = removedNode.previous;
		}

		if (removedNode.previous != null) {
			removedNode.previous.next = removedNode.next;
		}
		if (removedNode.next != null) {
			removedNode.next.previous = removedNode.previous;
		}

		modificationCount++;
		size--;
	}

	/**
	 * Removes all elements from the collection in linear time complexity
	 */
	public void clear() {
		first = last = null;
		modificationCount++;
		size = 0;
	}

	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new LinkedListElementsGetter<E>(this);
	}

	/**
	 * Help class used to create an Elements getter
	 * 
	 * @author Frano
	 * @param <E> The element type stored in the collection
	 */
	private static class LinkedListElementsGetter<E> implements ElementsGetter<E> {

		/**
		 * Reference to node that is going to be returned next
		 */
		private ListNode<E> currentElement;

		/**
		 * Modification count of collection at time of construction
		 */
		private long savedModificationCount;

		/**
		 * Collection to get elements from
		 */
		LinkedListIndexedCollection<E> collection;

		/**
		 * To construct an instance of this class a collection is provided
		 * 
		 * @param c collection to get elements from
		 */
		public LinkedListElementsGetter(LinkedListIndexedCollection<E> c) {
			currentElement = c.first;
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
			return currentElement != null;
		}

		@Override
		public E getNextElement() {
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}

			E nextElement = currentElement.value;
			currentElement = currentElement.next;

			return nextElement;
		}

	}

}
