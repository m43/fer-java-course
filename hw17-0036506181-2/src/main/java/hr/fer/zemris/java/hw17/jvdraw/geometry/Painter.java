package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class offers static functionality of painting specific geometry objects onto
 * an given graphics2D object
 * 
 * @author Frano Rajič
 */
public class Painter {

	/**
	 * Draw a line onto given graphics 2d
	 * 
	 * @param g2d  graphics to draw onto
	 * @param line the line object to draw
	 */
	public static void paint(Graphics2D g2d, Line line) {
		Color color = line.getColor();
		g2d.setColor(color);
		g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
	}

	/**
	 * Draw a circle onto given graphics 2d
	 * 
	 * @param g2d    graphics to draw onto
	 * @param circle circle to draw
	 */
	public static void paint(Graphics2D g2d, Circle circle) {
		Point c = circle.getCenter();
		int r = circle.getRadius();

		Color color = circle.getBorderColor();
		g2d.setColor(color);
		g2d.drawOval(c.x - r, c.y - r, 2 * r, 2 * r);
	}

	/**
	 * Draw a filled circle onto given graphics 2d
	 * 
	 * @param g2d          graphics to draw onto
	 * @param filledCircle the filled circle to draw
	 */
	public static void paint(Graphics2D g2d, FilledCircle filledCircle) {
		Point c = filledCircle.getCenter();
		int r = filledCircle.getRadius();

		Color color = filledCircle.getFillColor();
		g2d.setColor(color);
		g2d.fillOval(c.x - r, c.y - r, 2 * r, 2 * r);

		color = filledCircle.getBorderColor();
		g2d.setColor(color);
		g2d.drawOval(c.x - r, c.y - r, 2 * r, 2 * r);
	}

}
