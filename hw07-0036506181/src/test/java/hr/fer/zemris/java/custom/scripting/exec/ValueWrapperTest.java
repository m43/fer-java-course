package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class ValueWrapperTest {
	
	@Test
	void testAddingThrowsUncompatibleOperand() {
		ValueWrapper v = new ValueWrapper(3);

		assertThrows(RuntimeException.class, () -> v.add(v));
		assertThrows(RuntimeException.class, () -> v.add(new Object()));
		assertThrows(RuntimeException.class, () -> v.add(new StringBuilder()));
		assertThrows(RuntimeException.class, () -> v.add(Boolean.valueOf(true)));
	}

	@Test
	void testAddingIntegerAndIntegerReturnsInteger() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4);

		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	void testAddingWrapperToItself() {
		ValueWrapper v = new ValueWrapper(3);
		
		v.add(v.getValue());
		assertEquals(6, (int) v.getValue());
		
		v.add(v.getValue());
		assertEquals(12, (int) v.getValue());
	}

	@Test
	void testAddingIntegerAndDoubleReturnsDouble() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(4.4);

		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	void testAddingDoubleAndIntegerReturnsDouble() {
		ValueWrapper v1 = new ValueWrapper(3.4);
		ValueWrapper v2 = new ValueWrapper(4);

		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	void testAddingDoubleAndDoubleReturnsDouble() {
		ValueWrapper v1 = new ValueWrapper(3.4);
		ValueWrapper v2 = new ValueWrapper(4.4);

		v1.add(v2.getValue());

		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	void testAddingThrowsUncompatibleValueOfWrapper() {
		Integer i = Integer.valueOf(10);

		ValueWrapper v1 = new ValueWrapper(new Object());
		assertThrows(RuntimeException.class, () -> v1.add(i));

		ValueWrapper v2 = new ValueWrapper(new StringBuilder());
		assertThrows(RuntimeException.class, () -> v2.add(i));

		ValueWrapper v3 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> v3.add(i));
	}

	@Test
	void testAddingNullWrappers() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.

		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	void testAddingDoubleAndInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).

		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}

	@Test
	void testAddingParseableIntegerString() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());

		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}

	@Test
	void testAddingThrowsUnparseableString() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));

		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
	}

	@Test
	void testSubtractNullWrappers() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		v1.subtract(v2.getValue());

		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	void testSubtractDoubleAndInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		v3.subtract(v4.getValue());

		assertEquals(Double.valueOf(11), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}

	@Test
	void testSubtractResultOfCorrectNumberType() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(1);
		ValueWrapper v3 = new ValueWrapper(1.1);

		v1.subtract(v2.getValue());
		assertTrue(v1.getValue() instanceof Integer);

		v1.subtract(v3.getValue());
		assertTrue(v1.getValue() instanceof Double);

		v1.subtract(v2.getValue());
		assertTrue(v1.getValue() instanceof Double);

		v1.subtract(v3.getValue());
		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	void testSubtractParseableIntegerString() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.subtract(v6.getValue());

		assertEquals(Integer.valueOf(11), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}

	@Test
	void testSubtractThrowsUnparseableString() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));

		assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));
	}

	@Test
	void testSubtractThrowsUncompatibleOperand() {
		ValueWrapper v = new ValueWrapper(3);

		assertThrows(RuntimeException.class, () -> v.subtract(v));
		assertThrows(RuntimeException.class, () -> v.subtract(new Object()));
		assertThrows(RuntimeException.class, () -> v.subtract(new StringBuilder()));
		assertThrows(RuntimeException.class, () -> v.subtract(Boolean.valueOf(true)));
	}

	@Test
	void testSubtractThrowsUncompatibleValueOfWrapper() {
		Integer i = Integer.valueOf(10);

		ValueWrapper v1 = new ValueWrapper(new Object());
		assertThrows(RuntimeException.class, () -> v1.subtract(i));

		ValueWrapper v2 = new ValueWrapper(new StringBuilder());
		assertThrows(RuntimeException.class, () -> v2.subtract(i));

		ValueWrapper v3 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> v3.subtract(i));
	}

	@Test
	void testMultiplyNullWrappers() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		v1.multiply(v2.getValue());

		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}

	@Test
	void testMultiplyResultOfCorrectNumberType() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(1);
		ValueWrapper v3 = new ValueWrapper(1.1);

		v1.multiply(v2.getValue());
		assertTrue(v1.getValue() instanceof Integer);

		v1.multiply(v3.getValue());
		assertTrue(v1.getValue() instanceof Double);

		v1.multiply(v2.getValue());
		assertTrue(v1.getValue() instanceof Double);

		v1.multiply(v3.getValue());
		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	void testMultiplyDoubleAndInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		v3.multiply(v4.getValue());

		assertEquals(Double.valueOf(12), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}

	@Test
	void testMultiplyParseableIntegerString() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.multiply(v6.getValue());

		assertEquals(Integer.valueOf(12), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}

	@Test
	void testMultiplyThrowsUnparseableString() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));

		assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));
	}

	@Test
	void testMultiplyThrowsUncompatibleOperand() {
		ValueWrapper v = new ValueWrapper(3);

		assertThrows(RuntimeException.class, () -> v.multiply(v));
		assertThrows(RuntimeException.class, () -> v.multiply(new Object()));
		assertThrows(RuntimeException.class, () -> v.multiply(new StringBuilder()));
		assertThrows(RuntimeException.class, () -> v.multiply(Boolean.valueOf(true)));
	}

	@Test
	void testMultiplyThrowsUncompatibleValueOfWrapper() {
		Integer i = Integer.valueOf(10);

		ValueWrapper v1 = new ValueWrapper(new Object());
		assertThrows(RuntimeException.class, () -> v1.multiply(i));

		ValueWrapper v2 = new ValueWrapper(new StringBuilder());
		assertThrows(RuntimeException.class, () -> v2.multiply(i));

		ValueWrapper v3 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> v3.multiply(i));
	}

	@Test
	void testNullDivisionWithNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		assertThrows(ArithmeticException.class, () -> v1.divide(v2.getValue()));
	}

	@Test
	void testNullDivision() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(0);

		assertThrows(ArithmeticException.class, () -> v2.divide(v2.getValue()));
		assertThrows(ArithmeticException.class, () -> v1.divide(v2.getValue()));
	}

	@Test
	void testDivideDoubleAndInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		v3.divide(v4.getValue());

		assertEquals(Double.valueOf(12), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}

	@Test
	void testDivideResultOfCorrectNumberType() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(1);
		ValueWrapper v3 = new ValueWrapper(1.1);

		v1.divide(v2.getValue());
		assertTrue(v1.getValue() instanceof Integer);

		v1.divide(v3.getValue());
		assertTrue(v1.getValue() instanceof Double);

		v1.divide(v2.getValue());
		assertTrue(v1.getValue() instanceof Double);

		v1.divide(v3.getValue());
		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	void testDivideParseableIntegerString() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.divide(v6.getValue());

		assertEquals(Integer.valueOf(12), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}

	@Test
	void testDivideThrowsUnparseableString() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));

		assertThrows(RuntimeException.class, () -> v7.divide(v8.getValue()));
	}

	@Test
	void testDivideThrowsUncompatibleOperand() {
		ValueWrapper v = new ValueWrapper(3);

		assertThrows(RuntimeException.class, () -> v.divide(v));
		assertThrows(RuntimeException.class, () -> v.divide(new Object()));
		assertThrows(RuntimeException.class, () -> v.divide(new StringBuilder()));
		assertThrows(RuntimeException.class, () -> v.divide(Boolean.valueOf(true)));
	}

	@Test
	void testDivideThrowsUncompatibleValueOfWrapper() {
		Integer i = Integer.valueOf(10);

		ValueWrapper v1 = new ValueWrapper(new Object());
		assertThrows(RuntimeException.class, () -> v1.divide(i));

		ValueWrapper v2 = new ValueWrapper(new StringBuilder());
		assertThrows(RuntimeException.class, () -> v2.divide(i));

		ValueWrapper v3 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> v3.divide(i));
	}

	@Test
	void testNumCompareNullWrappers() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	void testNumCompareEqualNumbers() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(1);

		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	void testNumCompareBothWays() {
		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(1);

		assertTrue(v1.numCompare(v2.getValue()) == 0);
		assertTrue(v2.numCompare(v1.getValue()) == 0);

		ValueWrapper v3 = new ValueWrapper(2);
		ValueWrapper v4 = new ValueWrapper(1);

		assertTrue(v3.numCompare(v4.getValue()) > 0);
		assertTrue(v4.numCompare(v3.getValue()) < 0);
	}

	@Test
	void testNumCompareDoubleAndInteger() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));

		assertTrue(v3.numCompare(v4.getValue()) > 0);
	}

	@Test
	void testNumCompareParseableIntegerString() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		
		assertTrue(v5.numCompare(v6.getValue()) > 0);
		assertTrue(v6.numCompare(v5.getValue()) < 0);
	}

	@Test
	void testNumCompareThrowsUnparseableString() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));

		assertThrows(RuntimeException.class, () -> v7.numCompare(v8.getValue()));
	}

	@Test
	void testNumCompareThrowsUncompatibleOperand() {
		ValueWrapper v = new ValueWrapper(3);

		assertThrows(RuntimeException.class, () -> v.numCompare(v));
		assertThrows(RuntimeException.class, () -> v.numCompare(new Object()));
		assertThrows(RuntimeException.class, () -> v.numCompare(new StringBuilder()));
		assertThrows(RuntimeException.class, () -> v.numCompare(Boolean.valueOf(true)));
	}

	@Test
	void testNumCompareThrowsUncompatibleValueOfWrapper() {
		Integer i = Integer.valueOf(10);

		ValueWrapper v1 = new ValueWrapper(new Object());
		assertThrows(RuntimeException.class, () -> v1.numCompare(i));

		ValueWrapper v2 = new ValueWrapper(new StringBuilder());
		assertThrows(RuntimeException.class, () -> v2.numCompare(i));

		ValueWrapper v3 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> v3.numCompare(i));
	}

}
