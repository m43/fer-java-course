package hr.fer.zemris.java.custom.collections;

/**
 * Class provides client with the functionality of general stack like
 * collection, with most important functions pop, push, peek and clear. For
 * storing of elements another collection is used, this being only an Adaptor
 * class.
 * 
 * @author Frano
 *
 */
public class ObjectStack {

	/**
	 * Structure used for the storage of stack elements
	 */
	private ArrayIndexedCollection collection;

	/**
	 * Default collector creates the stack collection
	 */
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}

	/**
	 * Method to check if the stack is empty.
	 * 
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Looks up the size of the stack.
	 * 
	 * @return the size of the stack.
	 */
	public int size() {
		return collection.size();
	}

	/**
	 * The method adds one element on top of stack in constant complexity.
	 * 
	 * @param value of element to be added
	 * @throws NullPointerException if given value is null
	 */
	public void push(Object value) {
		// add will throw the NullPointerException, so there should be no need to
		// duplicate that code here
		collection.add(value);
	}

	/**
	 * Removes the last value pushed onto the stack and returns it. The stack should
	 * not be empty.
	 * 
	 * 
	 * @return the element on top of the stack.
	 * @throws EmptyStackException if stack contains no elements
	 */
	public Object pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		Object popElement = peek();
		collection.remove(size() - 1);
		return popElement;
	}

	/**
	 * Peeking into the stack returns the last element placed on stack but does not
	 * delete it from stack.
	 * 
	 * @return the value of the element on top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return collection.get(size() - 1);
	}

	/**
	 * Remove all elements from stack.
	 */
	public void clear() {
		collection.clear();
	}

}
