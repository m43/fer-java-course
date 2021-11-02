package hr.fer.zemris.java.hw03.prob1;

/**
 * The types of tokens created in process of tokenization
 * 
 * @author Frano
 *
 */
public enum TokenType {
	/**
	 * Token represents end of file
	 */
	EOF,

	/**
	 * Token denotes a word
	 */
	WORD,

	/**
	 * Token represents a number
	 */
	NUMBER,

	/**
	 * Token denotes a symbol
	 */
	SYMBOL;
}
