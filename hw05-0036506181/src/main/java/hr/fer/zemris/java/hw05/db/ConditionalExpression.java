package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class provides the functionality of storing an conditional expression made of
 * this three parts: a concrete strategy for getting the value of a field of, a
 * concrete strategy for the comparison operation and a condition string to do
 * the comparison with.
 * 
 * @author Frano Rajič
 */
public class ConditionalExpression {

	/**
	 * The concrete strategy for getting the value of a field
	 */
	private IFieldValueGetter fieldvalueGetter;

	/**
	 * Concrete strategy for the comparison operation
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * String given in the condition to do the comparison with
	 */
	private String stringLiteral;

	/**
	 * Create an conditional expression from given concrete strategies and the
	 * condition string
	 * 
	 * @param fieldvalueGetter   concrete strategy for getting the value of a field
	 * @param comparisonOperator concrete strategy for the comparison operation
	 * @param stringLiteral      string given in the condition to do the comparison
	 *                           with
	 * @throws NullPointerException if any of the arguments are null pointers
	 */
	public ConditionalExpression(IFieldValueGetter fieldvalueGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		Objects.requireNonNull(fieldvalueGetter);
		Objects.requireNonNull(comparisonOperator);
		Objects.requireNonNull(stringLiteral);

		this.fieldvalueGetter = fieldvalueGetter;
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
	}

	/**
	 * Get the concrete strategy for getting the value of a field
	 * 
	 * @return the concrete strategy
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldvalueGetter;
	}

	/**
	 * concrete strategy for the comparison operation
	 * 
	 * @return the concrete comparison strategy
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * string given in the condition to do the comparison with
	 * 
	 * @return the string of the condition expression with which the comparison is
	 *         done
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

}
