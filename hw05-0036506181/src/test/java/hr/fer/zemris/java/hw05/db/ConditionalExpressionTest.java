package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class ConditionalExpressionTest {

	@Test
	void testCodeExample() {
		StudentRecord record = new StudentRecord("1 Bosanac Bosonog 5");

		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);

		boolean recordSatisfies = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral());
		assertTrue(recordSatisfies);
	}

	@Test
	void testConditionalExpressionConstruction() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		assertNotNull(expr);
	}

	@Test
	void testConditionalExpressionConstructionWithNullValues() {
		assertThrows(NullPointerException.class,
				() -> new ConditionalExpression(null, "Bos*", ComparisonOperators.LIKE));
		assertThrows(NullPointerException.class,
				() -> new ConditionalExpression(FieldValueGetters.LAST_NAME, null, ComparisonOperators.LIKE));
		assertThrows(NullPointerException.class,
				() -> new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", null));
		assertThrows(NullPointerException.class, () -> new ConditionalExpression(null, null, null));
	}

	@Test
	void testGetFieldvalueGetter() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
	}

	@Test
	void testGetComparisonOperator() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
	}

	@Test
	void testGetConditionString() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*",
				ComparisonOperators.LIKE);
		assertEquals("Bos*", expr.getStringLiteral());
	}

}
