package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class models an {@link ILocalizationProvider} by implementing
 * {@link AbstractLocalizationProvider}. This class is modeled according to
 * Singleton pattern, which restricts this class to only one instance.
 * 
 * @author Frano Rajič
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * The language of the translation
	 */
	private String language;

	/**
	 * The bundle used to get the translations
	 */
	private ResourceBundle bundle;

	/**
	 * The only instance of this class.
	 */
	private static LocalizationProvider singleInstance;

	/**
	 * The path to where the translations are stored.
	 */
	private final static String translationsPath = "hr.fer.zemris.java.hw11.jnotepadpp.local.translations";

	/**
	 * The default translation language. On first time creation of this object, this
	 * language is set.
	 */
	private static final String DEFAULT_TRANSLATION_LANGUAGE = "en";

	/**
	 * Create a new {@link LocalizationProvider}.
	 */
	private LocalizationProvider() {
		language = DEFAULT_TRANSLATION_LANGUAGE;
		bundle = ResourceBundle.getBundle(translationsPath, new Locale(language));
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Class returns an instance of this class. The returned instance is always the
	 * same in a runtime because of the singleton pattern.
	 * 
	 * @return the only instance of this class.
	 */
	public static LocalizationProvider getInstance() {
		if (singleInstance == null) {
			singleInstance = new LocalizationProvider();
		}
		return singleInstance;
	}

	/**
	 * Set an new language that is being provided. Notify all listeners if language
	 * changed.
	 * 
	 * @param language The language that should be provided.
	 */
	public void setLanguage(String language) {
		if (Objects.requireNonNull(language).equals(this.language)) {
			return;
		}
		this.language = language;
		bundle = ResourceBundle.getBundle(translationsPath, new Locale(language));
		fire();
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
