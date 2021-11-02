package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The class is a program that provides the user with the functionality of
 * entering height and width through either standard input or command line
 * arguments. After entering rectangle data, the program tells what the area and
 * perimeter of the rectangle is. Calculations are done with Doubles.
 * 
 * @author Frano
 * @version 1.0
 */
public class Rectangle {
	/**
	 * Calculate the perimeter of rectangle with given height and width.Formula used
	 * is 2*height+2*width
	 * 
	 * @param height of rectangle
	 * @param width  of rectangle
	 * @return The perimeter of the rectangle
	 */
	public static Double perimeter(Double height, Double width) {
		return 2 * (height + width);
	}

	/**
	 * Calculate the area of rectangle, namely height*width, where width and height
	 * are given to method.
	 * 
	 * @param height of rectangle
	 * @param width  of rectangle
	 * @return The area of the rectangle
	 */
	public static Double area(Double height, Double width) {
		return height * width;
	}

	/**
	 * Main method provides the user with the functionality of entering height and
	 * width through either standard input or command line arguments. The entered
	 * data is used to print out area and perimeter of described rectangle
	 * 
	 * @param args Optional command line arguments. If given, there must be two
	 *             number arguments greater than 0.
	 */
	public static void main(String[] args) {
		if (args.length > 2 || args.length == 1) {
			System.out.println("Unesen je neispravan broj argumenata. Broj argumenata je ili 2 ili 0");
			return;
		}

		Double width;
		Double height;

		if (args.length == 2) {
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch (NumberFormatException e) {
				System.out.println("Dani argumenti nisu brojevi.");
				return;
			}

			if (height < 0 || width < 0) {
				System.out.println("Visina i širina pravokutnika moraju biti pozitivni brojevi.");
				return;
			}
		} else {
			Scanner sc = new Scanner(System.in);
			width = enterDouble(sc, "Unesite širinu > ");
			height = enterDouble(sc, "Unesite visinu > ");
			sc.close();
		}

		Double perimeter = Rectangle.perimeter(height, width);
		Double area = Rectangle.area(height, width);

		System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.%n", width.toString(),
				height.toString(), area.toString(), perimeter.toString());
		return;
	}

	/**
	 * Just a help function for the main program, returns an valid entered positive
	 * Double.
	 * 
	 * @param sc      Scanner to be used to read input
	 * @param message Message to show when asking for input
	 * @return Returns a valid positive Double
	 */
	private static Double enterDouble(Scanner sc, String message) {
		while (true) {
			System.out.print(message);
			String s = sc.next();
			try {
				Double result = Double.parseDouble(s);
				if (result < 0.) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					continue;
				}
				if (result.equals(0.)) {
					System.out.println("Vrijednost ne smije biti nula.");
					continue;
				}
				return result;
			} catch (NumberFormatException e) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", s);
			}
		}
	}

}
