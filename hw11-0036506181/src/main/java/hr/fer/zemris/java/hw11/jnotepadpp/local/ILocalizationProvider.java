package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface models the functionality of getting localized translations.
 * 
 * @author Frano Rajič
 */
public interface ILocalizationProvider {

	/**
	 * Add the given localization listener to listeners of this subject.
	 * 
	 * @param l the listener to add
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Remove the given localization listener from listeners of this subject.
	 * 
	 * @param l the listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Method to return the current language of translation.F
	 * 
	 * @return The language of translation
	 */
	String getCurrentLanguage();

	/**
	 * Get the translation that is associated with given key
	 * 
	 * @param key the key of the translation
	 * @return the translation
	 */
	String getString(String key);

}
