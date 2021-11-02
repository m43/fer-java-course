package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class LinkedListIndexedCollectionTest {

	@Test
	void testEmptyConstructor() {
		LinkedListIndexedCollectionTest c = new LinkedListIndexedCollectionTest();
		assertNotNull(c);
	}

	@Test
	void testConstructorWithCollectionParameter() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		LinkedListIndexedCollection c2 = new LinkedListIndexedCollection(c);
		assertNotNull(c2);
	}

	@Test
	void testAddingToCollection() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		c.add(5);
		assertThrows(NullPointerException.class, () -> c.add(null));
	}

	@Test
	void testCollectionSize() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		assertEquals(0, c.size());
		c.add(1);
		assertEquals(1, c.size());
		c.add(2);
		c.add(3);
		assertEquals(3, c.size());
		c.add(4);
		c.add(5);
		assertEquals(5, c.size());
	}

	@Test
	void testGettingAnObject() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(1);
		assertEquals(1, c.get(0));
		c.add(2);
		c.add(3);
		assertEquals(2, c.get(1));
		c.add(4);
		c.add(5);
		assertEquals(3, c.get(2));
		assertEquals(4, c.get(3));
		assertEquals(5, c.get(4));
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(5));
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(300));
	}

	@Test
	void testClearingTheCollection() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		c.add(5);
		c.clear();
		assertEquals(0, c.size());
	}

	@Test
	void testInsertingIntoCollection() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		
		c.insert(5, 2);
		assertEquals(2, c.get(1));
		assertEquals(5, c.get(2));
		assertEquals(3, c.get(3));
		
		c.insert(0, 0);
		c.insert(9, 6);
		c.insert(-2, 3);
		assertEquals(0, c.get(0));
		assertEquals(-2, c.get(3));
		assertEquals(9, c.get(7));
		
		assertThrows(NullPointerException.class, () -> c.insert(null, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(1, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(1, 9));
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(1, 100));
	}

	@Test
	void testIndexOfElementInCollection() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		assertEquals(-1, c.indexOf(1));
		assertEquals(-1, c.indexOf(null));
		c.add(1);
		assertEquals(0, c.indexOf(1));
		c.add(2);
		c.add(3);
		c.add(4);
		c.add(5);
		assertEquals(0, c.indexOf(1));
		assertEquals(4, c.indexOf(5));
		assertEquals(2, c.indexOf(3));
	}

	@Test
	void testRemovingElements() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> c.remove(0));
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		c.add(5);
		c.remove(4);
		c.remove(2);
		c.remove(0);
		assertEquals(2, c.get(0));
		assertEquals(4, c.get(1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.remove(2));
		assertThrows(IndexOutOfBoundsException.class, () -> c.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.remove(1000));
		c.remove(1);
		c.remove(0);
		assertEquals(0, c.size());
	}
}
