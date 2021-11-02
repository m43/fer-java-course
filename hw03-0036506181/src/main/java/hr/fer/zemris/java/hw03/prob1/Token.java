package hr.fer.zemris.java.hw03.prob1;

/**
 * A token used by lexer when tokenizing input. The types of tokens are modeled
 * by {@link TokenType}.
 * 
 * @author Frano
 */
public class Token {

	/**
	 * The type of the token
	 */
	private TokenType type;

	/**
	 * The value of the token
	 */
	private Object value;

	/**
	 * Create a new token defined by given type and value
	 * 
	 * @param type  the type of the token
	 * @param value the value of the token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

}
