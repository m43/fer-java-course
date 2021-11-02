package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class models the command that prints the tree of a directory.
 * 
 * @author Frano Rajič
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * The name of this command
	 */
	private static final String commandName = "tree";

	/**
	 * The description of this command
	 */
	private static final String description = "tree DIR - command expects a single argument: directory name and prints a tree (each directory level shifts output two charatcers to the right).";

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
			env.writeln("Invalid number of arguments given to command " + getCommandName());
			return ShellStatus.CONTINUE;
		}
		if (args.length == 0) {
			args = new String[] {""};
		}
		
		Path dir;
		try {
			dir = Paths.get(args[0]);
		}  catch (InvalidPathException e) {
			env.writeln(
					"Problems with paths, consider the following error message: " + e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(dir)) {
			env.writeln("Given path is not a directory");
		}

		SimpleFileVisitor<Path> visitor = new Visitor(env);
		try {
			Files.walkFileTree(dir, visitor);
		} catch (IOException e) {
			env.writeln("Couldn't create the tree, consider the following error: " + e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Help class that provides the functionality of going through the tree of a
	 * given directory and printing that tree out. The used separator/tabulator is
	 * defined by {@link Visitor#SEPARATOR} and it is repeated
	 * {@link Visitor#REPEAT} times the depth of path.
	 * 
	 * @author Frano Rajič
	 */
	private class Visitor extends SimpleFileVisitor<Path> {
		/**
		 * Variable used to memorize the current depth of the tree
		 */
		private int depth = 0;

		/**
		 * The environment object used only for output
		 */
		private Environment env;

		/**
		 * How many times will the {@link Visitor#SEPARATOR} be repeated for each depth
		 */
		private static final int REPEAT = 2;

		/**
		 * The separator/tabulator used when printing the tree out
		 */
		private static final String SEPARATOR = " ";

		/**
		 * Constructor to initialize the environment variable
		 * 
		 * @param e the environment to use
		 */
		public Visitor(Environment e) {
			Objects.requireNonNull(e);
			env = e;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(SEPARATOR.repeat(REPEAT).repeat(depth) + dir.getFileName());
			depth++;
			return super.preVisitDirectory(dir, attrs);
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(SEPARATOR.repeat(REPEAT).repeat(depth) + file.getFileName());
			return super.visitFile(file, attrs);
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			depth--;
			return FileVisitResult.CONTINUE;
		}

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
