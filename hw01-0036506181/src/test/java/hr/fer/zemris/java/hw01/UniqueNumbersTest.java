package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class UniqueNumbersTest {

	@Test
	void testAddingOfNodes() {
		TreeNode head = null;

		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		
		assertEquals(42, head.value);
		assertEquals(21, head.left.value);
		assertEquals(35, head.left.right.value);
		assertEquals(76, head.right.value);

	}
	
	@Test
	void testTreeSize() {
		TreeNode head = null;

		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);

		int velicina = treeSize(head);
		assertEquals(4, velicina);
	}
	
	@Test
	void testContainsMethod() {
		TreeNode head = null;

		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		
		assertTrue(containsValue(head, 42));
		assertTrue(containsValue(head, 21));
		assertFalse(containsValue(head, 22));
		assertFalse(containsValue(head, 0));
	}
	
	@Test
	void testSizeOfEmptyStructure() {
		TreeNode head = null;

		int velicina = treeSize(head);
		assertEquals(0, velicina);
	}

}
