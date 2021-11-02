package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testText() {
		// Lets check if a simple Text input gives tokens correctly
		SmartScriptLexer lexer = new SmartScriptLexer("Abc\r\n\toe");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "Abc\r\n\toe"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	// moje je gore

	@Test
	public void testOpenTagOnFirstPosition() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testOpenTagAfterText() {
		SmartScriptLexer lexer = new SmartScriptLexer("hhh{$");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "hhh"),
				new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testOpenTagAndCloseTagAlone() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testSpacesInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$     $}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testBlanksInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\t\t\t   \n\n\n \r \r \r \n \n \n$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testIdentifier() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$id1 id2 id3$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, "id1"),
				new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, "id2"),
				new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, "id3"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInteger() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$1 2 3$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "1"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "2"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "3"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testNumberWithDecimal() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$3.33 123.123 1.2$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "3.33"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "123.123"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "1.2"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testNegativeNumber() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$-1-2 3$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "-1"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "-2"),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "3"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testDecimalAndDot() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$123.123.123$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "123.123"),
				new SmartScriptToken(SmartScriptTokenType.SYMBOL, '.'),
				new SmartScriptToken(SmartScriptTokenType.NUMBER, "123"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testFunction() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$@ a @fun$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.SYMBOL, '@'),
				new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, "a"),
				new SmartScriptToken(SmartScriptTokenType.FUNCTION, "fun"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	

	@Test
	public void testString() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\"a\"\"bb\"\"123asd[]]$}}\"$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "a"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "bb"),
				new SmartScriptToken(SmartScriptTokenType.STRING, "123asd[]]$}}"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testStringWithClosedTagsInside() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\"a$}a\"$}");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.STRING, "a$}a"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testInvalidString() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\"abc");
																
		lexer.nextToken();

		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testInvalidEscapeEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\"a\\ebc\"");
		// letters string

		lexer.nextToken();

		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testInvalidStringEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$\"a\\ebc");
		// letters string

		lexer.nextToken();

		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	@Test
	public void testTextTagText() {
		SmartScriptLexer lexer = new SmartScriptLexer("\n{$=i$}\r");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
				new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.SYMBOL, '='),
				new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, "i"),
				new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "\r"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}

	@Test
	public void testNotNullInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testEmptyInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

}
