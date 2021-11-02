package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;

/**
 * Class models an abstraction of an environment. One must be able to write and
 * read from environment, change special symbols and get the commands of the
 * environment.
 * 
 * @author Frano Rajič
 */
public interface Environment {

	/**
	 * Read one line of input from user.
	 * 
	 * @return the string that has been read.
	 * @throws ShellIOException If any error during reading occurs
	 */
	String readLine() throws ShellIOException;

	/**
	 * Write to the output without an ending newline.
	 * 
	 * @param text Text to write.
	 * @throws ShellIOException Thrown if any error during writing occurs
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Write the given text as a new line to output.
	 * 
	 * @param text Text to write to output
	 * @throws ShellIOException Thrown if any error during writing occurs
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Get an unmodifiable sorted map of available environment commands.
	 * 
	 * @return an unmodifiable sorted map of available environment commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Get the multiline symbol currently used
	 * 
	 * @return the current multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Set the multiline symbol
	 * 
	 * @param symbol Symbol to be set for multiline
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Get the prompt symbol currently used
	 * 
	 * @return the current prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Set the prompt symbol
	 * 
	 * @param symbol Symbol to be set for prompt
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Get the morelines symbol currently used
	 * 
	 * @return the current morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Set the morelines symbol
	 * 
	 * @param symbol Symbol to be set for morelines
	 */
	void setMorelinesSymbol(Character symbol);

}
