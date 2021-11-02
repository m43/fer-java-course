package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModel;

/**
 * Abstract drawing class that implements {@link Tool} and adds the code for
 * constructing an abstract tool by storing the following information:<br>
 * <ul>
 * <li>model that stores information about all other objects already drawn on
 * canvas, modeled by {@link DrawingModel}
 * <li>foreground color provider modeled by{@link IColorProvider}
 * <li>background color provider modeled by{@link IColorProvider}
 * <li>canvas to draw onto modeled by {@link JDrawingCanvas}
 * <ul>
 * 
 * @author Frano Rajič
 */
public abstract class AbstractTool implements Tool {

	/**
	 * model that stores information about all other objects already drawn on canvas
	 */
	private DrawingModel drawingModel;

	/**
	 * foreground color provider
	 */
	private IColorProvider foregroundColorProvider;

	/**
	 * background color provider
	 */
	private IColorProvider backgroundColorProvider;

	/**
	 * canvas to draw onto
	 */
	private JDrawingCanvas drawingCanvas;

//	/**
//	 * Has the first click been made
//	 */
//	private boolean firstClick;
//
//	/**
//	 * Point where the mouse was clicked at
//	 */
//	private Point firstClickLocation;
//
//	/**
//	 * Is the button pressed down
//	 */
//	private boolean pressed;
//
//	/**
//	 * Point where the mouse was pressed at
//	 */
//	private Point pressLocation;
//
//	/**
//	 * Point where the mouse mouse is while it is currently dragged, moved or where
//	 * the second click was made
//	 */
//	private Point second;

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
	public AbstractTool(DrawingModel model, IColorProvider foreground, IColorProvider background,
			JDrawingCanvas canvas) {
		this.drawingModel = model;
		this.foregroundColorProvider = foreground;
		this.backgroundColorProvider = background;
		this.drawingCanvas = canvas;
	}

	/**
	 * @return the drawingModel
	 */
	public DrawingModel getDrawingModel() {
		return drawingModel;
	}

	/**
	 * @return the foregroundColorProvider
	 */
	public IColorProvider getForegroundColorProvider() {
		return foregroundColorProvider;
	}

	/**
	 * @return the backgroundColorProvider
	 */
	public IColorProvider getBackgroundColorProvider() {
		return backgroundColorProvider;
	}

	/**
	 * @return the drawingCanvas
	 */
	public JDrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}

//	@Override
//	public void mousePressed(MouseEvent e) {
//		pressed = true;
//		pressLocation = e.getPoint();
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//		if (!pressed)
//			return;
//
//		if (pressLocation.equals(e.getPoint())) {
//			pressed = false;
//			return;
//		}
//
//		if (firstClick) {
//			firstClick = false;
//		}
//
//		second = e.getPoint();
//		createNewObject();
//		pressed = false;
//	}
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		if (!firstClick) {
//			firstClick = true;
//			firstClickLocation = e.getPoint();
//		} else {
//			if (!pressed && !firstClickLocation.equals(e.getPoint())) {
//				second = e.getPoint();
//				createNewObject();
//			}
//			firstClick = false;
//		}
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		if (firstClick) {
//			second = e.getPoint();
//			getDrawingCanvas().repaint();
//		}
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		second = e.getPoint();
//		getDrawingCanvas().repaint();
//	}

	/**
	 * Help method to create an new object.
	 */
	abstract void createNewObject();

//	/**
//	 * @return the end point
//	 */
//	public Point getSecond() {
//		return second;
//	}
//
//	/**
//	 * Check whether painting is needed.
//	 * 
//	 * @return true if painting is needed
//	 */
//	public boolean needsPainting() {
//		return (pressed || firstClick) && (!getFirst().equals(second));
//	}
//
//	/**
//	 * @return get start point
//	 */
//	public Point getFirst() {
//		if (pressed) {
//			return pressLocation;
//		} else if (firstClick) {
//			return firstClickLocation;
//		}
//
//		return null;
//	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		moved(e.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		moved(e.getPoint());
	}

	/**
	 * Help method that gets called when a new click happends. The point where the
	 * mouse clicked is given.
	 * 
	 * @param p point where the mouse clicked
	 */
	abstract void clicked(Point p);

	/**
	 * Help method that gets called when the mouse gets moved or dragged. The point
	 * where the mouse moved is given.
	 * 
	 * @param p point where the mouse is pointing now
	 */
	abstract void moved(Point p);

}
