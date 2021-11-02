package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Class models listeners of {@link SingleDocumentModel}
 * 
 * @author Frano Rajič
 */
public interface SingleDocumentListener {

	/**
	 * Method to call when the modify status of the model is changed.
	 * 
	 * @param model the model that is being listen to
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method to call when the file path of the document gets changed.
	 * 
	 * @param model the model that is being listen to
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}