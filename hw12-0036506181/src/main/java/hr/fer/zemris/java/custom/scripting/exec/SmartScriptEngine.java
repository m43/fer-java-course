package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class provides the functionality of executing the document tree that was
 * created by {@link SmartScriptParser} and creating the respective html
 * document.
 * 
 * @author Frano Rajič
 */
public class SmartScriptEngine {

	/**
	 * The root node of the document tree
	 */
	private DocumentNode documentNode;

	/**
	 * The request context to use when executing
	 */
	private RequestContext requestContext;

	/**
	 * The {@link ObjectMultistack} used for storing operands while executing an
	 * {@link EchoNode}
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Visitor of nodes that does the execution
	 */
	private INodeVisitor visitor;

	/**
	 * Create a new instance of the engine
	 * 
	 * @param documentNode   the root node of the tree
	 * @param requestContext the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		visitor = new EngineVisitor(this.requestContext, this.multistack);
	}

	/**
	 * Start the engine
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
