package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This is a simple program that adds new prime numbers to two lists
 * simultaneously. New prime numbers are added by clicking "Next prime" in the
 * GUI. The two lists are also shown in GUI and are updated with the new prime
 * number as soon as the button is clicked.
 * 
 * @author Frano Rajič
 */
public class PrimDemo extends JFrame {

	/**
	 * The default serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a new {@link PrimDemo}.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");

		setLocation(20, 50);
		setSize(485, 300);

		initGUI();
	}

	/**
	 * Help method that initializes the graphical user interface.
	 */
	private void initGUI() {
		PrimListModel model = new PrimListModel();

		setLayout(new BorderLayout());

		JButton next = new JButton("Next prime");
		next.addActionListener((x) -> model.next());
		add(next, BorderLayout.PAGE_END);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.PAGE_START);
		panel.setLayout(new BorderLayout());
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		panel.add(new JScrollPane(list1), BorderLayout.LINE_START);
		panel.add(new JScrollPane(list2), BorderLayout.LINE_END);
	}

	/**
	 * Entry point for program.
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
