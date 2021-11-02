package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element to store a string
 * 
 * @author Frano
 *
 */
public class ElementString extends Element {

	/**
	 * The string value stored in element
	 */
	private String value;

	/**
	 * Construct the string with given value
	 * 
	 * @param value of string to be stored inside the element
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * @return the string value of the element
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return value;
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(asText().replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")
				.replace("\t", "\\t"));
		sb.append("\"");
		return sb.toString();
	}

}
