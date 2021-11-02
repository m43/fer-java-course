package searching.algorithms;

/**
 * Class models a node TODO
 * 
 * @author Frano Rajič
 * @param <S> The type of states that the Nodes are dealing with
 */
public class Node<S> {

	/**
	 * An reference to the parent node
	 */
	private Node<S> parent;

	/**
	 * The state stored in this node
	 */
	private S state;

	/**
	 * Cost for coming into this node
	 */
	private double cost;

	/**
	 * Construct an new node with given cost and parent node
	 * 
	 * @param parent the parent node
	 * @param state  the state to be stored in this node
	 * @param cost   the cost of coming into this state
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Get the state stored in the node
	 * 
	 * @return the state stored in th node
	 */
	public S getState() {
		return state;
	}

	/**
	 * Get the cost of getting into this node
	 * 
	 * @return the cost of the node
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Get the parent of this node - the node from which the searching came into
	 * this state
	 * 
	 * @return the parent of this node
	 */
	public Node<S> getParent() {
		return parent;
	}

}
