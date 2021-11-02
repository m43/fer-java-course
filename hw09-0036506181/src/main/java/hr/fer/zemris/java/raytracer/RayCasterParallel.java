package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This program draws beautifully colored balls with maximum processor power.
 * 
 * @author Frano Rajič
 */
public class RayCasterParallel {

	/**
	 * Constant used when comparing
	 */
	private static final double EPSILON = 0.01;

	/**
	 * Entry point for program
	 * 
	 * @param args irrelevant
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(RayTracerViewer.createPredefinedScene()), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Method is used to create an {@link IRayTracerProducer}.
	 * @param scene The scene that needs to be produced
	 * 
	 * @return an concrete implementation of {@link IRayTracerProducer} that takes
	 *         diffusion and reflection into account when producing the view of a
	 *         scene
	 */
	public static IRayTracerProducer getIRayTracerProducer(Scene scene) {
		return new IRayTracerProducer() {

			private short[] red;
			private short[] green;
			private short[] blue;

			private Point3D zAxis;
			private Point3D yAxis;
			private Point3D xAxis;
			private Point3D screenCorner;

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				class Job extends RecursiveAction {
					private static final long serialVersionUID = 1L;
					int yMin;
					int yMax;

					public Job(int yMin, int yMax) {
						super();
						this.yMin = yMin;
						this.yMax = yMax;
					}

					@Override
					protected void compute() {
						if (yMax - yMin <= 16) {
							computeDirect();
							return;
						}
						Job p1 = new Job(yMin, yMin + (yMax - yMin) / 2);
						Job p2 = new Job(yMin + (yMax - yMin) / 2 + 1, yMax);
						invokeAll(p1, p2);
					}

					private void computeDirect() {
						short[] rgb = new short[3];
						int offset = yMin * width;
						for (int y = yMin; y <= yMax; y++) {
							for (int x = 0; x < width; x++) {
								if (cancel.get())
									return;

								Point3D screenPoint = screenCorner
										.add(xAxis.scalarMultiply(horizontal * (x / (double) (width - 1))))
										.sub(yAxis.scalarMultiply(vertical * (y / (double) (height - 1))));
								Ray ray = Ray.fromPoints(eye, screenPoint);
								tracer(scene, ray, rgb);
								red[offset] = rgb[0] > 255 ? 255 : rgb[0];
								green[offset] = rgb[1] > 255 ? 255 : rgb[1];
								blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
								offset++;
							}
						}
					}
				}

				red = new short[width * height];
				green = new short[width * height];
				blue = new short[width * height];

				zAxis = view.sub(eye).negate().normalize();
				yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp)).negate()).normalize();
				xAxis = yAxis.vectorProduct(zAxis);
				screenCorner = view.add(yAxis.scalarMultiply(vertical / 2)).sub(xAxis.scalarMultiply(horizontal / 2));
				
				System.out.println("Započinjem izračune...");
				ForkJoinPool pool = new ForkJoinPool();

				Job p = new Job(0, height - 1);
				pool.invoke(p);
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Method to calculate the rgb values of one concrete ray
	 * 
	 * @param scene scene defines the surroundings
	 * @param ray   the ray that is being traced
	 * @param rgb   an array for saving the rendered rgb values
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;

		RayIntersection s = findClosestIntersection(scene, ray);
		if (s == null) {
			return;
		}

		// set ambient component
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		for (LightSource ls : scene.getLights()) {
			Ray rayFromLightSource = new Ray(ls.getPoint(), s.getPoint().sub(ls.getPoint()).normalize());

			if (findClosestIntersection(scene, rayFromLightSource).getDistance() + EPSILON < s.getPoint()
					.sub(ls.getPoint()).norm()) {
				continue;
			}

			// add diffuse component Id = Ii · kd · cos(θ)
			Point3D normalS = s.getNormal();
			double cosBetween = normalS.normalize().scalarProduct(rayFromLightSource.direction.negate().normalize());
			rgb[0] += ls.getR() * s.getKdr() * cosBetween;
			rgb[1] += ls.getG() * s.getKdg() * cosBetween;
			rgb[2] += ls.getB() * s.getKdb() * cosBetween;

			// add reflective components Is = Ii · ks · (^r · ^v)^n
			// je li okej ovako dug komad napisat?
			Ray reflectedRay = new Ray(s.getPoint(),
					rayFromLightSource.direction
							.sub(s.getNormal().normalize().scalarMultiply(2).scalarMultiply(
									rayFromLightSource.direction.scalarProduct(s.getNormal().normalize())))
							.normalize());

			cosBetween = reflectedRay.direction.scalarProduct(ray.direction.negate());
			double pow = Math.pow(cosBetween, s.getKrn());
			if (cosBetween > 0) {
				rgb[0] += ls.getR() * s.getKrr() * pow;
				rgb[1] += ls.getG() * s.getKrg() * pow;
				rgb[2] += ls.getB() * s.getKrb() * pow;
			}
		}
	}

	/**
	 * Method to look for the closest intersection of an ray in a given scene
	 * 
	 * @param scene the scene
	 * @param ray   the ray
	 * @return the intersection as an instance of {@link RayIntersection}
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {

		RayIntersection closest = null;

		for (GraphicalObject gogo : scene.getObjects()) {
			RayIntersection current = gogo.findClosestRayIntersection(ray);
			if (current == null) {
				continue;
			}
			if (closest == null) {
				closest = current;
			} else if (closest.getDistance() > current.getDistance()) {
				closest = current;
			}
		}

		return closest;
	}

}
