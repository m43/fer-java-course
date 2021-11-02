package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.createOriginalDocumentBody;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

@SuppressWarnings("javadoc")
class SmartScriptParserTest {

	@Test
	public void testNothingInBody() {
		SmartScriptParser p = new SmartScriptParser("");

		assertEquals(0, p.getDocumentNode().numberOfChildren());
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptParser(null));
	}

	@Test
	public void testTooManyEnds() {
		String docBody = "{$END$}";
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}

	@Test
	public void testUnclosedFor() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$FOR i 1 2 3$}"));
	}

	@Test
	public void testForWithInvalidVariableName() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR 3 1 10 1 $}"));
	}

	@Test
	public void testForLoopWithSymbolAsVariableName() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}"));
	}

	@Test
	public void testForWithFunction() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year @sin 10 $}"));
	}

	@Test
	public void testForWithTooManyArguments() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $}"));
	}

	@Test
	public void testTooManyArgumentsForForLoop() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$FOR i 1 2 3 4$}"));
	}

	@Test
	public void testTooFewArgumentsForForLoop() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$FOR i 1$}"));
	}

	@Test
	public void testEchoWithInvalidOpeator() {
		// must throw!
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$= i _ i$}"));
	}

	@Test
	void testIfRecursiceCallsGiveTheSameDocumentStrucutre() {
		String docBody = "This is \\{\\\\\\{$sample text.\r\n" + "{$ FOR i 1 10 1 $}\r\n"
				+ "This is {$= i $}-th time this message is generated.\r\n" + "{$END$}\r\n" + "{$FOR i 0 10 2 $}\r\n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"aaa\ta\\naa\\\\aa\\\"aa0.000\" @decfmt $}\r\n" + "{$END$}";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees

		String replicatedDocumentBody = createOriginalDocumentBody(document2);
		assertTrue(originalDocumentBody.equals(replicatedDocumentBody)); // returns true! nice
	}

	@Test
	void testEscapeInText() {
		String docBody = "ABC\\{\\\\\\{$text.";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();

		String textToString = document.getChild(0).toString();

		assertTrue(textToString.contains("\\{\\\\\\{$"));
	}

	@Test
	void testEscapeInString() {
		String docBody = loader("document3.txt"); // contains only one text element

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String echo = document.getChild(1).toString();

		assertTrue(echo.contains("\\t"));
		assertTrue(echo.contains("\\\""));
		assertTrue(echo.contains("\\n"));
	}

	@Test
	void testEscapeInTextWithLongerInput() {
		String docBody = loader("document2.txt"); // contains only one text element

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String text = document.getChild(0).toString();

		assertTrue(text.contains("\\"));
		assertTrue(text.contains("\\{"));
		assertTrue(text.contains("\\{$"));
	}

	@Test
	void testNumberOfChildren() {
		String docBody = loader("document1.txt");
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		assertEquals(4, document.numberOfChildren());
	}

	@SuppressWarnings("unused")
	@Test
	public void testParserShouldWork() {
		for (int i = 1; i < 10; i++) {
			String docBody = loader("document" + i + ".txt");
			if (i == 5) {
				continue; // for i=5 there is an END that closes no FOR loop
			}
			SmartScriptParser parser = new SmartScriptParser(docBody);
		}

		assertTrue(true);
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

}
