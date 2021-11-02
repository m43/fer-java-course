package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models an command that drops the last path on shared working directory
 * stack. It is called without arguments, just like "dropd"
 * 
 * @author Frano Rajič
 */
public class DropdShellCommand implements ShellCommand {
	/**
	 * The name of this command
	 */
	private static final String commandName = "dropd";

	/**
	 * The description of this command
	 */
	private static final String description = "dropd - command takes no arguments and drops the last path stored on shared working directories stack. If no path on stack, an appropriate message is given";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("The command " + getCommandName() + " takes no arguments!");
		}

		Stack<Path> stack = Utility.getStack(env);

		if (stack.isEmpty()) {
			env.writeln("The stack is empty so no paths can be removed at all.");
			return ShellStatus.CONTINUE;
		}

		stack.pop();
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
