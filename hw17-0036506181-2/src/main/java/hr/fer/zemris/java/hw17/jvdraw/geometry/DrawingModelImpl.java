package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents an implementation of {@link DrawingModel}.
 * 
 * @author Frano Rajič
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * The list that stores geometric objects.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();

	/**
	 * The list that stores observers of this object.
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();

	/**
	 * Boolean telling whether the model has been modified
	 */
	private boolean modified = false;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		Objects.checkIndex(index, getSize());
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		if (objects.contains(object)) {
			throw new IllegalArgumentException("The given object is already inside the model");
		}

		objects.add(object);
		object.addGeometricalObjectListener(this);
		int index = objects.size();

		modified = true;
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index, index);
		}
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		if (index == -1) {
			throw new IllegalArgumentException("The given object is not in the model and hence cannot be removed");
		}

		objects.remove(index);
		object.removeGeometricalObjectListener(this);

		modified = true;
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index, index);
		}
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int currentIndex = objects.indexOf(object);
		if (currentIndex == -1) {
			throw new IllegalArgumentException("The object is not at all in model");
		}

		Objects.checkIndex(offset + currentIndex, objects.size());

		if (offset == 0) {
			return;
		}

		objects.remove(currentIndex);
		objects.add(currentIndex + offset, object);

		modified = true;
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, currentIndex, currentIndex + offset);
		}
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		int size = objects.size();
		objects.clear();

		modified = true;
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, 0, size);
		}
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		if (listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.remove(l);
		}
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = indexOf(o);
		if (index == -1) {
			throw new IllegalArgumentException("The given geometrical object is not in the objects at all.");
		}

		modified = true;
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, index, index);
		}
	}

}
