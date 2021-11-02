package hr.fer.zemris.java.hw05.db;

/**
 * Interfaces models a strategy which is responsible for obtaining a requested
 * field value from given {@link StudentRecord}
 * 
 * @author Frano Rajič
 */
public interface IFieldValueGetter {

	/**
	 * Method that gets the needed field value from the given record
	 * 
	 * @param record to extract the value from the field 
	 * @return the extracted value
	 */
	String get(StudentRecord record);
	
}
