package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command that enables popping the last path pushed to the shared working
 * directories stack and setting it to the current working directory.
 * 
 * @author Frano Rajič
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "popd";

	/**
	 * The description of this command
	 */
	private static final String description = "popd - command takes no arguments and pops the last path pushed onto the shared stack of working directories. If the poped path is invalid, it wont be set to current.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!"".equals(arguments.trim())) {
			env.writeln("Command " + getCommandName() + " takes no arguments!");
			return ShellStatus.CONTINUE;
		}

		Stack<Path> stack = Utility.getStack(env);
		if (stack == null || stack.isEmpty()) {
			env.writeln("The stack of working directories is empty or cannot be used!");
			return ShellStatus.CONTINUE;
		}

		
		Path newPath = stack.pop();
		Utility.setNewWorkingDirectory(env, newPath);
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
