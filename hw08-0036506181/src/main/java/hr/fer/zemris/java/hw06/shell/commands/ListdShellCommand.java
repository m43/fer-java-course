package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models an command that writes to the environment all the directories
 * stored on the shared directories stack. It is called without arguments, just
 * like this: "listd"
 * 
 * @author Frano Rajič
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "listd";

	/**
	 * The description of this command
	 */
	private static final String description = "listd - command takes no arguments and prints out all the Paths stored at the shared working directories stack";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isBlank()) {
			env.writeln("The command " + getCommandName() + " takes no arguments!");
		}

		Stack<Path> stack = Utility.getStack(env);

		if (stack.isEmpty()) {
			env.writeln("No directories on stack!"); // aka "Nema pohranjenih direktorija."
			return ShellStatus.CONTINUE;
		}

		env.writeln("Paths on stack are:");

		List<Path> listOfPaths = stack.stream().collect(Collectors.toList());
		Collections.reverse(listOfPaths);
		for (Path p : listOfPaths) {
			env.writeln("--> " + p.toString());
		}

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
