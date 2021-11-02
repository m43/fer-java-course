package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models an worker by implementing {@link IWebWorker} and provides
 * the functionality of setting color given as parameter of request to a
 * persistent parameter
 * 
 * @author Frano Rajič
 */
public class BgColorWorker implements IWebWorker {

	/**
	 * The key in persistent map for background color
	 */
	private static final String PERSISTENT_BACKGROUND_KEY = "bgcolor";

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		boolean updated = false;

		if (color != null && (color.length() != 3 || color.length() != 6)) {
			try {
				context.setPersistentParameter(PERSISTENT_BACKGROUND_KEY, color);
				updated = true;
			} catch (NumberFormatException e) {
			}
		}

		context.write("<html><head><title>bgColor</title></head>"
				+ "<body style=\"background: linear-gradient(45deg, #02001F, #1F1B4E); text-align: center; margin: 300px\">"
				+ "<h1><a href=\"/index2.html\">COLOR " + (updated ? "" : "NOT ")
				+ "UPDATED<br>BACK TO HOME PAGE</a></h1></body></html>");
	}
}
