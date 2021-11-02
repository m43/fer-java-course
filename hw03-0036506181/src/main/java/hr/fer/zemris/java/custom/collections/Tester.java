package hr.fer.zemris.java.custom.collections;

/**
 * Interface used to model objects that test a object and tell if it is
 * acceptable or not by some criteria
 * 
 * @author Frano
 */
public interface Tester {

	/**
	 * Method to test if object satisfies specified criteria.
	 * 
	 * @param obj object to do the test upon
	 * @return true if object satisfies the criteria
	 */
	boolean test(Object obj);

}
