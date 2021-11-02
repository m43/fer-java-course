package hr.fer.zemris.java.p12.dao;

/**
 * Class models exceptions being thrown by {@link DAO} implementations when
 * something goes wrong.
 * 
 * @author Frano Rajič
 */
public class DAOException extends RuntimeException {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new exception
	 */
	public DAOException() {
	}

	/**
	 * Create new exception with given message, cause of the exception and by
	 * specifying whether suppression should be enabled and whether writeable stack
	 * trace enabled
	 * 
	 * @param message            the message
	 * @param cause              the cause of the exception
	 * @param enableSuppression  is suppression enabled
	 * @param writableStackTrace is the writable stack trace enabled
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Create a new exception with given message and cause
	 * 
	 * @param message the message
	 * @param cause   the cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create a new {@link DAOException} with given message
	 * 
	 * @param message the message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Create a new {@link DAOException} with given cause
	 * 
	 * @param cause the cause of the exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}