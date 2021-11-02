package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class ArrayIndexedCollectionTest {

	@Test
	void testEmptyConstructor() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertEquals(0, c.size());
	}

	@Test
	void testConstructorWithCapacity() {
		ArrayIndexedCollection c = new ArrayIndexedCollection(300);
		assertEquals(0, c.size());

		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-100));
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}

	@Test
	void testConstructorWithOtherCollection() {
		Collection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(3);

		ArrayIndexedCollection c2 = new ArrayIndexedCollection(c);
		assertEquals(3, c2.size());
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}

	@Test
	void testConstructorWithOtherCollectionAndCapacity() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(3);

		ArrayIndexedCollection c2 = new ArrayIndexedCollection(c, 500);
		assertEquals(3, c2.size());

		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 2));
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(c, -1));
	}

	@Test
	void testAddingToCollection() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(3);
		assertEquals(3, c.size());

		assertThrows(NullPointerException.class, () -> c.add(null));
	}

	@Test
	void testIsCollectionEmpty() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertTrue(c.isEmpty());
	}

	@Test
	void testGettingAnElement() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(0));

		c.add(3);
		c.add(4);
		c.add(3);
		c.add(5);
		c.add(3);
		assertEquals(3, c.get(0));
		assertEquals(4, c.get(1));
		assertEquals(3, c.get(2));
		assertEquals(5, c.get(3));
		assertEquals(3, c.get(4));

		assertThrows(IndexOutOfBoundsException.class, () -> c.get(-2));
		assertThrows(IndexOutOfBoundsException.class, () -> c.get(21));
	}

	@Test
	void testSizeMethodOfCollection() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertEquals(0, c.size());
		c.add(3);
		assertEquals(1, c.size());
		c.add(4);
		assertEquals(2, c.size());
		c.add(3);
		assertEquals(3, c.size());
		c.add(5);
		assertEquals(4, c.size());
		c.add(3);
		assertEquals(5, c.size());
	}

	@Test
	void testClearingTheCollection() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(3);
		c.clear();
		assertEquals(0, c.size());
	}

	@Test
	void testInsertingNewElements() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(3);
		c.insert(0, 2);
		assertEquals(3, c.get(0));
		assertEquals(4, c.get(1));
		assertEquals(0, c.get(2));
		assertEquals(3, c.get(3));

		assertThrows(NullPointerException.class, () -> c.insert(null, 2));
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(1, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> c.insert(1, 100));
	}

	@Test
	void testIndexLookUp() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(0);
		c.add(5);
		assertEquals(0, c.indexOf(3));
		assertEquals(1, c.indexOf(4));
		assertEquals(2, c.indexOf(0));
		assertEquals(3, c.indexOf(5));
		assertTrue(c.indexOf(-120) < 0);
	}

	@Test
	void testRemovingAnElementEqualToObject() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(0);
		c.add(5);
		c.remove(2);
		assertTrue(c.remove(Integer.valueOf(3)));
		assertTrue(c.remove(Integer.valueOf(4)));
		assertFalse(c.remove(Integer.valueOf(4)));
		assertFalse(c.remove(Integer.valueOf(10)));
		assertFalse(c.remove(null));
	}

	@Test
	void testRemovingAnElementAtIndex() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(3);
		c.add(4);
		c.add(0);
		c.add(5);
		c.remove(2);
		assertEquals(0, c.indexOf(3));
		assertEquals(1, c.indexOf(4));
		assertEquals(2, c.indexOf(5));
		assertThrows(IllegalArgumentException.class, () -> c.remove(-2));
		assertThrows(IllegalArgumentException.class, () -> c.remove(100));
		assertThrows(IllegalArgumentException.class, () -> c.remove(c.size()));
	}

	@Test
	void testIsEmpty() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		assertTrue(c.isEmpty());
		c.add(4);
		assertFalse(c.isEmpty());
		
	}
	
	@Test
	void testForEach() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(4);
		c.add(0);
		c.add(5);
		ArrayIndexedCollection c1 = new ArrayIndexedCollection();
		c1.addAll(c);
		assertEquals(3, c.size());
	}
	
//	@Test
//  Just checking if it is doing a deep or shallow copy
//	But for the solution of the problem of hw2 it was good enough
//	to just do a shallow copy... (deep copy of any object is not that easy anyway)
	void testCollectionToArray() {
		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add(1);
		
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(c);
				
		Object[] returnedArray = collection.toArray();
		
		ArrayIndexedCollection firstElement = (ArrayIndexedCollection) returnedArray[0];
		firstElement.add(2);

		System.out.println(returnedArray[0] == c);
		System.out.println(returnedArray[0] == firstElement);
		
		assertEquals(1, c.size());
		assertEquals(2, firstElement.size());
	}
}
