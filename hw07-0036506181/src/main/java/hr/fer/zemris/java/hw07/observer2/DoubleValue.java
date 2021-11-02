package hr.fer.zemris.java.hw07.observer2;

/**
 * The class models an observer of {@link IntegerStorage} that prints the
 * doubled value of updated value stored in Subject to the standard output and
 * it does so an finite number of times.
 * 
 * @author Frano Rajič
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * How many times the message of the observer has been printed.
	 */
	private int currentNumber;

	/**
	 * How many times the message should be printed
	 */
	private int targetNumber;

	/**
	 * Construct a new observer with given number of times to print the message. An
	 * valid number of times to be printed is positive and nonzero.
	 * 
	 * @param targetNumber The number of times for the message to be printed
	 * @throws IllegalArgumentException if given target number of times is invalid
	 */
	public DoubleValue(int targetNumber) {
		if (targetNumber <= 0) {
			throw new IllegalArgumentException("Number of times to be written must be positive and nonzero!");
		}
		this.targetNumber = targetNumber;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {

		System.out.println("Double value: " + istorage.subject.getValue() * 2);
		currentNumber++;

		if (currentNumber == targetNumber) {
			istorage.subject.removeObserver(this);
		}

	}

}
