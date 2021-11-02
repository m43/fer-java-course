package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class models an result of filtering files with regex and provides therefore
 * the construction of new filtered results with file name and matching pattern
 * given
 * 
 * @author Frano Rajič
 */
public class FilterResult {

	/**
	 * Matcher that was matched with name of the file
	 */
	private Matcher matcher;

	/**
	 * The name of the file that is filtered
	 */
	private String fileName;

	/**
	 * Construct a new filtered result by given name of file and pattern to match
	 * with.
	 * 
	 * @param nameOfFile the name of the filtered file
	 * @param pattern    the pattern to match the file name by
	 */
	public FilterResult(String nameOfFile, Pattern pattern) {
		fileName = nameOfFile;
		matcher = pattern.matcher(nameOfFile);

		if (!matcher.find()) {
			throw new IllegalArgumentException("The passed file name should be matching the given pattern!");
		}
	}

	/**
	 * Returns the name of the filtered result file
	 * 
	 * @return the filtered result file name
	 */
	public String toString() {
		return fileName;
	}

	/**
	 * Returns the number of found groups
	 * 
	 * @return the number of groups found
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}

	/**
	 * Get the group with given index. Valid index is 0 <= index <= numberOfGroups()
	 * 
	 * @param index index of group
	 * @return the group
	 */
	public String group(int index) {
		Objects.checkIndex(index, numberOfGroups() + 1);

		return matcher.group(index);
	}

}
