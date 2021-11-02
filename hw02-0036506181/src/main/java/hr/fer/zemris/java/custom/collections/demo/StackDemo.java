package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This is a program which accepts a single command-line argument, namely the
 * expression which should be evaluated. The expression must be in postfix
 * representation. Note that the entered expression must be sourounded with
 * double quotes when entered as command line argument and there must always be
 * one argument for the program to work.
 * 
 * Example 1: "8 2 /" means 8/2 and gives 4.
 * 
 * Example 2: "-1 8 2 / +" means divide 8 and 2, so 8/2=4, then apply + on -1
 * and 4, so the result is 3.
 * 
 * @author Frano
 *
 */

public class StackDemo {

	/**
	 * Main method implements described class functionality.
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
//		Integer[] a = new Integer[2];
//		Integer[] b = new Integer[2];
//		
//		a[0] = new Integer(1);
//		a[1] = new Integer(2);
//		
//		b = Arrays.copyOf(a, 10);
//		
//		System.out.println(a[0]==b[0]);
		
		if (args.length != 1) {
			System.out.println("Invalid number of arguments: " + args.length + "... Exactly one argument needed.");
			return;
		}

		// expression example:
		// "9 3 -2 4 20 2 * - * / -" --> 9
		// "9 3 -2 4 20 2 * - * + +" --> 84
		// "9 3 -2 4 20 2 * - * % -" --> 9

		ObjectStack stack = new ObjectStack();
		for (String s : args[0].split(" ")) {
			try {
				stack.push(Integer.parseInt(s));
			} catch (NumberFormatException e) {
				if (stack.size() < 2) {
					System.out.println("Invalid expression given: operation " + s + " needs two elements");
					return;
				}

				int result;
				int first = (int) stack.pop();
				int second = (int) stack.pop();
				switch (s) {
				case "+":

					result = second + first;
					stack.push(result);
					break;
				case "-":
					result = second - first;
					stack.push(result);
					break;
				case "/":
					if (first == 0) {
						System.out.println("Cannot devide by zero!");
						return;
					}
					result = second / first;
					stack.push(result);
					break;
				case "*":
					result = second * first;
					stack.push(result);
					break;
				case "%":
					result = second % first;
					stack.push(result);
					break;
				default:
					System.out.println("Invalid operation entered: " + s);
					return;
				}
			}
		}

		if (stack.size() != 1) {
			System.out.println("Invalid expression - only one number should be left after all operations!");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}

		return;
	}

}
