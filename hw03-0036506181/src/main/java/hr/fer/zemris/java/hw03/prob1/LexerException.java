package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception thrown when something goes wrong with Lexer object
 * 
 * @author Frano
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * The default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@link LexerException}
	 */
	public LexerException() {

	}

	/**
	 * Create a new {@link LexerException} with given message
	 * 
	 * @param message the message of the thrown exception
	 */
	public LexerException(String message) {
		super(message);
	}
}
