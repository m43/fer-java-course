package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command is designed to take no arguments and list names of supported
 * charsets for this Java platform. A single charset name is written per line.
 * 
 * @author Frano Rajič
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "charsets";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try{
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if (args.length > 0) {
			env.writeln("Command " + getCommandName() + " takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("All supported charsets on this java platform are: ");
		for (String s : Charset.availableCharsets().keySet()) {
			env.writeln(s);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Command takes no arguments and lists names of supported charsets for");
		list.add("this Java platform. A single charset name is written per line.");
		return list;
	}

}
