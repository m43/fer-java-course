package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class provides an implementation of {@link IFractalProducer} that produces
 * newton-raphson fractals.
 * 
 * @author Frano Rajič
 */
public class NewtonProducer implements IFractalProducer {

	/**
	 * Class that represents jobs {@link Callable}.
	 * 
	 * @author Frano Rajič
	 */
	public class CalculateJob implements Callable<Void> {
		/**
		 * The minimal value of real coefficient
		 */
		double reMin;

		/**
		 * The maxaimal value of real coefficient
		 */
		double reMax;

		/**
		 * The minimal value of imaginary coefficient
		 */
		double imMin;

		/**
		 * The maximal value of imaginary coefficient
		 */
		double imMax;

		/**
		 * The width of the drawing area
		 */
		int width;

		/**
		 * The height of the drawing area
		 */
		int height;

		/**
		 * The y to start drawing from
		 */
		int yMin;

		/**
		 * The y value to stop drawing at
		 */
		int yMax;

		/**
		 * Number of colors used for drawing
		 */
		int m;

		/**
		 * Array to store calculated values
		 */
		short[] data;

		/**
		 * {@link AtomicBoolean} used to cancel the calculation
		 */
		AtomicBoolean cancel;

		/**
		 * Create an job with all necessary information provided
		 * 
		 * @param reMin  The minimal value of real coefficient
		 * @param reMax  The maxaimal value of real coefficient
		 * @param imMin  The minimal value of imaginary coefficient
		 * @param imMax  The maximal value of imaginary coefficient
		 * @param width  The width of the drawing area
		 * @param height The height of the drawing area
		 * @param yMin   The y to start drawing from
		 * @param yMax   The y value to stop drawing at
		 * @param m      Number of colors used for drawing
		 * @param data   Array to store calculated values
		 * @param cancel {@link AtomicBoolean} used to cancel the calculation
		 */
		public CalculateJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public Void call() {

			double convergenceTreshold = 0.002;
			double rootTreshold = 0.001;
			int maxIteration = 64;
			int offset = yMin * width;
			
			Complex c, zn, znOld, numerator, denominator;
			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x <= width - 1; x++) {
					c = mapToComplexPlain(x, y);
					zn = c;

					int iter = 0;
					do {
						numerator = polynomial.apply(zn);
						denominator = derived.apply(zn);
						znOld = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						iter++;
					} while (znOld.sub(zn).module() > convergenceTreshold && iter < maxIteration);
					short index = (short) polynomial.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);
				}
			}

			return null;
		}

		/**
		 * Help method to linearly map (x,y) coordinates of plain to a real number
		 * 
		 * @param x x of pixel
		 * @param y y of pixel
		 * @return the complex number resulting from linear mapping of pixel (x,y)
		 */
		private Complex mapToComplexPlain(int x, int y) {

			double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
			double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

			return new Complex(cre, cim);
		}

	}

	/**
	 * The polynomial in root form used for fractal drawing
	 */
	private ComplexRootedPolynomial polynomial;

	/**
	 * The polynomial used for fractal drawing
	 */
	private ComplexPolynomial polynomialCR;

	/**
	 * The first derivative of the polynomial
	 */
	private ComplexPolynomial derived;

	/**
	 * The thread pool used for multithreading
	 */
	ExecutorService pool;

	/**
	 * Create an newton producer with given polynomial as
	 * {@link ComplexRootedPolynomial}
	 * 
	 * @param polynomial the polynomial
	 */
	public NewtonProducer(ComplexRootedPolynomial polynomial) {
		this.polynomial = polynomial;
		polynomialCR = polynomial.toComplexPolynom();
		derived = polynomialCR.derive();
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Zapocinjem izracun...");

		short m = (short) (polynomialCR.order() + 1);
		short[] data = new short[width * height];

		final int sections = Runtime.getRuntime().availableProcessors() * 8;
		int yLinesPerSection = height / sections;

		List<Future<Void>> results = new ArrayList<>();

		for (int i = 0; i < sections; i++) {
			int yMin = i * yLinesPerSection;
			int yMax = (i + 1) * yLinesPerSection - 1;
			if (i == yLinesPerSection - 1) {
				yMax = height - 1;
			}
			CalculateJob job = new CalculateJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
					cancel);
			results.add(pool.submit(job));
		}

		for (Future<Void> job : results) {
			try {
				job.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		if (cancel.get()) {
			System.out.println("Crtanje otkazano.");
			return;
		}
		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, m, requestNo);
	}

}
