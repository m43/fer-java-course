package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Class models an builder and offers the functionality of filling string
 * builders with data extracted from {@link FilterResult} objects.
 * 
 * @author Frano Rajič
 */
public interface NameBuilder {

	/**
	 * Execute the filling of the given string builder with given
	 * {@link FilterResult}.
	 * 
	 * @param result The result to filter
	 * @param sb     The string builder to fill
	 */
	void execute(FilterResult result, StringBuilder sb);

	/**
	 * Expand the current name builder with adding the given text to the string
	 * builder
	 * 
	 * @param t The text to add to the string builder
	 * @return the new name builder
	 */
	default NameBuilder text(String t) {
		return (x, y) -> {
			execute(x, y);
			y.append(t);
		};
	}

	/**
	 * Create a blank name builder that does absolutely nothing to given arguments.
	 * This empty static implementation can be used as a starting point for the
	 * creating of a more complicate name builder by expanding this one step by
	 * step.
	 * 
	 * @return An instance of an blank name builder
	 */
	static NameBuilder blank() {
		return (x, y) -> {
		};
	}

	/**
	 * Expand the current name builder with adding an group extracted from
	 * {@link FilterResult} to the current builder.
	 * 
	 * @param index The index of the group to extract
	 * @return the new name builder
	 */
	default NameBuilder group(int index) {
		return (x, y) -> {
			execute(x, y);
			y.append(x.group(index));
		};
	}

	/**
	 * Expand the current name builder with adding an group extracted from
	 * {@link FilterResult} to the current builder, but with formatting that adds
	 * padding to left of extracted string, with padding characters and minimal
	 * formated string width specified.
	 * 
	 * @param index    the index of the group to extract
	 * @param padding  the character used as padding character
	 * @param minWidth the minimal width of formated extracted string
	 * @return the new name builder
	 */
	default NameBuilder group(int index, char padding, int minWidth) {
		return (x, y) -> {
			execute(x, y);
			y.append(addLeftPadding(x.group(index), padding, minWidth));
		};
	}

	/**
	 * Help method that is used to add padding to given string with given padding
	 * character and given minimal width of final formated string.
	 * 
	 * @param s        the string to be formated by adding padding
	 * @param padding  the character used for padding
	 * @param minWidth the minimal width of created string
	 * @return the created formated string
	 */
	public static String addLeftPadding(String s, char padding, int minWidth) {
		if (s.length() >= minWidth)
			return s;
		return String.valueOf(padding).repeat(minWidth - s.length()) + s;

	}
}
