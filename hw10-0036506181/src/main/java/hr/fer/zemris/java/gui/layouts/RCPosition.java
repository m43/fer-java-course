package hr.fer.zemris.java.gui.layouts;

/**
 * This class defines an constraint describing the position using row number and
 * column number.
 * 
 * @author Frano Rajič
 */
public class RCPosition {

	/**
	 * Read only integer telling the row position of an component
	 */
	private final int row;

	/**
	 * Read only integer telling the column position of an component
	 */
	private final int column;

	/**
	 * Instantiate an {@link RCPosition} object with given position row and column.
	 * 
	 * @param row    the row
	 * @param column the column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Get the row position
	 * 
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Get the column position
	 * 
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Help method to create an {@link RCPosition} by parsing given Object. The
	 * given object can be of an instance of {@link RCPosition} itself or an String
	 * containing the necessary information in form "ROW,COLUMN", just like "1,3"
	 * for row=1 and column=3.
	 * 
	 * @param constraints the given constraints object upon which to create the
	 *                    {@link RCPosition} instance
	 * @return the created {@link RCPosition}
	 * @throws IllegalArgumentException thrown if an illegal constraints object is
	 *                                  given
	 */
	public static RCPosition parse(Object constraints) {
		if (constraints instanceof String) {
			String s = (String) constraints;
			String[] parts = s.trim().split(",");
			if (parts.length != 2) {
				throw new IllegalArgumentException("Invalid string given - cannot convert to RCPosition.");
			}

			try {
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);
				return new RCPosition(row, column);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Couldnt extract expected row and column numbers from given position string.");
			}
		}
		if (!(constraints instanceof RCPosition)) {
			throw new IllegalArgumentException("The given constraint cannot be interpretted as an RCPosition");
		}
		return (RCPosition) constraints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
