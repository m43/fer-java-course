package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class that illustrates the drawing of fractal by selecting the configuration
 * from a file
 * 
 * @author Frano Rajič
 */
public class Glavni3 {

	/**
	 * Main program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
