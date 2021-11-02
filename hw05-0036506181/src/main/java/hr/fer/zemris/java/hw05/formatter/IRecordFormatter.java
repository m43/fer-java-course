package hr.fer.zemris.java.hw05.formatter;

import java.util.List;

/**
 * Implementing classes need to provide the functionality of formatting output
 * of the given list of records.
 * 
 * @author Frano Rajič
 *
 * @param <T> Type of records
 */
public interface IRecordFormatter<T> {

	/**
	 * Method returns a list of specifically formated output of given records.
	 * 
	 * @param records records to format for output
	 * @return list of strings that represent lines of formatted output
	 */
	public List<String> format(List<T> records);

}
