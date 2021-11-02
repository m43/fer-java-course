package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class models an {@link JButton} that appends an specific digit to
 * {@link CalcModel} when an action happens.
 * 
 * @author Frano Rajič
 */
public class DigitJButton extends JButton implements ActionListener {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The digit that will be added when button pressed/released
	 */
	private final int digit;

	/**
	 * The model, refer to {@link CalcModel}.
	 */
	private final CalcModel calcModel;

	/**
	 * Initialize an new {@link DigitJButton} with given digit and model.
	 * 
	 * @param digit the digit to append to model when an action happens
	 * @param calcModel the model to be updated when action happens
	 * @throws NullPointerException     if given calcModel is null
	 */
	public DigitJButton(int digit, CalcModel calcModel) {
		super(String.valueOf(digit));
		Objects.requireNonNull(calcModel);
		
		this.digit = digit;
		this.calcModel = calcModel;
		
		this.setBackground(Color.WHITE);
		this.setFont(this.getFont().deriveFont(30f));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			calcModel.insertDigit(digit);
		} catch (CalculatorInputException ex) {
			System.out.println("Couldnt add digit cause of: " + ex.getMessage());
		}
	}

}
