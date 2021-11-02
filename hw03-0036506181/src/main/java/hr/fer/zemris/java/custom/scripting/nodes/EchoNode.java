package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Class models an echo node. An echo node can be used after parsing to output
 * an value at the place the node was, a value defined inside the node.
 * 
 * 
 * @author Frano
 */
public class EchoNode extends Node {

	/**
	 * The elements describing the echoing
	 */
	private Element[] elements;

	/**
	 * Create a new echo node
	 * 
	 * @param elements the elements of the echo node
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * @return elements of echo node
	 */
	public Element[] getElements() {
		return elements;
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
