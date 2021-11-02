package hr.fer.zemris.java.hw06.shell;

/**
 * Class enumerates all the states that a shell can be in. These are
 * {@link ShellStatus#CONTINUE} and {@link ShellStatus#TERMINATE}.
 * 
 * @author Frano Rajič
 */
public enum ShellStatus {
	/**
	 * If shell should continue with work.
	 */
	CONTINUE,

	/**
	 * If shell should terminate
	 */
	TERMINATE
}
