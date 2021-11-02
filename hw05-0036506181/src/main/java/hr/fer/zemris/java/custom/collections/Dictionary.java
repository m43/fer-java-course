package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Collection to store key-value elements. Each key is unique, but more keys can
 * store the same value. For storing of elements another collection is used,
 * this being only an Adaptor.
 * 
 * @author Frano
 *
 * @param <K> key of key-value pair
 * @param <V> value of key-value pair
 */
public class Dictionary<K, V> {

	/**
	 * Nested help class used to store individual entries of key-value pairs. Key
	 * cannot be null, though the value can.
	 * 
	 * @author Frano
	 *
	 * @param <K> key of key-value pair
	 * @param <V> value of key-value pair
	 */
	private static class Entry<K, V> {

		/**
		 * key of key-value pair
		 */
		private K key;

		/**
		 * value of key-value pair
		 */
		private V value;

		/**
		 * Constructor to create a key-value pair with both ends given
		 * 
		 * @param key   key to store in entry
		 * @param value value to store in entry
		 */
		public Entry(K key, V value) {
			Objects.requireNonNull(key);

			this.key = key;
			this.value = value;
		}

		/**
		 * Get the value of the key-value pair
		 * 
		 * @return the value of the key-value pair
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Set the value of the key-value pair
		 * 
		 * @param value the value of the key-value pair
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Get the key of the key-value pair
		 * 
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

	}

	/**
	 * Collection used for storing dictionary entries
	 */
	private ArrayIndexedCollection<Entry<K, V>> data;

	/**
	 * Construct a dictionary
	 */
	public Dictionary() {
		data = new ArrayIndexedCollection<>();
	}

	/**
	 * Check if the dictionary is empty.
	 * 
	 * @return true if the dictionary contains no pairs
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Get the number of stored key-value pairs.
	 * 
	 * @return the number of currently stored key-value pairs
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Clear all key-value pairs in dictionary
	 */
	public void clear() {
		data.clear();
	}

	/**
	 * Put an new value into the dictionary. If key is already in the dictionary,
	 * then overwrite the currently stored value. Key cannot be null!
	 * 
	 * @param key   of key-value pair
	 * @param value of key-value pair
	 * @throws NullPointerException if given key value is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);

		Entry<K, V> entry = getEntry(key);

		if (entry != null) {
			entry.setValue(value);
			return;
		}

		data.add(new Entry<K, V>(key, value));
	}

	/**
	 * Gets the element with specified key.
	 * 
	 * @param key of object to get the value of
	 * @return the value of the queried object or null otherwise
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		Entry<K, V> entry = getEntry(key);

		if (entry == null)
			return null;

		return entry.getValue();
	}

	/**
	 * Help method to look for an entry with specified key or return null if no
	 * entry of that key in dictionary
	 * 
	 * @param key of entry to look for
	 * @return the entry with specified key id found or null otherwise
	 */
	private Entry<K, V> getEntry(Object key) {
		if (key == null) {
			return null;
		}

		ElementsGetter<Entry<K, V>> e = data.createElementsGetter();

		Entry<K, V> currentElement;
		while (e.hasNextElement()) {
			currentElement = e.getNextElement();
			if (currentElement.getKey().equals(key)) {
				return currentElement;
			}
		}

		return null;
	}
}
