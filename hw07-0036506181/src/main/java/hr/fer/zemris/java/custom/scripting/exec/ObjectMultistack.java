package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Class provides the functionality of storing values in {@link ValueWrapper} on
 * multiple stacks, with all operations of adding, peeking and popping elements
 * all in O(1) complexity.
 * 
 * @author Frano Rajič
 */
public class ObjectMultistack {

	/**
	 * Internal structure used to store all the stacks created
	 */
	private Map<String, MultistackEntry> stacks = new TreeMap<>();

	/**
	 * Help class models the entries that will be stored in
	 * {@link ObjectMultistack}. Entries are stored in a single-linked list
	 * 
	 * @author Frano Rajič
	 */
	private static class MultistackEntry {

		/**
		 * Reference to next value
		 */
		private MultistackEntry next;

		/**
		 * The value stored in entry.
		 */
		ValueWrapper value;

		/**
		 * Instantiate an instance with given value
		 * 
		 * @param value To be set upon construction
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
		}

		/**
		 * Get the reference to the next entry
		 * 
		 * @return the next entry
		 */
		MultistackEntry getNext() {
			return next;
		}

		/**
		 * Set the reference to next entry
		 * 
		 * @param next the reference to the next entry
		 */
		void setNext(MultistackEntry next) {
			this.next = next;
		}

		/**
		 * Get the value stored in the entry.
		 * 
		 * @return the value of the entry
		 */
		private ValueWrapper getValue() {
			return value;
		}

		/**
		 * Set the value for the entry to hold
		 * 
		 * @param value The value for the entry to hold
		 */
		@SuppressWarnings("unused")
		private void setValue(ValueWrapper value) {
			this.value = value;
		}

	}

	/**
	 * Push the given value onto the stack that is associated with given key name
	 * 
	 * @param keyName      The name associated with a specific stack
	 * @param valueWrapper The value to be stored on stack
	 * @throws NullPointerException if given key or value is null
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		Objects.requireNonNull(valueWrapper);

		MultistackEntry pushedElement = new MultistackEntry(valueWrapper);
		pushedElement.setNext(stacks.get(keyName));
		stacks.put(keyName, pushedElement);
	}

	/**
	 * Pop (remove and return) the last element stored on top of stack with given
	 * key name.
	 * 
	 * @param keyName the name associated with a specific stack
	 * @return the popped value
	 * @throws EmptyStackException if trying to pop an element from an empty stack
	 */
	public ValueWrapper pop(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException();
		}

		MultistackEntry poppedElement = stacks.get(keyName);
		MultistackEntry next = poppedElement.getNext();

		if (next == null) {
			stacks.remove(keyName);
		} else {
			stacks.put(keyName, next);
		}

		return poppedElement.getValue();
	}

	/**
	 * Return an reference to the last value stored on stack with given key name
	 * 
	 * @param keyName the name associated with a specific stack
	 * @return the element on stack that is associated with given key name
	 * @throws EmptyStackException if trying to peek an empty stack
	 */
	public ValueWrapper peek(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException();
		}

		return stacks.get(keyName).getValue();
	}

	/**
	 * Check if stack with given key name is empty
	 * 
	 * @param keyName The name of the key that holds the wanted stack
	 * @return true if stack is empty
	 */
	public boolean isEmpty(String keyName) {
		return !stacks.containsKey(keyName);
	}

}
