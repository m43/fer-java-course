package hr.fer.zemris.java.p12.model;

/**
 * Class represents a model of a poll that contains an ID, a title and a
 * message.
 * 
 * @author Frano Rajič
 */
public class Poll {

	/**
	 * The ID of the poll
	 */
	private long id;

	/**
	 * The title of the poll
	 */
	private String title;

	/**
	 * The message of the poll
	 */
	private String message;

	/**
	 * Construct an empty poll.
	 */
	public Poll() {

	}

	/**
	 * Create a new instance of poll with all necessary information given.
	 * 
	 * @param id      the ID of the poll
	 * @param title   the title of the poll
	 * @param message the message of the poll
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
