package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectVisitor;

/**
 * Class models an abstract geometric object. An geometric object must accept an
 * {@link GeometricalObjectVisitor}. Objects are also subjects of the observer
 * design pattern, where the observer is modeled by
 * {@link GeometricalObjectListener}.
 * 
 * @author Frano Rajič
 */
public abstract class GeometricalObject {

	/**
	 * List of all registered listeners of this object.
	 */
	List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Accept the given object visitor.
	 * 
	 * @param v the object visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Create an appropriate object editor panel of this geometric object.
	 * 
	 * @return the created editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Method to register a new observer of this object
	 * 
	 * @param l the observer modeled by {@link GeometricalObjectListener}
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if (!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	/**
	 * Method to remove the given observer
	 * 
	 * @param l the observer modeled by {@link GeometricalObjectListener}
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		if (listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.remove(l);
		}
	}
	
	/**
	 * Method to be called to notify all listeners about change of object.
	 */
	public void fireAll() {
		for(GeometricalObjectListener l: listeners) {
			l.geometricalObjectChanged(this);
		}
	}

}
