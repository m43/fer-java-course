package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class models an collection that holds a given number of prime numbers. The
 * numbers are created lazily - when asked for by user. The only functionality
 * the class offers is iterating through the elements.
 * 
 * @author Frano Rajič
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * The number of primes that the collection holds
	 */
	private final int numberOfPrimes;

	/**
	 * Construct an collection with given number of elements
	 * 
	 * @param i Number of elements for the collection to hold
	 */
	public PrimesCollection(int i) {
		numberOfPrimes = i;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator();
	}

	/**
	 * Class models an iterator for the {@link PrimesCollection} collection
	 * 
	 * @author Frano Rajič
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {

		/**
		 * Index of item to be returned
		 */
		private int index;

		/**
		 * The last prime number generated.
		 */
		int lastPrime = 1;

		@Override
		public boolean hasNext() {
			return numberOfPrimes != index;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No elements left in collection");
			}
			index++;
			while (!isPrime(++lastPrime))
				;
			return lastPrime;
		}

		/**
		 * Help method to check if given number is a prime number
		 * 
		 * @param x The number that needs to be checked.
		 * @return true if given number is prime
		 */
		private boolean isPrime(int x) {
			for (int i = 2; i <= x / 2; i++) {
				if (x % i == 0)
					return false;
			}
			return true;
		}

	}

}
