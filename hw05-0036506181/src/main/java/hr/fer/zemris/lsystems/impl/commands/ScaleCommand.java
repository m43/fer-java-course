package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements the functionality of a command that changes the
 * effective unit length by multiplying with factor.
 * 
 * @author Frano Rajič
 */
public class ScaleCommand implements Command {

	/**
	 * The factor to scale by
	 */
	private double factor;

	/**
	 * Create the command with given factor
	 * 
	 * @param factor factor of command
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState();
		double newEffectiveMove = factor * turtleState.getEffectiveMove();
		turtleState.setEffectiveMove(newEffectiveMove);
	}

}
