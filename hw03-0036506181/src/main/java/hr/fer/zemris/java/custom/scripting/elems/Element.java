package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Will be used when parsing as an construction element of more complex
 * expressions.
 * 
 * @author Frano
 *
 */
public class Element {
// didnt find explicitly that we could use abstract so I sticked to the old ways

	/**
	 * Get the text representation of the element. This method only returns an empty
	 * string, the child elements will override the function.
	 * 
	 * @return the element as text
	 */
	public String asText() {
		return "";
	}

}
