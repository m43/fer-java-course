package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectVisitor;

/**
 * Class models an geometric object representing a line with a start and end
 * point and with a drawing color.
 * 
 * @author Frano Rajič
 */
public class Line extends GeometricalObject {

	/**
	 * Where is the start of the line
	 */
	private Point startPoint;

	/**
	 * Where is the end of the line
	 */
	private Point endPoint;

	/**
	 * The color of the drawn line
	 */
	private Color color;

	/**
	 * Create a line with given start and end points
	 * 
	 * @param startPoint the start point
	 * @param endPoint   the end point
	 * @param color      the color of the line
	 * @throws NullPointerException thrown if any reference is null
	 */
	public Line(Point startPoint, Point endPoint, Color color) {
		this.startPoint = Objects.requireNonNull(startPoint);
		this.endPoint = Objects.requireNonNull(endPoint);
		this.color = Objects.requireNonNull(color);
	}

	/**
	 * @return the startPoint
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * @param startPoint the startPoint to set
	 */
	public void setStartPoint(Point startPoint) {
		fireAll();
		this.startPoint = startPoint;
	}

	/**
	 * @return the endPoint
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(Point endPoint) {
		fireAll();
		this.endPoint = endPoint;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		fireAll();
		this.color = color;
	}

}
