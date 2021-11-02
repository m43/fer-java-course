package hr.fer.zemris.java.hw06.shell;

/**
 * Class models an exception that is thrown if something goes wrong inside shell
 * 
 * @author Frano Rajič
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 3844731367581627444L;

	/**
	 * Throw the exception with an message
	 * @param message message to show
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
}
