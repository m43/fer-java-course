package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This help class models an {@link INodeVisitor} that goes through the parsed
 * document body tree and writes an tree of its elements.
 * 
 * @author Frano Rajič
 */
public class EngineVisitor implements INodeVisitor {

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
	 * String builder used to load the output for the request. The output will then
	 * be send if everything is OK at end, but if for example an exception occurs,
	 * then the created output will not be send at all to given output stream. An
	 * exception would otherwise leave the output unfinished in the middle of the
	 * writing. (To provoke an exception - start script like this:
	 * http://www.localhost.com:5721/scripts/zbrajanje.smscr?a=3&b=1000000000000000000000000000000000000000000000000000000000000000000000000000000
	 * )
	 */
	private StringBuilder output = new StringBuilder();

	/**
	 * Create a new {@link EngineVisitor} with given request context and multistack
	 * 
	 * @param requestContext the request context
	 * @param multistack     the multistack
	 */
	public EngineVisitor(RequestContext requestContext, ObjectMultistack multistack) {
		this.requestContext = requestContext;
		this.multistack = multistack;
	}

	/**
	 * The key used for temporary stack in nodes of type {@link EchoNode}
	 */
	private final String ECHO_KEY = "1998";

	/**
	 * The key used for temporary swap stack when dumping result string in the node
	 * {@link EchoNode}
	 */
	private final String SWAP_STACK_KEY = "1997";

	/**
	 * Functions supported by {@link EchoNode} that take no arguments and return
	 * nothing
	 */
	Map<String, Runnable> voidFunctions = new HashMap<>();
	{
		voidFunctions.put("swap", () -> {
			ValueWrapper x = multistack.pop(ECHO_KEY);
			ValueWrapper y = multistack.pop(ECHO_KEY);
			multistack.push(ECHO_KEY, x);
			multistack.push(ECHO_KEY, y);
		});
		voidFunctions.put("dup",
				() -> multistack.push(ECHO_KEY, new ValueWrapper(multistack.peek(ECHO_KEY).getValue())));
	}

	/**
	 * Functions supported by {@link EchoNode} that one arguments and return the
	 * resulting {@link ValueWrapper} that should then be pushed onto stack. If null
	 * is returned, then nothing should be pushed onto stack!
	 */
	Map<String, UnaryOperator<ValueWrapper>> unaryFunctions = new HashMap<>();
	{
		unaryFunctions.put("sin", x -> {
			try {
				x.add(Double.valueOf(0.0));
				return new ValueWrapper(Math.sin(Math.toRadians((Double) x.getValue())));
			} catch (RuntimeException e) {
				throw new SmartScriptEngineException(
						"Sin operation could not be done cause there was no number value found.");
			}
		});

		unaryFunctions.put("pparamDel", x -> nameConsumer(x, z -> requestContext.removePersistentParameter(z)));

		unaryFunctions.put("tparamDel", x -> nameConsumer(x, z -> requestContext.removeTemporaryParameter(z)));

		unaryFunctions.put("setMimeType", x -> nameConsumer(x, z -> requestContext.setMimeType(z)));
	}

	/**
	 * Functions supported by {@link EchoNode} that two arguments and return the
	 * resulting {@link ValueWrapper} that should then be pushed onto stack. If null
	 * is returned, then nothing should be pushed onto stack!
	 */
	Map<String, BinaryOperator<ValueWrapper>> binaryFunctions = new HashMap<>();
	{
		binaryFunctions.put("decfmt", (a, b) -> {
			String format;
			if (b.getValue() instanceof String) {
				format = (String) b.getValue();
			} else {
				throw new SmartScriptEngineException(
						"Didnt find string, eventhough it is necessary for the decimal format function! The Value wrapper value was of class: "
								+ (b.getValue() == null ? null : b.getValue().getClass()));
			}

			try {
				a.add(Double.valueOf(0.0));
				NumberFormat formatter = new DecimalFormat(format);
				ValueWrapper result = new ValueWrapper(formatter.format((Double) a.getValue()));
				return result;
			} catch (RuntimeException e) {
				throw new SmartScriptEngineException("Couldn't do the decimal format operation.");
			}
		});

		binaryFunctions.put("paramGet", (x, y) -> parameterGetter(x, y, z -> requestContext.getParameter(z)));

		binaryFunctions.put("pparamGet",
				(x, y) -> parameterGetter(x, y, z -> requestContext.getPersistentParameter(z)));

		binaryFunctions.put("tparamGet", (x, y) -> parameterGetter(x, y, z -> requestContext.getTemporaryParameter(z)));

		binaryFunctions.put("pparamSet",
				(x, y) -> parameterSetter(x, y, (name, value) -> requestContext.setPersistentParameter(name, value)));

		binaryFunctions.put("tparamSet",
				(x, y) -> parameterSetter(x, y, (name, value) -> requestContext.setTemporaryParameter(name, value)));

	}

	@Override
	public void visitTextNode(TextNode node) {
		output.append(node.getText());
	}

	@Override
	public void visitForLoopNode(ForLoopNode node) {
		Number start = getExpressionNumber(node.getStartExpression());
		Number step = getExpressionNumber(node.getStepExpression());
		Number end = getExpressionNumber(node.getEndExpression());
		if (start == null || step == null || end == null) {
			throw new SmartScriptEngineException("Invalid start/step/end expression in for loop!");
		}

		String variableName = node.getVariable().getName();
		ValueWrapper variableValue = new ValueWrapper(start);
		multistack.push(variableName, variableValue);

		while (multistack.peek(variableName).numCompare(end) <= 0) {
			goDeeper(node);
			multistack.peek(variableName).add(step);
		}
	}

	@Override
	public void visitEchoNode(EchoNode node) {
		for (Element e : node.getElements()) {

			Number n = getExpressionNumber(e);
			if (n != null) {
				multistack.push(ECHO_KEY, new ValueWrapper(n));
				continue;
			}

			if (e instanceof ElementString) {
				String s = ((ElementString) e).getValue();
				multistack.push(ECHO_KEY, new ValueWrapper(s));
				continue;
			}

			if (e instanceof ElementVariable) {
				ValueWrapper varValue = multistack.peek(((ElementVariable) e).getName());
				multistack.push(ECHO_KEY, new ValueWrapper(varValue.getValue()));
				continue;
			}

			if (e instanceof ElementOperator) {
				operator(((ElementOperator) e).getSymbol());
				continue;
			}

			if (e instanceof ElementFunction) {
				function(((ElementFunction) e).getName());
				continue;
			}
		}

		while (!multistack.isEmpty(ECHO_KEY)) {
			multistack.push(SWAP_STACK_KEY, multistack.pop(ECHO_KEY));
		}

		StringBuilder sb = new StringBuilder();
		while (!multistack.isEmpty(SWAP_STACK_KEY)) {
			sb.append(String.valueOf(multistack.pop(SWAP_STACK_KEY).getValue()));
		}

		output.append(sb.toString());

	}

	@Override
	public void visitDocumentNode(DocumentNode node) {
		goDeeper(node);
		try {
			requestContext.write(output.toString());
		} catch (IOException e) {
			throw new SmartScriptEngineException("Couldn't write to requestContext ..?");
		}
	}

	/**
	 * Help method that goes through all children of a node and calls the visitor
	 * onto them
	 * 
	 * @param node the node to dig into
	 */
	private void goDeeper(Node node) {
		for (int i = 0; i < node.numberOfChildren(); i++) {
			node.getChild(i).accept(this);
		}
	}

	/**
	 * Help method to convert {@link Element} expression to a number value. If no
	 * element expression given, then null is returned
	 * 
	 * @param e the element to convert
	 * @return the number value or null if no number value inside element
	 */
	private Number getExpressionNumber(Element e) {
		if (e instanceof ElementConstantInteger) {
			return Integer.valueOf(((ElementConstantInteger) e).getValue());
		} else if (e instanceof ElementConstantDouble) {
			return Double.valueOf(((ElementConstantDouble) e).getValue());
		}
		return null;
	}

	/**
	 * Help method used to carry out supported operations. Supported operations are
	 * +, -, / and *.
	 * 
	 * @param symbol the symbol of the operation
	 */
	private void operator(String symbol) {
		ValueWrapper o1 = multistack.pop(ECHO_KEY);
		ValueWrapper o2 = multistack.pop(ECHO_KEY);

		try {

			switch (symbol) {
			case "*":
				o2.multiply(o1.getValue());
				break;

			case "+":
				o2.add(o1.getValue());
				break;

			case "-":
				o2.subtract(o1.getValue());
				break;

			case "/":
				o2.divide(o1.getValue());
				break;

			default:
				throw new SmartScriptEngineException(
						"Unsupported operator given to engine in echo node. The operator was: " + symbol);
			}

		} catch (RuntimeException e) {
			throw new SmartScriptEngineException("Unsupported values given for operator \"" + symbol
					+ "\" Consider the following error message: " + e.getMessage());
		}
		multistack.push(ECHO_KEY, o2);
	}

	/**
	 * Help method that is called to carry out functions supported by
	 * {@link EchoNode}.
	 * 
	 * @param name the name of the function
	 */
	private void function(String name) {

		if (voidFunctions.containsKey(name)) {
			voidFunctions.get(name).run();
			return;
		}

		if (unaryFunctions.containsKey(name)) {
			ValueWrapper x = multistack.pop(ECHO_KEY);

			ValueWrapper z = unaryFunctions.get(name).apply(x);
			if (z != null) {
				multistack.push(ECHO_KEY, z);
			}
			return;
		}

		if (binaryFunctions.containsKey(name)) {
			ValueWrapper y = multistack.pop(ECHO_KEY);
			ValueWrapper x = multistack.pop(ECHO_KEY);

			ValueWrapper z = binaryFunctions.get(name).apply(x, y);
			if (z != null) {
				multistack.push(ECHO_KEY, z);
			}
			return;
		}

		throw new SmartScriptEngineException("No function of name: " + name);
	}

	/**
	 * Help method used by {@link #binaryFunctions} for getting values from
	 * parameter maps of {@link RequestContext}
	 * 
	 * @param x        the wrapper containing the name
	 * @param y        the wrapper containing the default value
	 * @param getValue the operator that gets the value associated to x from
	 *                 {@link RequestContext}.
	 * @return the value that is returned by getValue or y as the default value if
	 *         getValue returns null
	 */
	private ValueWrapper parameterGetter(ValueWrapper x, ValueWrapper y, UnaryOperator<String> getValue) {
		String name;
		if (x.getValue() instanceof String || x.getValue() instanceof Number) {
			name = (String) x.getValue();
		} else {
			throw new SmartScriptEngineException("Didnt find a string in place of name!");
		}

		String value = getValue.apply(name);

		return (value == null ? y : new ValueWrapper(value));
	};

	/**
	 * Help method used by {@link #binaryFunctions} for setting values to parameter
	 * maps of {@link RequestContext}
	 * 
	 * @param x        the wrapper containing the name
	 * @param y        the wrapper containing the value to be set to key name
	 * @param consumer the consumer that does the actual updating of the map
	 * @return null which represents that nothing should be pushed onto stack after
	 *         this operation
	 */
	private ValueWrapper parameterSetter(ValueWrapper x, ValueWrapper y, BiConsumer<String, String> consumer) {
		String name;
		if (y.getValue() instanceof String || y.getValue() instanceof Number) {
			name = String.valueOf(y.getValue());
		} else {
			throw new SmartScriptEngineException("Didnt find a string in place of name!");
		}

		String value;
		if (x.getValue() instanceof String || x.getValue() instanceof Number) {
			value = String.valueOf(x.getValue());
		} else {
			throw new SmartScriptEngineException("Didnt find a string in place of value!");
		}

		consumer.accept(name, value);

		return null;
	}

	/**
	 * Help method used by {@link #unaryFunctions} for consuming one single name
	 * that is given in {@link ValueWrapper}
	 * 
	 * @param x        the name value that will be consumed
	 * @param consumer the consumer
	 * @return null, representing that nothing should be pushed onto stack after
	 *         this operation is done
	 */
	private ValueWrapper nameConsumer(ValueWrapper x, Consumer<String> consumer) {
		String name;
		if (x.getValue() instanceof String || x.getValue() instanceof Number) {
			name = String.valueOf(x.getValue());
		} else {
			throw new SmartScriptEngineException("Didnt find a string in place of name!");
		}

		consumer.accept(name);

		return null;
	}

}