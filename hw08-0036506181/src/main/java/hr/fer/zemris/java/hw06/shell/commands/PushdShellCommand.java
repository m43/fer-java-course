package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models an command that enables changing the current working directory
 * while storing the previous one on an stack of shared command data. The stack
 * is accessed with key "cdstack". The command is called like "pushd PATH"
 * 
 * @author Frano Rajič
 */
public class PushdShellCommand implements ShellCommand {
	/**
	 * The name of this command
	 */
	private static final String commandName = "pushd";

	/**
	 * The description of this command
	 */
	private static final String description = "pushd PATH - push the current working directory onto an shared stack with key \"cdstack\" and change the working directory by the given directory. The given path must be a valid directory path";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try {
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 1) {
			env.writeln("Invalid number of arguments given to command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		Path path = env.getCurrentDirectory();
		Path newPath = path.resolve(args[0]);
		if (!Utility.setNewWorkingDirectory(env, newPath)) {
			return ShellStatus.CONTINUE;
		}

		Stack<Path> stack = Utility.getStack(env);
		stack.push(path);

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
