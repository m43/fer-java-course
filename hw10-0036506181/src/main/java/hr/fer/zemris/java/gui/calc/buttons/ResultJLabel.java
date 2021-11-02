package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

/**
 * Class models an {@link JLabel} that shows the current value of a
 * {@link CalcModel}. As an {@link CalcValueListener} the result gets updated as
 * soon as the value in the model changes. Class used for showing results in the
 * {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class ResultJLabel extends JLabel implements CalcValueListener {

	/**
	 * Serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that initializes the {@link ResultJLabel} on creation to zero.
	 */
	public ResultJLabel() {
		super("0");
		this.setOpaque(true);
		this.setBackground(Color.YELLOW);
		this.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1, true));
		this.setHorizontalAlignment(RIGHT);
		this.setFont(this.getFont().deriveFont(30f));
	}

	@Override
	public void valueChanged(CalcModel model) {
		this.setText(model.toString());
	}

}
