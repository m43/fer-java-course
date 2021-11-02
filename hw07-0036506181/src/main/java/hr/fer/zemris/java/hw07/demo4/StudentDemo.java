package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class contains program for listing some general data about students.
 * 
 * @author Frano Rajič
 */
public class StudentDemo {

	/**
	 * Entry point for program.
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		List<String> lines;
		String pathToFile = "./studenti.txt";
		try {
			lines = Files.readAllLines(Paths.get(pathToFile));
		} catch (IOException e) {
			System.out.println("Problem with openning path -->" + pathToFile
					+ "<-- Consider the following error message: " + e.getMessage());
			return;
		}
		List<StudentRecord> records = lines.stream().filter(x -> x.trim().length() > 0).map(x -> new StudentRecord(x))
				.collect(Collectors.toList());

		int i = 1;

		// task 1
		long broj = vratiBodovaViseOd25(records);
		System.out.println("Zadatak " + i++ + "\n=========");
		System.out.println(broj);

		// task 2
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		System.out.println(broj5);

		// task 3
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		for (StudentRecord s : odlikasi) {
			System.out.println(s);
		}

		// task 4
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		for (StudentRecord s : odlikasiSortirano) {
			System.out.println(s);
		}

		// task 5
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		for (String s : nepolozeniJMBAGovi) {
			System.out.println(s);
		}

		// task 6
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		for (Map.Entry<Integer, List<StudentRecord>> e : mapaPoOcjenama.entrySet()) {
			System.out.println("× Učenici s ocjenom " + e.getKey() + ": ");
			for (StudentRecord s : e.getValue()) {
				System.out.println(s);
			}
		}

		// task 7
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		for (Map.Entry<Integer, Integer> e : mapaPoOcjenama2.entrySet()) {
			System.out.println("Broj učenika s ocjenom " + e.getKey() + " je " + e.getValue());
		}

		// task 8
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("\nZadatak " + i++ + "\n=========");
		for (Map.Entry<Boolean, List<StudentRecord>> e : prolazNeprolaz.entrySet()) {
			System.out.println("Učenici koji su " + (e.getKey() ? "prošli" : "pali") + ": ");
			for (StudentRecord s : e.getValue()) {
				System.out.println(s);
			}
		}
	}

	/**
	 * Count the students that have more than 25 points all together
	 * 
	 * @param records The list of students to query
	 * @return the number of students
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		// task 1
		return records.stream().map(StudentRecord::totalPoints).filter(x -> x > 25).count();
	}

	/**
	 * Count the students that have grade excellent
	 * 
	 * @param records The list of students to query
	 * @return the number of students
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		// task 2
		return records.stream().filter(x -> x.getGrade() == 5).count();
	}

	/**
	 * Filter only the students that have grade excellent
	 * 
	 * @param records The list of students to query
	 * @return list of excellent students
	 */
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		// task 3
		return records.stream().filter(x -> x.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Filter only the students that have grade excellent and return an sorted list
	 * of them
	 * 
	 * @param records The list of students to query
	 * @return sorted list of excellent students
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		// task 4
		return records.stream().filter(x -> x.getGrade() == 5)
				.sorted((x, y) -> (int) (Double.compare(y.totalPoints(), x.totalPoints())))
				.collect(Collectors.toList());
	}

	/**
	 * Filter only the students that didn't pass the course and return their JMBAG's
	 * 
	 * @param records The list of students to query
	 * @return list o students JMBAG's that didn't pass
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		// task 5
		return records.stream().filter(x -> x.getGrade() == 1).map(StudentRecord::getJMBAG).sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Create an map with students classified by grade
	 * 
	 * @param records The list of students to query
	 * @return map of student classified by grade
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		// task 6
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Create an map with the counted number of different students with grades, the
	 * grade being the key, the number of students with that respective grade being
	 * the value
	 * 
	 * @param records The list of students to query
	 * @return map of counted grades
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		// task 7
		return records.stream().collect(Collectors.toMap(StudentRecord::getGrade, x -> 1, (x, y) -> x + y));
	}

	/**
	 * Methods maps true to students of given records if the student passed the
	 * course, and false otherwise
	 * 
	 * @param records The list of students to query
	 * @return map of students where students that passed are mapped to true, and
	 *         students that did not are mapped to false
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		// task 8
		return records.stream().collect(Collectors.partitioningBy(x -> x.getGrade() != 1));
	}

}
