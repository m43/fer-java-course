package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that implements the command of drawing. Drawing is moving of the
 * drawing head (implemented by {@link TurtleState}) in the direction where it
 * is pointing to for the value of the effective unit length, leaving a trail
 * with configured color. It also updates the position of the turtle.
 * 
 * @author Frano Rajič
 */
public class DrawCommand implements Command {

	/**
	 * How many steps to draw
	 */
	private int step;

	/**
	 * Construct the command with given steps of drawing
	 * 
	 * @param step steps to draw
	 */
	public DrawCommand(int step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState();

		Vector2D positionBefore = turtleState.getPosition();
		double x0 = positionBefore.getX();
		double y0 = positionBefore.getY();

		double move = step * turtleState.getEffectiveMove();
		Vector2D moveVector = turtleState.getDirection().scaled(move);
		Vector2D positionAfter = positionBefore.translated(moveVector);

		turtleState.setPosition(positionAfter);
		double x1 = positionAfter.getX();
		double y1 = positionAfter.getY();

		Color color = turtleState.getColor();
		painter.drawLine(x0, y0, x1, y1, color, 1f);
	}

}
