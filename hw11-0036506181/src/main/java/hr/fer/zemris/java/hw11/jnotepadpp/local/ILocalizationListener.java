package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class models an listener of {@link ILocalizationProvider}.
 * 
 * @author Frano Rajič
 */
public interface ILocalizationListener {

	/**
	 * Method to be called by subject when localization settings change.
	 */
	void localizationChanged();

}
