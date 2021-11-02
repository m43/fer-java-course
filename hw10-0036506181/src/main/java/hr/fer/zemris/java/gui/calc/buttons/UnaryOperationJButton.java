package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;
import java.util.function.UnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class models {@link JButton} that do operations defined by
 * {@link UnaryOperator} on {@link CalcModel} which is the calculation model
 * used by {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class UnaryOperationJButton extends JButton implements ActionListener, ItemListener {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The operation name
	 */
	private String operationName;

	/**
	 * The double unary operation
	 */
	private UnaryOperator<Double> operation;

	/**
	 * The name of the inverted operation
	 */
	private String inverseOperationName;

	/**
	 * The double unary operation inverted
	 */
	private UnaryOperator<Double> inverseOperation;

	/**
	 * The {@link CalcModel} used by calculator
	 */
	private CalcModel calcModel;

	/**
	 * Is the operation currently inverted
	 */
	private boolean inverted = false;

	/**
	 * Create an {@link BinaryOperationJButton} that is invertible
	 * 
	 * @param operationName        the operation name
	 * @param operation            the operation
	 * @param inverseOperationName the inverse operation name
	 * @param inverseOperation     the inverse operation
	 * @param calcModel            the model of calculation
	 * @throws NullPointerException if any of the given values is null
	 */
	public UnaryOperationJButton(String operationName, UnaryOperator<Double> operation, String inverseOperationName,
			UnaryOperator<Double> inverseOperation, CalcModel calcModel) {

		super(operationName);

		Objects.requireNonNull(operation);
		Objects.requireNonNull(operationName);
		Objects.requireNonNull(inverseOperation);
		Objects.requireNonNull(inverseOperationName);
		Objects.requireNonNull(calcModel);

		this.operationName = operationName;
		this.operation = operation;
		this.inverseOperationName = inverseOperationName;
		this.inverseOperation = inverseOperation;
		this.calcModel = calcModel;
		this.addActionListener(this);
	}

	/**
	 * Create a new {@link BinaryOperationJButton} that is not invertible.
	 * 
	 * @param operationName the operation name
	 * @param operation     the operation
	 * @param calcModel     the model of calculation
	 */
	public UnaryOperationJButton(String operationName, UnaryOperator<Double> operation, CalcModel calcModel) {
		this(operationName, operation, operationName, operation, calcModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double result;
		if (!inverted) {
			result = operation.apply(calcModel.getValue());
		} else {
			result = inverseOperation.apply(calcModel.getValue());
		}

		calcModel.setValue(result);
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
	 * Change whether the operation is inverted or not
	 * 
	 * @param inverted should the operation be inverted or not
	 */
	public void setInversed(boolean inverted) {
		if (this.inverted == inverted) {
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
