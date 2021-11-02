package hr.fer.zemris.java.hw07.observer2;

/**
 * The class models an observer of {@link IntegerStorage} that prints the number
 * of time the stored value has changed to the system output.
 * 
 * @author Frano Rajič
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Number of times the stored value has changed
	 */
	private int numberOfChanges;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		numberOfChanges++;
		System.out.println("Number of value changes since tracking: " + numberOfChanges);

	}

}
