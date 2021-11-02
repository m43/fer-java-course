package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.lexer.Lexer;
import hr.fer.zemris.java.hw05.lexer.TokenType;

/**
 * {@link QueryParser} provides the the functionality of parsing simple query
 * student record statements. The tokenization used by the parser is done by
 * {@link Lexer}. </br>
 * Query is performed in two different ways: </br>
 * 1. If query is given only a single attribute (which must be JMBAG) and a
 * comparison operator is =, the command obtains the requested student
 * efficiently in O(1) complexity. </br>
 * 2.For any other query (a single JMBAG but operator is not =, or any query
 * having more than one attribute), the command performs sequential record
 * filtering using the given expressions. Filtering expressions are built using
 * only JMBAG, lastName and firstName attributes. No other attributes are
 * allowed in query. Filtering expression consists from multiple comparison
 * expressions. If more than one expression is given, all of them must be
 * composed by logical AND operator. No grouping by parentheses is supported and
 * allowed attributes are only those whose value is string. </br>
 * Examples of valid query statements: </br>
 * jmbag="0000000003" </br>
 * lastName = "Blažić"</br>
 * firstName>"A" and lastName LIKE "B*ć"</br>
 * firstName>"A" and firstName<"C" and lastName LIKE "B*ć" and jmbag>"0000000002"</br>
 * 
 * @author Frano Rajič
 */
public class QueryParser {

	/**
	 * Lexer used for tokenisation
	 */
	private Lexer lexer;

	/**
	 * Is the given query direct or not is stored in this variable
	 */
	private boolean isDirect;

	/**
	 * List of conditional expressions that were a result of parsing is stored here
	 */
	private List<ConditionalExpression> conditions;

	/**
	 * Create parser with given query and start parsing
	 * 
	 * @param query to parse
	 */
	public QueryParser(String query) {
		Objects.requireNonNull(query);

		lexer = new Lexer(query.trim());
		conditions = new LinkedList<>();
		parse();
	}

	/**
	 * Help function that does the actual parsing
	 * 
	 * @throws IllegalArgumentException if invalid query given
	 */
	private void parse() {

		ConditionalExpression exp = parseCondition();
		conditions.add(exp);
		lexer.nextToken();

		if (isConditionDirect(exp) && isEOF()) {
			isDirect = true;
			return;
		}
		isDirect = false;

		while (!isEOF()) {
			if (!isOfType(TokenType.LOGICAL_OPERATOR)) {
				throw new IllegalArgumentException("A logical operator is between multiple conditional expressions");
			}
			if (!lexer.getToken().getValue().equals("AND")) {
				throw new IllegalArgumentException("Only the logical operator AND is permitted");
			}

			exp = parseCondition();
			conditions.add(exp);
			lexer.nextToken();
		}
	}

	/**
	 * Help function to check if given condition is direct
	 * 
	 * @param exp gives the condition
	 * @return true if condition is a direct one
	 */
	private boolean isConditionDirect(ConditionalExpression exp) {

		if (!exp.getComparisonOperator().equals(ComparisonOperators.EQUALS)) {
			return false;
		}

		if (!exp.getFieldGetter().equals(FieldValueGetters.JMBAG)) {
			return false;
		}

		return true;
	}

	/**
	 * Help function that parses and returns the next {@link ConditionalExpression}
	 * 
	 * @return the parsed conditional expression
	 */
	private ConditionalExpression parseCondition() {
		lexer.nextToken();
		IFieldValueGetter fieldvalueGetter = getField();

		lexer.nextToken();
		IComparisonOperator comparisonOperator = getComparisonOperator();

		lexer.nextToken();
		String stringLiteral = getStringLiteral();

		return new ConditionalExpression(fieldvalueGetter, stringLiteral, comparisonOperator);
	}

	/**
	 * Returns the field getter
	 * 
	 * @return field getter
	 * @throws IllegalArgumentException if token is not a field token or the field
	 *                                  is unsupported
	 */
	private IFieldValueGetter getField() {
		if (isEOF() || !isOfType(TokenType.IDENTIFIER)) {
			throw new IllegalArgumentException("Invalid input. Expected an IDENTIFIER token.");
		}

		switch (lexer.getToken().getValue()) {
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "jmbag":
			return FieldValueGetters.JMBAG;
		default:
			throw new IllegalArgumentException("Invalid input. Unsupported identifier for field given.");
		}
	}

	/**
	 * Returns the string literal
	 * 
	 * @return the string literal
	 * @throws IllegalArgumentException if current token is not a string token
	 */
	private String getStringLiteral() {
		if (isEOF() || !isOfType(TokenType.STRING)) {
			throw new IllegalArgumentException("Invalid input. Expected an STRING token.");
		}
		return lexer.getToken().getValue();
	}

	/**
	 * Return an comparison operator
	 * 
	 * @return an comparison operator from current token
	 * @throws IllegalArgumentException if invalid token or unsuported operation
	 *                                  found in token
	 */
	private IComparisonOperator getComparisonOperator() {
		if (isEOF() || !isOfType(TokenType.OPERATION)) {
			throw new IllegalArgumentException("Invalid input. Expected an OPERATION token.");
		}

		switch (lexer.getToken().getValue()) {
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new IllegalArgumentException("Invalid input. Unsuported operation.");
		}
	}

	/**
	 * Checks eof next
	 * 
	 * @return true if eof is the current token of lexer
	 */
	private boolean isEOF() {
		return TokenType.EOF.equals(lexer.getToken().getType());
	}

	/**
	 * Check if given type is the current token of lexer
	 * 
	 * @param t type to check
	 * @return true if it is the given type
	 */
	private boolean isOfType(TokenType t) {
		return t.equals(lexer.getToken().getType());
	}

	/**
	 * Method returns true if the query is a direct query. Direct queries are of
	 * form jmbag="xxx" (i.e. it must have only one comparison, on attribute jmbag,
	 * and operator must be equals).
	 * 
	 * @return true if direct query
	 */
	public boolean isDirectQuery() {
		return isDirect;
	}

	/**
	 * Method returns the string which was given in equality comparison in direct
	 * query.
	 * 
	 * @return the JMBAG value of the direct query
	 * @throws IllegalStateException if query is not direct
	 */
	public String getQueriedJMBAG() {
		if (!isDirect) {
			throw new IllegalStateException();
		}
		return conditions.get(0).getStringLiteral();
	}

	/**
	 * For all queries, this method must return a list of conditional expressions
	 * from query
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return conditions;
	}

}
