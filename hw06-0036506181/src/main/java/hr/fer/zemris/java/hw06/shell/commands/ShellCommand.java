package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * @author Frano Rajič
 */
public interface ShellCommand {

	/**
	 * Executing a command requires an environemnt and arguments given.
	 * 
	 * @param env       Environment object of shell.
	 * @param arguments Arguments given to the command
	 * @return {@link ShellStatus#CONTINUE} if shell can continue with work, or
	 *         {@link ShellStatus#TERMINATE} if the shell needs to terminate
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Get the command name
	 * 
	 * @return the command name
	 */
	String getCommandName();

	/**
	 * Get the description of this command
	 * 
	 * @return the description of this command
	 */
	List<String> getCommandDescription();
}
