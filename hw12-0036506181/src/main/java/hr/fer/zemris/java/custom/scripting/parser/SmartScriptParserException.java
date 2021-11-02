package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class models an exception that is being thrown by {@link SmartScriptParser}.
 * 
 * @author Frano Rajič
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@link SmartScriptParserException} with given message.
	 * 
	 * @param msg the message of the exception
	 */
	public SmartScriptParserException(String msg) {
		super(msg);
	}

}
