package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements the functionality of a command that changes the drawing
 * color.
 * 
 * @author Frano Rajič
 */
public class ColorCommand implements Command {

	/**
	 * What color to draw with
	 */
	private Color color;

	/**
	 * Create a new command with given drawing color
	 * @param color drawing color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState();
		turtleState.setColor(color);
	}

}
