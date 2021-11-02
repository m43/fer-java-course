package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models a command that enables changing the current working directory.
 * The command is called like this: <br>
 * cd PATH - where path is the path to the new directory
 * 
 * @author Frano Rajič
 */
public class CdShellCommand implements ShellCommand {
	/**
	 * The name of this command
	 */
	private static final String commandName = "cd";

	/**
	 * The description of this command
	 */
	private static final String description = "cd PATH - change the current working directory to the given directory. Given path must be valid and must be a directory path, only then will the current working directory be changed";

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

		try {
			Path path = env.getCurrentDirectory();
			Path newDir = path.resolve(args[0]);
			if (!Files.exists(newDir)) {
				env.writeln("Given path does not exist..");
				return ShellStatus.CONTINUE;
			}
			if (!Files.isDirectory(newDir)) {
				env.writeln("Given path is not an directory path..");
				return ShellStatus.CONTINUE;
			}

			env.setCurrentDirectory(newDir);
		} catch (InvalidPathException e) {
			env.writeln("Invalid path given, consider the following: " + e.getMessage());
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
