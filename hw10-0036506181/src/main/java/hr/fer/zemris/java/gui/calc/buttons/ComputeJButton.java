package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class models {@link JButton} that computes the current result by doing left
 * over operations in {@link CalcModel} which is the calculation model used by
 * {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class ComputeJButton extends JButton implements ActionListener {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalcModel} used by calculator
	 */
	private CalcModel calcModel;

	/**
	 * Crate a new {@link ComputeJButton} with given calculation model
	 * 
	 * @param calcModel the model of calculation
	 * @throws NullPointerException     if given calcModel is a null reference
	 */
	public ComputeJButton(CalcModel calcModel) {
		super("=");

		Objects.requireNonNull(calcModel);
		this.calcModel = calcModel;

		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		this.setFont(this.getFont().deriveFont(40f));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			DoubleBinaryOperator pendingOperation = calcModel.getPendingBinaryOperation();

			double result;
			if (pendingOperation != null) {
				double x = calcModel.getActiveOperand();
				calcModel.clearActiveOperand();
				double y = calcModel.getValue();

				result = pendingOperation.applyAsDouble(x, y);
				calcModel.setPendingBinaryOperation(null);
			} else {
				result = calcModel.getValue();
			}

			calcModel.setValue(result);
		} catch (CalculatorInputException ex) {
			System.out.println("Problem while doing the computation. Consider this message: " + ex.getMessage());
		}
	}

}
