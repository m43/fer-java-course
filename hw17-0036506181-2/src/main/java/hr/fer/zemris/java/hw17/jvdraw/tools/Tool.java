package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Class models an state that can draw on a given {@link Graphics2D} object. The
 * drawing can be done as the mouse gets moved as well because mouse events are
 * also tracked by the state. The "state design pattern" is used.
 * 
 * @author Frano Rajič
 */
public interface Tool {

	/**
	 * Method to be called when mouse is pressed
	 * 
	 * @param e the mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Method to be called when mouse is released
	 * 
	 * @param e the mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Method to be called when mouse is clicked
	 * 
	 * @param e the mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Method to be called when mouse is moved
	 * 
	 * @param e the mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Method to be called when mouse is dragged
	 * 
	 * @param e the mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Method to call to use the tool on the given graphics object
	 * 
	 * @param g2d the current graphics
	 */
	public void paint(Graphics2D g2d);

}