package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class models an {@link JButton} that changes the current sign of
 * {@link CalcModel} used by the {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class SignJButton extends JButton implements ActionListener {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The {@link CalcModel} to which a dot will be appended
	 */
	private CalcModel calcModel;

	/**
	 * Create an new {@link SignJButton} with {@link CalcModel given}
	 * 
	 * @param calcModel the calcModel
	 * @throws NullPointerException     if given calcModel is null
	 */
	public SignJButton(CalcModel calcModel) {
		super("+/-");
		
		Objects.requireNonNull(calcModel);
		
		this.calcModel = calcModel;
		this.addActionListener(this);
		
		this.setBackground(Color.WHITE);
		this.setFont(this.getFont().deriveFont(20f));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		calcModel.swapSign();
	}

}
