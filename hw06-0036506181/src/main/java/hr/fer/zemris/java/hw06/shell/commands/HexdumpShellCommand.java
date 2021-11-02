package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models a command that prints out the hex dump of a file.
 * 
 * @author Frano Rajič
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "hexdump";

	/**
	 * The description of this command
	 */
	private static final String description = "hexdump FILE - command expects a single argument: file name, and produces hex-output";

	/**
	 * Default buffer size of buffer used to when reading input stream
	 */
	private static final int DEFAULT_BUFFER_SIZE = 4096;

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
			env.writeln("Invalid number of arguments given to command " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		BufferedInputStream bis = null;
		try {
			
			Path path = Paths.get(args[0]);
			if (!Files.isRegularFile(path)) {
				env.writeln("Given path is not a valid file path...");
				return ShellStatus.CONTINUE;
			}
			
			bis = new BufferedInputStream(Files.newInputStream(path));

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			String b;

			// bytes read from input
			long byteRead = 0;

			// bytes written to output
			long bytesWritten = 0;

			// hexLeft used for the hex values of first 8 bytes
			StringBuilder hexLeft = new StringBuilder();

			// hexRight used for the hex values of second 8 bytes
			StringBuilder hexRight = new StringBuilder();

			// human will hold the human readable chars that are shown on the right
			StringBuilder human = new StringBuilder();

			while ((byteRead = bis.read(buffer)) > 0) {
				for (int i = 0; i < byteRead; i++) {
					b = String.format("%02X ", buffer[i]);
					if (bytesWritten / 8 % 2 == 0) {
						hexLeft.append(b);
					} else {
						hexRight.append(b);
					}
					human.append((buffer[i] >= 32 && buffer[i] <= 127) ? (char) buffer[i] : ".");

					bytesWritten++;
					if (bytesWritten % 16 == 0) {
						env.writeln(String.format("%08X: %s|%s | %s", bytesWritten - 16, hexLeft.toString().trim(),
								hexRight.toString().trim(), human.toString()));
						hexLeft = new StringBuilder();
						hexRight = new StringBuilder();
						human = new StringBuilder();
					}
				}
			}

			// write the last line
			if (bytesWritten % 16 != 0) {
				env.writeln(String.format("%08X: %-23s|%-23s | %-16s", bytesWritten - bytesWritten % 16,
						hexLeft.toString().trim(), hexRight.toString().trim(), human.toString()));
			}

		} catch (IOException|InvalidPathException e) {
			env.writeln(
					"Problems with given file or file path, consider the following error message: " + e.getMessage());
		} finally {
			if (bis != null) {
				try {
					bis.close();
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
