package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * A lexical analyzer made as lazy one, grouping characters only when called.
 * Gives the functionality of creating Tokens and getting the last one generated
 * 
 * @author Frano
 *
 */
public class Lexer {

	/**
	 * Input text
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private Token token;

	/**
	 * Current state of lexer
	 */
	private LexerState state;

	/**
	 * Index of the first character that has not been processed yet
	 */
	private int currentIndex;

	/**
	 * Create a new {@link Lexer} with given text to tokenize
	 * 
	 * @param text the text to tokenize
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
		// token = nextToken();
	}

	/**
	 * Help interface to process some of the lexer tasks
	 * 
	 * @author Frano
	 *
	 */
	private static interface CharTester {

		/**
		 * Test the given Character
		 * 
		 * @param c the Character to test
		 * @return the testing result
		 */
		boolean test(Character c);
	}

	/**
	 * Token generating method. Generates and returns the next token.
	 * 
	 * @return the next token
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

		char c = data[currentIndex];

		if (state == LexerState.EXTENDED) {
			if (c == '#') {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, '#');
				state = LexerState.BASIC;
			} else {
				String anything = load(x -> !isBlank(x) && x != '#');
				token = new Token(TokenType.WORD, anything);
			}
		} else if (Character.isLetter(c) || c == '\\') {
			String word = loadWord();
			token = new Token(TokenType.WORD, word);
		} else if (Character.isDigit(c)) {
			String numberString = load(Character::isDigit);
			;
			try {
				Long number = Long.parseLong(numberString);
				token = new Token(TokenType.NUMBER, number);
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid input - too big number");
			}
		} else {
			if (c == '#') {
				state = LexerState.EXTENDED;
			}

			token = new Token(TokenType.SYMBOL, c);
			currentIndex++;
		}

		return token;
	}

	/**
	 * Load a string of neighboring chars that make up a token. Characters are
	 * loaded from the place where the index pointer is currently pointing at. The
	 * index pointer will be moved after this operation to the place where the next
	 * token starts.
	 * 
	 * @return the string that contains all token characters
	 */
	private String loadWord() {
		StringBuilder sb = new StringBuilder();
		char c;
		while (currentIndex != data.length) {
			c = data[currentIndex];

			if (Character.isLetter(c)) {
				currentIndex++;
				sb.append(c);
				continue;
			}

			if (c == '\\') {
				currentIndex++;

				if (currentIndex == data.length) {
					throw new LexerException("Backslash alone at end - not permited (invalid use of escape)");
				}

				c = data[currentIndex];
				if (!Character.isDigit(c) && c != '\\') {
					throw new LexerException("Invalid use of escape");
				}
				currentIndex++;
				sb.append(c);
				continue;
			}

			break;
		}

		return sb.toString();
	}

	/**
	 * Help class to group characters into strings by rules defined with given
	 * tester.
	 * 
	 * @param t tells how to test the chars for grouping. If tester is satisfied,
	 *          char is grouped
	 * @return the group of created characters
	 */
	private String load(CharTester t) {
		StringBuilder sb = new StringBuilder();
		char c;
		while (currentIndex != data.length) {
			c = data[currentIndex];

			if (!t.test(c)) {
				break;
			}

			currentIndex++;
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Method skips all the \r \t \n characters and spaces as well, leaving the
	 * index pointer at the start of next valid token
	 */
	private void skipBlanks() {
		char c;
		while (currentIndex < data.length) {
			c = data[currentIndex];
			if (isBlank(c)) {
				currentIndex++;
				continue;
			}
			break;
		}
	}

	/**
	 * Help method to check if the given character is blank. A blank character is
	 * either a space, \r, \n or \t
	 * 
	 * @param c the char to test
	 * @return true if the given char is considered blank
	 */
	private boolean isBlank(char c) {
		return c == '\r' || c == ' ' || c == '\n' || c == '\t';
	}

	/**
	 * Method that returns the last generated token. Can me called arbitrarily many
	 * times. Does not start the token generator method.
	 * 
	 * @return the last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Set the current state of the lexer to the given state
	 * 
	 * @param state the new state of the lexer
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);

		this.state = state;
	}
}