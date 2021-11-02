package hr.fer.zemris.java.webserver.servlets.glasanje;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class contains utilities used by classes that take care of voting logic. The
 * provided functionality includes getting mappings of ID to band name, link and
 * number of votes, as well as updating results with new vote.
 * 
 * @author Frano Rajič
 */
public class GlasanjeServletUtilities {

	/**
	 * Where are the results located relative to the application
	 */
	private static final String RESULTS_RELATIVE_PATH = "/WEB-INF/glasanje-rezultati.txt";

	/**
	 * Where is the definition located relative to the application
	 */
	private static final String DEFINITION_RELATIVE_PATH = "/WEB-INF/glasanje-definicija.txt";

	/**
	 * Help method to load current votes, update them and save the updated votes and
	 * then return the sorted map containing id-->votes mapping
	 * 
	 * @param req  the request of the servlet
	 * @param resp the response of the servlet
	 * @return the sorted id-->votes mapping or null if given voting parameters were
	 *         invalid (id was invalid)
	 * @throws IOException      thrown if IO error occurs
	 * @throws ServletException thrown if any servlet error occurs
	 */
	protected static Map<Integer, Integer> getVotesAfterUpdating(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String fileName = req.getServletContext().getRealPath(RESULTS_RELATIVE_PATH);
		Path path = Paths.get(fileName);

		if (Files.notExists(path)) {
			Files.createFile(path);
		}

		int id;
		try {
			id = Integer.valueOf(req.getParameter("id"));
		} catch (NumberFormatException e) {
			return null;
		}

		Map<Integer, Integer> sortedResults = getMapIDtoVotes(req, resp);

		Integer votes = sortedResults.get(id);
		votes = votes == null ? 0 : votes + 1;
		sortedResults.put(id, votes);

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> e : sortedResults.entrySet()) {
			sb.append(e.getKey() + "\t" + e.getValue() + "\n");
		}

		BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
		bw.write(sb.toString().trim());
		bw.flush();

		return sortedResults;
	}

	/**
	 * Help method to load current votes, update them and save the updated votes and
	 * then return the sorted map containing id-->votes mapping
	 * 
	 * @param req  the request of the servlet
	 * @param resp the response of the servlet
	 * @return the sorted id-->votes mapping or null if given voting parameters were
	 *         invalid (id was invalid)
	 * @throws IOException      thrown if IO error occurs
	 * @throws ServletException thrown if any servlet error occurs
	 */
	protected static Map<Integer, Integer> getMapIDtoVotes(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String fileName = req.getServletContext().getRealPath(RESULTS_RELATIVE_PATH);

		Path path = Paths.get(fileName);
		if (Files.notExists(path)) {
			Files.createFile(path);
		}

		// How to handle NumberFormatException?
		Map<Integer, Integer> mapIDtoVotes = Files.lines(path).filter(x -> x.split("\\t").length == 2)
				.map(s -> new SimpleEntry<>(Integer.valueOf(s.split("\\t")[0]), Integer.valueOf(s.split("\\t")[1])))
				.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue, (l, r) -> l > r ? l : r,
						TreeMap::new));

		return mapIDtoVotes;
	}

	/**
	 * Help method to load current votes, update them and save the updated votes and
	 * then return the sorted map containing id-->votes mapping
	 * 
	 * @param req  the request of the servlet
	 * @param resp the response of the servlet
	 * @return the sorted id-->votes mapping or null if given voting parameters were
	 *         invalid (id was invalid)
	 * @throws IOException      thrown if IO error occurs
	 * @throws ServletException thrown if any servlet error occurs
	 */
	protected static Map<Integer, Integer> getMapIDtoVotesSortedByVotes(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		String fileName = req.getServletContext().getRealPath(RESULTS_RELATIVE_PATH);

		Path path = Paths.get(fileName);
		if (Files.notExists(path)) {
			Files.createFile(path);
		}

		// How to handle NumberFormatException?
		Map<Integer, Integer> sortedMapIDtoVotes = Files.lines(path).filter(x -> x.split("\\t").length == 2)
				.map(s -> new SimpleEntry<>(Integer.valueOf(s.split("\\t")[0]), Integer.valueOf(s.split("\\t")[1])))
				.sorted((x, y) -> y.getValue() - x.getValue()).collect(Collectors.toMap(SimpleEntry::getKey,
						SimpleEntry::getValue, (l, r) -> l > r ? l : r, LinkedHashMap::new));

		return sortedMapIDtoVotes;
	}

	/**
	 * Help method to return an mapping of IDs to Names of bands
	 * 
	 * @param req the request of the servlet
	 * @return the mapping of IDs to band names.
	 * @throws IOException thrown if IO error occurs
	 */
	protected static Map<Integer, String> getMapIDtoName(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext().getRealPath(DEFINITION_RELATIVE_PATH);

		// Should i check for file existence here? How to handle non existing file?
		Path path = Paths.get(fileName);

		// How to handle NumberFormatFException?
		return Files.lines(path).filter(x -> x.split("\\t").length == 3)
				.map(s -> new SimpleEntry<>(Integer.valueOf(s.split("\\t")[0]), s.split("\\t")[1]))
				.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue, (l, r) -> l, TreeMap::new));
	}

	/**
	 * Help method to return an mapping of IDs to Names of bands
	 * 
	 * @param req the request of the servlet
	 * @return the mapping of IDs to band names.
	 * @throws IOException thrown if IO error occurs
	 */
	protected static Map<Integer, String> getMapIDtoLink(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext().getRealPath(DEFINITION_RELATIVE_PATH);
		Path path = Paths.get(fileName);

		return Files.lines(path).filter(x -> x.split("\\t").length == 3)
				.map(s -> new SimpleEntry<>(Integer.valueOf(s.split("\\t")[0]), s.split("\\t")[2]))
				.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue, (l, r) -> l, TreeMap::new));

	}

}
