package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models the command that is used to get or set the symbol values of
 * PROMPT, MORELINES and MULTILINE.
 * 
 * @author Frano Rajič
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "symbol";

	/**
	 * The description of this command
	 */
	private static final String description = "Command is used to get or set the symbol values of PROMPT, MORELINES and MULTILINE. Call it like 'symbol x' to get the current value of the symbol x. Call it like 'symbol x y' to set the value of symbol x to symbol y";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try{
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if (args.length > 2 || args.length == 0) {
			env.writeln("Invalid number of arguments given to command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		boolean set = (args.length == 2);
		if (set && args[1].length() != 1) {
			env.writeln("Symbol to set must be only one character");
			return ShellStatus.CONTINUE;
		}

		switch (args[0].toUpperCase()) {
		case "MORELINES":
			env.write("Symbol for MORELINES ");
			if (set) {
				env.writeln("changed from " + env.getMorelinesSymbol() + " to " + args[1]);
				env.setMorelinesSymbol(args[1].charAt(0));
			} else {
				env.writeln("is " + env.getMorelinesSymbol());
			}
			break;
		case "PROMPT":
			env.write("Symbol for PROMPT ");
			if (set) {
				env.writeln("changed from " + env.getPromptSymbol() + " to " + args[1]);
				env.setPromptSymbol(args[1].charAt(0));
			} else {
				env.writeln("is " + env.getPromptSymbol());
			}
			break;
		case "MULTILINE":
			env.write("Symbol for MULTILINE ");
			if (set) {
				env.writeln("changed from " + env.getMultilineSymbol() + " to " + args[1]);
				env.setMultilineSymbol(args[1].charAt(0));
			} else {
				env.writeln("is " + env.getMultilineSymbol());
			}
			break;
		default:
			env.writeln("There is no such symbol..");
			break;
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
