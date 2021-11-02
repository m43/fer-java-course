package hr.fer.zemris.java.hw17.jvdraw.geometry.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Class represents an {@link GeometricalObjectEditor} that is able to edit a
 * given {@link Line}. The editing includes changing color of line, as well as
 * changing end and start point of line.
 * 
 * @author Frano Rajič
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 2837006055043613408L;

	/**
	 * The original line that is being edited
	 */
	private Line line;

	/**
	 * The inputed point for start of line
	 */
	private Point start;

	/**
	 * The inputed point for end of line
	 */
	private Point end;

	/**
	 * The inputed color
	 */
	private Color color;

	/**
	 * The input field for X coordinate of line starting point
	 */
	private JTextField startX;

	/**
	 * The input field for Y coordinate of line starting point
	 */
	private JTextField startY;

	/**
	 * The input field for X coordinate of line ending point
	 */
	private JTextField endX;

	/**
	 * The input field for Y coordinate of line ending point
	 */
	private JTextField endY;

	/**
	 * The color chooser that is used to select the new color
	 */
	private JColorChooser colorChooser;

	/**
	 * Has the input already been checked successfully
	 */
	private boolean checked;

	/**
	 * Create a new line editor of the given line
	 * 
	 * @param line the line to edit
	 */
	public LineEditor(Line line) {
		this.line = line;

		setLayout(new BorderLayout());
		JLabel title = new JLabel(line.toString());
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 18));
		add(title, BorderLayout.PAGE_START);

		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(0, 2));

		JLabel startXLabel = new JLabel("Start point X coordinate:");
		startXLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(startXLabel);
		startX = new JTextField(String.valueOf(line.getStartPoint().x));
		gridPanel.add(startX);

		JLabel startYLabel = new JLabel("Start point Y coordinate:");
		startYLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(startYLabel);
		startY = new JTextField(String.valueOf(line.getStartPoint().y));
		gridPanel.add(startY);

		JLabel endXLabel = new JLabel("End point X coordinate:");
		endXLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(endXLabel);
		endX = new JTextField(String.valueOf(line.getEndPoint().x));
		gridPanel.add(endX);

		JLabel endYLabel = new JLabel("End point Y coordinate:");
		endYLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(endYLabel);
		endY = new JTextField(String.valueOf(line.getEndPoint().y));
		gridPanel.add(endY);

		gridPanel.setPreferredSize(new Dimension(30, 100));
		add(gridPanel, BorderLayout.CENTER);

		colorChooser = new JColorChooser(line.getColor());
		add(colorChooser, BorderLayout.PAGE_END);
	}

	@Override
	public void checkEditing() {
		try {
			Integer x = Integer.valueOf(startX.getText());
			Integer y = Integer.valueOf(startY.getText());
			start = new Point(x, y);

			x = Integer.valueOf(endX.getText());
			y = Integer.valueOf(endY.getText());
			end = new Point(x, y);

			color = colorChooser.getColor();
		} catch (Exception e) {
			throw new GemetricalObjectInvalidEditException("Invalid input: " + e.getMessage());
		}
		checked = true;
	}

	@Override
	public void acceptEditing() {
		if (!checked) {
			checkEditing();
		}

		line.setStartPoint(start);
		line.setEndPoint(end);
		line.setColor(color);
	}

}
