package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This program draws newton-raphson fractals with given input as roots of the
 * newton-raphson specific polynomial.F
 * 
 * @author Frano Rajič
 */
public class Newton {

	/**
	 * Entry point for program
	 * 
	 * @param args Command line arguments - irrelevant
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new LinkedList<>();

		int rootNumber = 1;
		String nextLine;
		while (true) {
			try {
				System.out.print("Root " + rootNumber + "> ");
				nextLine = sc.nextLine();
				if ("done".equals(nextLine)) {
					if (rootNumber <= 2) {
						System.out.println("At least two roots need to be given!");
						continue;
					}
					break;
				}
				roots.add(Complex.parse(nextLine));
				rootNumber++;
			} catch (NumberFormatException e) {
				System.out.println("Invalid complex number given, enter again...");
			}
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();

		Complex[] rootsArray = new Complex[roots.size()];
		roots.toArray(rootsArray);
		ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE, rootsArray);

		FractalViewer.show(new NewtonProducer(polynomial));
	}

}
