package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element to store constant integer.
 * 
 * @author Frano Rajič
 */
public class ElementConstantInteger extends Element {

	/**
	 * Constant value of the integer
	 */
	private int value;

	/**
	 * Construction of the element with given constant int value
	 * @param value to be stored
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * get the stored constant value
	 * @return the stored value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
	@Override
	public String toString() {
		return asText();
	}

}
