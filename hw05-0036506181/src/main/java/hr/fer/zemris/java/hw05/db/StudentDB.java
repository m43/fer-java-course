package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.formatter.StudentRecordsFormatter;
import hr.fer.zemris.java.hw05.formatter.IRecordFormatter;

/**
 * Program that enables making query searches in defined database. Terminated
 * with "exit". There is a special format of query conditional expressions.
 * 
 * @author Frano Rajič
 *
 */
public class StudentDB {

	/**
	 * Where is the database
	 */
	public static final String DATABASE_LOCATION = "./database.txt";

	/**
	 * Main program
	 * 
	 * @param args ignored and irrelevant for the functionality of the program
	 */
	public static void main(String[] args) {
		StudentDatabase db = loadDatabase(DATABASE_LOCATION);
		Scanner sc = new Scanner(System.in);
		String nextLine;
		String queryString;

		while (true) {
			System.out.print("> ");
			nextLine = sc.nextLine().trim();

			if (nextLine.isEmpty()) {
				continue;
			}

			if (nextLine.startsWith("exit")) {
				System.out.println("Goodbye!");
				break;
			}

			if (!nextLine.startsWith("query ")) {
				System.out.println("Invalid command! Only query is supported...\n");
				continue;
			}

			queryString = nextLine.substring("query ".length());

			try {

				List<StudentRecord> records = filterFromDatabaseGivenQuery(db, queryString);
				IRecordFormatter<StudentRecord> formatter = new StudentRecordsFormatter();
				List<String> output = formatter.format(records);
				output.forEach(System.out::println);

			} catch (IllegalArgumentException e) {
				System.out.println("Invalid query given..");
			} // catch (Exception e) {
				// System.out.println("This should NEVER be executed!");
				// }

			System.out.println();

		}
		sc.close();
	}

	/**
	 * Help method to load the database from specified file
	 * 
	 * @param databaseLocation where is the database, for example "./database.txt"
	 * @return the created student database
	 */
	private static StudentDatabase loadDatabase(String databaseLocation) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(databaseLocation), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Couldn't load database! TERMINATING...");
			System.exit(-1);
		}

		try {
			return new StudentDatabase(lines);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid data in database! TERMINATING...");
			System.exit(-1);
		}

		return null;
	}

	/**
	 * Help method to get the filtered records of given database and query
	 * 
	 * @param db          database to filter values from
	 * @param queryString string that contains the query to be parsed
	 * @return list containing filtered student records
	 */
	private static List<StudentRecord> filterFromDatabaseGivenQuery(StudentDatabase db, String queryString) {
		QueryParser parser = new QueryParser(queryString);

		if (parser.isDirectQuery()) {
			LinkedList<StudentRecord> records = new LinkedList<>();
			records.add(db.forJMBAG(parser.getQueriedJMBAG()));
			return records;
		} else {
			return db.filter(new QueryFilter(parser.getQuery()));
		}

	}
}
