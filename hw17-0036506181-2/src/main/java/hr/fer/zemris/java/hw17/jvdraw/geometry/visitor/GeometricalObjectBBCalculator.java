package hr.fer.zemris.java.hw17.jvdraw.geometry.visitor;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Class implements the {@link GeometricalObjectVisitor} and models an visitor
 * that calculates the bounding box for a complete image to fit in.
 * 
 * @author Frano Rajič
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * The maximal x coordinate value of the visited geometric objects
	 */
	private int xMax;

	/**
	 * The minimal x coordinate value of the visited geometric objects
	 */
	private int xMin;

	/**
	 * The maximal y coordinate value of the visited geometric objects
	 */
	private int yMax;

	/**
	 * The minimal y coordinate value of the visited geometric objects
	 */
	private int yMin;

	/**
	 * Is this the first point. If yes, use it as starting min and max values of x
	 * and y
	 */
	private boolean firstPoint = true;

	@Override
	public void visit(Line line) {
		updateBounds(line.getStartPoint());
		updateBounds(line.getEndPoint());
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();

		updateBounds(new Point(center.x - radius, center.y - radius));
		updateBounds(new Point(center.x + radius, center.y + radius));
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	/**
	 * Help method to update the bounds of the bounding box that is being
	 * calculated. Check if given points changes the bounds and if it does, update
	 * the boundaries.
	 * 
	 * @param p the point
	 */
	private void updateBounds(Point p) {
		// NOTE: it would be much easier to just use Rectengle.add(point), but i'll
		// leave it like this
		if (firstPoint) {
			firstPoint = false;
			xMin = p.x;
			xMax = p.x;
			yMin = p.y;
			yMax = p.y;
			return;
		}

		xMin = (xMin < p.x ? xMin : p.x);
		xMax = (xMax > p.x ? xMax : p.x);
		yMax = (yMax > p.y ? yMax : p.y);
		yMin = (yMin < p.y ? yMin : p.y);
	}

	/**
	 * Return an rectangle of the dimensions to fit in all the geometric objects
	 * that the visitor has visited.
	 * 
	 * @return an rectangle that is of the same dimensions as the respective
	 *         bounding box of visited geometric objects.
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
	}
}
