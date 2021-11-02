package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw17.trazilica.visitors.FrequencyVectorFileVisitor;
import hr.fer.zemris.java.hw17.trazilica.visitors.VocabularyFileVisitor;

/**
 * Console application that can query text documents located in a folder given
 * in arguments. The application uses TF-IDF (term frequency–inverse document
 * frequency) document vectors when running queries on analyzed document
 * database. <br>
 * When analyzing the given document database, necessary structures are created
 * and initialized on console start: a vocabulary, frequency vector of each
 * document and a IDF vector, all three being stored in memory. <br>
 * A given query is first converted into an TF-IDF vector that is used to
 * calculate the similarity with the rest of the documents. The first 10
 * documents ordered by similarity are shown as the result of the query. <br>
 * Detailed description of supported commands:
 * <ul>
 * <li>query word1, word2, ... - use the given list of words to query the
 * document database and show 10 most similar files.</li>
 * <li>type NUMBER - print out to the console the document from result that had
 * ordinal number as given NUMBER</li>
 * <li>results - print out again the best results of the last query</li>
 * <li>exit - terminate the application</li>
 * </ul>
 * 
 * @author Frano Rajič
 */
public class Konzola {

	// NOTE:
	// It would be more in the spirit of OO Paradigm if the console was modeled like
	// SHELL+ENVIRONMENT, and if commands would be modeled in a neater way than
	// just help methods. As the task is quite simple and does not have many
	// supported commands, I have modeled this console in a IF ELSE fashion.
	
	// NOTE2:
	// The solution works but the code could be much much better. As future TODO i would need to:
	// 		-model the documents in a special class that would include calculating all the stuff needed for vector manipulation
	//		-better vector representation, like a vector class that can by itself calculate dot product, norm, etc.
	//		-code needs much more refactoring, especially #query

	/**
	 * The path to the document database that the console commands are working with.
	 */
	private Path databasePath;

	/**
	 * The relative path to the ignored file resource
	 */
	private final static String IGNORED_STRING = "stoprijeci.txt";

	/**
	 * Path to ignored words file.
	 */
	private Path ignoredWords;

	/**
	 * Vocabulary maps a word to an assigned index. Each word must have a different
	 * assigned index.
	 */
	private HashMap<String, Integer> vocabulary = new HashMap<String, Integer>();

	/**
	 * Maps the document string path to its respective vector of frequencies. The
	 * vector is represented by an ArrayList
	 */
	private HashMap<String, ArrayList<Integer>> frequencyVectors = new HashMap<>();

	/**
	 * Array list representing a vector inverse document frequency (IDF) values
	 */
	private ArrayList<Double> idf;

	/**
	 * Map mapping the string path of a document from database to its similarity
	 * (double) with the last query. If it is of value null, then a query has not
	 * been made yet.
	 */
	private Map<String, Double> queryResult;

	/**
	 * Create a new console with given path to document database and ignored words
	 * 
	 * @param databasePath the path to the database of documents
	 * @param ignoredWords the path to the file containing words that will be
	 *                     ignored
	 * @throws IOException if IO error occurs
	 */
	public Konzola(Path databasePath, Path ignoredWords) throws IOException {
		this.databasePath = databasePath;
		this.ignoredWords = ignoredWords;
		initialization();
	}

	/**
	 * Help method used to initialize the console.
	 * 
	 * @throws IOException if IO error occurs
	 */
	private void initialization() throws IOException {

		// Create vocabulary
		VocabularyFileVisitor vocabularyVisitor = new VocabularyFileVisitor(ignoredWords);
		Files.walkFileTree(databasePath, vocabularyVisitor);
		vocabulary = vocabularyVisitor.getVocabulary();

		// Create frequency vectors for each document
		FrequencyVectorFileVisitor frequencyVisitor = new FrequencyVectorFileVisitor(vocabulary);
		Files.walkFileTree(databasePath, frequencyVisitor);
		frequencyVectors = frequencyVisitor.getFrequencyVectors();

		// Create IDF (inverse document frequency)
		int[] helpVector = new int[vocabulary.size()];
		for (ArrayList<Integer> arr : frequencyVectors.values()) {
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i) > 0) {
					helpVector[i] += 1;
				}
			}
		}

		idf = new ArrayList<Double>(Collections.nCopies(vocabulary.size(), (double) 0));
		int n = frequencyVectors.size();
		for (var e : vocabulary.entrySet()) {
			idf.set(e.getValue(), Math.log((double) n / helpVector[e.getValue()]));
		}
	}

	/**
	 * Entry point for console application
	 * 
	 * @param args only one argument should be given, with the path to the document
	 *             database
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Exactly one argument should be given, specifying the path to the document database.");
			return;
		}

		Path databasePath;
		try {
			databasePath = Paths.get(args[0]);
		} catch (InvalidPathException e) {
			System.out.println("Invalid path given, consider the following error message: " + e.getMessage());
			return;
		}

		if (!Files.exists(databasePath) || !Files.isDirectory(databasePath)) {
			System.out.println("Given path is not a database directory.");
			return;
		}

		URL ignoredURL = Thread.currentThread().getContextClassLoader().getResource(IGNORED_STRING);
		if (ignoredURL == null) {
			System.out.println("Couldnt find file with ignored words...");
		}

		Path ignored;
		try {
			ignored = Paths.get(ignoredURL.toURI());
		} catch (URISyntaxException | InvalidPathException e1) {
			System.out.println("Couldn't load the stop words resource, consider this: " + e1.getMessage());
			return;
		}

		Konzola console;
		try {
			console = new Konzola(databasePath, ignored);
		} catch (IOException e) {
			System.out.println("An IO error occured, consider the following error message: " + e.getMessage());
			return;
		}

		console.run(System.in, System.out);
	}

	/**
	 * Start the console. Take input from given input stream
	 * 
	 * @param in  the input stream from which to read
	 * @param out the output print stream to which to write
	 */
	private void run(InputStream in, PrintStream out) {
		Scanner sc = new Scanner(in);

		out.println("Veličina riječnika je " + vocabulary.size() + " riječi.\n");

		while (true) {
			out.print("Enter command > ");
			String line = sc.nextLine().trim();
			String[] parts = line.split("\\s");

			if (parts.length == 0 || parts[0].equals("")) {
				continue;
			}

			String first = parts[0];

			if ("exit".equals(first)) {
				if (parts.length != 1) {
					out.println("Exit takes no arguments...");
					continue;
				}
				break;
			} else if ("query".equals(first)) {
				query(parts, out);
			} else if ("type".equals(first)) {
				type(parts, out);
			} else if ("results".equals(first)) {
				results(parts, out);
			} else {
				out.println("No such command -->" + first + "<--");
			}

		}

		sc.close();
	}

	/**
	 * Help method that models the query command. Implements calculating the
	 * similarities between the given query and all documents from document
	 * database, prints out the results, as well as stores to {@link #queryResult}
	 * 
	 * @param parts the parts of the console command call
	 * @param out   the for writing stream
	 */
	private void query(String[] parts, PrintStream out) {
		
		if (parts.length < 2) {
			out.println("command takes at least one argument");
			return;
		}

		// convert the word parts of the query to a line and then convert it into query
		// words by eliminating the ignored words
		StringBuilder line = new StringBuilder();
		for (int i = 1; i < parts.length; i++) {
			line.append(" " + parts[i]);
		}
		List<String> query = Utilities.getWordsFromLine(line.toString().trim()).stream()
				.filter(x -> vocabulary.containsKey(x)).collect(Collectors.toList());
		if (query.size() == 0) {
			out.println(
					"The given query does not contain any query words. Please describe your search with more words.");
			return;
		}
		out.println("Query is: [" + query.stream().sorted().distinct().reduce((x, y) -> x + ", " + y).get() + "]");

		// TF of query document
		double[] tf = new double[vocabulary.size()];
		for (String word : query) {
			tf[vocabulary.get(word)] += 1;
		}

		// TF-IDF
		double[] tfidf = new double[vocabulary.size()];
		for (int i = 0; i < tfidf.length; i++) {
			tfidf[i] = tf[i] * idf.get(i);
		}

		// Absolute value of TF-IDF
		double tfidfABS = 0;
		for (double d : tfidf) {
			tfidfABS += d * d;
		}
		tfidfABS = Math.sqrt(tfidfABS);

		// Iterate over all documents in database and compare with calculated TF-IDF
		queryResult = new HashMap<>();
		for (var e : frequencyVectors.entrySet()) {
			// Absolute value document to compare with
			double v2ABS = 0;
			for (int i = 0; i < tfidf.length; i++) {
				v2ABS = v2ABS + Math.pow(e.getValue().get(i) * idf.get(i), 2);
			}
			v2ABS = Math.sqrt(v2ABS);

			// calculate the scalar product
			double scalarProduct = 0;
			for (int i = 0; i < tfidf.length; i++) {
				scalarProduct += tfidf[i] * e.getValue().get(i) * idf.get(i);
			}

			// calculate the similarity
			double similarity;
			if (Math.abs(tfidfABS * v2ABS) < 0.000000001 || Math.abs(scalarProduct) < 0.000000001) {
				similarity = 0;
			} else {
				similarity = scalarProduct / (tfidfABS * v2ABS);
			}
			queryResult.put(e.getKey(), similarity);
		}

		out.println("The top 10 results are:");
		out.println(getResults());

	}

	/**
	 * Type the document with position parts[1] in result query ordered by
	 * similarity.
	 * 
	 * @param parts the parts of the console command call
	 * @param out   the for writing stream
	 */
	private void type(String[] parts, PrintStream out) {
		if (queryResult == null) {
			out.println("First query then this.");
		}

		if (parts.length != 2) {
			out.println("Command takes exactly one argument.");
			return;
		}

		Integer i;
		try {
			i = Integer.valueOf(parts[1]);
		} catch (NumberFormatException nfe) {
			out.println("The argument must be an integer.");
			return;
		}

		try {
			String pathString = getResultPath(i);
			if (pathString == null) {
				out.println("No result with given position");
				return;
			}
			String text = Files.readString(Paths.get(pathString));
			out.println("----------------------------------------------------------------");
			out.println("Document: " + pathString);
			out.println("----------------------------------------------------------------");
			out.println(text);
			out.println("----------------------------------------------------------------");
		} catch (IOException | InvalidPathException e) {
			out.println("The associated file could not be read!");
		}
	}

	/**
	 * Help method that implements the printing of the results of the last query
	 * command
	 * 
	 * @param parts the parts of the console command call
	 * @param out   the for writing stream
	 */
	private void results(String[] parts, PrintStream out) {
		if (parts.length != 1) {
			out.println("command takes no arguments");
			return;
		}

		if (queryResult == null) {
			out.println("First query then this.");
		}

		out.println(getResults());
	}

	/**
	 * Help method to get a string representing the results of the query
	 * 
	 * @return the results of the query
	 */
	private String getResults() {
		if (queryResult == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		List<Entry<String, Double>> list = queryResult.entrySet().stream().filter(x -> (x.getValue() != 0.0))
				.sorted((x, y) -> (x.getValue() < y.getValue()) ? 1 : -1).limit(10).collect(Collectors.toList());

		int i = 0;
		for (Entry<String, Double> x : list) {
			sb.append(String.format("[%d](%1.4f) %s", i, x.getValue(), x.getKey()));
			sb.append("\n");
			i++;
		}

		return sb.toString();
	}

	/**
	 * Help method to get the string path of the file with given position when
	 * results ordered by similarity
	 * 
	 * @param position the position of the result in the results
	 * @return the string path
	 */
	private String getResultPath(int position) {
		if (queryResult == null) {
			return null;
		}

		List<Entry<String, Double>> list = queryResult.entrySet().stream().filter(x -> (x.getValue() != 0.0))
				.sorted((x, y) -> (x.getValue() < y.getValue()) ? 1 : -1).limit(10).collect(Collectors.toList());

		if (list.size() <= position || position<0) {
			return null;
		}

		return list.get(position).getKey();
	}

}
