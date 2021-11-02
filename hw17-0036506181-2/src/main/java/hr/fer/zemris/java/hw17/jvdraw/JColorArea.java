package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * {@link JComponent} that models an small 15x15 color component that also
 * provides the functionality of choosing new color of itself as well as
 * notifying interested {@link ColorChangeListener} about the color changes that
 * happen.
 * 
 * @author Frano Rajič
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = 8093200741964177167L;

	/**
	 * List of listeners of this object.
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * The selected color. Also the color that is being provided through
	 * {@link IColorProvider}.
	 */
	private Color selectedColor;

	/**
	 * Create a new {@link JColorArea} with given initial color.
	 * 
	 * @param initialColor the initial color of the component
	 */
	public JColorArea(Color initialColor) {
		selectedColor = Objects.requireNonNull(initialColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectNewColor();
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, 15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Help method to choose a new color as the selected color of this component.
	 * The new color is selected in a newly opened dialog.
	 */
	public void selectNewColor() {
		Color newColor = JColorChooser.showDialog(new JColorChooser(selectedColor), "Pick a color", selectedColor);

		if (newColor != null) {
			Color oldColor = selectedColor;
			selectedColor = newColor;
			fire(oldColor);
			repaint();
		}
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if (!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		if (listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.remove(l);
		}
	}

	/**
	 * Help method used to notify all listeners that the selected color has been
	 * changed.
	 * 
	 * @param old the old color, before the change occurred.
	 */
	private void fire(Color old) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, old, selectedColor);
		}
	}

}