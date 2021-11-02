package hr.fer.zemris.java.hw05.lexer;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Lexer that recognizes four types of tokens. Provides the functionality of
 * generating next tokens and retrieving the last token generated. Token is
 * modeled by {@link TokenType}
 * 
 * @author Frano Rajič
 *
 */
public class Lexer {

	/**
	 * Storing structure for the given input data that needs to be tokenized
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private Token token;

	/**
	 * Index of the first character that hasn't been processed yet
	 */
	private int currentIndex;

	/**
	 * Construct the lexer with given text to tokenize
	 * 
	 * @param text to tokenize
	 * @throws NullPointerException if given text is a null pointerF
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);

		data = text.trim().toCharArray();
		currentIndex = 0;
	}

	/**
	 * Method for token generation. Generates the next token and returns it.
	 * 
	 * @return the next generated token
	 * @throws LexerException if an error is met
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException();
		}

		skipBlanks();
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		token = generateToken();

		return token;
	}

	/**
	 * Help function that generates the next token
	 * 
	 * @return the generated token
	 * @throws LexerException if there is no token to be recognized and made as next
	 */
	private Token generateToken() {

		String operation = tryToLoadOperation();
		if (operation != null) {
			return new Token(TokenType.OPERATION, operation);
		}

		char c = data[currentIndex];

		if (Character.isAlphabetic(c)) {
			String id = load(Character::isAlphabetic);

			// side note: checking the AND is something the parser should rather do..
			if (id.toUpperCase().equals("AND")) {
				return new Token(TokenType.LOGICAL_OPERATOR, "AND");
			}

			return new Token(TokenType.IDENTIFIER, id);
		}

		if (c == '"') {
			return new Token(TokenType.STRING, loadString());
		}

		throw new LexerException("Cannot make any token from character " + c);
	}

	/**
	 * Help method to try to load an operation string. If the next token is not an
	 * operation, null is returned
	 * 
	 * @return the operation as string, or null otherwise
	 */
	private String tryToLoadOperation() {

		char c = data[currentIndex];

		if (currentIndex + 3 < data.length) {
			String like = "" + data[currentIndex] + data[currentIndex + 1] + data[currentIndex + 2]
					+ data[currentIndex + 3];
			if (like.equals("LIKE")) {
				currentIndex += like.length();
				return like;
			}
		}

		if (c == '<') {
			if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
				currentIndex += 2;
				return "<=";
			}
			currentIndex += 1;
			return "<";
		}

		if (c == '>') {
			if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
				currentIndex += 2;
				return ">=";
			}
			currentIndex += 1;
			return ">";
		}

		if (c == '!') {
			if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
				currentIndex += 2;
				return "!=";
			}
		}

		if (c == '=') {
			currentIndex += 1;
			return "=";
		}

		return null;
	}

	/**
	 * Generate an string literal. An string literal begins with a quote sign and
	 * ends with it.
	 * 
	 * @return generated string literal
	 */
	private String loadString() {
		currentIndex++; // to skip the quote

		StringBuilder sb = new StringBuilder();
		char c;

		while (currentIndex != data.length) {
			c = data[currentIndex];

			if (c == '"') {
				currentIndex++;
				break;
			}

			currentIndex++;
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Help function to load an identifier. Valid identifier name starts by lower
	 * case letter and contains only alphabetic letters
	 * 
	 * @param p the predicate to use when testing if a char is acceptable
	 * @return the generated identifier
	 */
	private String load(Predicate<Character> p) {

		StringBuilder sb = new StringBuilder();
		char c;

		while (currentIndex < data.length) {
			c = data[currentIndex];

			if (!p.test(c)) {
				break;
			}

			currentIndex++;
			sb.append(c);

		}
		return sb.toString();
	}

	/**
	 * Method skips \r \t \n characters and spaces as well, leaving the index
	 * pointer at the start of next valid token
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (!isBlank(data[currentIndex])) {
				break;
			}

			currentIndex++;
			;
		}
	}

	/**
	 * Method to check if given character is regarded as blank. Blank chars are \n,
	 * \t, \r and space
	 * 
	 * @param c character to check
	 * @return true if given char is blank
	 */
	private boolean isBlank(char c) {
		return c == '\r' || c == ' ' || c == '\n' || c == '\t';
	}

	/**
	 * This method that returns the last token generated and can be called as many
	 * times as needed. This method doesn't generate the next token.
	 * 
	 * @return the last token generated
	 */
	public Token getToken() {
		return token;
	}

}
