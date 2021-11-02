package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Class models an node representing a for loop.
 * 
 * @author Frano Rajič
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
	 * What should be the step of iteration? Doesn't need to be specified aka can be
	 * null
	 */
	private Element stepExpression;

	/**
	 * Create a new {@link ForLoopNode} with variable and all three start, end and
	 * step expression given
	 * 
	 * @param variable        the variable
	 * @param startExpression the start excpression
	 * @param endExpression   the end expression
	 * @param stepExpression  the step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Create a new {@link ForLoopNode} with variable given as well as start and end
	 * expression. The step expression is regarded as null
	 * 
	 * @param variable        the variable
	 * @param startExpression the start expression
	 * @param endExpression   the end expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * Get the variable of this {@link ForLoopNode}.
	 * 
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Get the start expression of this {@link ForLoopNode}.
	 * 
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Get the end expression of this {@link ForLoopNode}.
	 * 
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Get the step expression of this {@link ForLoopNode}.
	 * 
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
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
