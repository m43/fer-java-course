package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class provides the functionality of previewing current working directory of
 * the environment (therefore PWD). It is called without any arguments, just
 * like this: "pwd"
 * 
 * @author Frano Rajič
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "pwd";

	/**
	 * The description of this command
	 */
	private static final String description = "pwd - command takes no arguments and prints the current working directory.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!"".equals(arguments.trim())) {
			env.writeln("Command " + getCommandName() + " takes no arguments!");
			return ShellStatus.CONTINUE;
		}

		env.writeln(env.getCurrentDirectory().toString());

		return ShellStatus.CONTINUE;
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
