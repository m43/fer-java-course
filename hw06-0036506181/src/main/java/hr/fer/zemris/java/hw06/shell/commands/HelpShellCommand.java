package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class offers the functionality of printing out help information. If command
 * started with no arguments, it lists names of all supported commands. If
 * started with single argument, it prints name and the description of selected
 * command (or print appropriate error message if no such command exists).
 * 
 * @author Frano Rajič
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "help";

	/**
	 * The description of this command
	 */
	private static final String description = "help - if command started with no arguments, it lists names of all supported commands. If started with single argument, it prints name and the description of selected command (or print appropriate error message if no such command exists).";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try{
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if (args.length > 1) {
			env.writeln("Invalid number of arguments given for command " + getCommandName());
		}

		SortedMap<String, ShellCommand> sm = env.commands();
		if (args.length == 0) {
			env.writeln("Available commands:");
			for (String command : sm.keySet()) {
				env.writeln("--> " + command);
			}
		}

		if (args.length == 1) {
			if (!sm.containsKey(args[0])) {
				env.writeln("Command \"" + args[0] + "\" doesn't exist");
			} else {
				for(String line: sm.get(args[0]).getCommandDescription()) {
					env.writeln(line);
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
