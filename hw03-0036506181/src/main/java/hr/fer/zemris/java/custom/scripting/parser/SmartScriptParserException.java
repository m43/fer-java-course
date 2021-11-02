package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class models an exception that gets thrown by {@link SmartScriptParser}.
 * 
 * @author Frano
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@link SmartScriptParserException}.
	 * 
	 * @param string the message of the exception
	 */
	public SmartScriptParserException(String string) {
		super(string);
	}

}
