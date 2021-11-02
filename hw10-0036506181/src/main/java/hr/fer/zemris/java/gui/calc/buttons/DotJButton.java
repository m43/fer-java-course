package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Class models an {@link JButton} that ads decimal point to {@link CalcModel}
 * used by the {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class DotJButton extends JButton implements ActionListener {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalcModel} to which a dot will be appended
	 */
	protected CalcModel calcModel;

	/**
	 * Create an new {@link DotJButton} with {@link CalcModel} given
	 * 
	 * @param calcModel the calcModel
	 * @throws NullPointerException     if given calcModel is null
	 */
	public DotJButton(CalcModel calcModel) {
		super(".");
		Objects.requireNonNull(calcModel);
		
		this.calcModel = calcModel;
		this.setBackground(Color.WHITE);
		this.setFont(this.getFont().deriveFont(20f));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			calcModel.insertDecimalPoint();
		} catch (CalculatorInputException ex) {
			System.out.println("Couldnt add decimal point cause of: " + ex.getMessage());
		}
	}

}
