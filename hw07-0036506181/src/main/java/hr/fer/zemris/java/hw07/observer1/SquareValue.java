package hr.fer.zemris.java.hw07.observer1;

/**
 * The class models an observer of {@link IntegerStorage} that prints the square
 * of updated stored value of Subject to the standard output.
 * 
 * @author Frano Rajič
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int x = istorage.getValue();
		System.out.println("Provided new value: " + x + ", square is " + x * x);
	}

}
