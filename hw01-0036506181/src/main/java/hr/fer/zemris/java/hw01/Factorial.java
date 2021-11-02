package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Provides functionality of calculating factorial of integer up to
 * {@value #UPPER_BOUND} in O(n) and a specific usage of it in the main method
 * where the range is restricted to interval [{@value #LOWER_BOUND},
 * {@value #UPPER_BOUND}]. The program accepts input till {@value #END} is
 * received.
 * 
 * @author Frano
 * @version 1.0
 */

public class Factorial {

	/**
	 * The lower bound of calculated factorial range
	 */
	public static final int LOWER_BOUND = 3;

	/**
	 * The upper bound of calculated factorial range
	 */
	public static final int UPPER_BOUND = 20;

	/**
	 * The terminating keyword
	 */
	public static final String END = "kraj";

	/**
	 * Program to calculate factorial of integers in range [{@value #LOWER_BOUND},
	 * {@value #UPPER_BOUND}]. The program is terminated with keyword {@value #END}.
	 * 
	 * @param args Irrelevant to program
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			if (END.equals(input)) {
				System.out.println("Doviđenja");
				break;
			}

			int number = 0;
			try {
				number = Integer.parseInt(input);
				if (number > UPPER_BOUND || number < LOWER_BOUND) {
					System.out.format("'%d' nije broj u dozvoljenom rasponu.%n", number);
					continue;
				}
				long result = calculateFactorial(number);
				System.out.format("%d! = %d%n", number, result);
			} catch (NumberFormatException e) {
				System.out.printf("'%s' nije cijeli broj.%n", input);
				continue;
			} catch (IllegalArgumentException e) {
				System.out.format("'%d' nije broj u dozvoljenom rasponu.%n", number);
			}
		}

		sc.close();
	}

	/**
	 * A method to calculate the factorial of a given number. number! =
	 * number*(number-1)*...*2*1. Complexity is O(number). Number can be up to
	 * {@value #UPPER_BOUND}.
	 * 
	 * @param number The number needs to be positive or equal zero and less than
	 *               {@value #LOWER_BOUND}. Factorial of zero is defined as 0!=1
	 * @return Factorial of number, number!
	 * @throws IllegalArgumentException thrown if given number is out of bounds
	 */
	public static long calculateFactorial(int number) throws IllegalArgumentException {
		if (number < 0 || number > UPPER_BOUND) {
			throw new IllegalArgumentException();
		}

		if (number == 0) {
			return 1;
		}

		return (long) number * calculateFactorial(number - 1);
	}
}
