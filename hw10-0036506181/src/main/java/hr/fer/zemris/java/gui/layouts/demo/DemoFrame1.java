package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program demonstrating the use of {@link CalcLayout} with {@link JFrame}.
 * 
 * @author Frano Rajič
 */
public class DemoFrame1 extends JFrame {
	/**
	 * The serial uid version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct the frame
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Help method to initialize the frame
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 1"), new RCPosition(1, 6));
		cp.add(l("tekst 1"), new RCPosition(1, 7));

		for (int a = 2; a <= 5; a++) {
			for (int b = 1; b <= 7; b++) {
				cp.add(l("tekst " + a + "," + b), new RCPosition(a, b));
			}
		}
	}

	/**
	 * Help method to create an {@link JLabel} with given text
	 * 
	 * @param text text of label
	 * @return the created label
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * Entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}
}