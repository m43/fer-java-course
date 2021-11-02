package hr.fer.zemris.java.hw17.jvdraw.geometry.visitor;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Interface that uses the visitor design pattern in order to visit objects of
 * type {@link GeometricalObject}. The visitor knows how to visit {@link Line},
 * {@link Circle} and {@link FilledCircle} objects in particular.
 * 
 * @author Frano Rajič
 */
public interface GeometricalObjectVisitor {

	/**
	 * Visit the given {@link Line} geometric object.
	 * 
	 * @param line the line
	 */
	public abstract void visit(Line line);

	/**
	 * Visit the given {@link Line} geometric object.
	 * 
	 * @param circle the circle
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visit the given {@link FilledCircle} geometric object.
	 * 
	 * @param filledCircle the filled circle
	 */
	public abstract void visit(FilledCircle filledCircle);
}