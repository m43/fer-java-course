package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Class models an for loop node. A for loop node has all the necessary
 * information to run a for loop: it stores an variable used for iterating, a
 * start expression that can be used to initialize the variable, a end
 * expression that will determine when the loop should break and a expression
 * defining how an iteration step is done.
 * 
 * @author user
 */
public class ForLoopNode extends Node {

	/**
	 * Variable to be the iterating one
	 */
	private ElementVariable variable;

	/**
	 * Expression stating starting index of iteration
	 */
	private Element startExpression;

	/**
	 * Expression containing ending index of iteration (inclusively).
	 */
	private Element endExpression;

	/**
	 * What should be the step of iteration? Doesn't need to eb specified aka can be
	 * null
	 */
	private Element stepExpression;

	/**
	 * Create a new for loop node with given variable as well as start, step and end
	 * expressions
	 * 
	 * @param variable        the variable to be used for the loop
	 * @param startExpression the start expression
	 * @param endExpression   the end expression
	 * @param stepExpression  expression defining steps
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Create a new for loop node with given variable as well as start and end
	 * expressions
	 * 
	 * @param variable        the variable to be used for the loop
	 * @param startExpression the start expression
	 * @param endExpression   the end expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * @return the startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * @return the endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * @return the stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{$FOR ");

		sb.append(variable);
		sb.append(" ");
		sb.append(startExpression);
		sb.append(" ");
		sb.append(endExpression);
		if (stepExpression != null) {
			sb.append(" ");
			sb.append(stepExpression);
		}

		sb.append(" $}");

		sb.append(super.toString());

		sb.append("{$END$}");

		return sb.toString();
	}

}
