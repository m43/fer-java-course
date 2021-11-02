package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Painter;

/**
 * A tool that is used to draw lines. The line is started at a point where mouse
 * makes first click and ended where mouse clicks the second time. Meanwhile
 * while dragging the mouse pointer, a line preview is drawn simultaneously.
 * 
 * @author Frano Rajič
 */
public class LineTool extends AbstractTool {

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
	public LineTool(DrawingModel model, IColorProvider foreground, IColorProvider background, JDrawingCanvas canvas) {
		super(model, foreground, background, canvas);
	}

//	@Override
//	public void paint(Graphics2D g2d) {
//		if (!needsPainting()) {
//			return;
//		}
//
//		Line lineToPaint = new Line(getFirst(), getSecond(), getForegroundColorProvider().getCurrentColor());
//		Painter.paint(g2d, lineToPaint);
//	}
//
//	@Override
//	void createNewObject() {
//		Line newLine = new Line(getFirst(), getSecond(), getForegroundColorProvider().getCurrentColor());
//		getDrawingModel().add(newLine);
//	}

	@Override
	public void paint(Graphics2D g2d) {
		if (!firstClick || second == null) {
			return;
		}

		Line lineToPaint = new Line(first, second, getForegroundColorProvider().getCurrentColor());
		Painter.paint(g2d, lineToPaint);
	}

	@Override
	void createNewObject() {
		Line newLine = new Line(first, second, getForegroundColorProvider().getCurrentColor());
		
		firstClick = false;
		second = null;

		getDrawingModel().add(newLine);
	}

	boolean firstClick = false;
	Point first;
	Point second;

	@Override
	void clicked(Point p) {
		if(!firstClick) {
			firstClick = true;
			first = p;
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
