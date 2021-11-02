package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * An implementation of {@link MultipleDocumentModel} that is designed for use
 * in {@link JNotepadPP} application.
 * 
 * @author Frano Rajič
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Serialization.
	 */
	private static final long serialVersionUID = -6703389121479657316L;

	/**
	 * List of {@link SingleDocumentModel} that are in this model
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();

	/**
	 * An reference to the document that is currently shown in the tab
	 */
	private SingleDocumentModel currentDocument = null;

	/**
	 * List of listeners of this model as subject
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	/**
	 * The icon of an unmodified tab.
	 */
	private ImageIcon unmodifiedIcon;

	/**
	 * The path to the unmodified icon as string
	 */
	private String unmodifiedIconPathString = "icons/green.png";

	/**
	 * The icon of an tab that has been modified.
	 */
	private ImageIcon modifiedIcon = null;

	/**
	 * The path to the modified icon as string
	 */
	private String modifiedIconPathString = "icons/orange.png";

	/**
	 * This listener listens for modify changes on any of the
	 * {@link SingleDocumentModel} and than updates the icon of the respective tab.
	 */
	private final SingleDocumentListener modifiedListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = documents.indexOf(model);
			setIconAt(index, (model.isModified() ? modifiedIcon : unmodifiedIcon));
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = documents.indexOf(model);
			setTitleAt(index, model.getFilePath().getFileName().toString());
			setToolTipTextAt(index, model.getFilePath().toAbsolutePath().toString());
		}
	};

	/**
	 * Listener that tracks the change of the current tab and informs all the
	 * interested {@link MultipleDocumentListener} listeners
	 */
	private final ChangeListener currentTabListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			int index = ((JTabbedPane) e.getSource()).getSelectedIndex();

			SingleDocumentModel previousDocument = currentDocument;
			currentDocument = (index >= 0 ? documents.get(index) : null);

			listeners.forEach((x) -> x.currentDocumentChanged(previousDocument, currentDocument));
		}
	};

	/**
	 * Construct a new {@link DefaultMultipleDocumentModel}.
	 */
	public DefaultMultipleDocumentModel() {
		super();
		try (InputStream is = this.getClass().getResourceAsStream(unmodifiedIconPathString)) {
			if (is == null) {
				// not sure how to handle this. Is this exception appropriate
				throw new IllegalStateException("Could not load the modified icon...");
			}
			unmodifiedIcon = new ImageIcon(is.readAllBytes());
		} catch (IOException e) {
			// not sure how to handle this. Is this exception appropriate
			throw new IllegalStateException("Could not load the unmodified icon...");
		}

		try (InputStream is = this.getClass().getResourceAsStream(modifiedIconPathString)) {
			if (is == null) {
				throw new IllegalStateException("Could not load the modified icon...");
			}
			modifiedIcon = new ImageIcon(is.readAllBytes());
		} catch (IOException e) {
			throw new IllegalStateException("Could not load the unmodified icon...");
		}

		setFont(new Font("SansSerif", 0, 15));
		addChangeListener(currentTabListener);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");

		documents.add(newDocument);
		addTab(LocalizationProvider.getInstance().getString("unnamed"), modifiedIcon,
				new JScrollPane(newDocument.getTextComponent()),
				LocalizationProvider.getInstance().getString("unnamed"));
		newDocument.addSingleDocumentListener(modifiedListener);
		newDocument.setModified(true);

		setSelectedIndex(documents.size() - 1);

		listeners.forEach((x) -> x.documentAdded(newDocument));
		return newDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if given path is null
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		for (int i = 0, length = documents.size(); i < length; i++) {
			Path p = documents.get(i).getFilePath();

			if (p == null) {
				continue;
			}

			if (p.equals(path)) {
				setSelectedIndex(i);
				return documents.get(i);
			}
		}

		if (!Files.isReadable(path)) {
			JOptionPane.showOptionDialog(null, LocalizationProvider.getInstance().getString("error_opening_message"),
					LocalizationProvider.getInstance().getString("error_opening"), JOptionPane.ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE, null,
					new String[] { LocalizationProvider.getInstance().getString("ok") }, null);
			return null;
		}

		try {
			String text = Files.readString(path);
			SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, text);

			documents.add(newDocument);
			addTab(path.getFileName().toString(), unmodifiedIcon, new JScrollPane(newDocument.getTextComponent()),
					path.toAbsolutePath().toString());
			newDocument.addSingleDocumentListener(modifiedListener);
			newDocument.setModified(false);

			setSelectedIndex(documents.size() - 1);

			listeners.forEach((x) -> x.documentAdded(newDocument));
			return newDocument;
		} catch (IOException e) {
			JOptionPane.showOptionDialog(null,
					String.format(LocalizationProvider.getInstance().getString("error_loading_message"), path),
					LocalizationProvider.getInstance().getString("error_loading"), JOptionPane.ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE, null,
					new String[] { LocalizationProvider.getInstance().getString("ok") }, null);
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if given model is a null pointer
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Objects.requireNonNull(model);
		if (newPath == null) {
			newPath = model.getFilePath();
		} else {
			for (SingleDocumentModel doc : documents) {
				if (doc != model && newPath.equals(doc.getFilePath())) {
					JOptionPane.showOptionDialog(null,
							String.format(LocalizationProvider.getInstance().getString("error_opened_message"),
									newPath.toString()),
							LocalizationProvider.getInstance().getString("error_opened"), JOptionPane.ERROR_MESSAGE,
							JOptionPane.ERROR_MESSAGE, null,
							new String[] { LocalizationProvider.getInstance().getString("ok") }, null);
					return;
				}
			}
		}

		try {
			Files.writeString(newPath, model.getTextComponent().getText(), StandardCharsets.UTF_8,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			SingleDocumentModel oldModel = new DefaultSingleDocumentModel(model.getFilePath(),
					model.getTextComponent().getText());

			model.setFilePath(newPath);
			model.setModified(false);
			listeners.forEach((x) -> x.currentDocumentChanged(oldModel, model));
		} catch (IOException e) {
			JOptionPane.showOptionDialog(null,
					String.format(LocalizationProvider.getInstance().getString("save_unsuccessful_message"), newPath),
					LocalizationProvider.getInstance().getString("save_unsuccessful"), JOptionPane.ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE, null,
					new String[] { LocalizationProvider.getInstance().getString("ok") }, null);

		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IndexOutOfBoundsException if given model is not in stored in the
	 *                                   models
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		if (!documents.contains(model)) {
			throw new IllegalArgumentException("Given model is not in stored document models!");
		}

		int index = documents.indexOf(model);
		model.removeSingleDocumentListener(modifiedListener);
		documents.remove(index);
		removeTabAt(index);
		listeners.forEach((x) -> x.documentRemoved(model));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if given {@link MultipleDocumentListener} is a
	 *                              null reference.
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (!listeners.contains(Objects.requireNonNull(l))) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		if (listeners.contains(Objects.requireNonNull(l))) {
			listeners = new ArrayList<>(listeners);
			listeners.remove(l);
		}
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IndexOutOfBoundsException if the given index is invalid in regard to
	 *                                   number of elements inside the model.
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		Objects.checkIndex(index, documents.size());

		return documents.get(index);
	}

}
