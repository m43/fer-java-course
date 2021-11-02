package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;


/**
 * Class implements the command with functionality of pushing the current state
 * of the turtle onto the stack
 * 
 * @author Frano Rajič
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		// stanje s vrha stoga kopira i kopiju stavlja na stog
		TurtleState newTurtleState = ctx.getCurrentState().copy();
		ctx.pushState(newTurtleState);
	}

}
