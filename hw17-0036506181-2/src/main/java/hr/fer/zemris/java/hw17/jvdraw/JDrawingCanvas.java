package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Class models an canvas to draw onto. It paints objects that are in the
 * {@link DrawingModel} that is given in the constructor. The objects are
 * painted one after the other, from index 0 to n-1. After painting all objects
 * from model, the painting function of a tool is called. The tool is accessed
 * through an {@link Supplier} of tools given in constructor. Canvas also needs
 * to listen to changes done by mouse and therefore implements the interfaces
 * {@link MouseListener} and {@link MouseMotionListener}. The mouse events are
 * only passed further to the current tool. In order to know when the associated
 * {@link DrawingModel} changed, canvas implements {@link DrawingModelListener}
 * interface and repaints the canvas on each change of the model - in particular
 * on object removal, addition and reordering.
 * 
 * @author Frano Rajič
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener, MouseListener, MouseMotionListener {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = -3688031066760778307L;

	/**
	 * The supplier of tools
	 */
	private Supplier<Tool> toolSupplier;

	/**
	 * The drawing model containing all the geometric objects on the canvas.
	 */
	private DrawingModel drawingModel;

	/**
	 * Create a new canvas with given supplier of current tool and the drawing model
	 * 
	 * @param toolSupplier the tool supplier
	 * @param drawingModel the drawing model
	 */
	public JDrawingCanvas(Supplier<Tool> toolSupplier, DrawingModel drawingModel) {
		this.toolSupplier = Objects.requireNonNull(toolSupplier);
		this.drawingModel = Objects.requireNonNull(drawingModel);
		addMouseListener(this);
		addMouseMotionListener(this);

	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tool tool = toolSupplier.get();
		if (tool != null) {
			tool.mouseClicked(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Tool tool = toolSupplier.get();
		if (tool != null) {
			tool.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Tool tool = toolSupplier.get();
		if (tool != null) {
			tool.mouseReleased(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Tool tool = toolSupplier.get();
		if (tool != null) {
			tool.mouseDragged(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Tool tool = toolSupplier.get();
		if (tool != null) {
			tool.mouseMoved(e);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		for (int i = 0; i < drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(painter);
		}

		Tool tool = toolSupplier.get();
		if (tool != null) {
			tool.paint(g2d);
		}
	}

}
