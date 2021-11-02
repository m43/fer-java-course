package hr.fer.zemris.java.custom.collections;

/**
 * Interface used to model objects that test a object and tell if it is
 * acceptable or not by some criteria
 * 
 * @author Frano
 * 
 * @param <E> The element type to be tested
 */
public interface Tester<E> {

	/**
	 * Method to test if object satisfies specified criteria.
	 * 
	 * @param obj object to do the test upon
	 * @return true if object satisfies the criteria
	 */
	boolean test(E obj);

}
