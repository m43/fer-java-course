package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class QueryFilterTest {

	private List<ConditionalExpression> createTestingListOfConditionalExpression() {
		ConditionalExpression c1 = new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Josip",
				ComparisonOperators.EQUALS);
		ConditionalExpression c2 = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Jozo",
				ComparisonOperators.EQUALS);
		ConditionalExpression c3 = new ConditionalExpression(FieldValueGetters.JMBAG, "a", ComparisonOperators.GREATER);

		List<ConditionalExpression> list = new LinkedList<>();
		list.add(c1);
		list.add(c2);
		list.add(c3);

		return list;
	}

	@Test
	void testQueryFilterConstruction() {
		List<ConditionalExpression> list = createTestingListOfConditionalExpression();
		QueryFilter queryFilter = new QueryFilter(list);
		assertNotNull(queryFilter);
	}
	
	@Test
	void testQueryFilterConstructionWithNullPointerGiven() {
		assertThrows(NullPointerException.class, () -> new QueryFilter(null));
	}

	@Test
	void testQueryFilterConstructionWithEmptyList() {
		assertThrows(IllegalArgumentException.class, () -> new QueryFilter(new LinkedList<>()));
	}

	
	@Test
	void testAccepts() {
		StudentRecord student1 = new StudentRecord("b Jozo Josip 5");
		StudentRecord student2 = new StudentRecord("a Jozo Josip 5");
		StudentRecord student3 = new StudentRecord("b Jozoo Josip 5");
		StudentRecord student4 = new StudentRecord("b Jozo Josip 2");

		List<ConditionalExpression> list = createTestingListOfConditionalExpression();
		QueryFilter queryFilter = new QueryFilter(list);

		assertTrue(queryFilter.accepts(student1));
		assertFalse(queryFilter.accepts(student2));
		assertFalse(queryFilter.accepts(student3));
		assertTrue(queryFilter.accepts(student4));

	}

}
