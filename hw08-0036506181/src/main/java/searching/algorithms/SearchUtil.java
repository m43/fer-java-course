package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.slagalica.Slagalica;

/**
 * Class contains algorithms for solving the puzzle {@link Slagalica}
 * 
 * @author Frano Rajič
 */
public class SearchUtil {

	/**
	 * Method to look for the actual solution - going through states in a Breadth
	 * First Search (BFS) manner.
	 * 
	 * 
	 * @param s0   The supplier of the starting state
	 * @param succ The succeeding states of a state
	 * @param goal The goal that needs to be reached, checking if reached
	 * @return null if not fount and the solution node if found
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> nextList = new LinkedList<>();
		nextList.add(new Node<S>(null, s0.get(), 0));

		while (!nextList.isEmpty()) {
			Node<S> node = nextList.removeFirst();
			if (goal.test(node.getState())) {
				return node;
			}

			for (Transition<S> trans : succ.apply(node.getState())) {
				nextList.add(new Node<>(node, trans.getState(), node.getCost() + trans.getCost()));
			}
		}
		return null;
	}

	/**
	 * Method to look for the actual solution - going through states in a Breadth
	 * First Search (BFS) manner. The exploration traverse does not visit same
	 * states multiple times - it remembers the ones that were visited.
	 * 
	 * 
	 * @param s0   The supplier of the starting state
	 * @param succ The succeeding states of a state
	 * @param goal The goal that needs to be reached, checking if reached
	 * @return null if not fount and the solution node if found
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> nextList = new LinkedList<>();
		nextList.add(new Node<S>(null, s0.get(), 0));

		Set<S> visitedStates = new HashSet<>();
		visitedStates.add(s0.get());

		while (!nextList.isEmpty()) {
			Node<S> node = nextList.removeFirst();
			if (goal.test(node.getState())) {
				return node;
			}

			for (Transition<S> trans : succ.apply(node.getState())) {
				if (!visitedStates.contains(trans.getState())) {
					visitedStates.add(trans.getState());
					nextList.add(new Node<>(node, trans.getState(), node.getCost() + trans.getCost()));
				}
			}
		}
		return null;
	}
}
