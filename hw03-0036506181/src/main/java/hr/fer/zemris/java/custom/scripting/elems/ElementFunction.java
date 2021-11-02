package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This element stores functions according to their name. Gives the
 * functionality of storing a read only name of the function and getting it.
 * 
 * @author Frano
 *
 */
public class ElementFunction extends Element {

	/**
	 * Read only name of function
	 */
	private String name;

	/**
	 * Construct the function with name given
	 * 
	 * @param name name of function
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * Get the read only name of the function
	 * 
	 * @return the name of the function
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}

	@Override
	public String toString() {
		return "@" + asText();
	}

}
