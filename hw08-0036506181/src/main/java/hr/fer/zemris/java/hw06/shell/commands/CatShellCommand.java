package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class provides the functionality of showing a files content. It is called
 * like this:<br>
 * cat PATH [CHARSET] - takes one or two arguments. The first argument is path
 * to some file and is mandatory. The second argument is charset name that
 * should be used to interpret chars from bytes. If not provided, a default
 * platform charset should be used (see {@link java.nio.charset.Charset} class
 * for details). This command opens given file and writes its content to
 * console.
 * 
 * @author Frano Rajič
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "cat";

	/**
	 * The description of this command
	 */
	private static final String description = "cat PATH [CHARSET]- command takes one or two arguments. The first argument is path to some file and is mandatory. The second argument is charset name that should be used to interpret chars from bytes. If not provided, a default platform charset should be used. This command opens given file and writes its content to console.";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try {
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 1 && args.length != 2) {
			env.writeln("Invalid number of arguments given to command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		BufferedReader br = null;
		try {
			Charset charset = (args.length == 2 ? Charset.forName(args[1]) : Charset.defaultCharset());
			br = new BufferedReader(new FileReader(args[0], charset));
			String s;
			while ((s = br.readLine()) != null) {
				env.writeln(s);
			}

		} catch (IllegalArgumentException e) {
			env.writeln("Couldnt open file correctly, consider this error: " + e.getMessage());
		} catch (IOException e) {
			env.writeln(
					"Problems with given file or file path, consider the following error message: " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// irrelevant
				}
			}
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
