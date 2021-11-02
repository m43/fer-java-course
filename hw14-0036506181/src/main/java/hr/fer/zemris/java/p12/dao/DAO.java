package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Interface to the subsystem for persistence of data.
 * 
 * @author Frano Rajič
 */
public interface DAO {

	/**
	 * Method gets all polls in the database but fills the poll objects only with ID
	 * and title, not with the rest of the data.
	 * 
	 * @return an list containing all the polls in the database, with ID and title
	 *         set
	 * @throws DAOException thrown if an error occurs
	 */
	List<Poll> getPolls() throws DAOException;

	/**
	 * Method to load the poll with given ID from database.
	 * 
	 * @param id the id of the poll
	 * @return the poll object loaded from database
	 * @throws DAOException thrown if an error occurs
	 */
	Poll getPoll(long id) throws DAOException;

	/**
	 * Method to get all the poll options of the poll specified by given poll ID.
	 * 
	 * @param pollID The poll ID of the poll to get the options from
	 * @return an list of poll options
	 */
	List<PollOption> getPollOptions(long pollID);

	/**
	 * Increase the vote counter of the poll option with given id of the poll given
	 * by pollID.
	 * 
	 * @param pollID the poll where the poll option to increase is
	 * @param id     the id of the poll option to increase
	 */
	void addVote(long pollID, long id);

}