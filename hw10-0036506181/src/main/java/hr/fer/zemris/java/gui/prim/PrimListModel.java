package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * An list model that stores prime Integers by creating them incrementally.
 * Implements the {@link ListModel} and can therefore register
 * {@link ListDataListener} listeners.
 * 
 * @author Frano Rajič
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Collection that stores the created primes
	 */
	private ArrayList<Integer> data = new ArrayList<>();

	/**
	 * List of listeners of this model.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();

	/**
	 * The last calculated prime number. Initialized to 1 so that the first
	 * calculated prime number will be 2.
	 */
	private int currentPrime = 1;

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return data.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		if (!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		if (listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.remove(l);
		}
	}

	/**
	 * Method to create the next prime, add it to the data collection of the model
	 * and notify all listeners that an event happened.
	 * 
	 */
	public void next() {
		while (!isPrime(++currentPrime))
			;
		data.add(currentPrime);

		int index = data.size();
		for (ListDataListener l : listeners) {
			l.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
		}
	}

	/**
	 * Help method to determine if the given number is a prime number. 1 is
	 * considered a prime.
	 * 
	 * @param x the number to check, must be nonnegative
	 * @return true if given number is prime
	 * @throws IllegalArgumentException if given is not nonnegative
	 */
	private boolean isPrime(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("Can only check positive numbers.");
		}
		if (x < 4) {
			return true;
		}

		int root = (int) Math.sqrt(x);
		for (int i = 5; i <= root; i++) {
			if (x % i == 0) {
				return false;
			}
		}
		return true;
	}

}
