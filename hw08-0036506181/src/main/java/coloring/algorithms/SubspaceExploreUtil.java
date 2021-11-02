package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class offers space exploring functionality
 * 
 * @author Frano Rajič
 */
public class SubspaceExploreUtil {

	/**
	 * Method to do the actual space exploration - going through states in a Breadth
	 * First Search (BFS) manner
	 * 
	 * @param s0         The supplier of the starting state
	 * @param process    The Action to be consumed if an state is acceptable
	 * @param succ       The succeeding states of a state
	 * @param acceptable The predicate to check if an state is acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> listNext = new LinkedList<>();
		listNext.add(s0.get());

		while (!listNext.isEmpty()) {
			S next = listNext.removeFirst();
			if (!acceptable.test(next))
				continue;
			process.accept(next);
			listNext.addAll(succ.apply(next));
		}
	}

	/**
	 * Method to do the actual space exploration - going through states in a Breadth
	 * First Search (BFS) manner. The exploration traverse does not visit same
	 * states multiple times - it remembers the ones that were visited.
	 * 
	 * @param s0         The supplier of the starting state
	 * @param process    The Action to be consumed if an state is acceptable
	 * @param succ       The succeeding states of a state
	 * @param acceptable The predicate to check if an state is acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> listNext = new LinkedList<>();
		listNext.add(s0.get());

		Set<S> visited = new HashSet<S>();
		visited.add(s0.get());

		while (!listNext.isEmpty()) {
			S next = listNext.removeFirst();
			if (!acceptable.test(next))
				continue;
			process.accept(next);
			for (S s : succ.apply(next)) {
				if (!visited.contains(s)) {
					listNext.add(s);
					visited.add(s);
				}
			}
		}
	}

	/**
	 * Method to do the actual space exploration - going through states in a Depth
	 * First Search (DFS) manner
	 * 
	 * @param s0         The supplier of the starting state
	 * @param process    The Action to be consumed if an state is acceptable
	 * @param succ       The succeeding states of a state
	 * @param acceptable The predicate to check if an state is acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> listNext = new LinkedList<>();
		listNext.add(s0.get());

		while (!listNext.isEmpty()) {
			S next = listNext.removeFirst();
			if (!acceptable.test(next))
				continue;
			process.accept(next);
			listNext.addAll(0, succ.apply(next));
		}
	}
}
