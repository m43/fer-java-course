package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class ComplexNumberTest {

	@Test
	void testComplexNumber() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		ComplexNumber c2 = new ComplexNumber(-2.22, 3.33);
		ComplexNumber c3 = new ComplexNumber(0, 3312321);
		assertNotNull(c1);
		assertNotNull(c2);
		assertNotNull(c3);
	}

	@Test
	void testGetReal() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		ComplexNumber c2 = new ComplexNumber(-2.22, 3.33);
		ComplexNumber c3 = new ComplexNumber(3, 3312321);
		assertEquals(0d, c1.getReal());
		assertEquals(-2.22d, c2.getReal());
		assertEquals(3d, c3.getReal());
	}

	@Test
	void testGetImaginary() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		ComplexNumber c2 = new ComplexNumber(-2.22, 3.33);
		ComplexNumber c3 = new ComplexNumber(3, -33);
		assertEquals(0d, c1.getImaginary());
		assertEquals(3.33d, c2.getImaginary());
		assertEquals(-33d, c3.getImaginary());
	}

	@Test
	void testFromReal() {
		ComplexNumber c1 = ComplexNumber.fromReal(3);
		assertNotNull(c1);
		assertEquals(3d, c1.getReal());
		assertEquals(0d, c1.getImaginary());
	}

	@Test
	void testFromImaginary() {
		ComplexNumber c1 = ComplexNumber.fromImaginary(4);
		assertNotNull(c1);
		assertEquals(0d, c1.getReal());
		assertEquals(4d, c1.getImaginary());
	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2, 0);
		assertNotNull(c1);
		assertEquals(2d, c1.getReal());
		assertEquals(0d, c1.getImaginary());

		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI);
		assertNotNull(c2);
		assertTrue((c2.getReal() + 2) < 1e-7);
		assertTrue((c2.getImaginary()) < 1e-7);
	}

	@Test
	void testParse() {
		String[] validStrings = { " 2 + 3i", "3.51", "351", "-317", "3.51", "-3.17", "351i", "-317i", "3.51i", "-3.17i",
				"i", "-i", "1", "-2.71-3.15i", "-2.71-3.15i", "31+24i", "-1-i", "+2.71", "+2.71+3.15i", "+i" };
		// "3.15E-2" is not required - evethough it works..
		
		for (String s : validStrings) {
			ComplexNumber.parse(s);
		}


		String[] invalidStrings = { " e+-=_+-== ", "  ", "i351", "-i317", "i3.51", "-i3.17", "-+2.71", "--2.71", "-2.71+-3.15i",
				"+2.71-+3.15i", "-+2.71", "ai", "sta je ovo", "jooj", "-2..71", "-2.7.1", "spava mi se",
				"2.71....", "žnj" };

		for (String s : invalidStrings) {
			assertThrows(NumberFormatException.class, () -> ComplexNumber.parse(s));
		}
	}

	@Test
	void testGetMagnitude() {
		ComplexNumber c1 = new ComplexNumber(0, 0);
		ComplexNumber c2 = new ComplexNumber(2, 0);
		ComplexNumber c3 = new ComplexNumber(0, 3);
		
		assertEquals(0d, c1.getMagnitude());
		assertEquals(2d, c2.getMagnitude());
		assertEquals(3d, c3.getMagnitude());
	}

	@Test
	void testGetAngle() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-1, 1);
		ComplexNumber c3 = new ComplexNumber(1, -1);
		ComplexNumber c4 = new ComplexNumber(-1, -1);
		ComplexNumber c5 = new ComplexNumber(-2, 0);
		ComplexNumber c6 = new ComplexNumber(-1, -1);
		
		assertEquals(Math.PI / 4, c1.getAngle());
		assertEquals(3*Math.PI / 4, c2.getAngle());
		assertEquals(7*Math.PI / 4, c3.getAngle());
		assertEquals(5*Math.PI / 4, c4.getAngle());
		assertEquals(Math.PI, c5.getAngle());
		assertEquals(5 * Math.PI / 4, c6.getAngle());
	}

	@Test
	void testAdd() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-2, 0);
		ComplexNumber c3 = c1.add(c2);
		
		assertEquals(c3.getReal(), c1.getReal()+c2.getReal());
		assertEquals(c3.getImaginary(), c1.getImaginary()+c2.getImaginary());
	}

	@Test
	void testSub() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-2, 0);
		ComplexNumber c3 = c1.sub(c2);
		
		assertEquals(c3.getReal(), c1.getReal()-c2.getReal());
		assertEquals(c3.getImaginary(), c1.getImaginary()-c2.getImaginary());
	}

	@Test
	void testMul() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(-2, 4);
		ComplexNumber c3 = c1.mul(c2);
		
		assertEquals(-6d, c3.getReal());
		assertEquals(2d, c3.getImaginary());
	}

	@Test
	void testDiv() {
		ComplexNumber c1 = new ComplexNumber(4, 3);
		ComplexNumber c2 = new ComplexNumber(1, 1);
		ComplexNumber c3 = c1.div(c2);
		
		assertEquals(c3.getReal(), 3.5d);

		ComplexNumber c4 = new ComplexNumber(3, 3);
		ComplexNumber c5 = new ComplexNumber(1, 1);
		ComplexNumber c6 = c4.div(c5);

		assertEquals(c6.getAngle(), 0d);
	}

	@Test
	void testPower() {
		ComplexNumber c1 = new ComplexNumber(4, 3);
		ComplexNumber c2 = c1.power(3);

		assertEquals(125, c2.getMagnitude());
		
		ComplexNumber c3 = c1.power(0);
		assertEquals(1, c3.getReal());		
		assertEquals(0, c3.getImaginary());
		
		ComplexNumber c4 = new ComplexNumber(1, 1);
		ComplexNumber c5 = c4.power(4);
		assertEquals(Math.PI, c5.getAngle());
		
		assertThrows(IllegalArgumentException.class, () -> c5.power(-1));
	}

	@Test
	void testRoot() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber[] complexArray = c1.root(2);
		assertTrue( (1d - complexArray[0].getReal()) < 1e-7);
		assertTrue( (1d - complexArray[0].getImaginary()) < 1e-7);
		
		assertTrue( (-1d - complexArray[1].getReal()) < 1e-7);
		assertTrue( (-1d - complexArray[1].getImaginary()) < 1e-7);
		
		assertThrows(IllegalArgumentException.class, () -> c1.root(-1));
		assertThrows(IllegalArgumentException.class, () -> c1.root(0));
	}

	@Test
	void testToString() {
		ComplexNumber c1 = new ComplexNumber(-1, 1);
		ComplexNumber c2 = new ComplexNumber(2, 0);
		assertNotNull(c1.toString());
		assertNotNull(c2.toString());
	}

}
