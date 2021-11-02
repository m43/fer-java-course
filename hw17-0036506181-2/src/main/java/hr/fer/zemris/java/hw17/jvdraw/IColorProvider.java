package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * Interface models objects that can provide colors and notify listeners about .
 * 
 * @author Frano Rajič
 */
public interface IColorProvider {

	/**
	 * @return the currently selected color
	 */
	public Color getCurrentColor();

	/**
	 * Add the given {@link ColorChangeListener} to color change listeners
	 * 
	 * @param l the {@link ColorChangeListener} to add
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Remove the given {@link ColorChangeListener} from color change listeners
	 * 
	 * @param l the {@link ColorChangeListener} to remove
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}