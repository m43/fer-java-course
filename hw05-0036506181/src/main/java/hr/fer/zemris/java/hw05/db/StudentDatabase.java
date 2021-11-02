package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the functionality of storing student records in a
 * database. The database can be created with given lines of unparsed student
 * records. The functionality includes searching a single student with
 * identification number given (JMBAG) and filtering all the student records by
 * some criteria.
 * 
 * @author Frano Rajič
 */
public class StudentDatabase {

	/**
	 * Internal map for mapping the JMBAG as the key to a value that corresponds to
	 * the index of the stored record in the internal list of student records.
	 */
	private Map<String, Integer> studentsIndex;

	// I picked an ArrayList because it stores
	// elements at specific indexes and enables to get an element in O(1) if index
	// of it is known in advance.
	/**
	 * Internal list of student records.
	 */
	private ArrayList<StudentRecord> students;

	/**
	 * Add all the student records to this database from given list of input rows
	 * that contain records
	 * 
	 * @param databaseLines List of rows containing student records
	 * @throws IllegalArgumentException if given an invalid student record row
	 */
	public StudentDatabase(List<String> databaseLines) {
		students = new ArrayList<>();
		studentsIndex = new LinkedHashMap<>();

		for (String line : databaseLines) {
			StudentRecord s;
			try {
				s = new StudentRecord(line);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Invalid student record lineline! -->" + line + "<--");
			}

			if (studentsIndex.containsKey(s.getJmbag())) {
				// not sure what to throw, so here is a IAExc
				throw new IllegalArgumentException("Duplicate records in given database lines!");
			}
			studentsIndex.put(s.getJmbag(), studentsIndex.size());
			students.add(s);
		}
	}

	/**
	 * For the given JMBAG the Student record is returned if found or null
	 * otherwise. Done in O(1) time complexity
	 * 
	 * @param jmbag the JMBAG to look for
	 * @return the student record corresponding to the given JMBAG or null if there
	 *         is no such record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		Integer index = studentsIndex.get(jmbag);
		if (index == null) {
			return null;
		}

		return students.get(index);
	}

	/**
	 * Returns a list of filtered student records. Filter is given by given object.
	 * 
	 * @param filter used for filtering
	 * @return new list of filtered records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>();

		for (StudentRecord s : students) {
			if (filter.accepts(s)) {
				filteredList.add(s);
			}
		}

		return filteredList;
	}

}
