package hr.fer.zemris.java.webserver.demo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program that demonstrates the use of the {@link SmartScriptEngine} by writing
 * the result of the engine execution to SYSO.
 * 
 * @author Frano Rajič
 */
public class DemoSmartScriptEngine {

	/**
	 * Entry point for program. An argument containing path to document to parse
	 * should be given.
	 * 
	 * @param args Only one argument should be given, namely the path to the
	 *             document to parse. If no argument is given, an demo script will
	 *             be run through the engine
	 * @throws IOException if any error occurs when opening document.
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			DemoSmartScriptEngine demo = new DemoSmartScriptEngine();
			try {
				demo.demo1();
				demo.demo2();
				demo.demo3();
				demo.demo4();
				demo.demo5();
			} catch (URISyntaxException e) {
				System.out.println("Couldnt fine script");
			}
			return;
		}

		try {
			Path filePath = Paths.get(args[0]);
			String docBody = Files.readString(filePath);

			// String documentBody = readFromDisk(fileName);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

			// put some parameter into parameters map
			parameters.put("broj", "4");

			// create engine and execute it
			new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

		} catch (InvalidPathException e) {
			System.out.println("Invalid path given..");
			return;
		} catch (IOException e) {
			System.out.println("Couldn't read given file...");
		}
	}

	/**
	 * Demonstration program that runs script 1
	 * 
	 * @throws IOException        if error when reading script occurs
	 * @throws URISyntaxException if script couldn't be found
	 */
	private void demo1() throws IOException, URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("osnovni.smscr").toURI());
		String documentBody = Files.readString(path);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/**
	 * Demonstration program that runs script 2
	 * 
	 * @throws IOException        if error when reading script occurs
	 * @throws URISyntaxException if script couldn't be found
	 */
	private void demo2() throws IOException, URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("zbrajanje.smscr").toURI());
		String documentBody = Files.readString(path);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Demonstration program that runs script 3
	 * 
	 * @throws IOException        if error when reading script occurs
	 * @throws URISyntaxException if script couldn't be found
	 */
	private void demo3() throws IOException, URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("brojPoziva.smscr").toURI());
		String documentBody = Files.readString(path);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);

		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));

	}

	/**
	 * Demonstration program that runs script 4
	 * 
	 * @throws IOException        if error when reading script occurs
	 * @throws URISyntaxException if script couldn't be found
	 */
	private void demo4() throws IOException, URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("fibonacci.smscr").toURI());
		String documentBody = Files.readString(path);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

	/**
	 * Demonstration program that runs script 5
	 * 
	 * @throws IOException        if error when reading script occurs
	 * @throws URISyntaxException if script couldn't be found
	 */
	private void demo5() throws IOException, URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource("fibonaccih.smscr").toURI());
		String documentBody = Files.readString(path);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}

}
