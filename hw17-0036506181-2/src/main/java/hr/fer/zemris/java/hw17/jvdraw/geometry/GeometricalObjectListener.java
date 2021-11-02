package hr.fer.zemris.java.hw17.jvdraw.geometry;

/**
 * Class models an listener of geometric object changes. The listener is the
 * observer in the observer design pattern where {@link GeometricalObject} is
 * the subject.
 * 
 * @author Frano Rajič
 */
public interface GeometricalObjectListener {

	/**
	 * Method to be called when an change in t he geometric object occurs.
	 * 
	 * @param o the changed geometric object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}