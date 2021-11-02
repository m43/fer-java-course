package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Valid token types used for {@link SmartScriptLexer}
 * 
 * @author Frano
 *
 */
public enum SmartScriptTokenType {

	/**
	 * Token represents an identifier
	 */
	IDENTIFIER,

	/**
	 * Token to denote a function
	 */
	FUNCTION,

	/**
	 * Token to denote a string
	 */
	STRING,

	/**
	 * Token to denote a number
	 */
	NUMBER,

	/**
	 * Token to denote a symbol
	 */
	SYMBOL,

	/**
	 * Token to denote an opening tag
	 */
	OPEN_TAG,

	/**
	 * Token to denote a closing tag
	 */
	CLOSE_TAG,

	/**
	 * Token to denote normal text
	 */
	TEXT,

	/**
	 * Token to denote a end of file
	 */
	EOF

}
