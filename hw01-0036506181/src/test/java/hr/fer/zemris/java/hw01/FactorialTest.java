package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.Factorial.calculateFactorial;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class FactorialTest {

	@Test
	void testNegativeInteger() {
		assertThrows(IllegalArgumentException.class, () -> calculateFactorial(-3));
	}

	@Test
	void testZero() {
		try {
			assertEquals(1, calculateFactorial(0), "0! = 1");
		} catch (IllegalArgumentException e) {
			fail("IllegalArgumentException should not have been thrown for number>=0");
		}
	}

	@Test
	void testOne() {
		try {
			assertEquals(1, calculateFactorial(1), "1! = 1");
		} catch (IllegalArgumentException e) {
			fail("IllegalArgumentException should not have been thrown for number>=0");
		}
	}

	@Test
	void testPositiveNumbers() {
		try {
			assertEquals(2, calculateFactorial(2), "2! = 2");
			assertEquals(6, calculateFactorial(3), "3! = 6");
			assertEquals(24, calculateFactorial(4), "4! = 24");
			assertEquals(120, calculateFactorial(5), "5! = 120");
		} catch (IllegalArgumentException e) {
			fail("IllegalArgumentException should not have been thrown for number>=0");
		}
	}

	@Test
	void above21() {
		assertThrows(IllegalArgumentException.class, () -> calculateFactorial(21));
	}
}
