package searching.slagalica;

import java.util.Arrays;

/**
 * Class models configurations of a puzzle.
 * 
 * @author Frano Rajič
 */
public class KonfiguracijaSlagalice {

	/**
	 * The configuration array of the puzzle.
	 */
	private int[] config;

	/**
	 * Create an puzzle configuration with given configuration
	 * 
	 * @param config the puzzle configuration to store in instance
	 */
	public KonfiguracijaSlagalice(int[] config) {
		super();
		this.config = config;
	}

	/**
	 * Get an copy of the current puzzle configuration
	 * 
	 * @return an copy of the current puzzle configuration array
	 */
	public int[] getConfig() {
		return Arrays.copyOf(config, config.length);
	}
	
	/**
	 * Get an copy of the current puzzle configuration
	 * 
	 * @return an copy of the current puzzle configuration array
	 */
	public int[] getPolje() {
		//da mi radi sa gui
		return Arrays.copyOf(config, config.length);
	}

	/**
	 * Get the index of space element in array representation of puzzle
	 * 
	 * @return the index of space in array representation of puzzle
	 * @throws IllegalStateException if in some situation the puzzle configuration
	 *                               does not have an space inside
	 */
	public int indexOfSpace() {
		for (int i = 0; i < config.length; i++) {
			if (config[i] == 0) {
				return i;
			}
		}

		throw new IllegalStateException("Every posible configuration should contain an space!");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(config);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(config, other.config))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 9; i++) {
			sb.append(String.valueOf(config[i - 1]).replace('0', '*'));
			if (i % 3 == 0 && i != 9) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

}
