package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * {@link SingleDocumentModel} represents a model of single document, having
 * information about file path from which document was loaded (can be null for
 * new document), document modification status and reference to Swing component
 * which is used for editing (each document has its own editor component).
 * 
 * @author Frano Rajič
 */
public interface SingleDocumentModel {

	/**
	 * Get the editor component, the reference to the {@link JTextArea} Swing
	 * component that is used for editing.
	 * 
	 * @return the reference to the editor component
	 */
	JTextArea getTextComponent();

	/**
	 * Get the path to the file that this model is representing and editing.
	 * 
	 * @return the {@link Path} to the document
	 */
	Path getFilePath();

	/**
	 * Set the path to the file that this model is representing and editing.
	 * 
	 * @param path the {@link Path} to the document to edit
	 */
	void setFilePath(Path path);

	/**
	 * Has the file of this model been modified since opening.
	 * 
	 * @return true if the opened document has been modified
	 */
	boolean isModified();

	/**
	 * Set the modified flag of the opened document
	 * 
	 * @param modified the modified boolean to set
	 */
	void setModified(boolean modified);

	/**
	 * Add an given listener to this document model
	 * 
	 * @param l the {@link SingleDocumentListener} to add
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Remove the given listener from the listeners
	 * 
	 * @param l the {@link SingleDocumentListener} to add
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}