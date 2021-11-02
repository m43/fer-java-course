package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * Class gives the functionality of testing a record acording to multiple
 * {@link ConditionalExpression}'s
 * 
 * @author Frano Rajič
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List storing the conditional expressions to test given record upon
	 */
	private List<ConditionalExpression> queryConditions;

	/**
	 * Creation of the objects is made by passing an list of conditional expressions
	 * 
	 * @param queryConditions given conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> queryConditions) {
		Objects.requireNonNull(queryConditions, "Given conditions list cannot be a null pointer");
		if (queryConditions.isEmpty()) {
			throw new IllegalArgumentException("Given conditions list cannot be empty");
		}

		this.queryConditions = queryConditions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		if (record == null) {
			return false;
		}

		boolean recordSatisfies;
		for (ConditionalExpression c : queryConditions) {
			recordSatisfies = c.getComparisonOperator().satisfied(c.getFieldGetter().get(record), c.getStringLiteral());
			if (!recordSatisfies)
				return false;
		}

		return true;
	}

}
