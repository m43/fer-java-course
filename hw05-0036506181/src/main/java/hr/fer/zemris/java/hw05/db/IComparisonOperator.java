package hr.fer.zemris.java.hw05.db;

/**
 * Interface models an abstract strategy for comparing two values
 * 
 * @author Frano Rajič
 */
public interface IComparisonOperator {

	/**
	 * Check if the two given values satisfy the comparison defined by this operator
	 * 
	 * @param value1 first value
	 * @param value2 second value
	 * @return true if comparison operation satisfied
	 */
	boolean satisfied(String value1, String value2);

}
