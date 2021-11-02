package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models the ls command. Ls stand for list and lists the files and
 * folders of a given directory. The command is used as "ls DIR" taking only a
 * single argument – the directory - and writes a directory listing. The output
 * is formated in a special way in four collumns.
 * 
 * @author Frano Rajič
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "ls";

	/**
	 * The description of this command
	 */
	private static final String description = "ls DIR - takes a single argument – directory – and writes a directory"
			+ " listing. The output is formated in a special way.";

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
			env.writeln("Invalid number of arguments given for command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		try {
			if (!Files.isDirectory(Paths.get(args[0]))) {
				env.writeln("Given path is not a directory path!");
				return ShellStatus.CONTINUE;
			}
			LinkedList<Path> paths = new LinkedList<>();

			int longestFileSize = loadLongestFileSize(args[0]);
			Files.list(Paths.get(args[0])).forEach(x -> paths.add(x));
			for (Path x : paths) {
				env.writeln(getPathInfo(x, longestFileSize));
			}

		} catch (IOException | InvalidPathException e) {
			env.writeln("Problems with given directory path, consider the following error message: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;

	}

	/**
	 * Help method to determine the length of the file with the biggest size in the
	 * directory given by path argument
	 * 
	 * @param path Where to look for the biggest file length
	 * @return the string length of the biggest file size
	 * @throws IOException exception propagated if problems with loading given path
	 *                     happen
	 */
	private int loadLongestFileSize(String path) throws IOException {
		Optional<Integer> optionalLongest = Files.list(Paths.get(path)).map(x -> {
			try {
				return String.valueOf(Files.size(x)).length();
			} catch (IOException e) {
				return 3;
			}
		}).max(Integer::compare);

		if (optionalLongest.isPresent()) {
			return optionalLongest.get();
		} else {
			return 3;
		}

	}

	/**
	 * Help method to extract important data for list function
	 * 
	 * @param x               the Path to extract information from
	 * @param longestFileSize the longest file size
	 * @return return the string containing the information of the file in one line
	 * @throws IOException thrown if any error with input/output occurs
	 */
	private static String getPathInfo(Path x, int longestFileSize) throws IOException {
		String c1 = Files.isDirectory(x) ? "d" : "-";
		c1 += Files.isReadable(x) ? "r" : "-";
		c1 += Files.isWritable(x) ? "w" : "-";
		c1 += Files.isExecutable(x) ? "x" : "-";

		String c2 = String.valueOf(Files.size(x));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time = Files.getFileAttributeView(x, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
				.readAttributes().creationTime().toMillis();
		String c3 = sdf.format(new Date(time));

		String c4 = x.getFileName().toString();
		return String.format("%4s %" + longestFileSize + "s %s %s", c1, c2, c3, c4);
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
