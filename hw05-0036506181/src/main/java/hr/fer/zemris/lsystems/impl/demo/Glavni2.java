package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class that illustrates drawing of a fractal
 * 
 * @author Frano Rajič
 */
public class Glavni2 {

	/**
	 * Main program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

	}

	// @formatter:off
	@SuppressWarnings("javadoc")
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "F+F--F+F")
		.setAxiom("F")
		.build();
		}
	// @formatter:on

}
