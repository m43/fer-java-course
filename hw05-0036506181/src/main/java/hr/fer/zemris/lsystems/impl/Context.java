package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class enables manipulating a stack of turtle states. The functionality
 * includes creating the stack, pushing, popping, and peeking the created stack
 * 
 * @author Frano Rajič
 */
public class Context {

	/**
	 * The stack used for the context
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Create a new context
	 */
	public Context() {
		this.stack = new ObjectStack<>();
	}

	/**
	 * Get the current turtle state of the context. It is the element on top of
	 * stack
	 * 
	 * @return the current turtle state of the context
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * Push the given turtle state on the top of stack
	 * 
	 * @param state State to push
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Delete the state on top of stack
	 */
	public void popState() {
		stack.pop();
	}
}
