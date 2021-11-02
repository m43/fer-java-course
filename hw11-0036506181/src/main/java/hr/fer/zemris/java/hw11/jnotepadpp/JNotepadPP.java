package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.UnaryOperator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.swing.JMenuItemWithFont;
import hr.fer.zemris.java.hw11.jnotepadpp.swing.JTimeLabel;

/**
 * This program is an simple text editing program. It supports basic operations
 * of creating an empty document, opening an existing document, saving the
 * document to system files, showing document statistic, doing basic text
 * editing actions of copying, cutting and pasting text. The program enables
 * work with multiple documents and does not support any special formatting.
 * 
 * @author Frano Rajič
 */
public class JNotepadPP extends JFrame {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = -5429287933358474261L;

	/**
	 * The default application language
	 */
	private static final String DEFAULT_LANGUAGE = "de";

	/**
	 * The main title of the application
	 */
	private final String TITLE = "JNotepadPP";

	/**
	 * The model used to track all opened documents. Instance of
	 * {@link MultipleDocumentModel}
	 */
	private MultipleDocumentModel model;

	/**
	 * Provider of language translation
	 */
	private FormLocalizationProvider flp;

	/**
	 * Array of supported languages
	 */
	private String[] languages = new String[] { "en", "hr", "de" };

	/**
	 * The {@link JPanel} used for the status bar.
	 */
	private JPanel statusBar;

	/**
	 * The label showing information about caret position. It is located in the
	 * middle of the status bar.
	 */
	private JLabel middle;

	/**
	 * The label telling the length of the currently viewed document. It is located
	 * on the left of the status bar.
	 */
	private JLabel length;

	/**
	 * Action that creates a new empty document
	 */
	private Action newDocument;

	/**
	 * Action that opens an existing document
	 */
	private Action openDocument;

	/**
	 * Action to save the current document to same location
	 */
	private Action saveDocument;

	/**
	 * Action to save the current document to a new location
	 */
	private Action saveDocumentAs;

	/**
	 * Action to show the information about the currently edited document.
	 * Information includes number of characters, number of non blank characters and
	 * number of lines.
	 */
	private Action showDocumentInformation;

	/**
	 * Action to close the current document. If document was edited, an dialog will
	 * ask the user to approve closing.
	 */
	private Action closeDocument;

	/**
	 * Action to exit the {@link JNotepadPP} application. User is asked to confirm
	 * closing if any unsaved documents are opened.
	 */
	private Action exit;

	/**
	 * Action to cut the selected text.
	 */
	private Action cut;

	/**
	 * Action to copy the selected text.
	 */
	private Action copy;

	/**
	 * Action to paste the content of the clipboard into the edited document.
	 */
	private Action paste;

	/**
	 * Action to change the case of the selected text to upper case
	 */
	private Action toUppercase;

	/**
	 * Action to change the case of the selected text to lower case
	 */
	private Action toLowercase;

	/**
	 * Action to invert the case of the selected text
	 */
	private Action invertCase;
	/**
	 * Action to sort the lines of the selected text in ascending order
	 */
	private Action sortAscending;

	/**
	 * Action to sort the lines of the selected text in descending order
	 */
	private Action sortDescending;

	/**
	 * Action to leave only unique lines of the selected lines of text.
	 */
	private Action unique;

	/**
	 * Listener that tracks what is happening with the caret of the current document
	 * in order to update the status bar information.
	 */
	private CaretListener caretListener = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			caretChanged();
		}
	};

	/**
	 * Listener tracks the changes of the current document in order to update total
	 * document length of status bar
	 */
	private DocumentListener documentListener = new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			contentChanged();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			contentChanged();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			contentChanged();
		}
	};

	/**
	 * This listener listens for modify changes of the currently edited document
	 * {@link SingleDocumentModel} and sets the Save button to enabled and disabled
	 * as necessary
	 */
	private final SingleDocumentListener currentListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			saveDocument.setEnabled(model.isModified());
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
		}
	};

	/**
	 * Create a new instance of {@link JNotepadPP}.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(600, (int) (600 * 14.5 / 13));
		initGUI();

		setLocationRelativeTo(null);
	}

	/**
	 * Initialize the graphical user interface by adding all necessary menu options,
	 * tool bar and all supported actions.
	 */
	private void initGUI() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(() -> {
			if (model.getCurrentDocument() != null) {
				contentChanged();
			}
		});
		Container cp = getContentPane();
		// set up the window
		cp.setLayout(new BorderLayout());
		setTitle("JNotepad++");

		// listen to window changes
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onClosing();
			}
		});

		// create the tabbed pane and create its respective listener
		DefaultMultipleDocumentModel tabbedPane = new DefaultMultipleDocumentModel();
		model = tabbedPane;
		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				setTitle((currentModel == null ? flp.getString("unnamed")
						: (currentModel.getFilePath() == null ? flp.getString("unnamed")
								: currentModel.getFilePath().toAbsolutePath().toString()))
						+ " - " + TITLE);
				saveDocument.setEnabled(currentModel == null ? false
						: (currentModel.getFilePath() == null ? false : currentModel.isModified()));
				closeDocument.setEnabled(currentModel != null);
				saveDocumentAs.setEnabled(currentModel != null);
				copy.setEnabled(currentModel != null);
				cut.setEnabled(currentModel != null);
				paste.setEnabled(currentModel != null);
				showDocumentInformation.setEnabled(currentModel != null);

				if (previousModel != null) {
					previousModel.removeSingleDocumentListener(currentListener);
					previousModel.getTextComponent().removeCaretListener(caretListener);
					previousModel.getTextComponent().getDocument().removeDocumentListener(documentListener);
				}
				if (currentModel != null) {
					currentModel.addSingleDocumentListener(currentListener);
					currentModel.getTextComponent().addCaretListener(caretListener);
					currentModel.getTextComponent().getDocument().addDocumentListener(documentListener);
					contentChanged();
					caretChanged();
				} else {
					length.setText("");
					middle.setText("");
				}
			}
		});
		add(tabbedPane, BorderLayout.CENTER);

		// finish GUI initialization
		createActions();
		createMenus();
		createToolbar();
		createStatusBar();

		LocalizationProvider.getInstance().setLanguage(DEFAULT_LANGUAGE);
	}

	/**
	 * Help method to create and configure all the needed actions.
	 */
	private void createActions() {

		newDocument = new LocalizableAction("new", flp, KeyStroke.getKeyStroke("control N"), KeyEvent.VK_N, true) {
			private static final long serialVersionUID = -2796634880921047137L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};

		openDocument = new LocalizableAction("open", flp, KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, true) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = -3330931000709851504L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Open file to edit");
				jfc.addChoosableFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
				if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				model.loadDocument(jfc.getSelectedFile().toPath());
			}
		};

		saveDocument = new LocalizableAction("save", flp, KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 6366520240770468350L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().getFilePath() == null) {
					saveDocumentAs.actionPerformed(e);
				} else {
					model.saveDocument(model.getCurrentDocument(), null);
				}
			}
		};

		saveDocumentAs = new LocalizableAction("saveas", flp, KeyStroke.getKeyStroke("control shift S"), KeyEvent.VK_A,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = -7990560770170460604L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.setDialogTitle("Select saving location");
				if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				try {
					Path path = jfc.getSelectedFile().toPath();
					if (Files.exists(path)) {
						int dialogResult = JOptionPane.showOptionDialog(null,
								flp.getString("overwrite") + " \"" + path.toString() + "\" ?",
								flp.getString("confirm_overwrite"), JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, null,
								new String[] { flp.getString("yes"), flp.getString("no") }, null);
						if (dialogResult == JOptionPane.NO_OPTION) {
							return;
						}
					}
					model.saveDocument(model.getCurrentDocument(), path);
				} catch (InvalidPathException ex) {
					JOptionPane.showOptionDialog(null, flp.getString("invalid_path_message"),
							flp.getString("invalid_path"), JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
							new String[] { flp.getString("ok") }, null);
				}
			}
		};

		showDocumentInformation = new LocalizableAction("info", flp, KeyStroke.getKeyStroke("control P"), KeyEvent.VK_I,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 70178771341626582L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = model.getCurrentDocument().getTextComponent().getText();
				int totalChars = text.length();
				int totalNonBlank = text.replaceAll("\\s+", "").length();
				int totalLines = model.getCurrentDocument().getTextComponent().getLineCount();

				JOptionPane.showOptionDialog(null,
						String.format(flp.getString("info_message"), totalChars, totalNonBlank, totalLines),
						flp.getString("info"), JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null,
						new String[] { flp.getString("ok") }, null);
			}
		};

		closeDocument = new LocalizableAction("close", flp, KeyStroke.getKeyStroke("control W"), KeyEvent.VK_C, false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 7628783364274402725L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().isModified() == false) {
					model.closeDocument(model.getCurrentDocument());
					return;
				}

				int dialogResult = JOptionPane.showOptionDialog(null, flp.getString("close_message"),
						flp.getString("close"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
						new String[] { flp.getString("yes"), flp.getString("no") }, null);
				if (dialogResult == JOptionPane.NO_OPTION) {
					return;
				}

				model.closeDocument(model.getCurrentDocument());
			}
		};

		exit = new LocalizableAction("exit", flp, KeyStroke.getKeyStroke("control Q"), KeyEvent.VK_X, true) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 1623178307979843751L;

			@Override
			public void actionPerformed(ActionEvent e) {
				onClosing();
			}
		};

		cut = new LocalizableAction("cut", flp, KeyStroke.getKeyStroke("control X"), KeyEvent.VK_T, false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 1928901350688265998L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.getCurrentDocument().getTextComponent().cut();
			}
		};

		copy = new LocalizableAction("copy", flp, KeyStroke.getKeyStroke("control C"), KeyEvent.VK_C, false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 6919022162383895114L;

			@Override
			public void actionPerformed(ActionEvent e) {
				copySelected();
			}
		};

		paste = new LocalizableAction("paste", flp, KeyStroke.getKeyStroke("control V"), KeyEvent.VK_P, false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 8561091307169087482L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.getCurrentDocument().getTextComponent().paste();
			}
		};

		toUppercase = new LocalizableAction("toUppercase", flp, KeyStroke.getKeyStroke("control 1"), KeyEvent.VK_1,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = -6489276609958382939L;

			@Override
			public void actionPerformed(ActionEvent e) {
				editSelectedText(Character::toUpperCase);
			}
		};

		toLowercase = new LocalizableAction("toLowercase", flp, KeyStroke.getKeyStroke("control 2"), KeyEvent.VK_2,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 5568479492837973531L;

			@Override
			public void actionPerformed(ActionEvent e) {
				editSelectedText(Character::toLowerCase);

			}
		};

		invertCase = new LocalizableAction("invertCase", flp, KeyStroke.getKeyStroke("control 3"), KeyEvent.VK_3,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = -6911397698919512525L;

			@Override
			public void actionPerformed(ActionEvent e) {
				editSelectedText(x -> Character.isLetter(x)
						? (Character.isUpperCase(x) ? Character.toLowerCase(x) : Character.toUpperCase(x))
						: x);
			}

		};

		sortAscending = new LocalizableAction("ascending", flp, KeyStroke.getKeyStroke("control 4"), KeyEvent.VK_4,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = -5598165548165510133L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Locale locale = new Locale(flp.getCurrentLanguage());
				Collator collator = Collator.getInstance(locale);
				sortSelectedLines((x, y) -> collator.compare(x, y));
			}
		};

		sortDescending = new LocalizableAction("descending", flp, KeyStroke.getKeyStroke("control 5"), KeyEvent.VK_5,
				false) {
			/**
			 * Serialization
			 */
			private static final long serialVersionUID = -1247282962968730875L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Locale locale = new Locale(flp.getCurrentLanguage());
				Collator collator = Collator.getInstance(locale);
				sortSelectedLines((x, y) -> collator.compare(y, x));
			}
		};

		unique = new LocalizableAction("unique", flp, KeyStroke.getKeyStroke("control 6"), KeyEvent.VK_6, false) {

			/**
			 * Serialization
			 */
			private static final long serialVersionUID = 3625532375254563021L;

			@Override
			public void actionPerformed(ActionEvent e) {
				leaveUniqueLines();
			}
		};
	}

	/**
	 * Help method to create and add the menu bar.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		mb.setFont(new Font("SansSerif", 0, 15));

		JMenu file = new LJMenu("file", flp);
		file.setMnemonic('F');
		file.add(new JMenuItemWithFont(newDocument));
		file.add(new JMenuItemWithFont(openDocument));
		file.add(new JMenuItemWithFont(saveDocument));
		file.add(new JMenuItemWithFont(saveDocumentAs));
		file.addSeparator();
		file.add(new JMenuItemWithFont(showDocumentInformation));
		file.addSeparator();
		file.add(new JMenuItemWithFont(closeDocument));
		file.add(new JMenuItemWithFont(exit));
		mb.add(file);

		JMenu edit = new LJMenu("edit", flp);
		edit.setMnemonic('E');
		edit.add(new JMenuItemWithFont(copy));
		edit.add(new JMenuItemWithFont(cut));
		edit.add(new JMenuItemWithFont(paste));
		mb.add(edit);

		JMenu language = new LJMenu("lang", flp);
		language.setMnemonic('L');

		Action[] languageActions = new Action[languages.length];

		for (int i = 0, len = languages.length; i < len; i++) {
			final int ii = i;
			languageActions[i] = new AbstractAction(languages[i]) {
				/**
				 * Serialization
				 */
				private static final long serialVersionUID = -6043681070369494211L;

				@Override
				public void actionPerformed(ActionEvent e) {
					LocalizationProvider.getInstance().setLanguage(languages[ii]);
				}
			};

			language.add(languageActions[i]);
		}

		mb.add(language);

		JMenu tools = new LJMenu("tools", flp);
		tools.setMnemonic('T');
		JMenu changeCase = new LJMenu("changeCase", flp);
		changeCase.setMnemonic('C');
		changeCase.add(new JMenuItemWithFont(toUppercase));
		changeCase.add(new JMenuItemWithFont(toLowercase));
		changeCase.add(new JMenuItemWithFont(invertCase));
		tools.add(changeCase);

		JMenu sort = new LJMenu("sort", flp);
		sort.add(new JMenuItemWithFont(sortAscending));
		sort.add(new JMenuItemWithFont(sortDescending));
		sort.add(new JMenuItemWithFont(unique));
		tools.add(sort);

		mb.add(tools);

		for (Component child : mb.getComponents()) {
			child.setFont(new Font("SansSerif", 0, 15));
		}
		setJMenuBar(mb);
	}

	/**
	 * Help method to create and add the tool bar.
	 */
	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(newDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveDocumentAs));
		tb.addSeparator();
		tb.add(new JButton(showDocumentInformation));
		tb.addSeparator();
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(exit));
		tb.addSeparator();
		tb.addSeparator();
		tb.add(new JButton(cut));
		tb.add(new JButton(copy));
		tb.add(new JButton(paste));

		for (Component child : tb.getComponents()) {
			child.setFont(new Font("SansSerif", 0, 12));
		}

		getContentPane().add(tb, BorderLayout.PAGE_START);
	}

	/**
	 * Help method to create and add the status bar
	 */
	private void createStatusBar() {
		statusBar = new JPanel(new GridLayout(1, 3));
		statusBar.setMaximumSize(new Dimension(0, 120));

		// add document length label
		length = new JLabel();
		statusBar.add(length);

		// add middle label telling the caret position
		middle = new JLabel();
		middle.setHorizontalAlignment(SwingConstants.CENTER);
		statusBar.add(middle);

		// add the time label
		JLabel time = new JTimeLabel();
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		statusBar.add(time);

		for (Component child : statusBar.getComponents()) {
			child.setFont(new Font("SansSerif", 0, 13));
		}
		add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * This help method checks if there is unsaved work in any of the opened
	 * documents (tabs) and if there is some then asks the user to confirm closing
	 * without saving.
	 */
	private void onClosing() {
		boolean anythingModified = false;
		for (SingleDocumentModel s : model) {
			if (s.isModified()) {
				anythingModified = true;
				break;
			}
		}

		if (anythingModified) {
			int dialogResult = JOptionPane.showOptionDialog(null, flp.getString("exit_message"), flp.getString("exit"),
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
					new String[] { flp.getString("yes"), flp.getString("no") }, null);
			if (dialogResult == JOptionPane.NO_OPTION) {
				return;
			}
		}
		dispose();
	}

	/**
	 * Help method to copy the selected text of the current document.
	 */
	private void copySelected() {
		if (model.getCurrentDocument() == null
				|| model.getCurrentDocument().getTextComponent().getSelectedText() == null) {
			return;
		}

		JTextComponent doc = model.getCurrentDocument().getTextComponent();

		int selectionStart = doc.getSelectionStart();
		int selectionEnd = doc.getSelectionEnd();

		model.getCurrentDocument().getTextComponent().copy();
		doc.select(selectionStart, selectionEnd);
	}

	/**
	 * Help method to call when the caret of the current document changes. The
	 * method updates necessary status bar information
	 */
	private void caretChanged() {
		JTextComponent textArea = model.getCurrentDocument().getTextComponent();

		int selection = textArea.getSelectionEnd() - textArea.getSelectionStart();
		int caret = textArea.getCaretPosition();
		int line = 0, column = 0;

		Element root = textArea.getDocument().getDefaultRootElement();
		line = root.getElementIndex(caret) + 1;
		column = caret - root.getElement(line - 1).getStartOffset() + 1;

		middle.setText(String.format("Ln:%d Col:%d Sel:%d ", line, column, selection));

		copy.setEnabled(textArea.getSelectedText() != null);
		cut.setEnabled(textArea.getSelectedText() != null);
		toUppercase.setEnabled(textArea.getSelectedText() != null);
		toLowercase.setEnabled(textArea.getSelectedText() != null);
		invertCase.setEnabled(textArea.getSelectedText() != null);
		sortAscending.setEnabled(textArea.getSelectedText() != null);
		sortDescending.setEnabled(textArea.getSelectedText() != null);
		unique.setEnabled(textArea.getSelectedText() != null);
	}

	/**
	 * Help method to be called when the content of the current document changes in
	 * order to update the current length.
	 */
	private void contentChanged() {
		length.setText(flp.getString("length") + ": "
				+ model.getCurrentDocument().getTextComponent().getDocument().getLength());
	}

	/**
	 * Operation to edit the selected text of current document using given operator
	 * 
	 * @param operator the operator to apply on every character od selected text
	 */
	private void editSelectedText(UnaryOperator<Character> operator) {
		if (model.getCurrentDocument() == null
				|| model.getCurrentDocument().getTextComponent().getSelectedText() == null) {
			return;
		}

		JTextComponent doc = model.getCurrentDocument().getTextComponent();

		int selectionStart = doc.getSelectionStart();
		int selectionEnd = doc.getSelectionEnd();

		doc.replaceSelection(doc.getSelectedText().chars().map(x -> operator.apply((char) x))
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
		doc.select(selectionStart, selectionEnd);

	}

	/**
	 * Take all the selected lines and sort them in the given order. A line is
	 * considered to be selected if any part of it is selected.
	 * 
	 * @param comparator Comparator of strings used when sorting lines
	 */
	private void sortSelectedLines(Comparator<String> comparator) {
		if (model.getCurrentDocument() == null
				|| model.getCurrentDocument().getTextComponent().getSelectedText() == null) {
			return;
		}

		List<String> lines = getSelectedLines();
		lines.sort(comparator);

		replaceSelectedLines(lines);
	}

	/**
	 * Help method to leave only unique lines from selected lines
	 */
	private void leaveUniqueLines() {
		if (model.getCurrentDocument() == null
				|| model.getCurrentDocument().getTextComponent().getSelectedText() == null) {
			return;
		}

		Set<String> lines = new LinkedHashSet<>(getSelectedLines());
		replaceSelectedLines(new LinkedList<>(lines));
	}

	/**
	 * Return a list of currently selected lines
	 * 
	 * @return a list of selected lines in order as appearing in document
	 */
	private List<String> getSelectedLines() {
		try {
			JTextComponent textComponent = model.getCurrentDocument().getTextComponent();
			Document doc = textComponent.getDocument();
			Element root = doc.getDefaultRootElement();

			int startLine = root.getElementIndex(textComponent.getSelectionStart());
			int endLine = root.getElementIndex(textComponent.getSelectionEnd());

			List<String> lines = new ArrayList<>();
			for (int i = startLine; i <= endLine; i++) {
				int len = root.getElement(i).getEndOffset() - root.getElement(i).getStartOffset();
				lines.add(doc.getText(root.getElement(i).getStartOffset(), len));
			}

			return lines;
		} catch (BadLocationException ignoreable) {
			return null;
		}
	}

	/**
	 * Replace the selected lines with given lines list
	 * 
	 * @param lines lines to replace the selected ones with
	 */
	private void replaceSelectedLines(List<String> lines) {
		try {
			JTextComponent textComponent = model.getCurrentDocument().getTextComponent();
			Document doc = textComponent.getDocument();
			Element root = doc.getDefaultRootElement();

			int startLine = root.getElementIndex(textComponent.getSelectionStart());
			int endLine = root.getElementIndex(textComponent.getSelectionEnd());

			int replaceFrom = root.getElement(startLine).getStartOffset();
			int replaceTill = root.getElement(endLine).getEndOffset();
			if (replaceTill == doc.getLength() + 1) {
				replaceTill--;
			}

			boolean endingWithNewLine = "\n".equals(doc.getText(replaceTill - 1, 1));
			String newText = lines.stream().reduce("", (x, y) -> x + y);
			if (!endingWithNewLine) {
				newText = newText.substring(0, newText.length() - 1);
			}

			doc.remove(replaceFrom, replaceTill - replaceFrom);
			doc.insertString(replaceFrom, newText, null);

			textComponent.select(replaceFrom, endingWithNewLine ? replaceTill - 1 : replaceTill);
		} catch (BadLocationException ignoreable) {
			System.out.println(ignoreable.getMessage());
		}
	}

	/**
	 * Entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}