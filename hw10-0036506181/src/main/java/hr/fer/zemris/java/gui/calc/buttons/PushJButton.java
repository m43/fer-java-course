package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class models an {@link JButton} that pushs the current value of
 * {@link CalcModel} onto the stack.
 * 
 * @author Frano Rajič
 */
public class PushJButton extends JButton implements ActionListener {

	/**
	 * the serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The stack used for pushing elements
	 */
	private Stack<Double> stack;
	
	/**
	 * The {@link CalcModel} to which a dot will be appended
	 */
	protected CalcModel calcModel;

	/**
	 * Create an new {@link PushJButton} with stack and {@link CalcModel} given
	 * 
	 * @param stack the stack
	 * @param calcModel the calcModel
	 * @throws NullPointerException     if any of the given values is null
	 */
	public PushJButton(Stack<Double> stack, CalcModel calcModel) {
		super("push");
		
		Objects.requireNonNull(calcModel);
		Objects.requireNonNull(stack);
		
		this.stack = stack;
		this.calcModel = calcModel;
		
		this.setBackground(Color.CYAN);
		this.setFont(this.getFont().deriveFont(20f));
		this.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stack.push(calcModel.getValue());
		calcModel.clear();
	}
}
