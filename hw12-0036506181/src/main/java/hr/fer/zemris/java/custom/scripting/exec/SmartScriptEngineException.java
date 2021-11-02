package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Exception thrown by {@link SmartScriptEngineException} if an error occurs.
 * 
 * @author Frano Rajič
 */
public class SmartScriptEngineException extends RuntimeException {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = -8960812292826783495L;

	/**
	 * Create new exception
	 */
	public SmartScriptEngineException() {
		super();
	}

	/**
	 * Create new exception with given message
	 * 
	 * @param message the message of the exception
	 */
	public SmartScriptEngineException(String message) {
		super(message);
	}

}
