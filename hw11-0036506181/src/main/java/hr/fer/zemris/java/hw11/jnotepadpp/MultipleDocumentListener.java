package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Class models an listener of {@link MultipleDocumentModel}.
 * 
 * @author Frano Rajič
 */
public interface MultipleDocumentListener {

	/**
	 * Method that gets called if the current document is changed.
	 * 
	 * @param previousModel the model before the change
	 * @param currentModel  the model after the change happened
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method that gets called when an new document gets added
	 * 
	 * @param model the model to add
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method that gets called if an document is removed.
	 * 
	 * @param model model to remove
	 */
	void documentRemoved(SingleDocumentModel model);
}