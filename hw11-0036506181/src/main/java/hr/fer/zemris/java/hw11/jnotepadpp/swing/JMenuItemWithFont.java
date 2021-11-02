package hr.fer.zemris.java.hw11.jnotepadpp.swing;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.JMenuItem;

/**
 * Class models an {@link JMenuItem} that has already font applied. 
 * 
 * @author Frano Rajič
 */
public class JMenuItemWithFont extends JMenuItem {
	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 695352736497190391L;

	/**
	 * Create a new {@link JMenuItemWithFont} with given action
	 * 
	 * @param a the action of the menu item
	 */
	public JMenuItemWithFont(Action a) {
		super(a);
		setFont(new Font("SansSerif", 0, 12));
	}
}