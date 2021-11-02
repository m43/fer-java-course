package hr.fer.zemris.java.hw05.db;

/**
 * Provides the functionality of filtering students according to some criteria
 * that concrete classes will define
 * 
 * @author Frano Rajič
 *
 */
public interface IFilter {

	/**
	 * Method to tell if the given record passes the filter test
	 * 
	 * @param record the student record to check for filter
	 * @return true if filter satisfied
	 */
	boolean accepts(StudentRecord record);
}