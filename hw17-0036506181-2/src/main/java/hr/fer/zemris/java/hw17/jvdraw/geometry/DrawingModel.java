package hr.fer.zemris.java.hw17.jvdraw.geometry;

/**
 * Interface models an drawing model that stores {@link GeometricalObject}
 * objects in an ordered manner, provides the functionality of manipulating the
 * model by adding, removing, reordering geometric objects as well as looking
 * for an geometric object in the model.<br>
 * The drawing model has the responsibility to track drawing modification
 * status. When any geometric object is added or removed from the model, or is
 * changed (or all objects are removed from the model via clear method),
 * modification flag is be set to true. The only way to reset this flag back to
 * false is via clearModificationFlag method.
 * 
 * @author Frano Rajič
 */
public interface DrawingModel extends GeometricalObjectListener {

	/**
	 * @return the number of geometric objects stored in the drawing model
	 */
	public int getSize();

	// TODO Je li mi ovdje ispod treba u javadocu interfacea pisati da baca
	// exception? Definicija metode svakako ne natjera klase koje će implementirati
	// da moraju baciti exception
	/**
	 * Get the geometric object at given index or throw an appropriate exception
	 * 
	 * @param index the index of the object to get
	 * @return the object at given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Add the given geometric object to the model
	 * 
	 * @param object the geometric object to add
	 */
	public void add(GeometricalObject object);

	/**
	 * Remove the given object from the model
	 * 
	 * @param object The geometric object to remove
	 */
	public void remove(GeometricalObject object);

	/**
	 * Change order in the model by moving the geometric object by given offset
	 * 
	 * @param object the geometric object to offset
	 * @param offset the offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Get the index of the given geometric object
	 * 
	 * @param object the geometric object
	 * @return the index of the queried object
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Clear the model by removing all the geometric objects it is made of
	 */
	public void clear();

	/**
	 * Clear the modification flag by setting it to false
	 */
	public void clearModifiedFlag();

	/**
	 * Check if the model has been modified
	 * 
	 * @return true if modified
	 */
	public boolean isModified();

	/**
	 * Add the given listener to the observer list of this drawing model
	 * 
	 * @param l the listener to add
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Remove the given listener from the observers of this object
	 * 
	 * @param l the listener to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
}