package hr.fer.zemris.java.hw07.observer2;

/**
 * Class provides the functionality of accessing the new and old value of
 * {@link IntegerStorage} object when a change of the value occurs.
 * 
 * @author Frano Rajič
 */
@SuppressWarnings("unused")
public class IntegerStorageChange {

	/**
	 * A reference to the {@link IntegerStorage} object
	 */
	IntegerStorage subject;

	/**
	 * Read only old value, equal to the value before change
	 */
	private final int oldValue;

	/**
	 * Read only new value, equal to the value after the change has occured
	 */
	private final int newValue;

	/**
	 * Construct a new {@link IntegerStorageChange} object
	 * 
	 * @param subject The reference to original storage class
	 * @param oldValue The value stored before the change
	 * @param newValue The value stored after the change
	 */
	public IntegerStorageChange(IntegerStorage subject, int oldValue, int newValue) {
		this.subject = subject;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

}
