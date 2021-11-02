package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.util.Objects;

import javax.swing.AbstractListModel;

/**
 * Implementation of a list model that handles {@link GeometricalObject} objects
 * by accessing information of {@link DrawingModel} provided in constructor.
 * This list model mimics an adaptor of the given drawing model as in object
 * adaptor design pattern. The list model also needs to get notified about
 * drawing model changes to fire listeners that have been registered to this
 * object, hence implements {@link DrawingModelListener}.
 * 
 * @author Frano Rajič
 */
public class DrawingObjectListModel extends AbstractListModel<String> implements DrawingModelListener {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = -7488475966320605033L;

	/**
	 * The drawing model that is used to gain information about the list of objects
	 */
	private DrawingModel drawingModel;

	/**
	 * Create a new {@link DrawingObjectListModel} that uses given
	 * {@link DrawingModel}.
	 * 
	 * @param dm the drawing model
	 */
	public DrawingObjectListModel(DrawingModel dm) {
		drawingModel = Objects.requireNonNull(dm);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public String getElementAt(int index) {
		return drawingModel.getObject(index).toString();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
