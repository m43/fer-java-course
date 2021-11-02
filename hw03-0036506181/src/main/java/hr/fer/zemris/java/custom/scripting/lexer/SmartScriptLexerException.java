package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception thrown when something goes wrong with Lexer object
 * 
 * @author Frano
 *
 */
public class SmartScriptLexerException extends RuntimeException {
	/**
	 * The default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@link SmartScriptLexerException}.
	 */
	public SmartScriptLexerException() {

	}

	/**
	 * Create a new {@link SmartScriptLexerException} with given message.
	 * 
	 * @param message the message of the exception
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

}