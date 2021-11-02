package hr.fer.zemris.java.hw17.jvdraw.geometry.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;

/**
 * Class models an {@link GeometricalObjectEditor} that is capable of editing an
 * filled circle. It gives the options of changing the center coordinates, the
 * radius and the colors of the fill and of the border.
 * 
 * @author Frano Rajič
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = -5437313261265310495L;

	/**
	 * The original circle that is being edited.
	 */
	private FilledCircle filledCircle;

	/**
	 * The inputed center Point
	 */
	private Point center;

	/**
	 * The inputed radius
	 */
	private int radius;

	/**
	 * The inputed border color
	 */
	private Color fgcolor;

	/**
	 * The inputed fill color
	 */
	private Color bgcolor;

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
	 * Color chooser used for choosing new border color
	 */
	private JColorChooser fgColorChooser;

	/**
	 * Color chooser used for choosing new fill color
	 */
	private JColorChooser bgColorChooser;

	/**
	 * Has the input been checked
	 */
	private boolean checked;

	/**
	 * Create an editor of the given filled circle
	 * 
	 * @param filledCircle the filled circle that needs to be edited
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;

		setLayout(new BorderLayout());
		JLabel title = new JLabel(filledCircle.toString());
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 18));
		add(title, BorderLayout.PAGE_START);

		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(0, 2));

		JLabel centerXLabel = new JLabel("Center X:");
		centerXLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(centerXLabel);
		centerX = new JTextField(String.valueOf(filledCircle.getCenter().x));
		gridPanel.add(centerX);

		JLabel centerYLabel = new JLabel("Center Y:");
		centerYLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(centerYLabel);
		centerY = new JTextField(String.valueOf(filledCircle.getCenter().y));
		gridPanel.add(centerY);

		JLabel radiusLabel = new JLabel("Radius:");
		radiusLabel.setHorizontalAlignment(JLabel.RIGHT);
		gridPanel.add(radiusLabel);
		radiusField = new JTextField(String.valueOf(filledCircle.getRadius()));
		gridPanel.add(radiusField);

		gridPanel.setPreferredSize(new Dimension(30, 100));
		add(gridPanel, BorderLayout.CENTER);

		JPanel colorChoosers = new JPanel(new FlowLayout());
		fgColorChooser = new JColorChooser(filledCircle.getBorderColor());
		bgColorChooser = new JColorChooser(filledCircle.getFillColor());
		colorChoosers.add(fgColorChooser);
		colorChoosers.add(bgColorChooser);
		add(colorChoosers, BorderLayout.PAGE_END);
	}

	@Override
	public void checkEditing() {
		try {
			Integer x = Integer.valueOf(centerX.getText());
			Integer y = Integer.valueOf(centerY.getText());
			center = new Point(x, y);

			radius = Integer.valueOf(radiusField.getText());

			fgcolor = fgColorChooser.getColor();
			bgcolor = bgColorChooser.getColor();
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

		filledCircle.setRadius(radius);
		filledCircle.setCenter(center);
		filledCircle.setBorderColor(fgcolor);
		filledCircle.setFillColor(bgcolor);
	}

}
