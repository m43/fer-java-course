package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models the shell command to create a new directory. I it is called as
 * "mkdir DIR", taking only one argument, namely the directory name, and then
 * creates the appropriate directory structure.
 * 
 * @author Frano Rajič
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "mkdir";

	/**
	 * The description of this command
	 */
	private static final String description = "mkdir DIR- command takes a single argument: directory name, and creates"
			+ " the appropriate directory structure.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try{
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if (args.length != 1) {
			env.writeln("Invalid number of arguments given to command" + getCommandName());
			return ShellStatus.CONTINUE;
		}

		try {
			Files.createDirectories(Paths.get(args[0]));
		} catch (IOException | InvalidPathException e) {
			env.writeln("Cannot create directories - " + e);
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
