package hr.fer.zemris.java.hw05.lexer;

/**
 * If an Lexer comes upon something invalid or some other inconsistency it can
 * recognize, it will throw this error to indicate that something went wrong.
 * 
 * @author Frano Rajič
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * The default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct an exception
	 */
	public LexerException() {

	}

	/**
	 * Create an exception with given message
	 * 
	 * @param message message to go with exception
	 */
	public LexerException(String message) {
		super(message);
	}

}