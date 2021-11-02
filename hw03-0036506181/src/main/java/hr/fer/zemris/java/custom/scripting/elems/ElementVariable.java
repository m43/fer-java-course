package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Element to be used for storing names of variables
 * 
 * @author Frano
 *
 */
public class ElementVariable extends Element {

	/**
	 * The name of the variable
	 */
	private String name;

	/**
	 * Construct the element with given variable name
	 * @param name of the variable
	 */
	public ElementVariable(String name) {
		//super();
		this.name = name;
	}

	/**
	 * @return the name of the variable
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
		// ?? or is it better to use the getter here?
	}
	
	@Override
	public String toString() {
		return asText();
	}

}
