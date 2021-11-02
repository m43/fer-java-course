package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class models an abstract {@link AbstractAction} that provides the
 * functionality of automatically registering itself onto an given translation
 * provider and changing its properties according to localization settings
 * changes (aka language change). The action is connected to an given key, that
 * than gets translated.
 * 
 * @author Frano Rajič
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5307042184721514020L;

	/**
	 * Create a new {@link LocalizableAction} with given key and translation
	 * provider
	 * 
	 * @param key      the key that will used when translating
	 * @param provider the provider of the translation
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(provider);

		putValue(NAME, provider.getString(key));
		putValue(SHORT_DESCRIPTION, provider.getString(key + "_shortDescription"));

		provider.addLocalizationListener(() -> {
			putValue(NAME, provider.getString(key));
			putValue(SHORT_DESCRIPTION, provider.getString(key + "_shortDescription"));
		});
	}

	/**
	 * Create a new {@link LocalizableAction} with given key, translation provider,
	 * accelerator and mnemonic key.
	 * 
	 * @param key         the key that will used when translating
	 * @param provider    the provider of the translation
	 * @param keyStroke   the accelerator key for this action, null for none
	 * @param mnemonicKey the mnemonic key for this action
	 * @param enabled     is the action initially enabled or disabled
	 */
	public LocalizableAction(String key, ILocalizationProvider provider, KeyStroke keyStroke, int mnemonicKey,
			boolean enabled) {
		this(key, provider);

		Objects.requireNonNull(keyStroke);

		putValue(Action.ACCELERATOR_KEY, keyStroke);
		putValue(Action.MNEMONIC_KEY, mnemonicKey);
		setEnabled(enabled);
	}
}
