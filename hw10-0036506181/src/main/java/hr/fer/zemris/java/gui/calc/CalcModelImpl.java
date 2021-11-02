package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class models an concrete implementation of an calculator model which is
 * defined by interface {@link CalcModel}.
 * 
 * @author Frano Rajič
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * Is the calculator editable at the moment
	 */
	private boolean editable = true;

	/**
	 * Tells if the current number is positive or negative
	 */
	private boolean positive = true;

	/**
	 * String holding the currently entered number
	 */
	private String currentString = "";

	/**
	 * Double holding the double value of the currently entered number
	 */
	private Double currentDouble = 0.;

	/**
	 * The active operand that is used as the first operand in binary operations
	 */
	private Double activeOperand;

	/**
	 * Has the active operand been set
	 */
	private boolean activeOperandSet = false;

	/**
	 * The pending binary operation
	 */
	DoubleBinaryOperator pendingBinaryOperation;

	/**
	 * List of observers looking for the change in this object
	 */
	List<CalcValueListener> listeners = new ArrayList<>();
	
	/**
	 * Help method to notify all listeners that an change in value has occurred
	 */
	private void notifyListeners() {
		for(CalcValueListener l: listeners) {
			l.valueChanged(this);
		}
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (!listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (listeners.contains(l)) {
			listeners = new ArrayList<>(listeners);
			listeners.remove(l);
		}
	}

	@Override
	public double getValue() {
		return currentDouble;
	}

	@Override
	public void setValue(double value) {
		editable = false;
		positive = value >= 0;
		currentDouble = value;
		currentString = String.valueOf(Math.abs(value));
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		currentString = "";
		currentDouble = 0.;
		positive = true;
		editable = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		activeOperandSet = false;
		pendingBinaryOperation = null;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable()) {
			throw new CalculatorInputException("The current value is not editable!");
		}

		currentDouble = currentDouble * (-1);

		positive = (positive ? false : true);
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		String newCurrentString = (currentString.isEmpty()?"0":currentString) + ".";
		try {
			currentDouble = Double.parseDouble((positive ? "" : "-") + currentString + ".");
		} catch (NumberFormatException e) {
			throw new CalculatorInputException(
					"Cannot insert decimal point. Either already set or number is +-Infinity or NaN.");
		}

		currentString = newCurrentString;
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable()) {
			throw new CalculatorInputException("Cannot insert any digits, calculator is not editable.");
		}

		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Given int \"" + digit + "\" is not an digit.");
		}

		String newCurrentString = currentString + digit;
		try {
			double newCurrentDouble = Double.parseDouble((positive ? "" : "-") + newCurrentString);
			if (!Double.isFinite(newCurrentDouble)) {
				throw new CalculatorInputException("The new double would be infinite - cannot add given digit!");
			}
			currentDouble = newCurrentDouble;
		} catch (NumberFormatException e) {
			throw new CalculatorInputException(
					"The currently entered number cannot be parsed to an Double, hence cannot be used in calculation.");
		}

		currentString = newCurrentString;
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand not set!");
		}

		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		activeOperandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		activeOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingBinaryOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingBinaryOperation = op;
	}

	@Override
	public String toString() {
		if (currentDouble == currentDouble.longValue())
			return String.format((positive ? "" : "-") + "%d", (long) Math.abs(currentDouble));
		else
			return String.format((positive ? "" : "-") + "%s", Math.abs(currentDouble));
	}

}
