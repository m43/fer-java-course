package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * An bridge to an decorated {@link ILocalizationProvider}. It enables
 * registering listeners to the wrapped {@link ILocalizationProvider} by
 * registering to itself and then notifying all listeners when necessary.
 * 
 * @author Frano Rajič
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Reference to connected provider.
	 */
	private ILocalizationProvider provider;

	/**
	 * Boolean telling whether any provider is connected to this bridge.
	 */
	private boolean connected;

	/**
	 * The current language of translation. It is cached here.
	 */
	private String language;

	/**
	 * The listener of the decorated {@link ILocalizationProvider} wrapped object.
	 */
	private ILocalizationListener listener = () -> {
		language = provider.getCurrentLanguage();
		fire();
	};

	/**
	 * Create a new {@link LocalizationProviderBridge} with given instance of
	 * {@link ILocalizationProvider}. The given instance will be wrapped and will be
	 * listened to in order to notify its own listeners for the language change.
	 * 
	 * @param provider an given instance of an provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	@Override
	public String getString(String key) {
		if (!connected)
			throw new IllegalStateException("Need to connect bridge first.");
		return provider.getString(key);
	}

	/**
	 * Register this object to the wrapped {@link ILocalizationProvider}.
	 */
	public void connect() {
		provider.addLocalizationListener(listener);
		language = provider.getCurrentLanguage();
		connected = true;
	}

	/**
	 * Unregister this object to the wrapped {@link ILocalizationProvider}.
	 */
	public void disconnect() {
		provider.removeLocalizationListener(listener);
		connected = false;
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
