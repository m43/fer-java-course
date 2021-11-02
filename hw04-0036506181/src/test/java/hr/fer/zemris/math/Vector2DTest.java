package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class Vector2DTest {

	@Test
	void testHashCodeOfSameX() {
		Vector2D v1 = new Vector2D(3, 4);
		Vector2D v2 = new Vector2D(3, 5);

		assertNotEquals(v1.hashCode(), v2.hashCode());
	}

	@Test
	void testHashCodeOfSameY() {
		Vector2D v1 = new Vector2D(3, 4);
		Vector2D v2 = new Vector2D(4, 4);

		assertNotEquals(v1.hashCode(), v2.hashCode());
	}

	@Test
	void testHashCodeOfSameXandY() {
		Vector2D v1 = new Vector2D(3, 4);
		Vector2D v2 = new Vector2D(3, 4);

		assertEquals(v1.hashCode(), v2.hashCode());
	}

	@SuppressWarnings("unused")
	@Test
	void testVector2D() {
		Vector2D v1 = new Vector2D(1, 2);
		Vector2D v2 = new Vector2D(-11., 22.);
		Vector2D v3 = new Vector2D(-11, -22);
		Vector2D v4 = new Vector2D(11., -2222d);
		Vector2D v5 = new Vector2D(-110000, 10000000000.13123);
	}

	@Test
	void testGetX() {
		Vector2D v1 = new Vector2D(-11, 22);
		assertEquals(-11d, v1.getX());
	}

	@Test
	void testGetY() {
		Vector2D v1 = new Vector2D(-11, 22);
		assertEquals(22d, v1.getY());
	}

	@Test
	void testTranslate() {
		Vector2D v1 = new Vector2D(3d, 20d);
		Vector2D v2 = new Vector2D(4d, -10d);
		v1.translate(v2);

		assertEquals(new Vector2D(7d, 10d), v1);
	}

	@Test
	void testTranslated() {
		Vector2D v1 = new Vector2D(3d, 20d);
		Vector2D v2 = new Vector2D(4d, -10d);
		Vector2D v3 = v1.translated(v2);

		assertEquals(new Vector2D(7d, 10d), v3);
	}

	@Test
	void testRotate() {
		Vector2D v1 = new Vector2D(1d, 0d);
		v1.rotate(Math.PI / 2);
		assertEquals(new Vector2D(0d, 1d), v1);

		Vector2D v2 = new Vector2D(1d, 0d);
		v2.rotate(2 * Math.PI / 2);
		assertEquals(new Vector2D(-1d, 0d), v2);

		Vector2D v3 = new Vector2D(1d, 0d);
		v3.rotate(3 * Math.PI / 2);
		assertEquals(new Vector2D(0d, -1d), v3);

		Vector2D v4 = new Vector2D(1d, 0d);
		v4.rotate(4 * Math.PI / 2);
		assertEquals(new Vector2D(1d, 0d), v4);

	}

	@Test
	void testRotated() {
		Vector2D v1 = new Vector2D(1d, 0d);

		Vector2D v2 = v1.rotated(Math.PI / 2);
		Vector2D v3 = v1.rotated(Math.PI);
		Vector2D v4 = v1.rotated(3 * Math.PI / 2);
		Vector2D v5 = v1.rotated(2 * Math.PI);

		assertEquals(new Vector2D(0d, 1d), v2);
		assertEquals(new Vector2D(-1d, 0d), v3);
		assertEquals(new Vector2D(0d, -1d), v4);
		assertEquals(v1, v5);
	}

	@Test
	void testScale() {
		Vector2D v1 = new Vector2D(1d, 0d);
		Vector2D v2 = new Vector2D(0d, 1d);
		Vector2D v3 = new Vector2D(-1d, -1d);
		Vector2D v4 = new Vector2D(-3d, 3d);

		v1.scale(1);
		assertEquals(new Vector2D(1d, 0d), v1);
		v1.scale(2);
		assertEquals(new Vector2D(2d, 0d), v1);

		v2.scale(1);
		assertEquals(new Vector2D(0d, 1d), v2);
		v2.scale(2);
		assertEquals(new Vector2D(0d, 2d), v2);

		v3.scale(1);
		assertEquals(new Vector2D(-1d, -1d), v3);
		v3.scale(-5);
		assertEquals(new Vector2D(5d, 5d), v3);

		v4.scale(1);
		assertEquals(new Vector2D(-3d, 3d), v4);
		v4.scale(2);
		assertEquals(new Vector2D(-6d, 6d), v4);

	}

	@Test
	void testScaled() {
		Vector2D v1 = new Vector2D(1d, 0d);
		Vector2D v2 = new Vector2D(0d, 1d);
		Vector2D v3 = new Vector2D(-1d, -1d);
		Vector2D v4 = new Vector2D(-3d, 3d);

		assertEquals(new Vector2D(1d, 0d), v1.scaled(1));
		assertEquals(new Vector2D(2d, 0d), v1.scaled(2));
		assertEquals(new Vector2D(-5d, 0d), v1.scaled(-5));

		assertEquals(new Vector2D(0d, 1d), v2.scaled(1));
		assertEquals(new Vector2D(0d, 2d), v2.scaled(2));
		assertEquals(new Vector2D(0d, -5d), v2.scaled(-5));

		assertEquals(new Vector2D(-1d, -1d), v3.scaled(1));
		assertEquals(new Vector2D(-2d, -2d), v3.scaled(2));
		assertEquals(new Vector2D(5d, 5d), v3.scaled(-5));

		assertEquals(new Vector2D(-3d, 3d), v4.scaled(1));
		assertEquals(new Vector2D(-6d, 6d), v4.scaled(2));
		assertEquals(new Vector2D(15d, -15d), v4.scaled(-5));
	}

	@Test
	void testCopy() {
		Vector2D v1 = new Vector2D(1, 3);
		Vector2D v2 = new Vector2D(0, 0);

		assertEquals(new Vector2D(1, 3), v1.copy());
		assertEquals(new Vector2D(0, 0), v2.copy());
	}

	@Test
	void testEqualsObject() {
		Vector2D v1 = new Vector2D(1, 3);

		assertEquals(new Vector2D(1, 3), v1);
		assertEquals(new Vector2D(2.123123123, 2.123), new Vector2D(2.123123123, 2.123));
		assertNotEquals(new Vector2D(-2.123123123, 2.123), new Vector2D(2.123123123, 2.123));
		assertNotEquals(new Vector2D(2.223123123, 2.123), new Vector2D(2.123123123, 2.123));
		assertNotEquals(new Vector2D(0, 2.123), new Vector2D(1, 2.123));
		assertNotEquals(new Vector2D(0, 2.123), new Vector2D(0, 2));
	}

	@Test
	void testToString() {
		Vector2D v1 = new Vector2D(1, 3);
		String vectorString = v1.toString();

		assertTrue(vectorString.contains("("));
		assertTrue(vectorString.contains(","));
		assertTrue(vectorString.contains(" "));
		assertTrue(vectorString.contains(")"));
	}

}
