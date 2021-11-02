package hr.fer.zemris.java.hw17.jvdraw.geometry.editor;

/**
 * Exception thrown if an invalid edit of a geometric object is requested.
 * 
 * @author Frano Rajič
 */
public class GemetricalObjectInvalidEditException extends RuntimeException {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = 6617910023175822876L;

	/**
	 * Create an exception with given string message
	 * 
	 * @param message the exception message
	 */
	public GemetricalObjectInvalidEditException(String message) {
		super(message);
	}

}
