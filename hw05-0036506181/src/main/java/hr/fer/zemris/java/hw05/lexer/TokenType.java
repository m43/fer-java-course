package hr.fer.zemris.java.hw05.lexer;

/**
 * Valid token types used for {@link Lexer}
 * 
 * @author Frano Rajič
 *
 */
public enum TokenType {

	/**
	 * Used to identify field names
	 */
	IDENTIFIER,
	
	/**
	 * Used for string literals
	 */
	STRING,
	
	/**
	 * Used for operations
	 */
	OPERATION,
	
	/**
	 * Used for logical operators
	 */
	// ok, this one was actually unnecessary, i could've used just an IDENTIFIER..
	LOGICAL_OPERATOR,
	
	/**
	 * Used to identify the end of the input text
	 */
	EOF

}
