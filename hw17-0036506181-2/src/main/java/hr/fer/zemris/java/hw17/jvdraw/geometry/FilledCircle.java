package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectVisitor;

/**
 * Class models an filled circle that is being drawn with border of foreground
 * color and filled with background color.
 * 
 * @author Frano Rajič
 */
public class FilledCircle extends Circle {

	/**
	 * The fill color
	 */
	private Color fillColor;

	/**
	 * @return the fillColor
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * @param fillColor the fillColor to set
	 */
	public void setFillColor(Color fillColor) {
		fireAll();
		this.fillColor = fillColor;
	}

	/**
	 * Create a new filled circle with given center, radius, border and fill color.
	 * 
	 * @param center the center of the filled circle
	 * @param r      the radius of the filled circle
	 * @param border the background color of the filled circle
	 * @param fill   the color used to fill the circle with
	 * @throws NullPointerException     if given center or border or fill are null
	 * @throws IllegalArgumentException if given radius is not positive
	 */
	public FilledCircle(Point center, int r, Color border, Color fill) {
		super(center, r, border);
		fillColor = Objects.requireNonNull(fill);
	}

	/**
	 * Create a new filled circle with given center point, point at border, border
	 * and fill color.
	 * 
	 * @param center      the center of the circle
	 * @param borderPoint point on the border of the circle
	 * @param border      the color used to draw the border
	 * @param fill        the color that the circle will be filled with
	 * @throws NullPointerException     if given center or border or fill are null
	 * @throws IllegalArgumentException if calculated radius is not positive
	 */
	public FilledCircle(Point center, Point borderPoint, Color border, Color fill) {
		super(center, borderPoint, border);
		fillColor = fill;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toString() {
		String colorHEX = String.format("#%02x%02x%02x", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue())
				.toUpperCase();
		return String.format("Filled circle (%d,%d), %d, %s", getCenter().x, getCenter().y, getRadius(), colorHEX);
	}
}
