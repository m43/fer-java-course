package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;;

/**
 * The lexer used by {@link SmartScriptParser} when parsing document. This lexer
 * returns the necessary tokens.
 * 
 * @author Frano Rajič
 */
public class SmartScriptLexer {

	/**
	 * Input text
	 */
	private char[] data;

	/**
	 * Current token
	 */
	private SmartScriptToken token;

	/**
	 * Current state of lexer
	 */
	private SmartScriptLexerState state;

	/**
	 * Index of the first character that has not been processed yet
	 */
	private int currentIndex;

	/**
	 * Create the lexer with given text that needs to be tokenized.
	 * @param text the text to process
	 */
	public SmartScriptLexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT;
	}

	/**
	 * Method for token generation. Generates the next token and then returns it.
	 * 
	 * @return the next generated token
	 * @throws SmartScriptLexerException if an error is met
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException();
		}

		if (currentIndex == data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}

		if (state == SmartScriptLexerState.TEXT) {
			token = generateText();
		} else if (state == SmartScriptLexerState.TAG) {
			token = generateTag();
		}

		return token;
	}

	/**
	 * Help function to check if an open tag is next
	 * 
	 * @return true if the opening tag is next
	 */
	private boolean checkForOpenTag() {
		if (currentIndex >= data.length)
			return false;

		if (data[currentIndex] != '{')
			return false;
		if (data[currentIndex + 1] != '$')
			return false;

		return true;
	}

	/**
	 * Help function to check if an closing tag is next
	 * 
	 * @return true if the close tag is next
	 */
	private boolean checkForCloseTag() {
		if (currentIndex >= data.length)
			return false;
		if (data[currentIndex] != '$')
			return false;
		if (data[currentIndex + 1] != '}')
			return false;

		return true;
	}

	/**
	 * Help function to generate Text tokens
	 * 
	 * @return the generated text token
	 */
	private SmartScriptToken generateText() {
		StringBuilder sb = new StringBuilder();
		while (!checkForOpenTag() && currentIndex < data.length) {
			char c = data[currentIndex];
			switch (c) {

			case '\\':
				if (currentIndex + 1 == data.length) {
					throw new SmartScriptLexerException();
				}
				currentIndex++;
				if (data[currentIndex] != '\\' && data[currentIndex] != '{') {
					throw new SmartScriptLexerException();
				}
				sb.append(data[currentIndex]);
				break;

			default:
				sb.append(c);
				break;
			}
			currentIndex++;
		}

		if (sb.toString().isEmpty()) {
			state = SmartScriptLexerState.TAG;
			currentIndex += 2;
			return new SmartScriptToken(SmartScriptTokenType.OPEN_TAG, null);
		} else {
			return new SmartScriptToken(SmartScriptTokenType.TEXT, sb.toString());
		}

	}

	/**
	 * Help function to generate tokens that made up tags
	 * 
	 * @return the generated token
	 */
	private SmartScriptToken generateTag() {
		skipBlanks();

		if (currentIndex == data.length) {
			return new SmartScriptToken(SmartScriptTokenType.EOF, null);
			// ?? is it an exception if document ends here?
		}

		if (checkForCloseTag()) {
			state = SmartScriptLexerState.TEXT;
			currentIndex += 2;
			return new SmartScriptToken(SmartScriptTokenType.CLOSE_TAG, null);
		}

		char c = data[currentIndex];

		if (c == '"') {
			String value = loadString();
			return new SmartScriptToken(SmartScriptTokenType.STRING, value);
		}

		if (c == '-') {
			if (currentIndex + 1 != data.length && Character.isDigit(data[currentIndex + 1])) {
				String value = loadNumber();
				return new SmartScriptToken(SmartScriptTokenType.NUMBER, value);
			} else {
				return new SmartScriptToken(SmartScriptTokenType.SYMBOL, c);
			}

		}

		if (c == '@') {
			if (currentIndex + 1 != data.length && Character.isLetter(data[currentIndex + 1])) {
				currentIndex++;
				String value = loadIdentifier();
				return new SmartScriptToken(SmartScriptTokenType.FUNCTION, value);
			}
		}

		if (Character.isLetter(c)) {
			String value = loadIdentifier();
			return new SmartScriptToken(SmartScriptTokenType.IDENTIFIER, value);
		}

		if (Character.isDigit(c)) {
			String value = loadNumber();
			return new SmartScriptToken(SmartScriptTokenType.NUMBER, value);
		}

		currentIndex++;
		return new SmartScriptToken(SmartScriptTokenType.SYMBOL, c);
	}

	/**
	 * Help method to tokenize a String according rules:
	 * 
	 * \\ sequence treat as a single string character \
	 * 
	 * \" treat as a single string character " (and not the end of the string)
	 * 
	 * \n, \r and \t have its usual meaning (ascii 10, 13 and 9).
	 * 
	 * Every other sequence which starts with \ is treated as invalid and throw an
	 * an exception is thrown
	 * 
	 * @return the generated string
	 * @throws SmartScriptLexerException if invalid string or any other error
	 */
	private String loadString() {
		StringBuilder sb = new StringBuilder();

		currentIndex++;
		char c;
		while (currentIndex != data.length) {
			c = data[currentIndex];

			if (c == '"') {
				currentIndex++;
				return sb.toString();
			}

			if (c == '\\') {
				if (currentIndex + 1 == data.length) {
					throw new SmartScriptLexerException(
							"Backslash alone at end inside string token - not permited (invalid use of escape)");
				}

				c = data[++currentIndex];
				switch (c) {

				case '\\':
					sb.append(c);
					break;
				case 'n':
					sb.append('\n');
					break;
				case 'r':
					sb.append('\r');
					break;
				case 't':
					sb.append('\t');
					break;
				case '"':
					sb.append('"');
					break;

				default:
					throw new SmartScriptLexerException("Invalid escape in string token");
				}

				currentIndex++;
				continue;
			}

			currentIndex++;
			sb.append(c);
		}

		throw new SmartScriptLexerException("Opened string tag token was not closed");
	}

	/**
	 * Help function to load an identifier. Valid identifier name starts by letter
	 * and after follows zero or more letters, digits or underscores. If name is not
	 * valid, it is invalid. This variable names are valid: A7_bb, counter, tmp_34;
	 * these are not: _a21, 32, 3s_ee etc.
	 * 
	 * @return the next generated identifier
	 * 
	 */
	private String loadIdentifier() {
		StringBuilder sb = new StringBuilder();
		char c;

		while (currentIndex != data.length) {
			c = data[currentIndex];

			if (!Character.isLetterOrDigit(c) && c != '_') {
				break;
			}

			currentIndex++;
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Help function to load a number, either decimal or integer.
	 * 
	 * @return generated number in trimmed String
	 */
	private String loadNumber() {
		StringBuilder sb = new StringBuilder();

		if (data[currentIndex] == '-') {
			sb.append('-');
			currentIndex++;
		}

		char c;
		int dotCount = 0;
		while (currentIndex != data.length) {
			c = data[currentIndex];

			if (c == '.') {
				if (dotCount == 1 || currentIndex + 1 == data.length || !Character.isDigit(data[currentIndex + 1])) {
					break;
				}

				dotCount++;
			}

			if (!Character.isDigit(c) && c != '.') {
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
	 * Method to check if given character could be described as blank. Blank chars
	 * are new line, \t, \r and space
	 * 
	 * @param c to check if it is blank
	 * @return true if given char is blank
	 */
	private boolean isBlank(char c) {
		return c == '\r' || c == ' ' || c == '\n' || c == '\t';
	}

	/**
	 * This method that returns the last token generated and can be called as many
	 * times as needed. Does not start the token generation.
	 * 
	 * @return the last token generated
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Change the state of the lexer
	 * 
	 * @param state to put the lexer into
	 */
	public void setState(SmartScriptLexerState state) {
		Objects.requireNonNull(state);

		this.state = state;
	}

	/**
	 * Get the current state of the lexer
	 * @return the state
	 */
	public SmartScriptLexerState getState() {
		return state;
	}

}
