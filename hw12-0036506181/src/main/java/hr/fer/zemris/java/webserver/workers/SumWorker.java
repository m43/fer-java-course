package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models an worker by implementing {@link IWebWorker} and provides
 * the functionality of summing two parameters - a and b and showing an
 * appropriate image depending on the calculated result
 * 
 * @author Frano Rajič
 */
public class SumWorker implements IWebWorker {

	/**
	 * Name of the image that will be shown if the resulting sum is even
	 */
	private final static String IMAGE_PATH_EVEN = "fractal.jpg";

	/**
	 * Name of the image that will be shown if the resulting sum is odd
	 */
	private final static String IMAGE_PATH_ODD = "mars.png";

	/**
	 * Path to the script that will render the actual HTML using temporary
	 * parameters saved to context that were calculated here
	 */
	private final static String SCRIPT_PATH = "/private/pages/calc.smscr";

	@Override
	public void processRequest(RequestContext context) throws Exception {

		int a = (context.getParameter("a") == null ? 1 : Integer.valueOf(context.getParameter("a")));
		int b = (context.getParameter("b") == null ? 2 : Integer.valueOf(context.getParameter("b")));

		int result = a + b;

		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("result", String.valueOf(result));

		String imgName = result % 2 == 0 ? IMAGE_PATH_EVEN : IMAGE_PATH_ODD;
		context.setTemporaryParameter("imgName", imgName);

		context.getDispatcher().dispatchRequest(SCRIPT_PATH);
	}
}