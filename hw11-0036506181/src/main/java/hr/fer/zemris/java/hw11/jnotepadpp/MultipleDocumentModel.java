package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * {@link MultipleDocumentModel} represents a model capable of holding zero, one
 * or more documents, where each document and having a concept of current
 * document – the one which is shown to the user and on which user works.
 * 
 * @author Frano Rajič
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Create a new document and return respective {@link SingleDocumentModel}.
	 * 
	 * @return the created {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Return the currently active document
	 * 
	 * @return the current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Load the document with given path, create the respective
	 * {@link SingleDocumentModel} and add it to the documents stored in this model.
	 * 
	 * @param path the path of the document to load
	 * @return the created document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Save the document with specified model to given path
	 * 
	 * @param model   The model containing the document to save
	 * @param newPath the path where to save
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Close the document defined by given {@link SingleDocumentModel}.
	 * 
	 * @param model the model of the document to remove
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Add the given listener to registered listeners of this model.
	 * 
	 * @param l the listener to register
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Remove the given listener from listeners
	 * 
	 * @param l the listener to remove
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Get the number of documents in the model
	 * 
	 * @return the number of documents
	 */
	int getNumberOfDocuments();

	/**
	 * Get the document at given index.
	 * 
	 * @param index the index of the document to get
	 * @return the {@link SingleDocumentModel} object at given index
	 */
	SingleDocumentModel getDocument(int index);
}