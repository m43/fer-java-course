package hr.fer.zemris.java.webserver;

/**
 * Interface models objects that can process a request with given context and
 * create some content for client.
 * 
 * @author Frano Rajič
 */
public interface IWebWorker {

	/**
	 * Method called to process a request with given context and create some content
	 * for client
	 * 
	 * @param context the context of the request
	 * @throws Exception thrown if anything goes wrong
	 */
	public void processRequest(RequestContext context) throws Exception;
}