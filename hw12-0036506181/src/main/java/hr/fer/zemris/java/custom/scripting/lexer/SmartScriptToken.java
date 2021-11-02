package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Tokens used for {@link SmartScriptLexer}, and made od
 * {@link SmartScriptTokenType}.
 * 
 * @author Frano
 *
 */
public class SmartScriptToken {

	/**
	 * The type of the token
	 */
	private SmartScriptTokenType type;

	/**
	 * The value of the token
	 */
	private Object value;

	/**
	 * Construct the token with given type and value
	 * 
	 * @param type  of the token
	 * @param value of the token
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Get the value of the token
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Get the type of the token.
	 * 
	 * @return the type
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
}