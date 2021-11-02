package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Class models an {@link JButton} that pops the last element on stack and sets
 * the value of the {@link CalcModel} used by the {@link Calculator}.
 * 
 * @author Frano Rajič
 */
public class PopJButton extends JButton implements ActionListener {

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
	 * Create an new {@link PopJButton} with {@link CalcModel given}
	 * 
	 * @param stack     the stack
	 * @param calcModel the calcModel
	 * @throws NullPointerException     if any of the given values is null
	 */
	public PopJButton(Stack<Double> stack, CalcModel calcModel) {
		super("p");
		
		Objects.requireNonNull(calcModel);
		Objects.requireNonNull(stack);
		
		this.stack = stack;
		this.calcModel = calcModel;
		
		this.addActionListener(this);
		this.setBackground(Color.CYAN);
		this.setFont(this.getFont().deriveFont(20f));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (stack.isEmpty()) {
			System.out.println("The stack is empty!");
			return;
		}

		calcModel.setValue(stack.pop());
	}
}
