package searching.slagalica;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * TODO
 * 
 * @author Frano Rajič
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * The cost of one move in the puzzle
	 */
	private static final double COST_OF_MOVE = 1;
	/**
	 * The starting puzzle configuration
	 */
	private KonfiguracijaSlagalice referenceConfig;

	/**
	 * Construct an puzzle with given start configuration
	 * 
	 * @param referenceConfig The starting configuration of the puzzle
	 */
	public Slagalica(KonfiguracijaSlagalice referenceConfig) {
		this.referenceConfig = referenceConfig;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		int counter = 1;
		for (int i : t.getConfig()) {
			if (counter != i && counter != 9) {
				return false;
			}
			counter++;
		}

		return true;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();

		int spaceIndex = t.indexOfSpace();

		int a = spaceIndex / 3;
		int b = spaceIndex % 3;

		if (a > 0) {
			list.add(transitionFromAlteredConfig(t, spaceIndex, (a - 1) * 3 + b));
		}

		if (a < 2) {
			list.add(transitionFromAlteredConfig(t, spaceIndex, (a + 1) * 3 + b));
		}

		if (b > 0) {
			list.add(transitionFromAlteredConfig(t, spaceIndex, a * 3 + b - 1));

		}

		if (b < 2) {
			list.add(transitionFromAlteredConfig(t, spaceIndex, a * 3 + b + 1));
		}

		return list;
	}

	/**
	 * Help method to create an transition for config altered by only moving the
	 * space by one places. The old and new index of the space must be given to
	 * create the transition.
	 * 
	 * @param t             the original configuration
	 * @param oldSpaceIndex the old index of the space
	 * @param newSpaceIndex the new index of the space
	 * @return the Transition for the space that has been moved
	 */
	private Transition<KonfiguracijaSlagalice> transitionFromAlteredConfig(KonfiguracijaSlagalice t, int oldSpaceIndex,
			int newSpaceIndex) {
		int[] newConfig = t.getConfig();
		int temp = newConfig[newSpaceIndex];
		newConfig[newSpaceIndex] = 0;
		newConfig[oldSpaceIndex] = temp;

		return new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(newConfig), COST_OF_MOVE);

	}

	@Override
	public KonfiguracijaSlagalice get() {
		return referenceConfig;
	}

}
