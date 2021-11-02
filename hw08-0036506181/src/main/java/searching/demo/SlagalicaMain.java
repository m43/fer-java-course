package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Demo program to run the puzzle solving algorithms with included gui solution
 * 
 * @author Frano Rajič
 */
public class SlagalicaMain {

	/**
	 * Main entry point for demonstration program.
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(
					"Invalid number of arguments - exactly one is needed and should contain the starting configuration for exmple 123654780");
			return;
		}

		String configurationString = args[0];
		if (configurationString.length() != 9) {
			System.out.println("Configuration length should be 9 digits");
			return;
		}

		for (int i = 0; i < 9; i++) {
			if (!configurationString.contains(String.valueOf(i))) {
				System.out.println("The configuration needs to contain all digits from 0-8 for the it to be valid!");
				return;
			}
		}

		int[] configurationArray = configurationString.chars().map(x -> Integer.valueOf(x - '0').intValue()).toArray();

		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(configurationArray));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});

			System.out.println("Pokušajmo to i vizualno vidjeti: ");

			// ne radi mi u terminalu, ali u eclipse radi sve po PSu kada stavim run configuration sa "230146758"
			SlagalicaViewer.display(rješenje);

		}
	}

}
