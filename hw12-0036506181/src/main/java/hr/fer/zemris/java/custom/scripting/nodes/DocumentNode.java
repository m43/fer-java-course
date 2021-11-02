package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node that represents the root of a document tree
 * 
 * @author Frano
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

}
