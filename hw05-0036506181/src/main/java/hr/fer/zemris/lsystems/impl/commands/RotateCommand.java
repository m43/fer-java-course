package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class implements the functionality of rotating the direction where the turtle
 * is looking by an angle. Rotation is done in the mathematically positive angle
 * direction.
 * 
 * @author Frano Rajič
 */
public class RotateCommand implements Command {

	/**
	 * Angle to rotate by
	 */
	double angle;

	/**
	 * Create a rotation command with given angle in degrees.
	 * 
	 * @param angle angle to rotate in degrees
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState();
		Vector2D direction = turtleState.getDirection();
		direction.rotate(angle * Math.PI / 180.);
		turtleState.setDirection(direction);
	}

}
