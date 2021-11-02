package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models the command used to copy a file. It is used like "copy FILE_FROM
 * FILE_TO/DIR" If the destination file already exists, the user is asked if he
 * wants to overwrite it
 * 
 * @author Frano Rajič
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "copy";

	/**
	 * The description of this command
	 */
	private static final String description = "copy FILE_FROM FILE_TO/DIR - command expects two arguments: source file name"
			+ " and destination file name (i.e. paths and names). If destination file exists,"
			+ " user is asked to allow to overwrite it. Copy works only for files (not"
			+ " directories). If the second argument is a directory, the original file will be"
			+ " copied to the folder with its original file name.";

	/**
	 * The default buffer size used copying from the original to the new file.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 4096;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args;
		try {
			args = Utility.splitArgumentsContainingStrings(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid input given, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.length != 2) {
			env.writeln("Invalid number of arguments given to command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		Path from;
		Path to;
		try {
			from = env.getCurrentDirectory().resolve(args[0]);
			to = env.getCurrentDirectory().resolve(args[1]);
		} catch (InvalidPathException e) {
			env.writeln("Problems with paths, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (!Files.isRegularFile(from)) {
			env.writeln("The first path given must be an existing file.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(to)) {
			to = to.resolve(from.getFileName());
		}

		if (to.equals(from)) {
			env.writeln("Makes no sense to copy the file into itself..");
			return ShellStatus.CONTINUE;
		}

		if (Files.isRegularFile(to)) {
			env.write("The file " + to.toString()
					+ " points to an already existing file, would you like to overwrite it (yes/no)? ");
			String s;
			while (true) {
				s = env.readLine();
				if (s.toLowerCase().equals("yes")) {
					break;
				} else if (s.toLowerCase().equals("no")) {
					return ShellStatus.CONTINUE;
				}
				env.write("cmon, yes or no?");
			}
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(Files.newInputStream(from));
			bos = new BufferedOutputStream(Files.newOutputStream(to));

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();

			bis.close();
			bos.close();

		} catch (IOException e) {
			env.writeln(
					"There are some problems with the files, consider the following error message: " + e.getMessage());
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// irrelevant
				}
			}
			if (bos != null) {
				try {
					bos.close();
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
