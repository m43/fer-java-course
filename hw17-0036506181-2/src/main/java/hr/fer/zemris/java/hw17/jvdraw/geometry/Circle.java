package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectVisitor;

/**
 * Class models an geometric object representing circles. Circles have an center
 * point and a radius. This circles when drawn should not be filled and are
 * expected to only contain an outer border.
 * 
 * @author Frano Rajič
 */
public class Circle extends GeometricalObject {

	/**
	 * The point that represents the center of the circle
	 */
	private Point center;

	/**
	 * The radius of the circle
	 */
	private int radius;

	/**
	 * The color used when drawing border
	 */
	private Color borderColor;

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Point center) {
		fireAll();
		this.center = center;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		fireAll();
		this.radius = radius;
	}

	/**
	 * @return the borderColor
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(Color borderColor) {
		fireAll();
		this.borderColor = borderColor;
	}

	/**
	 * Create an circle with given center, radius and color to be used for border
	 * 
	 * @param center      the center of the circle
	 * @param r           the radius of the circle
	 * @param borderColor the color used to draw the border
	 * @throws NullPointerException     if given center or border are null
	 * @throws IllegalArgumentException if given radius is not positive
	 */
	public Circle(Point center, int r, Color borderColor) {
		Objects.requireNonNull(center);
		Objects.requireNonNull(borderColor);
		if (r <= 0) {
			throw new IllegalArgumentException("Radius must be a positive integer");
		}

		this.center = center;
		radius = r;
		this.borderColor = borderColor;
	}

	/**
	 * Create an circle with given center point and any point that is located on its
	 * border.
	 * 
	 * @param center      the center of the circle
	 * @param border      point on the border of the circle
	 * @param borderColor the color used to draw the border
	 * @throws NullPointerException     if any given argument is null
	 * @throws IllegalArgumentException if calculated radius is not a positive
	 *                                  integer
	 */
	public Circle(Point center, Point border, Color borderColor) {
		Objects.requireNonNull(center);
		Objects.requireNonNull(border);
		Objects.requireNonNull(borderColor);

		int r = (int) center.distance(border);
		if (r <= 0) {
			throw new IllegalArgumentException("Radius must be a positive integer");
		}

		this.center = center;
		radius = r;
		this.borderColor = borderColor;

	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", center.x, center.y, radius);
	}

}
