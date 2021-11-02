package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Valid token types used for {@link SmartScriptLexer}
 * 
 * @author Frano
 *
 */
public enum SmartScriptTokenType {

	/**
	 * Token representing an identifier
	 */
	IDENTIFIER,
	/**
	 * Token representing a function
	 */
	FUNCTION,
	/**
	 * Token representing an string that was found
	 */
	STRING,
	/**
	 * Token representing an number
	 */
	NUMBER,
	/**
	 * Token representing an symbol
	 */
	SYMBOL,
	/**
	 * Token representing the opening tag
	 */
	OPEN_TAG,
	/**
	 * Token representing the closing tag
	 */
	CLOSE_TAG,
	/**
	 * Token representing normal text
	 */
	TEXT,
	/**
	 * Token representing the end of file
	 */
	EOF

}
