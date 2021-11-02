package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models command that is used when exiting the shell.
 * 
 * @author Frano Rajič
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "exit";

	/**
	 * The description of this command
	 */
	private static final String description = "exit - to exit the shell, write 'exit' without arguments";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Exit command must not have any arguments given!");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Utility.getDescription(description);
	}

}
