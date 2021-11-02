package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class models students, storing their data. Provides the functionality of
 * storing student ID (aka JMBAG, two students are equal it's the same!), name,
 * surname and grade, the grade being the only mutable property. An instance can
 * be constructed using constructor with all the property values given, or given
 * an line to be parsed that contains the necessary student information.
 *
 * 
 * @author Frano Rajič
 */
public class StudentRecord {

	/**
	 * First name of student stored in the record. Read only and cannot be null.
	 */
	private String firstName;

	/**
	 * Last name of student stored in the record. Read only and cannot be null.
	 */
	private String lastName;

	/**
	 * JMBAG of student. JMBAG is a unique identifier of every student and should be
	 * different for each of them. Read only and cannot be null.
	 */
	private String jmbag;

	/**
	 * Grade of student. Must be in range 1 to 5. Can be updated using setter.
	 */
	private int grade;

	/**
	 * Create a student record with all necessary information provided
	 * 
	 * @param firstName first name of student
	 * @param lastName  last name of student
	 * @param jmbag     unique identifier of student
	 * @param grade     grade of student
	 * @throws NullPointerException     if jmbag, first or last name given are null
	 * @throws IllegalArgumentException if invalid grade given
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int grade) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(lastName);

		this.firstName = firstName;
		this.lastName = lastName;
		this.jmbag = jmbag;

		if (!isGradeValid(grade)) {
			throw new IllegalArgumentException();
		}
		this.grade = grade;
	}

	/**
	 * Create a student record with given line that contains the student record.
	 * Valid line contains all the following parts separated with one or more
	 * spaces: first comes an student identifier regarded as JMBAG, afterwards one
	 * or more surnames, then a first name and the last part must be an integer from
	 * 1 to 5 telling the grade of the given student.
	 * 
	 * @param line containing student record information needed, is parsed to create
	 *             a record of {@link StudentRecord}
	 * 
	 * @throws IllegalArgumentException if invalid input line given
	 */
	public StudentRecord(String line) {
		String[] parts = line.trim().split("\\t");
		if (parts.length != 4) {
			throw new IllegalArgumentException("Invalid student record line given - too few arguments!");
		}

		jmbag = parts[0].trim();
		lastName = parts[1].trim();
		firstName = parts[2].trim();

		String gradeString = parts[3].trim();
		try {
			grade = Integer.valueOf(gradeString);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Invalid student record line given - at last position needs to be an int that tells the grade!");
		}
		if (!isGradeValid(grade)) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * return the grade stored in this student record
	 * 
	 * @return grade of student
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * return the first name stored in this student record
	 * 
	 * @return first name of student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * return the last name stored in this student record
	 * 
	 * @return last name of student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Return JMBAG (the unique ID of the student)
	 * 
	 * @return the JMBAG of the student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Set a new grade for the student
	 * 
	 * @param grade grade of student
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * Hash code of a student is calculated according to the value of JMBAG property
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Two students are equal if they have the same JMBAG
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Help method to check if grade is valid. A valid grade is in the set {1, 2, 3,
	 * 4, 5}
	 * 
	 * @param grade to be checked
	 * @return true if grade is valid
	 */
	private boolean isGradeValid(int grade) {
		if (grade > 5 || grade < 1)
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "StudentRecord [firstName=" + firstName + ", lastName=" + lastName + ", jmbag=" + jmbag + ", grade="
				+ grade + "]";
	}

}
