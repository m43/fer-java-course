package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class DictionaryTest {

	@Test
	@SuppressWarnings("unused")
	void testDictionary() {
		Dictionary<String, Integer> d1 = new Dictionary<>();
		Dictionary<Object, Integer> d2 = new Dictionary<>();
		Dictionary<Object, Object> d3 = new Dictionary<>();
		Dictionary<String, Number> d4 = new Dictionary<>();
		Dictionary<ArrayIndexedCollection<String>, Integer> d5 = new Dictionary<>();
		Dictionary<ObjectStack<String>, Collection<Integer>> d6 = new Dictionary<>();
		Dictionary<LinkedListIndexedCollection<Integer>, Double> d7 = new Dictionary<>();
	}

	@Test
	void testIsEmpty() {
		Dictionary<String, Number> d1 = new Dictionary<>();
		assertTrue(d1.isEmpty());

		Dictionary<ArrayIndexedCollection<String>, Integer> d2 = new Dictionary<>();
		assertTrue(d2.isEmpty());

		Dictionary<ObjectStack<String>, Collection<Integer>> d3 = new Dictionary<>();
		assertTrue(d3.isEmpty());
	}

	@Test
	void testSizeOfEmptyDictionary() {
		Dictionary<String, Integer> d1 = new Dictionary<>();
		assertEquals(0, d1.size());
	}

	@Test
	void testPut() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("ea1", 1);
		d.put("ea2", 1);
		d.put("ea3", 1);
	}

	@Test
	void testSize() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("ea1", 1);
		d.put("ea2", 1);
		d.put("ea3", 1);
		assertEquals(3, d.size());
		d.put("ea4", 1);
		assertEquals(4, d.size());
		d.put("ea5", 1);
		assertEquals(5, d.size());
		d.put("ea6", 1);
		assertEquals(6, d.size());
	}

	@Test
	void testClear() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("ea1", 1);
		d.put("ea2", 1);
		d.put("ea3", 1);
		d.clear();
		assertEquals(0, d.size());
	}

	@Test
	void testPuttingExistingKey() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("ea1", 1);
		d.put("ea2", 1);
		d.put("ea3", 1);
		assertEquals(3, d.size());
		d.put("ea3", 1);
		assertEquals(3, d.size());
		d.put("ea3", 3000);
		assertEquals(3, d.size());
		d.put("ea3", -123);
		assertEquals(3, d.size());
	}

	@Test
	void testKeyNullValue() {
		Dictionary<String, Integer> d = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> d.put(null, 1));
	}

	@Test
	void testKeyAndValueNull() {
		Dictionary<String, Integer> d = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> d.put(null, null));
	}

	@Test
	void testValueIsNull() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("ea1", null);
	}

	@Test
	void testGet() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("e", 1);
		d.put("o", 44);
		assertEquals(44, d.get("o"));
		assertEquals(1, d.get("e"));
	}

	@Test
	void testGettingAnElementNotInTheDictionary() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("e", 1);
		d.put("o", 44);
		assertEquals(null, d.get("ee"));
		assertEquals(null, d.get("oo"));
		assertEquals(null, d.get("1"));
	}

	@Test
	void testGettingAnEntryWithValueuNull() {
		Dictionary<String, Integer> d = new Dictionary<>();
		d.put("n", null);
		assertEquals(null, d.get("n"));
	}

}