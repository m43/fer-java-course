package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class models an {@link JButton} that clears currently entered number by
 * altering {@link CalcModel} used by the {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class ClearJButton extends JButton implements ActionListener {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalcModel} to which a dot will be appended
	 */
	protected CalcModel calcModel;

	/**
	 * Create an new {@link ClearJButton} with {@link CalcModel} given
	 * 
	 * @param calcModel the calcModel
	 * @throws NullPointerException     if the given calcModel is a null reference
	 */
	public ClearJButton(CalcModel calcModel) {
		super("clr");

		Objects.requireNonNull(calcModel);
		this.calcModel = calcModel;

		this.setBackground(Color.CYAN);
		this.setFont(this.getFont().deriveFont(20f));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		calcModel.clear();
	}
}
