package hr.fer.zemris.java.hw17.trazilica.visitors;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;

import hr.fer.zemris.java.hw17.trazilica.Utilities;

/**
 * Class offers functionality of visiting a root directory, looking for readable
 * text files and creating a vocabulary of all the words found in the documents.
 * The words are accumulated in a map of String, Integer pairs. The words that
 * appear in the ignored list specified in constructor are not added to
 * vocabulary.
 * 
 * @author Frano Rajič
 */
public class VocabularyFileVisitor extends SimpleFileVisitor<Path> {

	/**
	 * Map maps a word to an assigned index. Each word must have a different
	 * assigned index.
	 */
	private HashMap<String, Integer> words = new HashMap<String, Integer>();

	/**
	 * The set of ignored words, words that should be left out when creating the
	 * dictionary.
	 */
	private HashSet<String> ignored = new HashSet<String>();

	/**
	 * Create a new {@link VocabularyFileVisitor} with given path to ignored words
	 * file
	 * 
	 * @param ignoredWords the path to the ignored words file
	 */
	public VocabularyFileVisitor(Path ignoredWords) {
		super();
		try {
			BufferedReader reader = Files.newBufferedReader(ignoredWords, StandardCharsets.UTF_8);

			String line;
			while ((line = reader.readLine()) != null) {
				for (String w : Utilities.getWordsFromLine(line)) {
					ignored.add(w);
				}
			}

		} catch (IOException e) {
			throw new IllegalArgumentException("The given ignoreable words file cannot be used.", e);
		}
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (!Files.isRegularFile(file) || !Files.isReadable(file)) {
			return FileVisitResult.CONTINUE;
		}

		try {
			BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);

			String line;
			while ((line = reader.readLine()) != null) {
				for (String w : Utilities.getWordsFromLine(line)) {
					if (!ignored.contains(w) && !words.containsKey(w)) {
						words.put(w, words.size());
					}
				}
			}

		} catch (IOException ignoreable) {
			System.out.println(ignoreable);
		}

		return FileVisitResult.CONTINUE;
	}

	/**
	 * Get the vocabulary of words mapped to a unique index.
	 * 
	 * @return the vocabulary
	 */
	public HashMap<String, Integer> getVocabulary() {
		return words;
	}

}
