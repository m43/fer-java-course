package hr.fer.zemris.java.hw06.shell;

/**
 * This class is a program an concrete implementation of an simple environment -
 * {@link EnvironmentImpl}.
 * 
 * @author Frano Rajič
 * @version 1.0
 */
public class MyShell {

	/**
	 * Point of entry for program to start at.<br>
	 * 
	 * @param args arguments are irrelevant
	 */
	public static void main(String[] args) {
		// i used EnviromentImpl as type only to be able to use added method "terminate"
		// to close the scanner
		EnvironmentImpl env = new EnvironmentImpl();

		String line = "";
		ShellStatus status;
		while (true) {
			env.write((line.isEmpty() ? env.getPromptSymbol() : env.getMultilineSymbol()) + " ");
			line += env.readLine();

			if (line.endsWith(String.valueOf(env.getMorelinesSymbol()))) {
				line = line.substring(0, line.length() - 1);
				continue;
			}

			if ("".equals(line)) {
				continue;
			}

			String[] lineSplit = line.trim().split("\\s+", 2);
			line = "";
			if (!env.commands().containsKey(lineSplit[0])) {
				env.writeln("No command of name: \"" + lineSplit[0] + "\"");
			} else {
				try {
					if (lineSplit.length == 1) {
						status = env.commands().get(lineSplit[0]).executeCommand(env, "");
					} else {
						status = env.commands().get(lineSplit[0]).executeCommand(env, lineSplit[1]);
					}

					if (ShellStatus.TERMINATE.equals(status)) {
						env.terminate();
						break;
					}
				} catch (ShellIOException e) {
					env.writeln("Holala some unseenable error happened: " + e.getMessage() + "\n TERMINATING ! ...");
				}
			}
		}

	}

}
