package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element to store an operator. Read only values of operator only permitted.
 * 
 * @author Frano
 *
 */
public class ElementOperator extends Element {
	/**
	 * Symbol of operation to store
	 */
	private String symbol;


	/**
	 * Construct the element with given symbol of the operation
	 * 
	 * @param symbol of operation
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Get the stored symbol of the operation
	 * @return the operation symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return asText();
	}

}
