package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class ComparisonOperatorsTest {

	@Test
	void testExampleCode() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna")); // true, since Ana < Jasna
		// In like operator, first argument will be string to be checked, and the second
		// argument pattern to be checked.
		oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*")); // false
		assertFalse(oper.satisfied("AAA", "AA*AA")); // false
		assertTrue(oper.satisfied("AAAA", "AA*AA")); // true
	}

	@Test
	void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertFalse(oper.satisfied(s, sLess));
		assertTrue(oper.satisfied(s, sGreater));
		assertFalse(oper.satisfied(s, sEqual));
	}

	@Test
	void testLessOrEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertFalse(oper.satisfied(s, sLess));
		assertTrue(oper.satisfied(s, sGreater));
		assertTrue(oper.satisfied(s, sEqual));
	}

	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertTrue(oper.satisfied(s, sLess));
		assertFalse(oper.satisfied(s, sGreater));
		assertFalse(oper.satisfied(s, sEqual));
	}

	@Test
	void testGreaterOrEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertTrue(oper.satisfied(s, sLess));
		assertFalse(oper.satisfied(s, sGreater));
		assertTrue(oper.satisfied(s, sEqual));
	}

	@Test
	void testEqual() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertFalse(oper.satisfied(s, sLess));
		assertFalse(oper.satisfied(s, sGreater));
		assertTrue(oper.satisfied(s, sEqual));
	}

	@Test
	void testNotEqual() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertTrue(oper.satisfied(s, sLess));
		assertTrue(oper.satisfied(s, sGreater));
		assertFalse(oper.satisfied(s, sEqual));
	}

	@Test
	void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		String sLess = "Aaa";
		String s = "Baa";
		String sEqual = "Baa";
		String sGreater = "Caa";

		assertFalse(oper.satisfied(s, sLess));
		assertFalse(oper.satisfied(s, sGreater));
		assertTrue(oper.satisfied(s, sEqual));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied(s, "ee*e*aaa"));
		assertFalse(oper.satisfied(s, "Ba*aa"));
		assertFalse(oper.satisfied(s, "*aaa"));
		assertTrue(oper.satisfied(s, "B*aa"));
		assertTrue(oper.satisfied(s, "B*a"));
		assertTrue(oper.satisfied(s, "B*"));
		assertTrue(oper.satisfied(s, "*aa"));
		assertTrue(oper.satisfied(s, "*a"));
		assertTrue(oper.satisfied(s, "*"));
	}

}
