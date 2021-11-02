package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command models an command that offers the functionality of bulk file rename.
 * The command is called like this:<br>
 * massrename DIR1 DIR2 CMD MASK other - where CMD is one of the supported
 * commands of massrename, the command is executed on all files in dirctory DIR1
 * and if new files are created, they are put into the directory DIR2. Only
 * files with given MASK are chosen for the command (where MASK is of syntax
 * that is supported by class Pattern). There can also be additional arguments
 * under 'other', depending on the command. Supported commands are: filter,
 * groups, show and execute. Show and execute take an fifth argument (other)
 * that represents the expression string. An expression string is used to create
 * new renamed file names. The rules of string expressions are documented in
 * {@link NameBuilderParser}.
 * 
 * @author Frano Rajič
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "massrename";

	/**
	 * The description of this command
	 */
	private static final String description = "massrename DIR1 DIR2 CMD MASK other - execute"
			+ "command CMD on all files from DIR1 and put the result into DIR2 with. Only files"
			+ "with given MASK are chosen (where mask is of syntax supproted by class Pattern)."
			+ "There can be also additional arguments  under 'other', depending on the command."
			+ "Supported commands are: filter, groups, show and execute. show and execute take"
			+ "an fifth argument (other) that represents the expression string";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try {
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length < 4) {
			env.writeln("Invalid number of arguments given for command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		try {
			Path dir1 = Paths.get(args[0]);
			Path dir2 = Paths.get(args[1]);
			String cmd = args[2];
			String mask = args[3];

			if (!Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
				env.writeln("Invalid directory paths given.");
				return ShellStatus.CONTINUE;
			}

			switch (cmd) {
			case "filter":
				if (args.length != 4) {
					env.writeln("Filter needs to exactly 4 arguments given");
					return ShellStatus.CONTINUE;
				}
				env.writeln("The files filtered with given mask " + mask + " are:");
				filter(dir1, mask).stream().map(FilterResult::toString).forEach(env::writeln);
				return ShellStatus.CONTINUE;

			case "groups":
				if (args.length != 4) {
					env.writeln("Groups needs to exactly 4 arguments given");
					return ShellStatus.CONTINUE;
				}
				env.writeln("The files filtered and grouped with mask " + mask + " and the matched groups are: ");
				for (FilterResult e : filter(dir1, mask)) {
					env.write(e.toString());
					for (int i = 0; i <= e.numberOfGroups(); i++) {
						env.write(" " + i + ": " + e.group(i));
					}
					env.writeln("");
				}
				return ShellStatus.CONTINUE;

			case "show":
				if (args.length != 5) {
					env.writeln("Show needs to have 5 arguments");
					return ShellStatus.CONTINUE;
				}
				return showOrExecute(dir1, dir2, mask, args[4], env, false);

			case "execute":
				if (args.length != 5) {
					env.writeln("Execute needs to have 5 arguments");
					return ShellStatus.CONTINUE;
				}
				return showOrExecute(dir1, dir2, mask, args[4], env, true);

			default:
				env.writeln("Unsupported command given: " + cmd);
				return ShellStatus.CONTINUE;
			}

		} catch (IOException e) {
			env.writeln("Error with input/output, consider the following error message: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid arguments given, consider the following error message: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Help method to do the actual execution of the bulk renaming or to show what
	 * the bulk renaming would do, depending on the boolean execute.
	 * 
	 * @param dir1             The directory to get the files from
	 * @param dir2             The directory to put the files into
	 * @param mask             The mask used to chose files to rename
	 * @param expressionString The expression string used to format the new file
	 *                         name
	 * @param env              The environment to write messages to
	 * @param execute          If true than the renaming will be executed, if false
	 *                         only new file names would be shown
	 * @return the new status of the environment
	 * @throws IllegalArgumentException thrown if invalid expression string given.
	 *                                  Exception is thrown when parsing
	 * @throws IOException              thrown if any Input/Output error occurs when
	 *                                  working with the given directories and the
	 *                                  files within them
	 */
	private ShellStatus showOrExecute(Path dir1, Path dir2, String mask, String expressionString, Environment env,
			boolean execute) throws IllegalArgumentException, IOException {
		NameBuilderParser parser = new NameBuilderParser(expressionString);
		NameBuilder builder = parser.getNameBuilder();

		if (!execute) {
			env.writeln("The new files that would be created by renaming would be these:");
		}

		List<FilterResult> files = filter(dir1, mask);
		for (FilterResult file : files) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			String newFileName = sb.toString();
			if (execute) {
				// execute
				Files.move(dir1.resolve(file.toString()), dir2.resolve(newFileName));
			} else {
				// show only
				env.writeln(dir1.resolve(file.toString()).toString() + " => " + dir2.resolve(newFileName));
			}
		}

		if (execute ) {
			env.writeln("Renamed " + files.size() + " files created succesfuly. New files are in directory " + dir2.toString());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Help method to create a list of filtered results
	 * 
	 * @param dir     directory to filter
	 * @param pattern the regex pattern
	 * @return an list of filtered results
	 * @throws IOException if reading from file systems gives any error
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {

		Pattern patternCompiled = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

		return Files.list(dir).filter(Files::isRegularFile).map(Path::getFileName).map(Path::toString)
				.filter(patternCompiled.asMatchPredicate()).map(x -> {
					return new FilterResult(x.toString(), patternCompiled);
				}).collect(Collectors.toList());

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
