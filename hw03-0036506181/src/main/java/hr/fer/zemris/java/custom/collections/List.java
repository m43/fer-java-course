package hr.fer.zemris.java.custom.collections;

/**
 * Inteface is a specific case of interface Collection where elements are stored
 * in a indexed structure and therefore include the functionality of getting,
 * removing and inserting elements at specified index
 * 
 * @author Frano
 *
 */
public interface List extends Collection {

	/**
	 * Get the element with given index
	 * 
	 * @param index the index of the element to get
	 * @return the element at given index
	 * @throws IndexOutOfBoundsException if invalid index given
	 */
	Object get(int index);

	/**
	 * Inserts (does not overwrite) the given value at the given position in by
	 * shifting other elements one place towards the end.
	 * 
	 * @param value    Object to be inserted
	 * @param position where the element needs to be placed. Valid positions are 0
	 *                 to size (both are included)
	 * @throws IndexOutOfBoundsException if given position is invalid
	 * @throws NullPointerException      if given object is a null reference
	 */
	void insert(Object value, int position);

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. null is a valid argument. The
	 * equality is determined using the equals method.
	 * 
	 * @param value of the searched element
	 * @return the index of the element if found, -1 otherwise
	 */
	int indexOf(Object value);

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1.
	 * 
	 * @param index the element that needs to be removed from collection
	 * @throws IndexOutOfBoundsException if invalid index given
	 */
	void remove(int index);

}
