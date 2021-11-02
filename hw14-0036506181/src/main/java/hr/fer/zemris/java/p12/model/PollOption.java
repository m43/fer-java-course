package hr.fer.zemris.java.p12.model;

/**
 * Class represents a model of a poll option that contains an ID, a poll ID, a
 * title, a link and a counter of votes.
 * 
 * @author Frano Rajič
 */
public class PollOption {

	/**
	 * the id of the poll option
	 */
	private long id;

	/**
	 * the poll id of the poll related to this poll option
	 */
	private long pollId;

	/**
	 * the title of the poll option
	 */
	private String title;

	/**
	 * the link of the poll option
	 */
	private String link;

	/**
	 * the number of votes for this poll option
	 */
	private long numberOfVotes;

	/**
	 * Construct an empty poll option
	 */
	public PollOption() {

	}

	/**
	 * Create a constructor with all necessary information given to create a
	 * complete poll option
	 * 
	 * @param id            the id of the poll option
	 * @param pollId        the poll id of the poll related to this poll option
	 * @param title         the title of the poll option
	 * @param link          the link of the poll option
	 * @param numberOfVotes the number of votes for this poll option
	 */
	public PollOption(long id, long pollId, String title, String link, long numberOfVotes) {
		super();
		this.id = id;
		this.pollId = pollId;
		this.title = title;
		this.link = link;
		this.numberOfVotes = numberOfVotes;
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
	 * @return the pollId
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
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
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the numberOfVotes
	 */
	public long getNumberOfVotes() {
		return numberOfVotes;
	}

	/**
	 * @param numberOfVotes the numberOfVotes to set
	 */
	public void setNumberOfVotes(long numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

}
