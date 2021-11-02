package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing utilities used by console. The offered functionality
 * consists only of parsing a line and getting alphabetic groupings of
 * characters as words.
 * 
 * @author Frano Rajič
 */
public class Utilities {

	/**
	 * Return groupings of alphabetic characters in the given line. Every created
	 * grouping is considered to be a word. All characters are turned to lowercase
	 * letters. Duplicates are kept.
	 * 
	 * @param line the line to parse
	 * @return a list of parsed words
	 */
	public static List<String> getWordsFromLine(String line) {
		List<String> words = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		for (char c : line.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				sb.append((char) c);
				continue;
			}

			if (sb.length() == 0) {
				continue;
			}

			String word = sb.toString().toLowerCase();
			words.add(word);
			sb.setLength(0);
		}

		if (sb.length() != 0) {
			words.add(sb.toString().toLowerCase());
		}

		return words;
	}

}
