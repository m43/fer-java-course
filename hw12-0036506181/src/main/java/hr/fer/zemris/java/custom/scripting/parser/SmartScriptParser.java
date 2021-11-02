package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Implementation of smart script parser with basic functionality of parsing
 * text bodies of specific format. Uses {@link SmartScriptLexer} for creating
 * tokens.
 * 
 * @author Frano
 *
 */

public class SmartScriptParser {

	/**
	 * Reference for lexer used for creating tokens
	 */
	private SmartScriptLexer lexer;

	/**
	 * Node representing the beginning of document tree
	 */
	private DocumentNode documentNode;

	/**
	 * Constructing the parser with given document body text.
	 * 
	 * @param body a string that contains document body
	 */
	public SmartScriptParser(String body) {

		lexer = new SmartScriptLexer(body);

		documentNode = parse();

	}

	/**
	 * Return the created document tree node
	 * 
	 * @return document tree node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Method offers the functionality of creating a top level tree node of type
	 * {@link DocumentNode} that contains nodes that create the document tree of the
	 * parsed aka given input. Used as a help function.
	 * 
	 * @return the reference to the created document tree
	 */
	private DocumentNode parse() {
		Stack<Node> stack = new Stack<>();
		stack.push(new DocumentNode());

		nextToken();
		while (!isTokenOfType(SmartScriptTokenType.EOF)) {
			if (isTokenOfType(SmartScriptTokenType.TEXT)) {
				Node top = (Node) stack.peek();
				top.addChildNode(new TextNode((String) lexer.getToken().getValue()));
				nextToken();
				continue;
			}

			if (isTokenOfType(SmartScriptTokenType.OPEN_TAG)) {
				nextToken();
				if (isTokenOfType(SmartScriptTokenType.IDENTIFIER)) {
					String id = lexer.getToken().getValue().toString();
					if (id.toUpperCase().equals("FOR")) {
						Node top = (Node) stack.peek();
						ForLoopNode forNode = parseForLoopNode();
						top.addChildNode(forNode);
						stack.push(forNode);
						nextToken();
						continue;
					} else if (id.toUpperCase().equals("END")) {
						nextToken();
						if (isTokenOfType(SmartScriptTokenType.CLOSE_TAG)) {
							try {
								stack.pop();
								stack.peek();
								nextToken();
								continue;
							} catch (Exception e) {
								throw new SmartScriptParserException("Too many ENDs...");
							}
						}
					}

					throw new SmartScriptParserException("Invalid keyword " + id + "given in tag...");
				} else if (isTokenOfType(SmartScriptTokenType.SYMBOL)) {
					char c = (Character) lexer.getToken().getValue();
					if (c == '=') {
						Node top = (Node) stack.peek();
						nextToken();
						top.addChildNode(parseEchoNode());
						nextToken();
						continue;
					}
				}

			}

			throw new SmartScriptParserException("Invalid document body given...");
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("Too many or too few END Tags");
		}

		if (!(stack.peek() instanceof DocumentNode)) {
			throw new SmartScriptParserException("The first node should be a DocumentNode!");
		}

		return (DocumentNode) stack.pop();
	}

	/**
	 * Help function to parse only the tag that corresponds to the EchoNode (tag
	 * starting with symbol '=').
	 * 
	 * @return the parsed echo node
	 */
	private EchoNode parseEchoNode() {
		ArrayList<Element> children = new ArrayList<>();

		while (!isTokenOfType(SmartScriptTokenType.CLOSE_TAG)) {
			if (isTokenOfType(SmartScriptTokenType.IDENTIFIER)) {
				children.add(new ElementVariable((String) lexer.getToken().getValue()));
				nextToken();
				continue;
			} else if (isTokenOfType(SmartScriptTokenType.SYMBOL)) {
				char c = (char) lexer.getToken().getValue();
				if (isValidOperator(c)) {
					children.add(new ElementOperator(String.valueOf(c)));
					nextToken();
					continue;
				}
			} else if (isTokenOfType(SmartScriptTokenType.FUNCTION)) {
				children.add(new ElementFunction((String) lexer.getToken().getValue()));
				nextToken();
				continue;
			} else if (isTokenOfType(SmartScriptTokenType.STRING)) {
				children.add(new ElementString((String) lexer.getToken().getValue()));
				nextToken();
				continue;
			} else if (isTokenOfType(SmartScriptTokenType.NUMBER)) {
				String numberString = lexer.getToken().getValue().toString();
				if (numberString.contains(".")) {
					children.add(new ElementConstantDouble(Double.parseDouble(numberString)));
				} else {
					children.add(new ElementConstantInteger(Integer.parseInt(numberString)));
				}
				nextToken();
				continue;
			}

			// For everything else throw an error
			throw new SmartScriptParserException("Invalid element given to EchoNode");
		}

		int childredSize = children.size();
		Element[] elementArray = new Element[childredSize];
		for (int i = 0; i < childredSize; i++) {
			elementArray[i] = (Element) children.get(i);
		}

		return new EchoNode(elementArray);
	}

	/**
	 * Help function to check if given char is a supported operation.
	 * 
	 * @param c to test if supported
	 * @return true if supported operation
	 */
	private boolean isValidOperator(char c) {
		return (c == '+' || c == '*' || c == '/' || c == '-' || c == '^');
	}

	/**
	 * Help function to return parsed FOR tag
	 * 
	 * @return parsed node of a for loop
	 */
	private ForLoopNode parseForLoopNode() {

		nextToken();
		ElementVariable variable;
		if (isTokenOfType(SmartScriptTokenType.IDENTIFIER)) {
			variable = new ElementVariable((String) lexer.getToken().getValue());
		} else {
			throw new SmartScriptParserException("Error in FOR loop tag - first element should be an identifier");
		}

		nextToken();
		Element startExpression = initializeForLoopExpression();

		nextToken();
		Element endExpression = initializeForLoopExpression();

		nextToken();
		Element stepExpression;
		if (isTokenOfType(SmartScriptTokenType.CLOSE_TAG)) {
			stepExpression = null;
		} else {
			stepExpression = initializeForLoopExpression();
			nextToken();
			if (!isTokenOfType(SmartScriptTokenType.CLOSE_TAG)) {
				throw new SmartScriptParserException("Invalid FOR loop...");
			}
		}

		return new ForLoopNode(variable, startExpression, endExpression, stepExpression);

	}

	/**
	 * Help function to safely get the next token from the lexer (catching any
	 * errors of the lexer on the way)
	 */
	private void nextToken() {
		try {
			lexer.nextToken();
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException("Lexer throws some errors of his own when giving next characters...");
		}
	}

	/**
	 * Help function used to initialize some of the for loop variables.
	 * 
	 * @return The element that will be part of the for loop elements
	 */
	private Element initializeForLoopExpression() {
		if (isTokenOfType(SmartScriptTokenType.IDENTIFIER)) {
			return new ElementVariable(lexer.getToken().getValue().toString());
		} else if (isTokenOfType(SmartScriptTokenType.NUMBER)) {
			String numberString = lexer.getToken().getValue().toString();
			if (numberString.contains(".")) {
				return new ElementConstantDouble(Double.parseDouble(numberString));
			} else {
				return new ElementConstantInteger(Integer.parseInt(numberString));
			}
		} else if (isTokenOfType(SmartScriptTokenType.STRING)) {
			return new ElementString(lexer.getToken().getValue().toString());
		}

		throw new SmartScriptParserException("Invalid tokens in for loop expressions");
	}

	/**
	 * Help method to check if last token generated was of given type.
	 * 
	 * @param type Type to check
	 * @return true if type of last generated token is the same as the given type
	 */
	private boolean isTokenOfType(SmartScriptTokenType type) {
		Objects.requireNonNull(type);

		return lexer.getToken().getType() == type;
	}

	/**
	 * Gives the functionality of recreating original document body text of
	 * generated tree structure. Assures accurate recreation of
	 * {@link SmartScriptParser}.
	 * 
	 * @param document that points to the root of the document tree
	 * @return original document body
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		return document.toString();
	}

}
