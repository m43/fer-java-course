package hr.fer.zemris.java.hw17.jvdraw.geometry.visitor;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Class is an visitor of {@link GeometricalObject} that knows how to convert
 * lines, circles and filled circles to text in vector like format. This is the
 * format used:
 * <ul>
 * <li>LINE x0 y0 x1 y1 red green blue</li>
 * <li>CIRCLE centerx centery radius red green blue</li>
 * <li>FCIRCLE centerx centery radius red green blue red green blue</li>
 * </ul>
 * So an valid text would be:
 * <ul>
 * <li>LINE 10 10 50 50 255 255 0</li>
 * <li>LINE 50 90 30 10 128 0 128</li>
 * <li>CIRCLE 40 40 18 0 0 255</li>
 * <li>FCIRCLE 40 40 18 0 0 255 255 0 0</li>
 * </ul>
 * 
 * @author Frano Rajič
 */
public class GeometricalObjectToText implements GeometricalObjectVisitor {

	/**
	 * String builder used to accumulate the generated text
	 */
	StringBuilder text = new StringBuilder();

	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();
		Color color = line.getColor();

		text.append(String.format("LINE %d %d %d %d %d %d %d\n", start.x, start.y, end.x, end.y, color.getRed(),
				color.getGreen(), color.getBlue()));
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();
		Color fgcolor = circle.getBorderColor();

		text.append(String.format("CIRCLE %d %d %d %d %d %d\n", center.x, center.y, radius, fgcolor.getRed(),
				fgcolor.getGreen(), fgcolor.getBlue()));
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();
		Color fgcolor = filledCircle.getBorderColor();
		Color bgcolor = filledCircle.getFillColor();

		text.append(String.format("FCIRCLE %d %d %d %d %d %d %d %d %d\n", center.x, center.y, radius,
				fgcolor.getRed(), fgcolor.getGreen(), fgcolor.getBlue(), bgcolor.getRed(), bgcolor.getGreen(),
				bgcolor.getBlue()));
	}

	/**
	 * Get the accumulated text that is the result of the visiting
	 * 
	 * @return text
	 */
	public String getText() {
		return text.toString();
	}

}
