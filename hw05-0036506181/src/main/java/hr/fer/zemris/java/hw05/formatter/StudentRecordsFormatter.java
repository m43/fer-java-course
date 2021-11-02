package hr.fer.zemris.java.hw05.formatter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Class provides the functionality of printing an list of students in a
 * specific format that takes into account the inequality of the variable names
 * lengths. The printed format is table like.
 * 
 * @author Frano Rajič
 */
public class StudentRecordsFormatter implements IRecordFormatter<StudentRecord> {

	/**
	 * Used as a horizontal delimiter in formatted lines
	 */
	private final static String horizontal = "=";

	/**
	 * Used as a vertical delimiter in formatted lines
	 */
	private final static String vertical = "|";

	/**
	 * Used for crossings in formatted lines
	 */
	private final static String crossing = "+";

	/**
	 * The width of a grade
	 */
	private int gradeWidth = 1;

	/**
	 * Space around value
	 */
	private final static int space = 1;

	/**
	 * Method returns a list of specifically formated output of given student
	 * records.
	 * 
	 * @param records students records to format for output
	 * @return list of strings that represent lines of output
	 */
	public List<String> format(List<StudentRecord> records) {
		List<String> lines = new LinkedList<String>();

		long numberOfRecords = records.stream().count();
		if (numberOfRecords > 0) {
			int longestJmbag = records.stream().mapToInt(s -> s.getJmbag().length()).max().getAsInt();
			int longestFirstName = records.stream().mapToInt(s -> s.getFirstName().length()).max().getAsInt();
			int longestLastName = records.stream().mapToInt(s -> s.getLastName().length()).max().getAsInt();

			lines.add(createClosure(longestJmbag, longestLastName, longestFirstName, gradeWidth));
			lines.addAll(records.stream()
					.map((s) -> createLine(s, longestJmbag, longestLastName, longestFirstName, gradeWidth))
					.collect(Collectors.toList()));
			lines.add(createClosure(longestJmbag, longestLastName, longestFirstName, gradeWidth));
		}
		lines.add("Records selected: " + numberOfRecords);
		return lines;
	}

	/**
	 * Help method to create just one formated line from the given student and
	 * returnd it.
	 * 
	 * @param s  student record to format
	 * @param r1 width of row1
	 * @param r2 width of row1
	 * @param r3 width of row1
	 * @param r4 width of row1
	 * @return the formated line string
	 */
	private static String createLine(StudentRecord s, int r1, int r2, int r3, int r4) {
		StringBuilder sb = new StringBuilder();
		sb.append(vertical + " ");
		sb.append(s.getJmbag());
		sb.append(" ".repeat(r1 - s.getJmbag().length()));

		sb.append(" " + vertical + " ");
		sb.append(s.getLastName());
		sb.append(" ".repeat(r2 - s.getLastName().length()));

		sb.append(" " + vertical + " ");
		sb.append(s.getFirstName());
		sb.append(" ".repeat(r3 - s.getFirstName().length()));

		sb.append(" " + vertical + " ");
		sb.append(s.getGrade()); // width of grade is always 1

		sb.append(" " + vertical);
		return sb.toString();
	}

	/**
	 * Help method to create the opening and closing lines or in other words the
	 * starting and ending line.
	 * 
	 * @param r1 width of row1
	 * @param r2 width of row1
	 * @param r3 width of row1
	 * @param r4 width of row1
	 * @return the formated line string
	 */
	private static String createClosure(int r1, int r2, int r3, int r4) {
		StringBuilder sb = new StringBuilder();
		sb.append(crossing);
		sb.append(horizontal.repeat(r1 + 2 * space));
		sb.append(crossing);
		sb.append(horizontal.repeat(r2 + 2 * space));
		sb.append(crossing);
		sb.append(horizontal.repeat(r3 + 2 * space));
		sb.append(crossing);
		sb.append(horizontal.repeat(r4 + 2 * space));
		sb.append(crossing);
		return sb.toString();
	}

}
