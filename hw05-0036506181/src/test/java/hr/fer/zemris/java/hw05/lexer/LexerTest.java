package hr.fer.zemris.java.hw05.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");

		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testNextAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    ");

		assertEquals(TokenType.EOF, lexer.nextToken().getType(),
				"Input had no content. Lexer should generated only EOF token.");
	}

	@Test
	public void testOneIdentifier() {
		Lexer lexer = new Lexer("id");

		Token correctData[] = { new Token(TokenType.IDENTIFIER, "id"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testIdentifierWithLotsOfBlanks() {
		Lexer lexer = new Lexer("   \t   \t\t \r\n\t \nid\t\t    \t \t \r");

		Token correctData[] = { new Token(TokenType.IDENTIFIER, "id"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTwoIdentifier() {
		Lexer lexer = new Lexer("   id   idD  ");

		Token correctData[] = { new Token(TokenType.IDENTIFIER, "id"), new Token(TokenType.IDENTIFIER, "idD"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testSomeIdentifiers() {
		Lexer lexer = new Lexer("firstName lastName jmbag");

		Token correctData[] = { new Token(TokenType.IDENTIFIER, "firstName"),
				new Token(TokenType.IDENTIFIER, "lastName"), new Token(TokenType.IDENTIFIER, "jmbag"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testOperations() {
		Lexer lexer = new Lexer("  < <= > >= = != LIKE ");

		Token correctData[] = { new Token(TokenType.OPERATION, "<"), new Token(TokenType.OPERATION, "<="),
				new Token(TokenType.OPERATION, ">"), new Token(TokenType.OPERATION, ">="),
				new Token(TokenType.OPERATION, "="), new Token(TokenType.OPERATION, "!="),
				new Token(TokenType.OPERATION, "LIKE"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testOneString() {
		Lexer lexer = new Lexer("  \"anica\" ");

		Token correctData[] = { new Token(TokenType.STRING, "anica"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTwoStrings() {
		Lexer lexer = new Lexer("  \"anica\"\"josip\" ");

		Token correctData[] = { new Token(TokenType.STRING, "anica"), new Token(TokenType.STRING, "josip"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testLikeWithStringAfter() {
		Lexer lexer = new Lexer("LIKE\"like\"");

		Token correctData[] = { new Token(TokenType.OPERATION, "LIKE"), new Token(TokenType.STRING, "like"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testAND() {
		Lexer lexer = new Lexer(" AND AND AND");

		Token correctData[] = { new Token(TokenType.LOGICAL_OPERATOR, "AND"),
				new Token(TokenType.LOGICAL_OPERATOR, "AND"), new Token(TokenType.LOGICAL_OPERATOR, "AND"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testSimpleCombinedInput() {
		// Lets check for several symbols...
		Lexer lexer = new Lexer("jmbag=\"0000000003\"");

		Token correctData[] = { new Token(TokenType.IDENTIFIER, "jmbag"), new Token(TokenType.OPERATION, "="),
				new Token(TokenType.STRING, "0000000003"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testComplexCombinedInput() {
		// Lets check for several symbols...
		Lexer lexer = new Lexer(
				"firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

		Token correctData[] = { new Token(TokenType.IDENTIFIER, "firstName"), new Token(TokenType.OPERATION, ">"),
				new Token(TokenType.STRING, "A"), new Token(TokenType.LOGICAL_OPERATOR, "AND"),
				new Token(TokenType.IDENTIFIER, "firstName"), new Token(TokenType.OPERATION, "<"),
				new Token(TokenType.STRING, "C"), new Token(TokenType.LOGICAL_OPERATOR, "AND"),
				new Token(TokenType.IDENTIFIER, "lastName"), new Token(TokenType.OPERATION, "LIKE"),
				new Token(TokenType.STRING, "B*ć"), new Token(TokenType.LOGICAL_OPERATOR, "AND"),
				new Token(TokenType.IDENTIFIER, "jmbag"), new Token(TokenType.OPERATION, ">"),
				new Token(TokenType.STRING, "0000000002"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Method to check tokens that the lexer generates for equality with expected
	 * correct data stream. Both are given
	 * 
	 * @param lexer       lexer instance
	 * @param correctData expected data
	 */
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "While checking " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
}
