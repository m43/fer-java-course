package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Demonstration program for testing most functionality of SimpleHashtable
 */
public class SimpleHashtableDemo {

	/**
	 * Entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		// fill data:
		System.out.println(examMarks.toString2());
		examMarks.put("Ivana", 1);
		System.out.println(examMarks.toString2());
		examMarks.put("Ante", 2);
		System.out.println(examMarks.toString2());
		examMarks.put("Jasna", 3);
		System.out.println(examMarks.toString2());
		examMarks.put("Kristina", 4);
		System.out.println(examMarks.toString2());
		examMarks.put("Tamara", 5);
		System.out.println(examMarks.toString2());
		examMarks.put("Ivana", 6);
		System.out.println(examMarks.toString2());

		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("\n\nKristina's exam grade is: " + kristinaGrade); // writes: 4
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 5

		// Print out the collection
		System.out.println(examMarks);

		// Print out the collection my way
		System.out.println("\n" + examMarks.toString2());

		// basic test for iterator - printing all entries using iterator
//		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//		}

		// cartesian product
//		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
//			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
//				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
//						pair2.getValue());
//			}
//		}

		// throws IllegalStateException
//		var iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove();
//				iter.remove();
//			}
//		}

		// remove Ivana
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
//			}
//		}

		// throws ConcurrentModificationException
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}

		// should print out and remove all entries
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//			iter.remove();
//		}
//		System.out.printf("Veličina: %d%n", examMarks.size());
//
//		System.out.println("\n" + examMarks.toString2());

	}
}
