package hr.fer.zemris.java.hw11.jnotepadpp.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Class models an {@link JLabel} that shows the current time, updating it once
 * every second.
 * 
 * @author Frano Rajič
 */
public class JTimeLabel extends JLabel {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = -5003497174327281611L;

	/**
	 * Create a new {@link JTimeLabel}
	 */
	public JTimeLabel() {
		super();
		time();
	}

	/**
	 * Help method to add the functionality of setting the current time as content
	 * of this label once every second.
	 */
	private void time() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Timer t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date d = new Date();
				setText(sdf.format(d));
			}
		});
		t.start();
	}

}
