package hr.fer.zemris.java.hw06.crypto;

/**
 * @author Frano Rajič
 */
public class Util {

	/**
	 * Method takes hex-encoded String and returns appropriate byte[]. For
	 * zero-length string, method returns zero-length byte array. Invalid strings
	 * are those with illegal characters (not 0-9 or a-f or A-F). Method supports
	 * both uppercase letters and lowercase letters.
	 * 
	 * @param keyText hex string to turn into byte array
	 * @return the byte array of the hex string
	 * @throws IllegalArgumentException If string is not valid (odd-sized or has
	 *                                  invalid characters)
	 */
	public static byte[] hextobyte(String keyText) {		
		if (!keyText.isEmpty() && !isValidHEXString(keyText)) {
			throw new IllegalArgumentException("Hex string is invalid");
		}
		byte[] b = new byte[keyText.length() / 2];

		int c1, c2;
		for (int i = 0; i < keyText.length() / 2; i++) {
			c1 = Character.digit(keyText.charAt(2 * i), 16);
			c2 = Character.digit(keyText.charAt(2 * i + 1), 16);
			if (c1 == -1 || c2 == -1) {
				throw new IllegalArgumentException("Invalid char in given string...");
			}

			b[i] = (byte) (16 * c1 + c2);
		}

		return b;
	}

	/**
	 * Method uses lowercase letters to create a string representation of given
	 * bytes array. Byte array is considered to be big endian.
	 * 
	 * @param bytearray given byte array to convert to hex
	 * @return the hex-encoded string representation of the given byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();

		char c1, c2;
		int bInt;
		for (byte b : bytearray) {
			bInt = (b >= 0 ? b : b + 256);
			c1 = (char) (bInt / 16 > 9 ? bInt / 16 + 'a' - 10 : '0' + bInt / 16);
			c2 = (char) (bInt % 16 > 9 ? bInt % 16 + 'a' - 10 : '0' + bInt % 16);
					
			sb.append(c1);
			sb.append(c2);
		}

		return sb.toString().toLowerCase();
	}

	/**
	 * Check if string is a valid hex string
	 * 
	 * @param s string to check
	 * @return true if valid HEX string
	 */
	public static boolean isValidHEXString(String s) {
		return s.length() % 2 == 0 && s.toLowerCase().matches("^[0-9a-f]+$");
	}

}
