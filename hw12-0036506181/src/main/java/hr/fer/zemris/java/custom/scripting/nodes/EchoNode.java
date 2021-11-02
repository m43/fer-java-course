package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class models an echo node.
 * 
 * @author Frano Rajič
 */
public class EchoNode extends Node {

	/**
	 * The elements of the echo node.
	 */
	private List<Element> elements;

	/**
	 * Create a new echo node with given elements.
	 * 
	 * @param elements the elements of the echo node
	 */
	public EchoNode(Element[] elements) {
		this.elements = Arrays.asList(elements);
	}

	/**
	 * Get the elements of the echo node
	 * 
	 * @return the elements
	 */
	public List<Element> getElements() {
		return elements;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{$= ");

		for (Element e : elements) {
			sb.append(e.toString());
			sb.append(" ");
		}

		sb.append("$}");
		return sb.toString();
	}

}
