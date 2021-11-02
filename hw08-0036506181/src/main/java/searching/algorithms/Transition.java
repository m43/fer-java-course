package searching.algorithms;

/**
 * This class models transitions from one state to another with a cost to it.
 * 
 * @author Frano Rajič
 * @param <S> The type of states that the Transitions are dealing with
 */
public class Transition<S> {

	/**
	 * The resulting state of the transition (aka the new state)
	 */
	private S state;

	/**
	 * The cost of doing this transition
	 */
	private double cost;

	/**
	 * Create an transition with given result state and cost of doing the transition
	 * 
	 * @param state the result state of the transition
	 * @param cost  the cost of the transition
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Get the state where this transition leads to
	 * 
	 * @return the resulting state of the transition
	 */
	public  S getState() {
		return state;
	}

	/**
	 * Get the cost of this transition
	 * 
	 * @return the cost of this transition
	 */
	public  double getCost() {
		return cost;
	}

}
