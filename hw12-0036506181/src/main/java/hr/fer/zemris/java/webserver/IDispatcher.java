package hr.fer.zemris.java.webserver;

/**
 * Interface models classes capable of dispatching an given URL path
 * 
 * @author Frano Rajič
 */
public interface IDispatcher {

	/**
	 * Method called to dispatch the given request defined with given urlPath
	 * 
	 * @param urlPath the URL path
	 * @throws Exception thrown if any error occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
}