package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class models an abstract node.
 * 
 * @author Frano Rajič
 */
public abstract class Node {

	/**
	 * A list of child nodes
	 */
	private List<Node> nodes;

	/**
	 * Adds given child to an internally managed list of children.
	 * 
	 * @param child the child node to be added
	 * @throws NullPointerException if given node is a null reference
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);

		if (nodes == null) {
			nodes = new ArrayList<>();
		}

		nodes.add(child);
	};

	/**
	 * Returns the number of direct children of this node.
	 * 
	 * @return the number of children
	 */
	public int numberOfChildren() {
		if (nodes == null)
			return 0;

		return nodes.size();
	}

	/**
	 * Returns selected child or throws an appropriate exception if the index is
	 * invalid.
	 * 
	 * @param index of element to be returned
	 * @return the element at specified index
	 * @throws IndexOutOfBoundsException if invalid index
	 */
	public Node getChild(int index) {
		Objects.checkIndex(index, nodes.size());

		return (Node) nodes.get(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int n = nodes.size();
		for (int i = 0; i < n; i++) {
			sb.append(nodes.get(i).toString());
		}

		return sb.toString();
	}

	/**
	 * Method to call when visiting this node.
	 * 
	 * @param visitor the visitor
	 */
	public abstract void accept(INodeVisitor visitor);

}
