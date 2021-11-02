package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that models a command. A command can be executed.
 * 
 * @author Frano Rajič
 */
public interface Command {

	/**
	 * Execute the command with given context and painters
	 * 
	 * @param ctx     context
	 * @param painter painter to use for drawing
	 */
	void execute(Context ctx, Painter painter);

}
