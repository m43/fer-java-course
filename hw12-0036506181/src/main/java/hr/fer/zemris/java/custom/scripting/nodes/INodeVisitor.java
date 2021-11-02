package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface models the functionality that an {@link Node} visitor should have
 * implemented. That functionality includes the visiting of all the
 * following:<br>
 * <ul>
 * <li>{@link ForLoopNode}
 * <li>{@link EchoNode}
 * <li>{@link DocumentNode}
 * <li>{@link TextNode}
 * <ul>
 * 
 * @author Frano Rajič
 */
public interface INodeVisitor {

	/**
	 * Invoked for a text node.
	 * 
	 * @param node the node that is being visited.
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Invoked for a {@link ForLoopNode}
	 * 
	 * @param node the {@link ForLoopNode}
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Invoked for a {@link EchoNode}
	 * 
	 * @param node the {@link EchoNode}
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Invoked for a {@link DocumentNode}
	 * 
	 * @param node the {@link DocumentNode}
	 */
	public void visitDocumentNode(DocumentNode node);
}