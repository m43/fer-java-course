package hr.fer.zemris.java.custom.collections;

/**
 * Class provides a model of an object capable of performing some operation on
 * the passed element.
 * 
 * @author Frano Rajič
 * 
 * @param <E> the element type to be processed
 */
public interface Processor<E> {

	/**
	 * Process to be performed among given element
	 * 
	 * @param value element to process
	 */
	public void process(E value);

}
