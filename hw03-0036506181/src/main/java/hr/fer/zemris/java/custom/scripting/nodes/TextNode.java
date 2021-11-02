package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node that models an node containg simple text
 * 
 * @author Frano
 *
 */
public class TextNode extends Node {

	/**
	 * The text stored in the node
	 */
	private String text;

	/**
	 * Create a text node with given text
	 * 
	 * @param text the text to store inside the node
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * @return the text of the node
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}

}
