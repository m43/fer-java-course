package hr.fer.zemris.java.hw05.lexer;

/**
 * Tokens used for {@link Lexer}. Tokens are made of {@link TokenType}. Token
 * instances are immutable.
 * 
 * @author Frano Rajič
 */
public class Token {

	/**
	 * Type of the token
	 */
	private TokenType type;

	/**
	 * Stored value of token
	 */
	private String value;

	/**
	 * Construct the token with given token type and value
	 * 
	 * @param type  type of the token
	 * @param value value of the token
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Get the stored value in this token
	 * 
	 * @return the stored value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Get the type of this token
	 * 
	 * @return the token type
	 */
	public TokenType getType() {
		return type;
	}
}