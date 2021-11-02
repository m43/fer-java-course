package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Painter;

/**
 * Class models a tool that is used to draw circles that are filled with
 * background color. Drawn circles have a border as well that is of foreground
 * color. The class that models the geometric object is {@link FilledCircle}.
 * The circle center is where the mouse made the first click and the circle
 * spans till the border that is determined by second mouse click. Meanwhile
 * while dragging the mouse pointer, a circle preview is drawn simultaneously.
 * 
 * @author Frano Rajič
 */
public class FilledCircleTool extends AbstractTool {

	/**
	 * Create a new abstract drawing tool by giving references to model, canvas, and
	 * color providers
	 * 
	 * @param model      the model that stores information about all other objects
	 *                   already drawn on canvas
	 * @param foreground the foreground color provider
	 * @param background the background color provider
	 * @param canvas     the canvas to draw onto
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider foreground, IColorProvider background,
			JDrawingCanvas canvas) {
		super(model, foreground, background, canvas);
	}

//	@Override
//	public void paint(Graphics2D g2d) {
//		if (!needsPainting()) {
//			return;
//		}
//
//		FilledCircle filledCircle = new FilledCircle(getFirst(), getSecond(),
//				getForegroundColorProvider().getCurrentColor(), getBackgroundColorProvider().getCurrentColor());
//		Painter.paint(g2d, filledCircle);
//	}
//
//	@Override
//	void createNewObject() {
//
//		FilledCircle filledCircle = new FilledCircle(getFirst(), getSecond(),
//				getForegroundColorProvider().getCurrentColor(), getBackgroundColorProvider().getCurrentColor());
//		getDrawingModel().add(filledCircle);
//	}

	@Override
	public void paint(Graphics2D g2d) {
		if (!firstClick || second == null) {
			return;
		}

		FilledCircle circleToPaint = new FilledCircle(first, second, getForegroundColorProvider().getCurrentColor(),
				getBackgroundColorProvider().getCurrentColor());
		Painter.paint(g2d, circleToPaint);
	}

	@Override
	void createNewObject() {
		FilledCircle newCircle = new FilledCircle(first, second, getForegroundColorProvider().getCurrentColor(),
				getBackgroundColorProvider().getCurrentColor());
		firstClick = false;
		second = null;
		getDrawingModel().add(newCircle);
	}

	boolean firstClick = false;
	Point first;
	Point second;

	@Override
	void clicked(Point p) {
		if (!firstClick) {
			first = p;
			firstClick = true;
		} else {
			second = p;
			createNewObject();
		}
	}

	@Override
	void moved(Point p) {
		second = p;
		getDrawingCanvas().repaint();
	}

}
