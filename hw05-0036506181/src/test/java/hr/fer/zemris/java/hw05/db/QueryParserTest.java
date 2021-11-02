package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class QueryParserTest {

	@Test
	void testQueryParser() {
		QueryParser p = new QueryParser("firstName=\"Petar\"");
		assertNotNull(p);
	}

	@Test
	void testEmptyQuery() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser(""));
	}

	@Test
	void testOnlySpacesQuery() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("   "));
	}

	@Test
	void testInvalidQuery() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("firstName=lastName"));
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("\"Ante\"=firstName"));
	}

	@Test
	void testGetQuery() {
		QueryParser p = new QueryParser("jmbag>\"123\"");
		assertNotNull(p.getQuery());
	}
	
	@Test
	void testGetQuerySize() {
		QueryParser p = new QueryParser("jmbag>\"123\"");
		assertEquals(1, p.getQuery().size());
	}
	
	@Test
	void testOneExpressionQuery() {
		QueryParser p = new QueryParser("jmbag>\"123\"");
		assertEquals(1, p.getQuery().size());
	}

	@Test
	void testTwoExpressionQuery() {
		QueryParser p = new QueryParser("jmbag>\"123\" and firstName=\"Josip\"");
		assertEquals(2, p.getQuery().size());
	}
	
	@Test
	void testMultipleExpressionQuery() {
		QueryParser p = new QueryParser("jmbag>\"123\" and firstName=\"Josip\" and lastName!=\"Josip\"");
		assertEquals(3, p.getQuery().size());
	}
	
	@Test
	void testIsDirectQuery() {
		QueryParser p = new QueryParser("jmbag=\"123\"");
		assertTrue(p.isDirectQuery());
	}

	@Test
	void testNonDirectQuery() {
		QueryParser p = new QueryParser("jmbag>\"123\"");
		assertFalse(p.isDirectQuery());
	}

	@Test
	void testGetQueriedJMBAG() {
		QueryParser p = new QueryParser("jmbag=\"123\"");
		assertEquals("123", p.getQueriedJMBAG());
	}
	
	@Test
	void testGetQueriedJMBAGIfNotDirect() {
		QueryParser p = new QueryParser("jmbag>\"123\"");
		assertThrows(IllegalStateException.class, () -> p.getQueriedJMBAG());
	}

}
