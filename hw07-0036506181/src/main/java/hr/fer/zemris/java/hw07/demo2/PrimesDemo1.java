package hr.fer.zemris.java.hw07.demo2;

/**
 * Class represents an demonstration program for the use of
 * {@link PrimesCollection}
 * 
 * @author Frano Rajič
 */
public class PrimesDemo1 {

	/**
	 * Entry point for demonstration program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
