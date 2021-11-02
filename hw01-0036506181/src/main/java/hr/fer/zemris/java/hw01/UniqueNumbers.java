package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class provides the necessary functionality to store unique numbers (numbers
 * that do not repeat). The unique numbers structure functionality includes
 * adding nodes, checking if a node was already added and checking the number of
 * stored numbers.
 * 
 * @author Frano
 * @version 1.0
 */
public class UniqueNumbers {

	/**
	 * The keyword for terminating application
	 */
	public static final String END = "kraj";

	/**
	 * TreeNode is used as a help structure in achieving the unique numbers storage.
	 * Nodes reference to their children and contain a value stored in themselves.
	 * The nodes construct a binary tree.
	 * 
	 * @author Frano
	 */
	public static class TreeNode {

		/**
		 * The left child node of the tree node
		 */
		TreeNode left;

		/**
		 * The right child node of the tree node
		 */
		TreeNode right;

		/**
		 * The value stored in this tree node
		 */
		int value;

		/**
		 * Create a new tree node with given value
		 * 
		 * @param value the value of the tree node
		 */
		public TreeNode(int value) {
			this.value = value;
		}
	}

	/**
	 * Method to add values to unique numbers structure. If a value has already been
	 * added, it wont be added again.
	 * 
	 * @param head  A reference to the unique numbers structure.
	 * @param value The value to be stored in the unique numbers structure.
	 * @return Returns a reference to the provided given unique numbers structure.
	 *         If the structure has not been initialized yet, the method returns a
	 *         reference to a newly created unique numbers structure.
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			return new TreeNode(value);
		}

		if (head.value > value) {
			head.left = (addNode(head.left, value));
		} else if (head.value < value) {
			head.right = (addNode(head.right, value));
		}

		// if head.value == value then do nothing

		return head;
	}

	/**
	 * Method to tell how many values are stored in the unique numbers structure.
	 * 
	 * @param head A reference to the structure
	 * @return The number of stored values
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		return 1 + treeSize(head.left) + treeSize(head.right);
	}

	/**
	 * Method checks if the unique numbers structure contain given value
	 * 
	 * @param head  A reference to the structure
	 * @param value The value that needs to be looked up
	 * @return True if the value is contained in the unique numbers structure, false
	 *         otherwise.
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}

		if (head.value == value) {
			return true;
		}

		if (head.value > value) {
			return containsValue(head.left, value);
		} else {
			return containsValue(head.right, value);
		}
	}

	/**
	 * Methods provides printing out the stored values in ascending order if order=0
	 * or descending order otherwise. Values are printed in one line separated by a
	 * space, and without a line break.
	 * 
	 * @param head  A reference to the unique numbers structure
	 * @param order Ascending order if order=0, descending otherwise
	 */
	public static void printSequance(TreeNode head, int order) {
		if (head == null) {
			return;
		}

		printSequance((order == 0) ? head.left : head.right, order);
		System.out.print(head.value + " ");
		printSequance((order == 0) ? head.right : head.left, order);
	}

	/**
	 * Main program receives user input. If a integer is entered for the first time
	 * it is stored in the unique numbers structure. Otherwise an appropriate
	 * message is printed. To terminate the program enter {@value #END}}.
	 * 
	 * @param args Irrelevant
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		TreeNode head = null;

		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			if (END.equals(input)) {
				break;
			}

			int number;
			try {
				number = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.printf("'%s' nije cijeli broj.%n", input);
				continue;
			}

			if (containsValue(head, number)) {
				System.out.println("Broj veÄ‡ postoji. PreskaÄ�em.");
			} else {
				head = addNode(head, number);
				System.out.println("Dodano.");
			}
		}
		sc.close();

		System.out.print("Ispis od najmanjeg: ");
		printSequance(head, 0);
		System.out.printf("%nIspis od najveÄ‡eg: ");
		printSequance(head, 1);
	}

}
