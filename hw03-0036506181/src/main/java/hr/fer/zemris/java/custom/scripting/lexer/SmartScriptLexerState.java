package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Constructed {@link SmartScriptLexer} can take only one of these states.
 * 
 * @author Frano
 *
 */
public enum SmartScriptLexerState {
	/**
	 * State denotes that the lexer is currently tokenizing a part of the script
	 * considered to be normal text.
	 */
	TEXT,
	/**
	 * State denotes that the lexer is currently tokenizing a part of the script
	 * that is considered to be a tag
	 */
	TAG
}
