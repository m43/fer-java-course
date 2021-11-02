package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class offers functionality that classes of same package share like spliting
 * arguments
 * 
 * @author Frano Rajič
 */
public class Utility {

	/**
	 * Utility class that enables splitting arguments that support strings with
	 * quotes inside and escaping quotes and backslashes inside strings of input.
	 * For example:<br>
	 * <li>"a b c" -> "a", "b", "c"
	 * <li>"a "b" c" -> "a", "b", "c"
	 * <li>"a "b\\b" c" -> "a", "b\b", "c"
	 * <li>"a "b\"b" c" -> "a", "b"b", "c"
	 * 
	 * @param string to be parsed as described
	 * @return a list of parsed arguments
	 * @throws IllegalArgumentException If invalid string given
	 */
	public static String[] splitArgumentsContainingStrings(String string) {
		List<String> indices = new ArrayList<>();
		string = string.trim();
		int lastSplit = 0;
		boolean inString = false;
		int length = string.length();
		for (int i = 0; i < length; i++) {
			if (!inString) {
				if (Character.isWhitespace(string.charAt(i))) {
					indices.add(string.substring(lastSplit, i));

					while (++i < length && Character.isWhitespace(string.charAt(i))) {
						// skipping whitespace characters
					}
					i--; // because the outer for loop will increment anyway
					lastSplit = i + 1;
				}
				if (string.charAt(i) == '"') {
					if (lastSplit < i - 1) {
						indices.add(string.substring(lastSplit, i));
					}
					lastSplit = i + 1;
					inString = true;
				}
			} else {
				if (string.charAt(i - 1) != '\\' && string.charAt(i) == '\"') {
					if (i + 1 < length && !Character.isWhitespace(string.charAt(i + 1))) {
						throw new IllegalArgumentException("After closing quatation character must be white space...");
					}
					indices.add(string.substring(lastSplit, i).trim().replaceAll("\\\\\\\\", "\\\\")
							.replaceAll("\\\\\"", "\""));
					while (++i < length && Character.isWhitespace(string.charAt(i)))
						;
					i--;
					lastSplit = i + 1;
					inString = false;
				}
			}
		}
		if (lastSplit <= length - 1) {
			indices.add(string.substring(lastSplit, length).trim());
		}

		if (inString) {
			throw new IllegalArgumentException("Opened quotation must be closed!");
		}

		String[] result = new String[indices.size()];
		int i = 0;
		for (String element : indices) {
			result[i++] = element;
		}

		return result;
	}

	/**
	 * Return a list of string split into lines of maximal length 80.
	 * 
	 * @param description the string to split
	 * @return the list of lines of split string
	 */
	public static List<String> getDescription(String description) {
		return getDescription(description, 80);
	}

	/**
	 * Create an unmodifiable list of lines of given argument split. The maximal
	 * length of a line is given as well with an argument.
	 * 
	 * @param description the string to split
	 * @param maxLength   the maximal length of a line to be split
	 * @return the list of lines of split string
	 */
	public static List<String> getDescription(String description, int maxLength) {
		List<String> list = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		for (String s : description.split("\\s+")) {
			if (sb.length() + s.length() > maxLength) {
				list.add(sb.toString());
				sb = new StringBuilder();
			}
			sb.append(s + " ");
		}
		list.add(sb.toString());
		return Collections.unmodifiableList(list);
	}

}
