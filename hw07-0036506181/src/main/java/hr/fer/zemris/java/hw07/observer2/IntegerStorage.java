package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * The class models an integer storing object, providing the functionality of
 * modifying the stored value and registering observers of type
 * {@link IntegerStorageObserver}.
 * 
 * @author Frano Rajič
 */
public class IntegerStorage {
	/**
	 * The value that is stored by the object.
	 */
	private int value;

	/**
	 * List of registered observers
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>();

	/**
	 * Create an new instance with given value
	 * 
	 * @param initialValue Initial value to be stored.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Register an observer for this Subject.
	 * 
	 * @param observer The observer to be registered.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers = new ArrayList<>(observers);
			observers.add(observer);
		}
	}

	/**
	 * Remove the given observer. Equal to unregistering the observer from the
	 * Subject.
	 * 
	 * @param observer The observer to be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers = new ArrayList<>(observers);
			observers.remove(observer);
		}
	}

	/**
	 * Clear all the observers. All observers are now unregistered from this
	 * Subject.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Get the currently stored value
	 * 
	 * @return the stored value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Update the stored value. Observers are notified for the changed if the new
	 * value is different from the last stored value.
	 * 
	 * @param value new value to be stored
	 */
	public void setValue(int value) {
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
			}
		}
	}
}