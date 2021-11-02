package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element to store a string
 * 
 * @author Frano
 *
 */
public class ElementString extends Element {

	/**
	 * The stored string value
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
	 * Get the stored string value
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(value
				.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\n", "\\n")
				.replace("\r", "\\r")
				.replace("\t", "\\t")
		);
		sb.append("\"");
		return sb.toString();
	};

	@Override
	public String toString() {
		return asText();
	}

}
