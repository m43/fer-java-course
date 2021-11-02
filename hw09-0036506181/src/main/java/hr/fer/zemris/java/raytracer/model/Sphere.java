package hr.fer.zemris.java.raytracer.model;

/**
 * Class models an sphere {@link GraphicalObject}. An sphere has an center and
 * radius.
 * 
 * @author Frano Rajič
 */
public class Sphere extends GraphicalObject {

	/**
	 * The center of the sphere
	 */
	Point3D center;

	/**
	 * The radius of the sphere
	 */
	double radius;

	/**
	 * The diffusion coefficient for red color
	 */
	double kdr;

	/**
	 * The diffusion coefficient for green color
	 */
	double kdg;

	/**
	 * The diffusion coefficient for blue color
	 */
	double kdb;

	/**
	 * The reflection coefficient for red color
	 */
	double krr;

	/**
	 * The reflection coefficient for green color
	 */
	double krg;

	/**
	 * The reflection coefficient for blue color
	 */
	double krb;

	/**
	 * The reflection coefficient used when calculating the reflected ray's impact
	 */
	double krn;

	/**
	 * Construct an sphere with all necessary information given.
	 * 
	 * @param center the center
	 * @param radius the radius
	 * @param kdr    the red color diffusion coefficient
	 * @param kdg    the green color diffusion coefficient
	 * @param kdb    the blue color diffusion coefficient
	 * @param krr    the red color reflection coefficient
	 * @param krg    the green color reflection coefficient
	 * @param krb    the blue color reflection coefficient
	 * @param krn    coefficient used when calculating reflected ray intensity
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	public RayIntersection findClosestRayIntersection(Ray ray) {

		// visualization https://www.youtube.com/watch?v=HFPlKQGChpE

		// T is the closest point of the array
		double t = center.sub(ray.start).scalarProduct(ray.direction);
		Point3D T = ray.start.add(ray.direction.scalarMultiply(t));

		double y = T.sub(center).norm();

		Point3D intersectionPoint;
		double distance;
		boolean outer;

		if (y > radius) {
			return null;
		} else if (areEqual(y, radius)) {
			intersectionPoint = T;
			distance = t;
			outer = true;
		} else {
			double x = Math.sqrt(radius * radius - y * y);
			if (t > 0) {
				distance = t - x;
				outer = false;
			} else {
				distance = t + x;
				outer = true;
			}
			intersectionPoint = ray.start.add(ray.direction.scalarMultiply(distance));
		}

		return new RayIntersection(intersectionPoint, distance, outer) {

			@Override
			public Point3D getNormal() {
				return intersectionPoint.sub(center);
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};

	}

	/**
	 * Constant to be used to compare components of two vectors. If the abs value of
	 * (firstDouble - secondDoule) is less than this constant, then these two
	 * doubles are considered to be equal.
	 */
	public static final double EPSILON = 0.0000001;

	/**
	 * Help function to check if two doubles are equal. Two doubles are equal if the
	 * absolute value of their difference is less then constant
	 * {@link Sphere#EPSILON}
	 * 
	 * @param a first double to compare
	 * @param b second double to compare
	 * @return true if given doubles are equal
	 */
	private boolean areEqual(double a, double b) {
		return Math.abs(a - b) < EPSILON;
	}
}