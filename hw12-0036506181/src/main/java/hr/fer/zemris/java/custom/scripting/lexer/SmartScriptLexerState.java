package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Constructed {@link SmartScriptLexer} can take only one of these states.
 * 
 * @author Frano
 *
 */
public enum SmartScriptLexerState {
	/**
	 * The state where the lexer is tokenizing text
	 */
	TEXT,
	/**
	 * The state where the lexer is tokenising special tags
	 */
	TAG
}
