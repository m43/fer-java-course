package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;

/**
 * Turtle state models the state of the drawing head (the turtle). Enables the
 * functionality of manipulating the position, direction, color and effective
 * move of the drawing pointer
 * 
 * @author Frano Rajič
 */
public class TurtleState {

	/**
	 * Current position of turtle.
	 */
	private Vector2D position;

	/**
	 * Current direction where the turtle is looking. It is a unit vector.
	 */
	private Vector2D direction; // unit vector

	/**
	 * Current color of turtle
	 */
	private Color color;

	/**
	 * The current effective move of the turtle
	 */
	private double effectiveMove;

	/**
	 * Create a turtle state with all needed information given as arguments.
	 * 
	 * @param position      the position of the turtle
	 * @param direction     the direction where the turtle is looking
	 * @param color         the color of the turtle
	 * @param effectiveMove the effective move
	 * @throws NullPointerException if any of the given references is a null pointer
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveMove) {
		Objects.requireNonNull(position);
		Objects.requireNonNull(direction);
		Objects.requireNonNull(color);

		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveMove = effectiveMove;
	}

	/**
	 * Get the position of the turtle
	 * 
	 * @return the position of the turtle
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Set the position of the turtle to given vector
	 * 
	 * @param position position to set
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Get the direction of the turtle
	 * 
	 * @return the direction of the turtle
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Set the direction of the turtle to given vector
	 * 
	 * @param direction position to set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Get the color of the turtle
	 * 
	 * @return the color of the turtle
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the color of the turtle
	 * 
	 * @param color the color of the turtle to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Get the effective move of the turtle
	 * 
	 * @return the effective move of the turtle
	 */
	public double getEffectiveMove() {
		return effectiveMove;
	}

	/**
	 * Set the effective move of the turtle
	 * 
	 * @param effectiveMove the effective move of the turtle to set
	 */
	public void setEffectiveMove(double effectiveMove) {
		this.effectiveMove = effectiveMove;
	}

	/**
	 * Create a copy this turtle
	 * 
	 * @return a copy of this turtle
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, effectiveMove);
	}

}
