package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class models an text node.
 * 
 * @author Frano Rajič
 */
public class TextNode extends Node {

	/**
	 * The text that is stored in the node
	 */
	private String text;

	/**
	 * Create a new text node with given text.
	 * 
	 * @param text the text of the node
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Get the text that is stored in the node.
	 * 
	 * @return the text of the node
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}

}
