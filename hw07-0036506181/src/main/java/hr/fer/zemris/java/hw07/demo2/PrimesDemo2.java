package hr.fer.zemris.java.hw07.demo2;

/**
 * Class represents an demonstration program for the use of
 * {@link PrimesCollection}
 * 
 * @author Frano Rajič
 */
public class PrimesDemo2 {

	/**
	 * Entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
