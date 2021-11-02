package hr.fer.zemris.java.hw05.db;

/**
 * Class provides static implementations of {@link IComparisonOperator}.
 * 
 * @author Frano
 *
 */
public class ComparisonOperators {

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does the operation less than
	 */
	public static final IComparisonOperator LESS;

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does the operation less than or equal
	 */
	public static final IComparisonOperator LESS_OR_EQUALS;

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does the operation greater than
	 */
	public static final IComparisonOperator GREATER;

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does the operation greater than or equal
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS;

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does the operation of checking for equality
	 */
	public static final IComparisonOperator EQUALS;

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does the operation of checking for inequality
	 */
	public static final IComparisonOperator NOT_EQUALS;

	/**
	 * Instance of class implementing {@link IComparisonOperator}. This operator
	 * does a special operation of comparing the first string with the second with a
	 * special character in the second string permitted. The special character is
	 * the asterisk (aka *) and if present in second string it is regarded as being
	 * a placeholder for zero or more characters of any kind except an asterisk.
	 * There can be only one asterisk. All of this are legit second strings
	 * {"Ba*aa", "B*a", "*aaa", "B*","*a", "*"}
	 */
	public static final IComparisonOperator LIKE;

	/**
	 * Static initializer initializes the static implementations of
	 * {@link IComparisonOperator}
	 */
	static {
		LESS = (x, y) -> x.compareTo(y) < 0;
		LESS_OR_EQUALS = (x, y) -> x.compareTo(y) <= 0;
		GREATER = (x, y) -> x.compareTo(y) > 0;
		GREATER_OR_EQUALS = (x, y) -> x.compareTo(y) >= 0;
		EQUALS = (x, y) -> x.equals(y);
		NOT_EQUALS = (x, y) -> !x.equals(y);
		LIKE = (x, y) -> {
			String[] parts = y.split("\\*", -1);

			if (parts.length > 2) {
				throw new IllegalArgumentException(
						"String literal cannot have more than one asterix in LIKE comparison");
			}

			if (parts.length == 1) {
				return x.equals(y);
			}

			if (!x.startsWith(parts[0])) {
				return false;
			}

			String xEnding = x.substring(parts[0].length());
			if (!xEnding.endsWith(parts[1])) {
				return false;
			}

			return true;
		};
	}

}
