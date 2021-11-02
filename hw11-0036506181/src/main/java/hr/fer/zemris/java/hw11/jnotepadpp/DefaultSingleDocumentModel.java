package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of {@link SingleDocumentModel} that is designed for use in
 * {@link JNotepadPP} application.
 * 
 * @author Frano Rajič
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * The text component that will act as an editor for the opened document
	 */
	private JTextArea textComponent;

	/**
	 * The path to the file that is being shown.
	 */
	private Path filePath;

	/**
	 * Storing if the document has been modified.
	 */
	private boolean modified = false;

	/**
	 * An set of listeners to this model.
	 */
	private Set<SingleDocumentListener> listeners = new HashSet<>();

	/**
	 * The {@link DocumentListener} that waits for the change of the document to
	 * update the {@link #modified} flag.
	 */
	private DocumentListener modifyListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			setModified(true);
//			System.out.println("MODu");
			e.getDocument().removeDocumentListener(this);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setModified(true);
//			System.out.println("MODi");
			e.getDocument().removeDocumentListener(this);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			setModified(true);
//			System.out.println("MODcucu");
			e.getDocument().removeDocumentListener(this);
		}
	};

	/**
	 * Create an {@link DefaultSingleDocumentModel} with given path to document and
	 * the text that is in that document.
	 * 
	 * @param path        The path to the document
	 * @param textContent The text that is in the document.
	 */
	public DefaultSingleDocumentModel(Path path, String textContent) {
		textComponent = new JTextArea(textContent);
		filePath = path;
		textComponent.getDocument().addDocumentListener(modifyListener);
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException the given path cannot be null
	 */
	@Override
	public void setFilePath(Path path) {
		if (Objects.requireNonNull(path).equals(filePath))
			return;

		this.filePath = path;

		listeners.forEach((x) -> x.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.modified == modified)
			return;
		if (modified == false) {
			textComponent.getDocument().addDocumentListener(modifyListener);
		}

		this.modified = modified;
		listeners.forEach((x) -> x.documentModifyStatusUpdated(this));
	}

	/**
	 * {@inheritDoc}}
	 * 
	 * @throws NullPointerException if given listener is a null reference.
	 */
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if (!listeners.contains(Objects.requireNonNull(l))) {
			listeners = new HashSet<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		if (listeners.contains(l)) {
			listeners = new HashSet<>(listeners);
			listeners.remove(l);
		}
	}

}
