package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is a program that allows the user to encrypt/decrypt given file as
 * described in {@link Crypto#encrypt(String, String, String, String, int)} or
 * calculate and check the SHA-256 file digest of a given file, done by
 * {@link Crypto#generateSHA(String)}. The program communicates with user over
 * command line, asking for initializing data till it gets valid data.
 * 
 * @author Frano Rajič
 */
public class Crypto {

	/**
	 * Keyword for the command of checking SHA digest,
	 * {@link Crypto#CHECK_SHA_KEYWORD}
	 */
	public final static String CHECK_SHA_KEYWORD = "checksha";

	/**
	 * Keyword for encryption is {@link Crypto#ENCRYPTION_KEYWORD}
	 */
	public final static String ENCRYPTION_KEYWORD = "encrypt";

	/**
	 * Keyword for decryption is {@link Crypto#DECRYPTION_KEYWORD}
	 */
	public final static String DECRYPTION_KEYWORD = "decrypt";

	/**
	 * Entry point to program.
	 * 
	 * @param args Arguments start with either checksha or encrypt/decrypt. In first
	 *             case an additional argument containing path to file is expected.
	 *             In letter case two aditional arguments are expected, first is the
	 *             file to be encrypted or decrypted and the second one is where to
	 *             decrypt it.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No arguments given... HELP:");
			help();
			return;
		}

		if (CHECK_SHA_KEYWORD.equals(args[0])) {
			if (args.length != 2) {
				System.out.println("Invalid number of arguments given for " + CHECK_SHA_KEYWORD + "... HELP:");
				help();
				return;
			}

			processCheckSha(args);
			return;
		}

		if (DECRYPTION_KEYWORD.equals(args[0]) || ENCRYPTION_KEYWORD.equals(args[0])) {
			if (args.length != 3) {
				System.out.println("Invalid number of arguments given for command " + DECRYPTION_KEYWORD + "... HELP:");
				help();
				return;
			}

			if (!Files.exists(Paths.get(args[1]))) {
				System.out.println("File " + args[1] + " doesnt exist at all!");
				return;
			}

			processEncryption(args);
			return;
		}

		System.out.println("Unsupported parameter given... HELP:");
		help();
	}

	/**
	 * Help method to check the SHA of a file
	 * 
	 * @param args First argument is checksha and the rest contains exactly one
	 *             argument which is the path to the file to be checked
	 */
	private static void processCheckSha(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.printf("Please provide expected sha-256 digest for " + args[1] + ":\n> ");
			String expected;
			while (!isSHA256Valid(expected = sc.nextLine())) {
				System.out.println("Invalid sha-256 key given! Enter again");
				System.out.print("> ");
			}

			String calculated = null;
			try {
				calculated = generateSHA(args[1]);
			} catch (IOException e) {
				System.out.println("Couldn't open given path to file. Check if read/write is ok for both...");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Algorithm SHA-256 cannot be used!");
			}

			if (expected.equals(calculated)) {
				System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + args[1]
						+ " does not match the expected digest. Digest was: " + calculated);
			}

		}
	}

	/**
	 * Help method to process an encryption
	 * 
	 * @param args Arguments contain the command encrypt or decrypt and paths of two
	 *             files
	 */
	private static void processEncryption(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int mode = ENCRYPTION_KEYWORD.equals(args[0]) ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;

			System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			String key;
			while (!isKeyValid(key = sc.nextLine())) {
				System.out.println("Invalid KEY given!");
				System.out.print("> ");
			}

			System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			String initialVector;
			while (!isIVValid(initialVector = sc.nextLine())) {
				System.out.println("Invalid KEY given!");
				System.out.print("> ");
			}

			encrypt(args[1], args[2], key, initialVector, mode);

			if (ENCRYPTION_KEYWORD.equals(args[0])) {
				System.out
						.println("Encryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
			} else {
				System.out
						.println("Decryption completed. Generated file " + args[2] + " based on file " + args[1] + ".");
			}

		} catch (IOException e) {
			System.out.println("Couldn't open given paths to file, " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid arguments given. (Message: " + e.getMessage() + ")");
		}

	}

	/**
	 * Methods that checks if given SHA256 string is valid.
	 * 
	 * @param string to check for validity
	 * @return true if given string is a valid SHA256 string
	 */
	private static boolean isSHA256Valid(String string) {
		return string.length() == 64 && Util.isValidHEXString(string);
	}

	/**
	 * Help methods that checks if given initial vector is valid. It needs to be
	 * 128, 192 or 256 long and have only valid HEX chars
	 * 
	 * @param string Initial vector to check
	 * @return true if initial vector is valid
	 */
	private static boolean isIVValid(String string) {
		return (string.length() == 32 || string.length() == 48 || string.length() == 64)
				&& Util.isValidHEXString(string);
	}

	/**
	 * Help methods that checks if given key is valid. It needs to be 128, 192 or
	 * 256 long and have only valid HEX chars
	 * 
	 * @param string key to check
	 * @return true if key is valid
	 */
	private static boolean isKeyValid(String string) {
		return (string.length() == 32 || string.length() == 48 || string.length() == 64)
				&& Util.isValidHEXString(string);
	}

	/**
	 * Method does the encryption or decryption of given files, key and initial
	 * vector. The symmetric crypto-algorithm AES is used, which can work with three
	 * different key sizes: 128 bit, 192 bit and 256 bit. Since AES is block cipher,
	 * it always consumes 128 bit of data at a time (or adds padding if no more data
	 * is available) and produces 128 bits of encrypted text. Therefore, the length
	 * (in bytes) of encrypted file file will always be divisible by 16. Encrypted
	 * data will always be as big (or even bigger) as were the original data. An
	 * "AES/CBC/PKCS5Padding" instance of {@link Cipher} is used.
	 * 
	 * @param firstPath     Path to first file, the file to generate from
	 * @param secondPath    Path to second file that will be generated
	 * @param keyString     The key to use
	 * @param initialVector The initial vector to use
	 * @param mode          Either encryption when {@link Cipher#ENCRYPT_MODE} or
	 *                      decryption when {@value Cipher#DECRYPT_MODE}
	 * @throws IOException              If any problem with reading given paths
	 *                                  occurs
	 * @throws IllegalArgumentException If any problem when doing the encryption or
	 *                                  decryption occurs
	 */
	public static void encrypt(String firstPath, String secondPath, String keyString, String initialVector, int mode)
			throws IOException {
		SecretKey key = new SecretKeySpec(Util.hextobyte(keyString), "AES");

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(Files.newInputStream(Paths.get(firstPath)));
			bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(secondPath)));

			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(mode, key, new IvParameterSpec(Util.hextobyte(initialVector)));

			int bytesRead = 0;
			byte[] buffIn = new byte[4096];
			byte[] buffOut = new byte[4096];
			while ((bytesRead = bis.read(buffIn)) > 0) {
				bytesRead = c.update(buffIn, 0, bytesRead, buffOut);
				bos.write(buffOut, 0, bytesRead);
			}

			bytesRead = c.doFinal(buffIn, 0, 0, buffOut);
			bos.write(buffOut, 0, bytesRead);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | ShortBufferException | IllegalBlockSizeException
				| BadPaddingException | InvalidPathException e) {
			throw new IllegalArgumentException(e.getMessage());
		} finally {
			//@formatter:off
			if (bis != null) {
				try { bis.close(); } catch(IOException ignorable) {}
			}
			if (bos != null) {
				try { bos.close(); } catch(IOException ignorable) {}
			}
			//@formatter:on
		}
	}

	/**
	 * Help method that does the actual calculation of "SHA-256"
	 * 
	 * @param path Path to file which hash needs to be checked
	 * @return the generated sha string of given file
	 * @throws IOException              If any problem with reading given file path
	 *                                  occurs
	 * @throws NoSuchAlgorithmException If used algorithm is unsupported by
	 *                                  {@link MessageDigest} for some reason
	 */
	public static String generateSHA(String path) throws IOException, NoSuchAlgorithmException {
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(path)))) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			int bytesRead = 0;
			byte[] buff = new byte[4096];
			while ((bytesRead = bis.read(buff)) > 0) {
				md.update(buff, 0, bytesRead);
			}

			return Util.bytetohex(md.digest());
		}
	}

	/**
	 * Print out the instructions for using the program
	 */
	private static void help() {
		System.out.println("Program works like this:");
		System.out.println("checksha x\t(x - path to file)");
		System.out.println("encrypt x y\t(x - path to file to encrypt, y - path to generated file)");
		System.out.println("decrypt x y\t(x - path to encypted file, y - path to generated file)");
		System.out.println();
	}

}