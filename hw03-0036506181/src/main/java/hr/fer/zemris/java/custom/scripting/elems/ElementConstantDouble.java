package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element to store constant doubles.
 * 
 * @author Frano
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * the constant value of the element. read-only
	 */
	private double value;

	/**
	 * Construction of this element
	 * 
	 * @param value the constant value that will be set once and forever
	 */
	public ElementConstantDouble(double value) {
		// super();
		this.value = value;
	}

	/**
	 * Get the stored value
	 * 
	 * @return the constant double value
	 */
	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	@Override
	public String toString() {
		return asText();
	}

}
