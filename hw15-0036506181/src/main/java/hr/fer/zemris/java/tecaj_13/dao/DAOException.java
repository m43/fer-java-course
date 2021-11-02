package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Class models exceptions being thrown by {@link DAO} implementations when
 * something goes wrong.
 * 
 * @author Frano Rajič
 */

public class DAOException extends RuntimeException {

	/**
	 * serialization.
	 */
	private static final long serialVersionUID = -9084128150437631395L;

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
}