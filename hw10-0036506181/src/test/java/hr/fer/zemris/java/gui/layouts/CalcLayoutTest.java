package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class CalcLayoutTest {

	@Test
	void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));

		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.getWidth());
		assertEquals(158, dim.getHeight());
	}

	@Test
	void testPreferredSizeAgain() {

		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));

		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.getWidth());
		assertEquals(158, dim.getHeight());
	}

	@Test
	void testAddingValidRCPositionComponents() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		cp.add(new JLabel("e"), new RCPosition(1, 1));
		cp.add(new JLabel("e"), new RCPosition(1, 6));

		cp.add(new JLabel("e"), new RCPosition(2, 1));
		cp.add(new JLabel("e"), new RCPosition(2, 7));

		cp.add(new JLabel("e"), new RCPosition(3, 1));
		cp.add(new JLabel("e"), new RCPosition(3, 2));
		cp.add(new JLabel("e"), new RCPosition(3, 3));
		cp.add(new JLabel("e"), new RCPosition(3, 4));
		cp.add(new JLabel("e"), new RCPosition(3, 5));
		cp.add(new JLabel("e"), new RCPosition(3, 6));
		cp.add(new JLabel("e"), new RCPosition(3, 7));

		cp.add(new JLabel("e"), new RCPosition(5, 1));
		cp.add(new JLabel("e"), new RCPosition(5, 7));

	}

	@Test
	void testAddingSameObjectTwiceToSamePosition() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		JLabel label = new JLabel("e");
		cp.add(label, new RCPosition(1, 1));
		cp.add(label, new RCPosition(1, 1));

		JLabel label2 = new JLabel("e");
		cp.add(label2, new RCPosition(2, 2));
		cp.add(label2, new RCPosition(2, 2));
	}

	@Test
	void testSameComponentChangingPosition() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		JLabel label = new JLabel("e");
		cp.add(label, new RCPosition(1, 1));

		// not sure if this should or should not throw
		cp.add(label, new RCPosition(2, 1));

		// assertThrows(CalcLayoutException.class, () -> cp.add(label, new
		// RCPosition(2,1)));
	}

	@Test
	void testThrowsWhenAddingDifferentComponentsToSamePosition() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		JLabel label1 = new JLabel("e");
		JLabel label2 = new JLabel("e");

		cp.add(label1, new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> cp.add(label2, new RCPosition(1, 1)));
	}

	@Test
	void testAddingValidStringComponentPositions() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		cp.add(new JLabel("e"), "1,1");
		cp.add(new JLabel("e"), "1,6");

		cp.add(new JLabel("e"), "2,1");
		cp.add(new JLabel("e"), "2,7");

		cp.add(new JLabel("e"), "3,1");
		cp.add(new JLabel("e"), "3,2");
		cp.add(new JLabel("e"), "3,3");
		cp.add(new JLabel("e"), "3,4");
		cp.add(new JLabel("e"), "3,5");
		cp.add(new JLabel("e"), "3,6");
		cp.add(new JLabel("e"), "3,7");

		cp.add(new JLabel("e"), "5,1");
		cp.add(new JLabel("e"), "5,7");
	}

	@Test
	void testThrowsWithInvalidRowNumber() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "0,3"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "-1,3"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "6,3"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "7,3"));
	}

	@Test
	void testThrowsWithInvalidColumnNumber() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));

		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "2,0"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "2,-1"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "2,8"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "2,9000"));
	}

	@Test
	void testThrowsWithInvalidFirstComponent() {
		Container cp = new Container();
		cp.setLayout(new CalcLayout(10));
		cp.add(new JLabel("e"), "1,1");
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "1,2"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "1,3"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "1,4"));
		assertThrows(CalcLayoutException.class, () -> cp.add(new JLabel("e"), "1,5"));
		cp.add(new JLabel("e"), "1,6");
		cp.add(new JLabel("e"), "1,7");
	}
}
