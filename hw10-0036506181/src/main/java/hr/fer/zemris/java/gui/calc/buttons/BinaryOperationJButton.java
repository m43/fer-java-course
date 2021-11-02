package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class models {@link JButton} that do operations defined by
 * {@link DoubleBinaryOperator} on {@link CalcModel} which is the calculation
 * model used by {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class BinaryOperationJButton extends JButton implements ActionListener, ItemListener {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The operation name
	 */
	private String operationName;

	/**
	 * The double binary operation
	 */
	private DoubleBinaryOperator operation;

	/**
	 * The name of the inverted operation
	 */
	private String inverseOperationName;

	/**
	 * The double binary operation inverted
	 */
	private DoubleBinaryOperator inverseOperation;

	/**
	 * The {@link CalcModel} used by calculator
	 */
	private CalcModel calcModel;

	/**
	 * Is the operation currently inverted
	 */
	private boolean inverted = false;

	/**
	 * Is the operation invertible at all, or should the operation be the same
	 * regardless
	 */
	private boolean invertible = false;

	/**
	 * Create an {@link BinaryOperationJButton} that is invertible
	 * 
	 * @param operationName        the operation name
	 * @param operation            the operation
	 * @param inverseOperationName the inverse operation name
	 * @param inverseOperation     the inverse operation
	 * @param calcModel            the model of calculation
	 * @throws NullPointerException     if any of the given values is null
	 */
	public BinaryOperationJButton(String operationName, DoubleBinaryOperator operation, String inverseOperationName,
			DoubleBinaryOperator inverseOperation, CalcModel calcModel) {
		this(operationName, operation, calcModel);
		
		Objects.requireNonNull(inverseOperation);
		Objects.requireNonNull(inverseOperationName);
		
		this.inverseOperationName = inverseOperationName;
		this.inverseOperation = inverseOperation;
		invertible = true;
	}

	/**
	 * Create a new {@link BinaryOperationJButton} that is not invertible.
	 * 
	 * @param operationName the operation name
	 * @param operation     the operation
	 * @param calcModel     the model of calculation
	 * @throws NullPointerException     if any of the given values is null
	 */
	public BinaryOperationJButton(String operationName, DoubleBinaryOperator operation, CalcModel calcModel) {
		super(operationName);
		
		Objects.requireNonNull(operation);
		Objects.requireNonNull(operationName);
		Objects.requireNonNull(calcModel);
		
		this.operationName = operationName;
		this.operation = operation;
		this.calcModel = calcModel;
		this.setBackground(Color.CYAN);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			DoubleBinaryOperator pendingOperation = calcModel.getPendingBinaryOperation();

			if (pendingOperation != null) {
				double x = calcModel.getActiveOperand();
				double y = calcModel.getValue();
				double result = pendingOperation.applyAsDouble(x, y);
				calcModel.clearActiveOperand();
				calcModel.setValue(result);
			}

			if (invertible && inverted) {
				calcModel.setPendingBinaryOperation(inverseOperation);
			} else {
				calcModel.setPendingBinaryOperation(operation);
			}

			calcModel.setActiveOperand(calcModel.getValue());
			calcModel.clear();

		} catch (CalculatorInputException ex) {
			System.out.println("Problem while adding binary operation. Consider this message: " + ex.getMessage());
		}
	}

	/**
	 * Has the operation been inverted
	 * 
	 * @return true if inverted
	 */
	public boolean isInversed() {
		return inverted;
	}

	/**
	 * Is the operation invertible at all
	 * 
	 * @return true if invertible
	 */
	public boolean isInvertible() {
		return invertible;
	}

	/**
	 * Change whether the operation is inverted or not
	 * 
	 * @param inverted should the operation be inverted or not
	 */
	public void setInversed(boolean inverted) {
		if (!invertible || this.inverted == inverted) {
			return;
		}

		this.inverted = inverted;
		if (inverted) {
			this.setText(inverseOperationName);
		} else {
			this.setText(operationName);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		setInversed(e.getStateChange() == 1 ? true : false);
	}
}
