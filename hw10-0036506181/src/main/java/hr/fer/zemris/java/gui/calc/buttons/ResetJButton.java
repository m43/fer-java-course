package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class models an {@link JButton} that resets the {@link CalcModel} used by the
 * {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class ResetJButton extends JButton implements ActionListener {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalcModel} to which a dot will be appended
	 */
	protected CalcModel calcModel;

	/**
	 * Create an new {@link ResetJButton} with {@link CalcModel} given
	 * 
	 * @param calcModel the calcModel
	 * @throws NullPointerException     if given calcModel is null
	 */
	public ResetJButton(CalcModel calcModel) {
		super("res");
		Objects.requireNonNull(calcModel);
		
		this.setBackground(Color.CYAN);
		this.setFont(this.getFont().deriveFont(20f));

		this.calcModel = calcModel;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		calcModel.clearAll();
	}
}
