package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class contains an implementation of an parameterized map. One key-value
 * pair is the element that builds up the hashtable, and is stored in a table
 * like structure. The key is mapped to the value. Keys must be unique. The
 * hashcode of the key determines where the entry will be stored, so it is like
 * a random putting of elements into the table. Keys cannot be null, while
 * values can. This class provides as well the functionality of getting,
 * putting, checking if there is a specigied key or value.
 * 
 * @author Frano
 *
 * @param <K> Type of key
 * @param <V> Type of value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * The default capacity of the table created in this specific implementation
	 */
	private final static int DEFAULT_CAPACITY = 16;

	/**
	 * Acceptable ratio of the number of stored entries and the implemented table
	 * size. If the ratio of these two is bigger than the acceptable, the table
	 * needs to be bigger.
	 */
	private final static double ACCEPTABLE_RATIO = 0.75d;

	/**
	 * Array containing references to entries. Table assures that a specific number
	 * of slots is created for the storage of entries, each slot corresponding to
	 * one hash value of stored elements. If slot has an entry allready, other
	 * entries are added to be linked to the entry that is already in the slot.
	 */
	TableEntry<K, V>[] table;

	/**
	 * Variable to store the current number of elements in the structure
	 */
	private int size;

	/**
	 * Variable to hold the number of modifications made to the table. Modifications
	 * are adding and removing elements
	 */
	private long modificationCount;

	/**
	 * Create a new {@link SimpleHashtable}
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];
	}

	/**
	 * Create a new {@link SimpleHashtable} with given initial capacity
	 * 
	 * @param initialCapacity the initial capacity of the hashtable
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Invalid initial capacity given. Capacity must be nonzero and positive");
		}

		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity *= 2;
		}

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * An entry stores the key-value pair. It needs to be created with given key and
	 * value. Offers the functionality of reading both key and value, setting an new
	 * value and linking the entry to some next entry.
	 * 
	 * @author Frano
	 *
	 * @param <K> Type of key
	 * @param <V> Type of value
	 */
	public static class TableEntry<K, V> {

		/**
		 * key of key-value pair
		 */
		private K key;

		/**
		 * value of key-value pair
		 */
		private V value;

		/**
		 * Pointer to the next table entry for creating a linked-list-like structure
		 */
		TableEntry<K, V> next;

		/**
		 * Constructor to create a key-value entry
		 * 
		 * @param key   key to store in entry
		 * @param value value to store in entry
		 */
		public TableEntry(K key, V value) {
			Objects.requireNonNull(key);

			this.key = key;
			this.value = value;
		}

		/**
		 * Constructor to create a key-value entry with pointer to next entry given
		 * 
		 * @param key   key to store in entry
		 * @param value value to store in entry
		 * @param next  pointer to next entry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			Objects.requireNonNull(key);

			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * @return the value corresponding to this entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Replaces the value corresponding to this entry with the specified value.
		 * 
		 * @param value the value to replace with
		 */
		// not sure if this one should be public..
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * @return the key corresponding to this entry.
		 */
		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return "TableEntry [key=" + key + ", value=" + value + "]";
		}

	}

	/**
	 * Method to store given key-value pair. If entry already stored in table, it is
	 * overwritten with new value. For checking if two keys are same, method
	 * equals(other key) is used on the key.
	 * 
	 * @param key   key to store in entry
	 * @param value value to store in entry
	 * @throws NullPointerException if given key is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);

		int hash = hash(key, table.length);

		// slot empty, put an entry into its place
		if (table[hash] == null) {
			table[hash] = new TableEntry<K, V>(key, value);
			newEntryAdded();
			return;
		}

		TableEntry<K, V> runningEntry = table[hash];

		// loop to look if key already in linked list of slot at hash
		while (true) {
			if (runningEntry.getKey().equals(key)) {
				// update value of found entry and return
				runningEntry.setValue(value);
				return;
			}

			if (runningEntry.next == null)
				break;

			runningEntry = runningEntry.next;
		}

		// put element at end
		runningEntry.next = new TableEntry<K, V>(key, value);
		newEntryAdded();
	}

	/**
	 * Method to get the value at key. If key is not in table (or null), null is
	 * returned.
	 * 
	 * @param key to look the value of
	 * @return the value stored at given key and null if key not in table
	 */
	public V get(K key) {
		TableEntry<K, V> e = getEntryAtKey(key);

		if (e == null)
			return null;

		return e.getValue();
	}

	/**
	 * Help method to get an reference to the entry with given key.
	 * 
	 * @param key given key of entry to search for in the table
	 * @return the entry if found, null otherwise
	 */
	private TableEntry<K, V> getEntryAtKey(Object key) {
		if (key == null) {
			return null;
		}

		int hash = hash(key, table.length);

		if (table[hash] == null) {
			return null;
		}

		TableEntry<K, V> runningEntry = table[hash];
		while (runningEntry.next != null) {
			if (runningEntry.getKey().equals(key))
				break;

			runningEntry = runningEntry.next;
		}

		return runningEntry;
	}

	/**
	 * Get the number of entries stored in table
	 * 
	 * @return number of stored entries
	 */
	public int size() {
		return size;
	}

	/**
	 * Check if there is an object with given key stored in this table.
	 * 
	 * @param key to check
	 * @return true if table contains entry with given key
	 */
	public boolean containsKey(Object key) {
		return getEntryAtKey(key) != null;
	}

	/**
	 * Check if table contains given value in any of it's entries
	 * 
	 * @param value to look for
	 * @return true if value found somewhere in table
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> runningEntry = null;
		for (int i = 0; i < table.length; i++) {
			runningEntry = table[i];
			while (runningEntry != null) {
				if (runningEntry.getValue().equals(value)) {
					return true;
				}
				runningEntry = runningEntry.next;
			}
		}

		return false;
	}

	/**
	 * Method to remove the entry of given key if there is an entry of that kind in
	 * the table, otherwise it does nothing.
	 * 
	 * @param key of entry to remove
	 */
	public void remove(Object key) {
		if (key == null)
			return;

		int hash = hash(key, table.length);

		if (table[hash] == null)
			return;

		TableEntry<K, V> runningEntry = table[hash];

		if (runningEntry.getKey().equals(key)) {
			table[hash] = runningEntry.next;
			size--;
			modificationCount++;
			return;
		}

		while (runningEntry.next != null) {
			if (runningEntry.next.getKey().equals(key)) {
				runningEntry.next = runningEntry.next.next;
				size--;
				modificationCount++;
				return;
			}
			runningEntry = runningEntry.next;
		}
	}

	/**
	 * Method to remove all elements
	 */
	public void clear() {
		size = 0;
		modificationCount++;
		Arrays.fill(table, null);
	}

	/**
	 * Check if table does not contain any entries stored
	 * 
	 * @return true if table is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * @return the string representation of the table
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		boolean first = true;

		TableEntry<K, V> runningEntry = null;

		for (int i = 0; i < table.length; i++) {
			runningEntry = table[i];
			while (runningEntry != null) {
				if (first == true) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(runningEntry.getKey() + "=" + runningEntry.getValue());
				runningEntry = runningEntry.next;
			}
		}

		sb.append("]");
		return sb.toString();
	}

	/**
	 * @return a string representation of the table (view of linked entries and
	 *         table slots included!)
	 */
	public String toString2() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		TableEntry<K, V> runningEntry = null;
		for (int i = 0; i < table.length; i++) {

			if (i != 0) {
				sb.append(", ");
			}
			sb.append("\n   [" + i + "](");

			runningEntry = table[i];
			while (runningEntry != null) {
				sb.append("\"" + runningEntry.getKey() + "\":\"" + runningEntry.getValue() + "\"");
				if (runningEntry.next != null) {
					sb.append(" --> ");
				}
				runningEntry = runningEntry.next;
			}

			sb.append(")");
		}

		sb.append("\n}");
		return sb.toString();
	}

	/**
	 * Help function to calculate the hash value of any given key. Used to determine
	 * where an entry is in the table. Performes modulus operation of lenght of
	 * table on the absolute value of the hashcode() of the given key object
	 * 
	 * @param key key to get hash value
	 * @param mod with which number to do the modulus operation
	 * @return the calculated hash
	 */
	private int hash(Object key, int mod) {
		return Math.abs(key.hashCode()) % mod;
	}

	/**
	 * Method should be called if a new element is added. This function will after
	 * doing size++ check if the table needs to be made bigger
	 */
	private void newEntryAdded() {
		size++;
		modificationCount++;

		if ((double) size() / (double) table.length > ACCEPTABLE_RATIO) {
			createBiggerTable();
		}
	}

	/**
	 * Method to create a bigger table. Size of reallocated table is two times the
	 * previous size
	 */
	private void createBiggerTable() {
		table = Arrays.copyOf(table, table.length * 2);

		TableEntry<K, V> runningEntry = null;
		for (int i = 0; i < table.length / 2; i++) {
			runningEntry = table[i];

			while (runningEntry != null && hash(runningEntry.key, table.length) != i) {
				table[i] = runningEntry.next;
				size--;
				put(runningEntry.getKey(), runningEntry.getValue());
				runningEntry = table[i];
			}

			while (runningEntry != null && runningEntry.next != null) {
				if (hash(runningEntry.next.key, table.length) != i) {
					size--;
					put(runningEntry.next.getKey(), runningEntry.next.getValue());
					runningEntry.next = runningEntry.next.next;
				} else {
					runningEntry = runningEntry.next;
				}
			}
		}
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Class implementing an iterator. Created iterator can go only one way through
	 * the data structure and returns the elements it meets. Functionality of
	 * removing the last removed element is also added. Modification of hashtable is
	 * taken into account and when any method from the iterator is called, it checks
	 * if the modification count has changed since the time the iterator was made.
	 * 
	 * @author Frano
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Slot where the currently returned entity is
		 */
		int currentSlot;

		/**
		 * Prevoius slot
		 */
		int previousSlot;

		/**
		 * Indexed of the entry last time returned
		 */
		int currentEntryIndex;

		/**
		 * Was the returned entry the first one in a slot
		 */
		boolean firstInSlot;

		/**
		 * Variable for holding information if an removing has already been done
		 */
		boolean removable;

		/**
		 * Variable to store the modificatio number of the table when the iterator was
		 * first created
		 */
		long modCount;

		/**
		 * Entry used in moving through the table when getting and removing elements
		 */
		TableEntry<K, V> currentEntry;

		/**
		 * Entry used in moving through the table when getting and removing elements
		 */
		TableEntry<K, V> previousEntry;

		/**
		 * Construct an iterator
		 */
		public IteratorImpl() {
			firstInSlot = true;
			modCount = modificationCount;
		}

		/**
		 * @return true if iterator has more elements to return
		 */
		public boolean hasNext() {
			checkModificationCount();

			return size() != currentEntryIndex;
		}

		/**
		 * @return the next entry for the iterator to return
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			currentEntryIndex++;
			previousEntry = currentEntry;
			previousSlot = currentSlot;

			if (currentEntry != null) {
				if (currentEntry.next != null) {
					firstInSlot = false;
					currentEntry = currentEntry.next;
				} else {
					currentEntry = null;
					firstInSlot = true;
					while (currentEntry == null) {
						currentEntry = table[currentSlot++];
					}
				}
			}

			while (currentEntry == null) {
				currentEntry = table[currentSlot++];
			}

			removable = true;
			return currentEntry;
		}

		/**
		 * Remove the entry from hashtable that the iterator returned last time
		 */
		public void remove() {
			checkModificationCount();
			if (!removable) {
				throw new IllegalStateException("This element cannot be removed");
			}

			// i could've implemented this in some more simple way (delegating operations to
			// other methods in SimpleHashTable, but for this one I know
			// that it performs decent in time complexity analysis.

			size--;
			currentEntryIndex--;
			modCount++;
			modificationCount++;
			removable = false;

			if (firstInSlot) {
				table[currentSlot - 1] = currentEntry.next;
			} else {
				previousEntry.next = currentEntry.next;
			}
			currentEntry = previousEntry;
			currentSlot = previousSlot;
			previousEntry = null;
		}

		/**
		 * Help method used to check if the hashtable has been changed by adding or
		 * removing elements
		 * 
		 * @throws ConcurrentModificationException if hashtable size changed
		 */
		private void checkModificationCount() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException("Do not modify the table while an iterator iterates");
			}
		}

	}

}
