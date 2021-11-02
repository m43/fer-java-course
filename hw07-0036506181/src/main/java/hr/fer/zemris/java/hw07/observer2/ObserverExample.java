package hr.fer.zemris.java.hw07.observer2;

/**
 * Demo program demonstrates the use of an {@link IntegerStorage} object with
 * it's observers of type {@link IntegerStorageObserver}.
 * 
 * @author Frano Rajič
 */
public class ObserverExample {

	/**
	 * The entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);

		// adding each observer once at the beginning
		istorage.addObserver(new SquareValue());		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));

		//adding values to Subject
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}