package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class ObjectMultistackTest {

	@Test
	void testPushOneElement() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(1));
	}

	@Test
	void testPushMultipleElementsToSameKey() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(1));
		s.push("key", new ValueWrapper(2));
		s.push("key", new ValueWrapper(3));
		s.push("key", new ValueWrapper(4));
	}

	@Test
	void testPushToDifferentKeys() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key1", new ValueWrapper(1));
		s.push("key2", new ValueWrapper(1));
		s.push("key3", new ValueWrapper(1));
		s.push("key4", new ValueWrapper(2));
		s.push("key1", new ValueWrapper(1));
		s.push("key2", new ValueWrapper(2));
		s.push("key3keykey", new ValueWrapper(3));
		s.push("????123", new ValueWrapper(4));
		s.push("gsuydzfj;o", new ValueWrapper(1));
		s.push("oooo", new ValueWrapper(2));
		s.push("eee", new ValueWrapper(3));
		s.push("0", new ValueWrapper(4));
	}

	@Test
	void testPushAndPop() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(1));
		assertNotNull(s.pop("key"));
	}

	@Test
	void testPushAndPopMultiple() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(1));
		s.push("key", new ValueWrapper(2));
		s.push("key", new ValueWrapper(1));
		s.push("key", new ValueWrapper(1));
		assertNotNull(s.pop("key"));
		assertNotNull(s.pop("key"));
		assertNotNull(s.pop("key"));
		assertNotNull(s.pop("key"));
	}

	@Test
	void testPushThrowsWhenNullValuesGiven() {
		ObjectMultistack s = new ObjectMultistack();

		assertThrows(NullPointerException.class, () -> s.push("key", null));
		assertThrows(NullPointerException.class, () -> s.push(null, new ValueWrapper(3)));
		assertThrows(NullPointerException.class, () -> s.push(null, null));
	}

	@Test
	void testPushDoesntThrowWhenNullIsWrapped() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(null));
	}

	@Test
	void testPeek() {
		ObjectMultistack s = new ObjectMultistack();

		ValueWrapper v = new ValueWrapper(1);
		s.push("key", v);

		assertEquals(v, s.peek("key"));
	}

	@Test
	void testPeekDoesntRemoveTheElement() {
		ObjectMultistack s = new ObjectMultistack();

		ValueWrapper v = new ValueWrapper(1);
		s.push("key", v);

		assertEquals(v, s.peek("key"));
		assertEquals(v, s.peek("key"));
		assertEquals(v, s.peek("key"));
	}

	@Test
	void testPeekThrowsWhenEmpty() {
		ObjectMultistack s = new ObjectMultistack();

		assertThrows(EmptyStackException.class, () -> s.peek("wut"));
	}

	@Test
	void testIsEmpty() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(1));

		assertTrue(s.isEmpty("a"));
		assertTrue(s.isEmpty("b"));
		assertTrue(s.isEmpty("aea ofuhsdfilh assadf"));
		assertFalse(s.isEmpty("key"));
	}

	@Test
	void testIsEmptyAfterTheStackGotEmpty() {
		ObjectMultistack s = new ObjectMultistack();

		s.push("key", new ValueWrapper(1));
		assertFalse(s.isEmpty("key"));

		s.pop("key");
		assertTrue(s.isEmpty("key"));
	}

	@Test
	void testIsEmptyDoesntChangeStacks() {
		ObjectMultistack s = new ObjectMultistack();

		ValueWrapper v = new ValueWrapper(1);

		s.push("key", v);
		assertFalse(s.isEmpty("key"));
		assertFalse(s.isEmpty("key"));

		assertEquals(v, s.pop("key"));
		;
		assertTrue(s.isEmpty("key"));
		assertTrue(s.isEmpty("key"));
	}

	@Test
	void testIsEmptyThrowsWhenAskedForKeyNull() {
		ObjectMultistack s = new ObjectMultistack();

		assertThrows(NullPointerException.class, () -> s.isEmpty(null));
	}

	@Test
	void testPop() {
		ObjectMultistack s = new ObjectMultistack();

		ValueWrapper v = new ValueWrapper(3);
		s.push("key", v);

		assertEquals(v, s.pop("key"));
	}

	@Test
	void testPoppingMultipleElements() {
		ObjectMultistack s = new ObjectMultistack();

		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2);
		ValueWrapper v3 = new ValueWrapper(34);
		ValueWrapper v4 = new ValueWrapper(44);

		s.push("key", v1);
		s.push("key", v2);
		s.push("key", v3);
		s.push("key", v4);
		s.push("key1", v4);
		s.push("key1", v4);

		assertEquals(v4, s.pop("key"));
		assertEquals(v3, s.pop("key"));
		assertEquals(v2, s.pop("key"));
		assertEquals(v1, s.pop("key"));
		assertEquals(v4, s.pop("key1"));
		assertEquals(v4, s.pop("key1"));
	}

	@Test
	void testPopThrowsWhenEmpty() {
		ObjectMultistack s = new ObjectMultistack();

		assertThrows(EmptyStackException.class, () -> s.pop("wut"));
	}

	@Test
	void testComplicatedWorkflowWithMultistack() {
		ObjectMultistack s = new ObjectMultistack();

		ValueWrapper v1 = new ValueWrapper(1);
		ValueWrapper v2 = new ValueWrapper(2);
		ValueWrapper v3 = new ValueWrapper(3);

		s.push("key1", v1);
		s.push("key1", v2);
		s.push("key1", v3);

		s.push("key2", v3);
		s.push("key2", v3);
		s.push("key2", v3);

		assertEquals(v3, s.peek("key1"));
		assertEquals(v3, s.pop("key1"));
		assertFalse(s.isEmpty("key1"));

		assertEquals(v2, s.peek("key1"));
		assertEquals(v2, s.pop("key1"));
		assertFalse(s.isEmpty("key1"));

		assertEquals(v1, s.peek("key1"));
		assertEquals(v1, s.pop("key1"));
		assertTrue(s.isEmpty("key1"));

		assertEquals(v3, s.peek("key2"));
		assertEquals(v3, s.pop("key2"));
		assertFalse(s.isEmpty("key2"));

		assertEquals(v3, s.peek("key2"));
		assertEquals(v3, s.pop("key2"));
		assertFalse(s.isEmpty("key2"));

		assertEquals(v3, s.peek("key2"));
		assertEquals(v3, s.pop("key2"));
		assertTrue(s.isEmpty("key2"));
	}

}
