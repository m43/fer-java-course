package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationJButton;
import hr.fer.zemris.java.gui.calc.buttons.ClearJButton;
import hr.fer.zemris.java.gui.calc.buttons.ComputeJButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitJButton;
import hr.fer.zemris.java.gui.calc.buttons.DotJButton;
import hr.fer.zemris.java.gui.calc.buttons.PopJButton;
import hr.fer.zemris.java.gui.calc.buttons.PushJButton;
import hr.fer.zemris.java.gui.calc.buttons.ResetJButton;
import hr.fer.zemris.java.gui.calc.buttons.ResultJLabel;
import hr.fer.zemris.java.gui.calc.buttons.SignJButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationJButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program to run a simple GUI calculator with basic unary and binary
 * operations. All operations done on doubles.
 * 
 * @author Frano Rajič
 */
public class Calculator extends JFrame {

	/**
	 * The default serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalcModel} used to store the numbers that are used in the
	 * calculations.
	 */
	CalcModel calcModel;

	/**
	 * CheckBox to tell if the operations should be inverted.
	 */
	protected JCheckBox invertCheckBox;

	/**
	 * The stack used for push and pop buttons
	 */
	Stack<Double> stack = new Stack<>();

	/**
	 * Construct the frame
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		calcModel = new CalcModelImpl();
		initGUI();
		pack();
	}

	/**
	 * Help method to initialize the frame
	 */
	private void initGUI() {
		this.setLayout(new CalcLayout(3));

		addCheckBox();
		addDisplay();
		addDigitButtons();
		addUnaryOperations();
		addBinaryOperations();
		addEqualsButton();
		addSignAndDot();
		addSeventhColumn();
	}

	/**
	 * Help method to add and initialize the invert check box
	 */
	private void addCheckBox() {
		invertCheckBox = new JCheckBox("INV");
		invertCheckBox.setBackground(Color.ORANGE);
		this.add(invertCheckBox, new RCPosition(5, 7));
	}

	/**
	 * Help method to add the display button
	 */
	private void addDisplay() {
		ResultJLabel result = new ResultJLabel();
		calcModel.addCalcValueListener(result);
		this.add(result, new RCPosition(1, 1));
	}

	/**
	 * Help method to add all buttons that do the digit input
	 */
	private void addDigitButtons() {
		this.add(new DigitJButton(0, calcModel), new RCPosition(5, 3));
		for (int i = 1; i <= 9; i++) {
			this.add(new DigitJButton(i, calcModel), new RCPosition(4 - (i - 1) / 3, 3 + (i - 1) % 3));
		}
	}

	/**
	 * Help method to add all buttons with unary operations
	 */
	private void addUnaryOperations() {
		UnaryOperationJButton reciprocally = new UnaryOperationJButton("1/x", x -> 1 / x, calcModel);
		this.add(reciprocally, new RCPosition(2, 1));
		invertCheckBox.addItemListener(reciprocally);

		UnaryOperationJButton log = new UnaryOperationJButton("log", Math::log10, "10^x", x -> Math.pow(10, x),
				calcModel);
		this.add(log, new RCPosition(3, 1));
		invertCheckBox.addItemListener(log);

		UnaryOperationJButton ln = new UnaryOperationJButton("ln", Math::log, "e^x", Math::exp, calcModel);
		this.add(ln, new RCPosition(4, 1));
		invertCheckBox.addItemListener(ln);

		UnaryOperationJButton sin = new UnaryOperationJButton("sin", Math::sin, "arcsin", Math::asin, calcModel);
		this.add(sin, new RCPosition(2, 2));
		invertCheckBox.addItemListener(sin);

		UnaryOperationJButton cos = new UnaryOperationJButton("cos", Math::cos, "arccos", Math::acos, calcModel);
		this.add(cos, new RCPosition(3, 2));
		invertCheckBox.addItemListener(cos);

		UnaryOperationJButton tan = new UnaryOperationJButton("tan", Math::tan, "arctan", Math::atan, calcModel);
		this.add(tan, new RCPosition(4, 2));
		invertCheckBox.addItemListener(tan);

		UnaryOperationJButton ctg = new UnaryOperationJButton("ctg", x -> 1 / Math.tan(x), "arcctg",
				x -> Math.tan(1 / x), calcModel);
		this.add(ctg, new RCPosition(5, 2));
		invertCheckBox.addItemListener(ctg);

	}

	/**
	 * Help method that adds the sign-swap and dot adding buttonF
	 */
	private void addSignAndDot() {
		this.add(new SignJButton(calcModel), new RCPosition(5, 4));
		this.add(new DotJButton(calcModel), new RCPosition(5, 5));
	}

	/**
	 * Help method to create and add the button to compute the current result
	 */
	private void addEqualsButton() {
		this.add(new ComputeJButton(calcModel), new RCPosition(1, 6));
	}

	/**
	 * Help method to create and add the buttons that compute binary operations
	 */
	private void addBinaryOperations() {
		this.add(new BinaryOperationJButton("/", (x, y) -> x / y, calcModel), new RCPosition(2, 6));
		this.add(new BinaryOperationJButton("*", (x, y) -> x * y, calcModel), new RCPosition(3, 6));
		this.add(new BinaryOperationJButton("-", (x, y) -> x - y, calcModel), new RCPosition(4, 6));
		this.add(new BinaryOperationJButton("+", (x, y) -> x + y, calcModel), new RCPosition(5, 6));

		BinaryOperationJButton power = new BinaryOperationJButton("x^n", (x, y) -> Math.pow(x, y), "x^(1/n)",
				(x, y) -> Math.pow(x, 1 / y), calcModel);
		this.add(power, new RCPosition(5, 1));
		invertCheckBox.addItemListener(power);
	}

	/**
	 * Help method to create all the buttons in the seventh column
	 */
	private void addSeventhColumn() {
		this.add(new ClearJButton(calcModel), new RCPosition(1, 7));
		this.add(new ResetJButton(calcModel), new RCPosition(2, 7));
		this.add(new PushJButton(stack, calcModel), new RCPosition(3, 7));
		this.add(new PopJButton(stack, calcModel), new RCPosition(4, 7));
	}

	/**
	 * Entry point for program.
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

}
