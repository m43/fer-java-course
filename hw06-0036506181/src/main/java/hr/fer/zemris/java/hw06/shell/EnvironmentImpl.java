package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * This class models an simple environment that uses the standard input and
 * output and supports some basic file manipulation commands <br>
 *
 * These are the supported commands:
 * <li>symbol SYMBOL_IDENTIFIER_NAME [char_value_to_update] - get current symbol
 * or set given. SYMBOL_IDENTIFIER_NAME is one of these: PROMPT,
 * MORELINESSYMBOL, MULTILINESYMBOL
 * <li>charsets - takes no arguments and lists names of supported charsets for
 * your Java platform. A single charset name is written per line
 * <li>cat PATH [CHARSET]- takes one or two arguments. The first argument is
 * path to some file and is mandatory. The second argument is charset name that
 * should be used to interpret chars from bytes. If not provided, a default
 * platform charset should be used (see {@link java.nio.charset.Charset} class
 * for details). This command opens given file and writes its content to
 * console.
 * <li>ls DIR - takes a single argument – directory – and writes a directory
 * listing. The output is formated in a special way.
 * <li>tree DIR - command expects a single argument: directory name and prints a
 * tree (each directory level shifts output two characters to the right).
 * <li>copy FILE1 FILE2/DIR - command expects two arguments: source file name
 * and destination file name (i.e. paths and names). If the destination file
 * already exists, user is asked to allow to overwrite it. Copy works only for
 * files (not directories). If the second argument is a directory, the original
 * file will be copied to the folder with its original file name.
 * <li>mkdir DIR- command takes a single argument: directory name, and creates
 * the appropriate directory structure.
 * <li>hexdump FILE - command expects a single argument: file name, and produces
 * hex-output
 * <li>help - if command started with no arguments, it lists names of all
 * supported commands. If started with single argument, it prints name and the
 * description of selected command (or print appropriate error message if no
 * such command exists).
 * 
 * @author Frano Rajič
 */
public class EnvironmentImpl implements Environment {

	/**
	 * Default symbol for prompt
	 */
	private static final Character DEFAULT_PROMPT_SYMBOL = '>';

	/**
	 * Default symbol for more lines
	 */
	private static final Character DEFAULT_MORELINES_SYMBOL = '\\';

	/**
	 * Default symbol for multi-line input
	 */
	private static final Character DEFAULT_MULTILINE_SYMBOL = '|';

	/**
	 * An map that maps the command name to its respective command which is an
	 * implementation of {@link ShellCommand}
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Scanner to be used for reading from input
	 */
	private Scanner scanner;

	/**
	 * Symbol to be written for prompt
	 */
	private Character promptSymbol;

	/**
	 * Symbol to be regarded as the symbol that determines when to break the current
	 * line of input to write in the next one
	 */
	private Character moreLinesSymbol;

	/**
	 * Symbol to be written before next lines of multi-line input
	 */
	private Character multiLineSymbol;

	/**
	 * Default constructor for creating an environment
	 */
	public EnvironmentImpl() {
		init();
		this.promptSymbol = DEFAULT_PROMPT_SYMBOL;
		this.multiLineSymbol = DEFAULT_MULTILINE_SYMBOL;
		this.moreLinesSymbol = DEFAULT_MORELINES_SYMBOL;
	}

	/**
	 * Help method run to initialize a newly created shell
	 */
	private void init() {
		commands = new TreeMap<>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		scanner = new Scanner(System.in);
		System.out.println("Welcome to MyShell v 1.0");
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			return scanner.nextLine();
		} catch (IllegalStateException | NoSuchElementException e) {
			throw new ShellIOException("Could not read from input");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}

	/**
	 * Method to terminate the shell
	 */
	public void terminate() {
		scanner.close();
	}
}