package hr.fer.zemris.java.hw06.shell;

import java.io.IOError;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

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
 * <li>dropd - command takes no arguments and drops the last path stored on
 * shared working directories stack. If no path on stack, an appropriate message
 * is given
 * <li>ls DIR - takes a single argument – directory – and writes a directory
 * listing. The output is formated in a special way.
 * <li>tree DIR - command expects a single argument: directory name and prints a
 * tree (each directory level shifts output two characters to the right).
 * <li>copy FILE1 FILE2/DIR - command expects two arguments: source file name
 * and destination file name (i.e. paths and names). If the destination file
 * already exists, user is asked to allow to overwrite it. Copy works only for
 * files (not directories). If the second argument is a directory, the original
 * file will be copied to the folder with its original file name.
 * <li>massrename DIR1 DIR2 CMD MASK other - execute CMD on all files from DIR1
 * and put the result into DIR2 with. Only files with given MASK are chosen.
 * There can be also additional arguments under 'other'
 * <li>mkdir DIR- command takes a single argument: directory name, and creates
 * the appropriate directory structure.
 * <li>hexdump FILE - command expects a single argument: file name, and produces
 * hex-output
 * <li>help - if command started with no arguments, it lists names of all
 * supported commands. If started with single argument, it prints name and the
 * description of selected command (or print appropriate error message if no
 * such command exists).
 * <li>popd - command takes no arguments and pops the last path pushed onto the
 * shared stack of working directories. If the poped path is invalid, it wont be
 * set to current
 * <li>pushd PATH - push the current working directory onto an shared stack with
 * key \"cdstack\" and change the working directory by the given directory. The
 * given path must be a valid directory path
 * <li>pwd - command takes no arguments and prints the current working
 * directory.
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
	 * The path to the current working directory
	 */
	private Path currentDirectory;

	/**
	 * This map stores the shared objects
	 */
	private Map<String, Object> sharedObjects = new TreeMap<>();

	/**
	 * Default constructor for creating an environment
	 */
	public EnvironmentImpl() {
		init();
		this.promptSymbol = DEFAULT_PROMPT_SYMBOL;
		this.multiLineSymbol = DEFAULT_MULTILINE_SYMBOL;
		this.moreLinesSymbol = DEFAULT_MORELINES_SYMBOL;
		setCurrentDirectory(Paths.get(".")); // should never throw an exception
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
		commands.put("massrename", new MassrenameShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		scanner = new Scanner(System.in);
		System.out.println("Welcome to MyShell v 1.0");
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * @throws ShellIOException If environment could not read from input
	 */
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

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * @throws ShellIOException If given path is invalid
	 */
	// nisam siguran di se dodaje ovaj throws, u javadoc sučelja ili ovdje?
	public void setCurrentDirectory(Path path) {
		try {
			if (!Files.exists(path)) {
				throw new ShellIOException("Given path does not exist!");
			}

			if (!Files.isDirectory(path)) {
				throw new ShellIOException("Given path is not a directory!");
			}

			currentDirectory = path.toAbsolutePath().normalize();
		} catch (IOError | SecurityException e) {
			throw new ShellIOException(
					"Invalid current directory path is given, consider this message: " + e.getMessage());
		}
	}

	@Override
	public Object getSharedData(String key) {
		if (!sharedObjects.containsKey(key))
			return null;

		return sharedObjects.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key);

		sharedObjects.put(key, value);
	}
}