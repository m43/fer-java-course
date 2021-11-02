package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.Font;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class models an {@link JLabel} that automatically translates information
 * related to given key using given translation provider. The automation is in
 * regard of registering itself as an observer of the given provider upon
 * construction and implementing the change of necessary parts of the label when
 * language changes.
 * 
 * @author Frano Rajič
 */
public class LJLabel extends JLabel {
	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5446110150140655033L;

	/**
	 * The key associated with this label.
	 */
	private String key;

	/**
	 * The translation provider
	 */
	private ILocalizationProvider provider;

	/**
	 * Construct a new {@link LJLabel} with given key and provider.
	 * 
	 * @param key      the key
	 * @param provider the translation provider
	 */
	public LJLabel(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		setText(provider.getString(key));
		setFont(new Font("SansSerif", 0, 12));

		provider.addLocalizationListener(() -> {
			updateLabel();
		});
	}

	/**
	 * Help method used to update the label
	 */
	private void updateLabel() {
		setText(provider.getString(key));
	};
}
