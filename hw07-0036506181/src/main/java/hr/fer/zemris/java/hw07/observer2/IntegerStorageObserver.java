package hr.fer.zemris.java.hw07.observer2;

/**
 * Class models an observer of {@link IntegerStorage}.
 * 
 * @author Frano Rajič
 */
public interface IntegerStorageObserver {

	/**
	 * Method to be called when the stored value of the linked
	 * {@link IntegerStorage} Subject has changed.
	 * 
	 * @param istorage The Subject that has been linked to the concrete observer object.
	 */
	public void valueChanged(IntegerStorageChange istorage);
}