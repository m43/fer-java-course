package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Class models an coloring giving functionality of pixels on pictures. Pixels
 * are modeled with {@link Pixel} and the pictures are modeled with
 * {@link Picture}.
 * 
 * @author Frano Rajič
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

	/**
	 * The reference Pixel is the starting state
	 */
	Pixel reference;

	/**
	 * The color where the coloring is done
	 */
	Picture picture;

	/**
	 * The color of filling
	 */
	int fillColor;

	/**
	 * The color of the reference pixel
	 */
	int refColor;

	/**
	 * Instantiate Coloring with given picture, reference Pixel and filling color
	 * 
	 * @param reference The reference Pixel
	 * @param picture   The picture to do the coloring to
	 * @param fillColor The color to fill where necessary
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> list = new LinkedList<>();

		Pixel up = new Pixel(t.x, t.y + 1);
		if (up.y < picture.getHeight()) {
			list.add(up);
		}

		Pixel down = new Pixel(t.x, t.y - 1);
		if (down.y >= 0) {
			list.add(down);
		}

		Pixel right = new Pixel(t.x + 1, t.y);
		if (right.x < picture.getWidth()) {
			list.add(right);
		}

		Pixel left = new Pixel(t.x - 1, t.y);
		if (left.x >= 0) {
			list.add(left);
		}

		return list;
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}

	@Override
	public boolean test(Pixel t) {
		return (refColor == picture.getPixelColor(t.x, t.y));
	}

}
