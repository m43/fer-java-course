package hr.fer.zemris.java.gui.layouts;

/**
 * Class models the exception that gets thrown if an error in {@link CalcLayout}
 * occurs.
 * 
 * @author Frano Rajič
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crate a new {@link CalcLayoutException}.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Create new {@link CalcLayoutException} with more details given.
	 * 
	 * @param message            message of exception
	 * @param cause              what was the cause
	 * @param enableSuppression  is suppression enabled
	 * @param writableStackTrace is stack writable
	 */
	public CalcLayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Crate a new {@link CalcLayoutException} with given message and cause.
	 * 
	 * @param message message of exception
	 * @param cause   the cause of the exception
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Crate a new {@link CalcLayoutException} with given message
	 * 
	 * @param message The message of the exception
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Crate a new {@link CalcLayoutException} with given cause.
	 * 
	 * @param cause the cause of the exception
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

}
