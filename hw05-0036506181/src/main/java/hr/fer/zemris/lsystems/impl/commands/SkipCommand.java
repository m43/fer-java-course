package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class implements the skipping command. Skipping is like drawing, but only
 * without leaving a trail behind (does not draw).
 * 
 * @author Frano Rajič
 */
public class SkipCommand implements Command {

	/**
	 * Number of steps to skip
	 */
	private int step;

	/**
	 * Create a skip command with given steps to skip
	 * 
	 * @param step steps to skip
	 */
	public SkipCommand(int step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtleState = ctx.getCurrentState();

		Vector2D positionBefore = turtleState.getDirection();
		double move = step * turtleState.getEffectiveMove();
		Vector2D moveVector = turtleState.getDirection().scaled(move);
		Vector2D positionAfter = positionBefore.translated(moveVector);

		turtleState.setPosition(positionAfter);
	}

}
