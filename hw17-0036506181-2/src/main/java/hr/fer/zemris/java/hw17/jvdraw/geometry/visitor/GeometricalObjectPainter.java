package hr.fer.zemris.java.hw17.jvdraw.geometry.visitor;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Painter;

/**
 * Class is an implementation of {@link GeometricalObjectVisitor} which can
 * paint geometric objects to a given {@link Graphics2D}.
 * 
 * @author Frano Rajič
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * The graphics object to paint the visited object onto.
	 */
	private Graphics2D g2d;

	/**
	 * Create a new geometric object painter visitor with given graphics
	 * 
	 * @param g2d the graphics 2d object
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		Painter.paint(g2d, line);
	}

	@Override
	public void visit(Circle circle) {
		Painter.paint(g2d, circle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Painter.paint(g2d, filledCircle);
	}

}
