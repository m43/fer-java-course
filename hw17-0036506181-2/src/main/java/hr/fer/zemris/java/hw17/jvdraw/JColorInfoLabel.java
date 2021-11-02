package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Component that shows information about the selected foreground and background
 * color. The information text could for example be like this: "Foreground
 * color: (255, 10, 210), background color: (128, 128, 0)."
 * 
 * @author Frano Rajič
 */
public class JColorInfoLabel extends JLabel implements ColorChangeListener {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = -8563611644395922908L;

	/**
	 * The foreground color provider.
	 */
	private IColorProvider fgColorProvider;

	/**
	 * The background color provider.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Create a new {@link JColorInfoLabel} with given foreground and background
	 * colors providers. This label is registered as an {@link ColorChangeListener}
	 * of both providers.
	 * 
	 * @param fgColorProvider the foreground {@link IColorProvider}
	 * @param bgColorProvider the background {@link IColorProvider}
	 */
	JColorInfoLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);

		updateText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Update the text of the component with the colors currently in
	 * {@link #fgColorProvider} and {@link #bgColorProvider}.
	 */
	private void updateText() {
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor();

		setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", fgColor.getRed(),
				fgColor.getGreen(), fgColor.getBlue(), bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
	}
}
