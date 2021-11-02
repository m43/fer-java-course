package hr.fer.zemris.java.hw06.shell.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class UtilityTest {

	@Test
	void testOneArgumentNoQuotes() {
		String s = "a";
		String[] expected = { "a" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testTwoArgumentsNoQuotes() {
		String s = "a123 123";
		String[] expected = { "a123", "123" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testOneArgumentsWithQuotes() {
		String s = "\"oo\"";
		String[] expected = { "oo" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testOneArgumentsWithQuotesAndSouroundedBySpaces() {
		String s = " \r  \t \"oo\"  \t  ";
		String[] expected = { "oo" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testTwoArgumentsWithQuotes() {
		String s = "a123 \"ooo\"";
		String[] expected = { "a123", "ooo" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testMultipleArgumentsWithoutQuotes() {
		String s = "a b c ddd e||e oOo123 123 [][] ee";
		String[] expected = { "a", "b", "c", "ddd", "e||e", "oOo123", "123", "[][]", "ee" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testOneQuoteWithOneEscape() {
		String s = "\"12\\\"345\"";
		;
		String[] expected = { "12\"345" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testQuotesAndSpacesAndEscape() {
		String s = "a \"b\" c \" d \\d \\\\d f\\\"f \"";
		;
		String[] expected = { "a", "b", "c", "d \\d \\d f\"f" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));

	}

	@Test
	void testComplicatedWithSpacesAndQuotesAndEscaping() {
		String s = "a \"b\" c \" d \\d \\\\d f\\\"f \"";
		;
		String[] expected = { "a", "b", "c", "d \\d \\d f\"f" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testCocretePath() {
		String s = "\"C:\\Documents and Settings\\Users\\javko\"";
		String[] expected = { "C:\\Documents and Settings\\Users\\javko" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testConcretePath2() {
		String s = "\"C:\\\\Documents and Settings\\\\Users\\\\javko\"";
		String[] expected = { "C:\\Documents and Settings\\Users\\javko" };

		checkExpected(expected, Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testOriginalUnchanged() {
		String s1 = " \t  aa \"aaa\"   ";
		String s2 = " \t  aa \"aaa\"   ";
		Utility.splitArgumentsContainingStrings(s2);
		assertEquals(s1, s2);
	}

	@Test
	void testThrowsWhenNoSpaceAfterQuotation() {
		String s = "\"C:\\fi le\".txt";
		assertThrows(IllegalArgumentException.class, () -> Utility.splitArgumentsContainingStrings(s));
	}

	@Test
	void testThrowsWhenNoClosingQuotation() {
		String s = "\"C:\\fi le";
		assertThrows(IllegalArgumentException.class, () -> Utility.splitArgumentsContainingStrings(s));
	}

	@Test

	/**
	 * Check if an array and list have the same elements with assertion, expected
	 * string array and actual collection of elements given.
	 * 
	 * @param a expected string array
	 * @param b actual string collection
	 */
	private void checkExpected(String[] a, String[] b) {
		assertEquals(a.length, b.length);

		for (int i = 0; i < a.length; i++) {
			assertEquals(a[i], b[i]);
		}
	}

}
