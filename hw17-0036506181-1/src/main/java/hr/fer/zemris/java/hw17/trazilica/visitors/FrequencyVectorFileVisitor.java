package hr.fer.zemris.java.hw17.trazilica.visitors;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import hr.fer.zemris.java.hw17.trazilica.Utilities;

/**
 * Visitor that walks around readable documents and counts how many times each
 * words appears in a specific document. The counted frequencies of words are
 * stored separately in a HashMap that maps each document string path to its
 * respective ArrayList. Each array list has the same number of elements as the
 * vocabulary that got passed in the constructor.
 * 
 * @author Frano Rajič
 */
public class FrequencyVectorFileVisitor extends SimpleFileVisitor<Path> {

	/**
	 * Maps the document string path to its respective vector of frequencies. The
	 * vector is represented by an ArrayList
	 */
	private HashMap<String, ArrayList<Integer>> fVectors = new HashMap<>();

	/**
	 * Vocabulary maps a word to an assigned index. Each word must have a different
	 * assigned index.
	 */
	HashMap<String, Integer> vocabulary;

	/**
	 * Construct a {@link FrequencyVectorFileVisitor} with given vocabulary.
	 * 
	 * @param vocabulary the vocabulary
	 */
	public FrequencyVectorFileVisitor(HashMap<String, Integer> vocabulary) {
		this.vocabulary = vocabulary;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (!Files.isRegularFile(file) || !Files.isReadable(file)) {
			return FileVisitResult.CONTINUE;
		}

		try {
			BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);

			ArrayList<Integer> vector = new ArrayList<>(Collections.nCopies(vocabulary.size(), 0));
			String line;
			while ((line = reader.readLine()) != null) {
				for (String w : Utilities.getWordsFromLine(line)) {
					if (vocabulary.containsKey(w)) {
						int index = vocabulary.get(w);
						vector.set(index, vector.get(index) + 1);
					}
				}
			}

			fVectors.put(file.toAbsolutePath().toString(), vector);
		} catch (IOException ignoreable) {
		}

		return FileVisitResult.CONTINUE;
	}

	/**
	 * @return the frequency vectors
	 */
	public HashMap<String, ArrayList<Integer>> getFrequencyVectors() {
		return fVectors;
	}

}
