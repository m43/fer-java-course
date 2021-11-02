package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models an worker by implementing {@link IWebWorker} and provides
 * the functionality generating the home page. It detects if the background
 * color is present in persistent parameters and copies the color to temporary
 * parameters or uses default color if none was found in persistent parameters.
 * The further work is delegated to a script that renders the HTML home page.
 * 
 * @author Frano Rajič
 */
public class Home implements IWebWorker {

	/**
	 * Path to the script that will render the actual HTML using temporary
	 * parameters saved to context that were calculated here
	 */
	private final static String HOME_SCRIPT_PATH = "./private/pages/home.smscr";

	/**
	 * The default color used if none was found in the persistent parameters map
	 */
	private static final String DEFAULT_COLOR = "34495e"; // instead of "7F7F7F"

	/**
	 * The key in persistent map for background color
	 */
	private static final String PERSISTENT_BACKGROUND_KEY = "bgcolor";

	/**
	 * The key in temporary map for background color
	 */
	private static final String TEMPORARY_BACKGROUND_KEY = "background";

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getPersistentParameter(PERSISTENT_BACKGROUND_KEY);
		context.setTemporaryParameter(TEMPORARY_BACKGROUND_KEY, (color == null ? DEFAULT_COLOR : color));

		// delegate rendering to respective script
		context.getDispatcher().dispatchRequest(HOME_SCRIPT_PATH);
	}

}
