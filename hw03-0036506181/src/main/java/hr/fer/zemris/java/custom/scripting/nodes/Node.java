package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class models an general node that will be created in the process of parsing a
 * script. A node is an object that can have child nodes and this class models
 * the part of the functionality related to child nodes manipulation. Other
 * classes implement specific purpose nodes, for example the following classes:
 * {@link ForLoopNode}, {@link TextNode} and {@link DocumentNode}.
 * 
 * @author Frano
 *
 */
public class Node {

	/**
	 * This collection is used to store the child nodes. To see the specifications
	 * od the used collection lookup {@link ArrayIndexedCollection}
	 */
	private ArrayIndexedCollection nodes;

	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * The used collection is {@link ArrayIndexedCollection}
	 * 
	 * @param child the child node to be added
	 * @throws NullPointerException if given node is a null reference
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);

		if (nodes == null) {
			nodes = new ArrayIndexedCollection();
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

}
