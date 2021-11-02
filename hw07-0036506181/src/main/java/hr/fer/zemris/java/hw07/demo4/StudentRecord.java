package hr.fer.zemris.java.hw07.demo4;

/**
 * Class models a student record. Student information can include all the
 * followings: JMBAG, lastName, firstName, pointsMI, pointsZI, labPoints, grade
 * 
 * @author Frano Rajič
 */
public class StudentRecord {

	/**
	 * The unique identifier of the student, the JMBAG
	 */
	private String JMBAG;

	/**
	 * The last name of the student
	 */
	private String lastName;

	/**
	 * The first name of the student
	 */
	private String firstName;

	/**
	 * Points the student had at the middle exam
	 */
	private double pointsMI;

	/**
	 * Points the student had at the final
	 */
	private double pointsZI;

	/**
	 * Points the student had in laboratory
	 */
	private double labPoints;

	/**
	 * The grade the student got
	 */
	private int grade;

	/**
	 * Construct an student record with given line containing all necessary student
	 * information. The information should be given in following order: JMBAG,
	 * lastName, firstName, pointsMI, pointsZI, labPoints, grade
	 * 
	 * @param line The line containing the student information
	 */
	public StudentRecord(String line) {
		String[] parts = line.trim().split("\\t");

		if (parts.length != 7) {
			throw new IllegalArgumentException(
					"Invalid number of arguments in given student line, given numner of argument is: " + parts.length);
		}

		JMBAG = parts[0];
		lastName = parts[1];
		firstName = parts[2];

		try {
			pointsMI = Double.parseDouble(parts[3]);
			pointsZI = Double.parseDouble(parts[4]);
			labPoints = Double.parseDouble(parts[5]);
			grade = Integer.parseInt(parts[6]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Couldnt parse the given argument, consider the following error message: " + e.getMessage());
		}

	}

	/**
	 * Get the unique identifier of the student (JMBAG)
	 * 
	 * @return the JMBAG of the student
	 */
	public String getJMBAG() {
		return JMBAG;
	}

	/**
	 * Get the last name of the student
	 * 
	 * @return the last name of student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Get the first name of the student
	 * 
	 * @return the first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Return the points the student had at the midterm exam
	 * 
	 * @return the midterm exam points
	 */
	public double getPointsMI() {
		return pointsMI;
	}

	/**
	 * Return the points the student had at the final exam
	 * 
	 * @return the final exam points
	 */
	public double getPointsZI() {
		return pointsZI;
	}

	/**
	 * Get the laboratory points the student had
	 * 
	 * @return the lab points
	 */
	public double getLabPoints() {
		return labPoints;
	}

	/**
	 * Get the grade of the student
	 * 
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Method to return the total number of points the student had at exams and in laboratory
	 * 
	 * @return the total number of points the student has
	 */
	public double totalPoints() {
		return pointsMI + pointsZI + labPoints;
	}
	
	@Override
	public String toString() {
		return JMBAG + "\t" + lastName + "\t" + firstName + "\t" + pointsMI + "\t" + pointsZI + "\t" + labPoints + "\t"
				+ grade;
	}

}
