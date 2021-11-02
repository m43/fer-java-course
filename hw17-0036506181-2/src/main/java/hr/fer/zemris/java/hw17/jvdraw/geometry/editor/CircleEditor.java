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

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;

/**
 * Class models an {@link GeometricalObjectEditor} that can edit {@link Circle}
 * objects. It gives the possibility of changing the center point coordinates,
 * the color of the border and the radius.
 * 
 * @author Frano Rajič
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 7907511686498016205L;

	/**
	 * The original circle that is being edited.
	 */
	private Circle circle;

	/**
	 * The inputed center Point
	 */
	private Point center;

	/**
	 * The inputed radius
	 */
	private int radius;

	/**
	 * The inputed color
	 */
	private Color color;

	/**
	 * Field for entering the X coordinate of the center
	 */
	private JTextField centerX;

	/**
	 * Field for entering the Y coordinate of the center
	 */
	private JTextField centerY;

	/**
	 * The field for entering the radius of the circle
	 */
	private JTextField radiusField;

	/**
	 * Color chooser used for choosing new color
	 */
	private JColorChooser colorChooser;

	/**
	 * Has the input been checked
	 */
	private boolean checked;

	/**
	 * Create an editor of the given circle
	 * 
	 * @param circle the circle that needs to be edited
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;

		setLayout(new BorderLayout());
		JLabel title = new JLabel(circle.toString());
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 18));
		add(title, BorderLayout.PAGE_START);

		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(0, 2));

		JLabel centerXLabel = new JLabel("Center X:");
		centerXLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(centerXLabel);
		centerX = new JTextField(String.valueOf(circle.getCenter().x));
		gridPanel.add(centerX);

		JLabel centerYLabel = new JLabel("Center Y:");
		centerYLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(centerYLabel);
		centerY = new JTextField(String.valueOf(circle.getCenter().y));
		gridPanel.add(centerY);

		JLabel radiusLabel = new JLabel("Radius:");
		radiusLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(radiusLabel);
		radiusField = new JTextField(String.valueOf(circle.getRadius()));
		gridPanel.add(radiusField);

		gridPanel.setPreferredSize(new Dimension(30, 100));
		add(gridPanel, BorderLayout.CENTER);

		colorChooser = new JColorChooser(circle.getBorderColor());
		add(colorChooser, BorderLayout.PAGE_END);
	}

	@Override
	public void checkEditing() {
		try {
			Integer x = Integer.valueOf(centerX.getText());
			Integer y = Integer.valueOf(centerY.getText());
			center = new Point(x, y);

			radius = Integer.valueOf(radiusField.getText());

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

		circle.setRadius(radius);
		circle.setCenter(center);
		circle.setBorderColor(color);
	}

}
