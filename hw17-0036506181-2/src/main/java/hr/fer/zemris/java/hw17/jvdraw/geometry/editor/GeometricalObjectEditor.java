package hr.fer.zemris.java.hw17.jvdraw.geometry.editor;

import javax.swing.JPanel;

/**
 * Class models an panel that enables the editing of geometric objects
 * 
 * @author Frano Rajič
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * serialization
	 */
	private static final long serialVersionUID = -5207695849143488059L;

	/**
	 * When checkEditing is called, the method checks if fields are correctly filled
	 * and if not, throws an exception.
	 */
	public abstract void checkEditing();

	/**
	 * When acceptEditing is called, the values from all fields must be written back
	 * into given geometric object.
	 */
	public abstract void acceptEditing();
}