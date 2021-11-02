package hr.fer.zemris.java.hw06.shell.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class gives the functionality of parsing an expression string where
 * expression of the form "${inside}" occur and are later replaced by groups
 * extracted from {@link FilterResult}. This parser creates an
 * {@link NameBuilder} that can take an {@link FilterResult} and build a new out
 * of it. There are specific rules for valid expressions. The expression can
 * occur only in these two forms:<br>
 * <li>${numberOfGroup} - numberOfGroup is a positive non-zero integer
 * <li>${numberOfGroup, minimalPadding} - numberOfGruop is again a positive
 * non-zero integer, minimalPadding is an positive integer that can be zero. The
 * minimalPadding can also have an additional leading zero that denotes that the
 * padding should be done by adding zeros (where by default spaces are added).
 * <br>
 * There can be any number of spaces around numberOfGroups and minimalPadding,
 * but there can not be any spaces between ${. Here is a list of valid
 * expressions:
 * <li>${1} - take the group with index 1 from {@link FilterResult} and put it
 * into this place
 * <li>${ 1\t \t } - same as ${1}
 * <li>${2} - take the group with index 2 from {@link FilterResult} and put it
 * into this place
 * <li>${123} - take the group with index 123 from {@link FilterResult} and put
 * it into this place
 * <li>${1, 0} - the same as ${1}
 * <li>${1, 00} - the same as ${1, 0}. The zero denotes that no padding will be
 * added at all
 * <li>${1, 7} - take the group with index 1 from {@link FilterResult} and put
 * it into this place with added padding of spaces if the string is shorter than
 * 7 places <br>
 * Some examples of invalid expressions:
 * <li>$ {1}
 * <li>${a}
 * <li>${$2}
 * <li>${0}
 * <li>${-1}
 * <li>${10, -10}
 * <li>${1, 1, 1}
 * 
 * @author Frano Rajič
 */
public class NameBuilderParser {

	/**
	 * The parsed name builder
	 */
	NameBuilder nameBuilder;

	/**
	 * Create an new name builder parser with given string expression
	 * 
	 * @param expression the expression parsed to create the name builder
	 */
	public NameBuilderParser(String expression) {
		Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(expression);
		nameBuilder = NameBuilder.blank();

		while (m.find()) {
			String before = expression.substring(0, m.start()); // text before next expression
			if (before.contains("}")) { // if } is in the text before ${ is considered invalid!
				throw new IllegalArgumentException(
						"Invalid expression given - consider the number and position of ${ and }");
			}
			nameBuilder = nameBuilder.text(before);

			// String outside = m.group(0); // ${xxx} - inside with ${}
			String inside = m.group(1).trim(); // xxx - what is inside of next ${xxx}
			ExpressionResult ex = new ExpressionResult(inside);
			nameBuilder = nameBuilder.group(ex.getGroup(), ex.getPadding(), ex.getMinWidth());

			expression = expression.substring(m.end()); // Strings in java are immutable
			m = p.matcher(expression);
		}

		if (expression.contains("${")) { // if ${ is in the text after last } is considered invalid!
			throw new IllegalArgumentException(
					"Invalid expression given - consider the number and position of ${ and }");
		}
		nameBuilder = nameBuilder.text(expression);
	}

	/**
	 * Get the parsed name builder
	 * 
	 * @return the name builder
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;
	}

	/**
	 * Help class that models an parsed expression. The class can tell if the given
	 * expression string is valid and if so it will store the group number, the
	 * width to be filled and the character used for filling. Expression modeled
	 * here are used to denote how an formated string should later look like, so
	 * this expression is specifying its minimal width, characters to be used to
	 * fill the missing places for the formated string to have the minimal width and
	 * the group that should be placed onto the place of this expression when
	 * creating the formated string. <br>
	 * 
	 * Examples of valid expressions: "2", "2, 1", "2, 01", "2, 0", "2, 00"...
	 * 
	 * @author Frano Rajič
	 */
	private static class ExpressionResult {

		/**
		 * The group to be placed onto the place of this expression
		 */
		private int group;

		/**
		 * The minimal width of the string that will be put onto the place of this
		 * expression
		 */
		private int minWidth = 0;

		/**
		 * The characters to be used when filling
		 */
		private char padding = ' ';

		/**
		 * Create an instance of the resulting parsed expression given by expression
		 * string.
		 * 
		 * 
		 * @param expressionString the string containing the expression to be parsed
		 * @throws IllegalArgumentException If the given string is invalid
		 */
		public ExpressionResult(String expressionString) {
			String[] parts = expressionString.split(",");

			try {

				if (parts.length == 1) {
					group = Integer.parseInt(parts[0].trim());
					if (group <= 0) {
						throw new IllegalArgumentException("The group number must be positive and non zero!");
					}
					return;
				}

				if (parts.length == 2) {
					group = Integer.parseInt(parts[0].trim());
					if (parts[1].trim().startsWith("0")) {
						padding = '0';
						parts[1] = parts[1].trim().substring(1);
					}
					minWidth = Integer.parseInt(parts[1]);
					if (group <= 0 || minWidth < 0) {
						throw new IllegalArgumentException("Numbers must be positive!");
					}
					return;
				}

			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Given expression string does not have valid formating inside - there was no number where a number should have been");
			}

			throw new IllegalArgumentException("Cannot parse expression, invalid number of arguments");
		}

		/**
		 * Get the group defined in the expression
		 * 
		 * @return the group that the expression held
		 */
		private int getGroup() {
			return group;
		}

		/**
		 * The minimal width latter to be used when filling with characters.
		 * 
		 * @return the minimal width
		 */
		private int getMinWidth() {
			return minWidth;
		}

		/**
		 * Get the character used for padding
		 * 
		 * @return the char of padding
		 */
		private char getPadding() {
			return padding;
		}

		@Override
		public String toString() {
			return "Group: " + group + " Width: " + minWidth + " Char: '" + padding + "'";
		}

	}
}
