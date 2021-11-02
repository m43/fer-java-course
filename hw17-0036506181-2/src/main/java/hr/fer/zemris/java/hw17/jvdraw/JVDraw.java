package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.geometry.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.GemetricalObjectInvalidEditException;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectToText;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitor.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * JVDraw is an swing GUI application created by extending {@link JFrame} that
 * provides functionality of drawing basic geometric objects - lines and
 * circles, manipulating and editing the drawn objects (changing coordinates,
 * color and order of drawing, deleting drawn objects), exporting drawn canvas
 * to image format (either JPG, PNG or GIF), saving all drawn objects on canvas
 * to a special format JVD (stands for Java vector drawing) and later opening
 * created JVD files.
 * 
 * @author Frano Rajič
 */
public class JVDraw extends JFrame {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 6108567709464148143L;

	/**
	 * Action used to open an existing JVD vector file.
	 */
	private Action open;

	/**
	 * Action used to save the opened JVD vector file or ask to where to save to if
	 * unsaved.
	 */
	private Action save;

	/**
	 * Action used to save the opened JVD vector as a new file
	 */
	private Action saveas;

	/**
	 * Action used to export what is drawn on canvas to a picture, with supported
	 * formats being ".png", ".jpg" and ".gif"
	 */
	private Action export;

	/**
	 * Action called to exit the program. Action asks to save nonempty canvas that
	 * is unsaved.
	 */
	private Action exit;

	/**
	 * The color area that provides the foreground color
	 */
	private JColorArea fgColorArea;

	/**
	 * The color area that provides the background color
	 */
	private JColorArea bgColorArea;

	/**
	 * The currently used tool
	 */
	private Tool currentTool;

	/**
	 * The action that picks the {@link LineTool} as the current tool
	 */
	private Action lineAction;

	/**
	 * Action that picks the {@link CircleTool} as the current tool
	 */
	private Action circleAction;

	/**
	 * Action that picks the {@link FilledCircleTool} as the current drawing tool
	 */
	private Action filledCircleAction;

	/**
	 * The drawing model
	 */
	private DrawingModel drawingModel;

	/**
	 * The canvas where the drawing is created.
	 */
	private JDrawingCanvas canvas;

	/**
	 * The model of the list that shows object information
	 */
	private DrawingObjectListModel listModel;

	/**
	 * The path to the currently opened and edited vector image file. Null means it
	 * has not been saved yet.
	 */
	Path currentFilePath;

	/**
	 * Create a new instance of {@link JVDraw}.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit.actionPerformed(null);
			}
		});
		setSize(600, (int) (600 * 14.5 / 13));
		initGUI();

		setLocationRelativeTo(null);
	}

	/**
	 * Initialize the frame
	 */
	public void initGUI() {
		createActions();
		createMenu();
		createToolbarAndStatusBar();
		createDrawingArea();
		createObjectList();
		lineAction.actionPerformed(null);
	}

	/**
	 * Help method to create all necessary actions.
	 */
	private void createActions() {

		open = new AbstractAction("Open") {
			private static final long serialVersionUID = 2971104351970300016L;

			@Override
			public void actionPerformed(ActionEvent e) {
				openJVD();
			}
		};
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		save = new AbstractAction("Save") {
			private static final long serialVersionUID = 2971104351970300016L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentFilePath == null) {
					saveAs();
					return;
				}

				try {
					saveToFile(currentFilePath);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Could not save to " + currentFilePath.toAbsolutePath());
				}

				drawingModel.clearModifiedFlag();
			}
		};
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveas = new AbstractAction("Save as") {
			private static final long serialVersionUID = 2971104351970300016L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		};
		saveas.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveas.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		export = new AbstractAction("Export image") {
			private static final long serialVersionUID = 2971104351970300016L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exportImage();
			}
		};
		export.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		export.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		exit = new AbstractAction("Exit") {
			private static final long serialVersionUID = 2971104351970300016L;

			@Override
			public void actionPerformed(ActionEvent e) {
				onClosing();
			}
		};
		exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

		lineAction = new AbstractAction("line") {
			private static final long serialVersionUID = 2971104351970300016L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = new LineTool(drawingModel, fgColorArea, bgColorArea, canvas);
			}
		};
		lineAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		lineAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		circleAction = new AbstractAction("circle") {
			private static final long serialVersionUID = 3337194991809750490L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = new CircleTool(drawingModel, fgColorArea, bgColorArea, canvas);
			}
		};
		circleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control c"));
		circleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		filledCircleAction = new AbstractAction("filled circle") {
			private static final long serialVersionUID = 7075974649147567625L;

			@Override
			public void actionPerformed(ActionEvent e) {
				currentTool = new FilledCircleTool(drawingModel, fgColorArea, bgColorArea, canvas);
			}
		};
		filledCircleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control f"));
		filledCircleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
	}

	/**
	 * Help method used to create the menu bar of the frame.
	 */
	private void createMenu() {
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveas);
		fileMenu.add(export);
		// fileMenu.addSeparator();
		fileMenu.add(exit);

		menu.add(fileMenu);

		setJMenuBar(menu);
	}

	/**
	 * Help method used to create the tool bar as well as the status bar. Tool bar
	 * contains foreground and background color area choosers and tools for drawing
	 * on the canvas (selecting line tool, or circle with and without fill tool).
	 */
	private void createToolbarAndStatusBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);

		fgColorArea = new JColorArea(Color.BLACK);
		bgColorArea = new JColorArea(Color.WHITE);

		JPanel colorChooserPanel = new JPanel();
		colorChooserPanel.add(fgColorArea);
		colorChooserPanel.add(bgColorArea);

		toolbar.add(colorChooserPanel);

		JToggleButton line = new JToggleButton(lineAction);
		JToggleButton circle = new JToggleButton(circleAction);
		JToggleButton filledCircle = new JToggleButton(filledCircleAction);

		ButtonGroup bg = new ButtonGroup();
		bg.add(line);
		bg.add(circle);
		bg.add(filledCircle);

		line.setSelected(true);

		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCircle);

		getContentPane().add(toolbar, BorderLayout.PAGE_START);

		JColorInfoLabel bottomColorInfo = new JColorInfoLabel(fgColorArea, bgColorArea);
		getContentPane().add(bottomColorInfo, BorderLayout.PAGE_END);
	}

	/**
	 * Help method used to create the drawing area.
	 */
	private void createDrawingArea() {

		drawingModel = new DrawingModelImpl();

		canvas = new JDrawingCanvas(() -> currentTool, drawingModel);
		drawingModel.addDrawingModelListener(canvas);

		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	/**
	 * Help method to create the
	 */
	private void createObjectList() {
		listModel = new DrawingObjectListModel(drawingModel);
		drawingModel.addDrawingModelListener(listModel);

		JList<String> list = new JList<>(listModel);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				@SuppressWarnings("unchecked")
				JList<String> list = (JList<String>) evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					if (index != -1) {
						listItemDoubleClicked(drawingModel.getObject(index));
					}
				}
			}
		});
		list.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (!list.isSelectionEmpty()) {
					try {
						int selected = list.getSelectedIndex();

						if (e.getKeyCode() == KeyEvent.VK_DELETE) {
							drawingModel.remove(drawingModel.getObject(selected));
						}
						if ('+' == e.getKeyChar()) {
							drawingModel.changeOrder(drawingModel.getObject(selected), -1);
							list.setSelectedIndex(selected - 1);
						}
						if ('-' == e.getKeyChar()) {
							drawingModel.changeOrder(drawingModel.getObject(selected), 1);
							list.setSelectedIndex(selected + 1);
						}

					} catch (IndexOutOfBoundsException ignoreable) {
					}

				}

			}
		});

		list.setFont(new Font("serif", Font.PLAIN, 12));

		JScrollPane scrollPane = new JScrollPane(list);
		Dimension scrollDimensions = scrollPane.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(scrollDimensions.width * 2 / 3, scrollDimensions.height));

		getContentPane().add(scrollPane, BorderLayout.LINE_END);

	}

	/**
	 * Help method that handles the event of double clicking an object in the list
	 * of geometric objects. What it does is start an editor dialogue that offers to
	 * edit the geometric object that has been double clicked. The editing of the
	 * object is canceled by closing the dialogue, or the clicked object is editing
	 * information is saved. If invalid information is entered while editing, the
	 * dialogue will be opened again.
	 * 
	 * @param clicked the clicked geometric object
	 */
	protected void listItemDoubleClicked(GeometricalObject clicked) {
		GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();

		boolean editCompleted = false;
		while (!editCompleted) {
			editCompleted = true;

			if (JOptionPane.showConfirmDialog(null, editor, "Edit object",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				try {
					editor.checkEditing();
					editor.acceptEditing();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Editing object failed",
							JOptionPane.ERROR_MESSAGE);
					if (ex instanceof GemetricalObjectInvalidEditException) {
						editCompleted = false; // try to edit again because the error was invalid information given
												// about geometric object
					}
				}
			}
		}

	}

	/**
	 * Help method to export graphics in drawing model to an image of type JPG, PNG
	 * or GIF. The file needs to be selected from user and a {@link JFileChooser} is
	 * for that purpose
	 */
	private void exportImage() {
		JFileChooser jf = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("image files {jpg, png, gif}", "jpg", "png",
				"gif");
		jf.setFileFilter(filter);

		if (jf.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		try {
			File file = jf.getSelectedFile();

			int extensionDot = file.getName().lastIndexOf('.');
			String extension = file.getName().substring(extensionDot + 1);
			if (extensionDot <= 0 || !("jpg".equals(extension) || "png".equals(extension) || "gif".equals(extension))) {
				JOptionPane.showMessageDialog(null, "The extension must be either jpg, png or gif!");
				return;
			}

			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			visit(drawingModel, bbcalc);
			Rectangle box = bbcalc.getBoundingBox();

			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.translate(-box.x, -box.y);

			g.setPaint(Color.WHITE);
			g.fillRect(box.x, box.y, box.width, box.height);

			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			visit(drawingModel, painter);
			g.dispose();

			ImageIO.write(image, extension, file);
			JOptionPane.showMessageDialog(null,
					extension.toUpperCase() + " image exported succesfully to " + file.getAbsolutePath());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "An error occured");
		}
	}

	/**
	 * This help method checks firsts checks if there are unsaved changes, and if
	 * any asks user whether he wants to save the changes, quit without saving or
	 * cancel the closing of the application.
	 */
	private void onClosing() {

		String[] choices = { "Save", "Don't Save", "Cancel" };
		while (drawingModel.isModified()) {

			int result = JOptionPane.showOptionDialog(this, "You have unsaved changes. Save before quitting?",
					"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices,
					choices[0]);

			switch (result) {
			case 0:
				save.actionPerformed(null);
				break;
			case 1:
				dispose();
				return;
			case 2:
				return;
			}
		}
		dispose();
	}

	/**
	 * Load the drawing model with an opened JVD file.
	 */
	protected void openJVD() {
		if (drawingModel.isModified()) {
			int dialogResult = JOptionPane.showOptionDialog(null, "Open file before saving the current drawing?",
					"Confirm not saving changes", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null,
					null);
			if (dialogResult != JOptionPane.YES_OPTION) {
				return;
			}
		}

		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files only", "jvd");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("Select saving location");

		if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		try {
			Path selectedPath = jfc.getSelectedFile().toPath();

			if (!checkExtension(selectedPath, "jvd")) {
				JOptionPane.showMessageDialog(null, "The extension must be JVD!");
				return;
			}

			String text = Files.readString(selectedPath);
			drawingModel.clear();
			laodDrawingModel(drawingModel, text);
			drawingModel.clearModifiedFlag();

			currentFilePath = selectedPath;

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not load selected file!");
		}
	}

	/**
	 * Help method used to check if extension of given path is among given
	 * extensions
	 * 
	 * @param path       the path to check
	 * @param extensions the extensions that are accepted
	 * @return true if path extension is among the extensions
	 */
	private boolean checkExtension(Path path, String... extensions) {
		int extensionDot = Objects.requireNonNull(path).getFileName().toString().lastIndexOf('.');
		String extension = path.getFileName().toString().substring(extensionDot + 1);

		if (extensionDot <= 0) {
			return false;
		}

		for (String e : extensions) {
			if (extension.equals(e)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Help method used to load drawing model using given text. The format in which
	 * the geometric object representations should be writen is the following:
	 * <ul>
	 * <li>LINE x0 y0 x1 y1 red green blue</li>
	 * <li>CIRCLE centerx centery radius red green blue</li>
	 * <li>FCIRCLE centerx centery radius red green blue red green blue</li>
	 * </ul>
	 * So an valid text would be:
	 * <ul>
	 * <li>LINE 10 10 50 50 255 255 0</li>
	 * <li>LINE 50 90 30 10 128 0 128</li>
	 * <li>CIRCLE 40 40 18 0 0 255</li>
	 * <li>FCIRCLE 40 40 18 0 0 255 255 0 0</li>
	 * </ul>
	 * 
	 * @param model the model to load the objects into. The model is not cleaned
	 * @param text  the text to load from
	 * @throws NullPointerException     thrown if null pointers given
	 * @throws IllegalArgumentException throws if invalid text given
	 */
	private void laodDrawingModel(DrawingModel model, String text) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(text);

		for (String line : text.lines().collect(Collectors.toList())) {
			if (line.isEmpty()) {
				continue;
			}

			String[] parts = line.trim().split("\\s+");

			try {
				if ("LINE".equals(parts[0]) && parts.length == 8) {
					Point start = new Point(Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
					Point end = new Point(Integer.valueOf(parts[3]), Integer.valueOf(parts[4]));
					Color color = new Color(Integer.valueOf(parts[5]), Integer.valueOf(parts[6]),
							Integer.valueOf(parts[7]));
					Line l = new Line(start, end, color);
					model.add(l);
				} else if ("CIRCLE".equals(parts[0]) && parts.length == 7) {
					Point center = new Point(Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
					Integer radius = Integer.valueOf(parts[3]);
					Color fgcolor = new Color(Integer.valueOf(parts[4]), Integer.valueOf(parts[5]),
							Integer.valueOf(parts[6]));

					Circle c = new Circle(center, radius, fgcolor);
					model.add(c);
				} else if ("FCIRCLE".equals(parts[0]) && parts.length == 10) {
					Point fcenter = new Point(Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
					Integer fradius = Integer.valueOf(parts[3]);
					Color ffgcolor = new Color(Integer.valueOf(parts[4]), Integer.valueOf(parts[5]),
							Integer.valueOf(parts[6]));
					Color bgcolor = new Color(Integer.valueOf(parts[7]), Integer.valueOf(parts[8]),
							Integer.valueOf(parts[9]));

					FilledCircle fc = new FilledCircle(fcenter, fradius, ffgcolor, bgcolor);
					model.add(fc);
				} else {
					throw new IllegalArgumentException("Invalid format of given text. Invalid first word identifier.");
				}

			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Invalid format of given text. Number not found where number should have been.");
			}
		}

	}

	/**
	 * Help method used to save file as a new file
	 */
	private void saveAs() {

		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JVD files only", "jvd");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("Select saving location");

		if (jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		try {
			Path selectedPath = jfc.getSelectedFile().toPath();

			int extensionDot = selectedPath.getFileName().toString().lastIndexOf('.');
			String extension = selectedPath.getFileName().toString().substring(extensionDot + 1);
			if (extensionDot <= 0 || !"jvd".equals(extension)) {
				JOptionPane.showMessageDialog(null, "The extension must be JVD!");
				return;
			}

			if (Files.exists(selectedPath)) {
				int dialogResult = JOptionPane.showOptionDialog(null,
						"Overwrite " + " \"" + selectedPath.getFileName() + "\" ?", "Confirm overwrite",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
				if (dialogResult == JOptionPane.NO_OPTION) {
					return;
				}
			}

			saveToFile(selectedPath);
			currentFilePath = selectedPath;
			drawingModel.clearModifiedFlag();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not complete action");
		}
	}

	/**
	 * Help method that saves the textual representation of the objects in drawn
	 * model to given path.
	 * 
	 * @param path the path to save the text to
	 * @throws IOException thrown if anything goes wrong with input output
	 */
	private void saveToFile(Path path) throws IOException {
		GeometricalObjectToText visitor = new GeometricalObjectToText();
		visit(drawingModel, visitor);
		String drawingModelText = visitor.getText();

		BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
		writer.write(drawingModelText);
		writer.flush();
	}

	/**
	 * Help method used to visit all objects in given drawing model with given
	 * visitor
	 * 
	 * @param model the model to visit
	 * @param v     the visitor
	 */
	private void visit(DrawingModel model, GeometricalObjectVisitor v) {
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(v);
		}
	}

	/**
	 * Entry point for program
	 * 
	 * @param args arguments are irrelevant
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

}
