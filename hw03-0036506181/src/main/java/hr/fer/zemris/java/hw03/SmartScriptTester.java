package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.parser.*;
import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.*;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.*;

//import java.nio.file.Files;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Paths;

/**
 * Program to test the basic functionality of the {@link SmartScriptParser}
 * 
 * @author Frano
 */
public class SmartScriptTester {

	/**
	 * Main entry point of the program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {

//		if (args.length < 1) {
//			System.out.println(
//					"The first argument you enter to command line is the path to the file to test. ENTER THE PATH!");
//			System.exit(-1);
//		}
//		String filepath = args[0];

		String docBody = "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.";
//		try {
//			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
//		} catch (IOException e1) {
//			System.out.println("Couldnt open file " + filepath);
//			System.exit(-1);
//		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (SmartScriptLexerException e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);

		for (int i = 0; i < document.numberOfChildren(); i++) {
			System.out.println(
					document.getChild(i).numberOfChildren() + " children <-->" + document.getChild(i).toString());
		}

		System.out.println(docBody); // original
		System.out.println(); // some spacing
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
	}

}
