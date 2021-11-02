package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

/**
 * Interface models listeners of {@link IColorProvider} that get notified when
 * the provided color gets changed. This listener gets to know which object the
 * source is, as well as which color was selected before.
 * 
 * @author Frano Rajič
 */
public interface ColorChangeListener {

	/**
	 * A new color was selected by the {@link IColorProvider} object. Besides
	 * getting to know the new color the oldColor is also passed to the method.
	 * 
	 * @param source   the source object that implements {@link IColorProvider}
	 * @param oldColor the old color
	 * @param newColor the newly selected color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}