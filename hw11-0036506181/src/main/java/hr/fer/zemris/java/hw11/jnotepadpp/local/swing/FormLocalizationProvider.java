package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProviderBridge;

/**
 * Class adds to the functionality of {@link LocalizationProviderBridge} by
 * adding window listening to frame and connecting to provider when created as
 * well as disconnecting when frame window gets disposed.
 * 
 * @author Frano Rajič
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Create an new instance of {@link FormLocalizationProvider} with given
	 * provider and frame
	 * 
	 * @param provider the provider
	 * @param frame    the frame
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		connect();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
