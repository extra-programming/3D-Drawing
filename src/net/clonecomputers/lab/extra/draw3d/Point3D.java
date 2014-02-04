package net.clonecomputers.lab.extra.draw3d;

import static java.lang.Math.*;

public class Point3D {
	
	public final double x;
	public final double y;
	public final double z;
	
	/**
	 * Create a new point from coordinates
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns a string representing the point in the format (x,y,z)
	 */
	@Override
	public String toString() {
		return String.format("(%f,%f,%f)", x,y,z);
	}
	
	/**
	 * Finds the dot product of two Point3D
	 * @return A double representing the dot product of the two points
	 */
	public static double dot(Point3D a, Point3D b) {
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	/**
	 * Finds the cross product of two Point3D. Note that the cross product is not commutative!
	 * @param a The first point
	 * @param b The second point
	 * @return A Point3D representing the cross product axb
	 */
	public static Point3D cross(Point3D a, Point3D b) {
		return new Point3D(
			a.y*b.z - b.y*a.z,
			a.z*b.x - b.z*a.x,
			a.x*b.y - b.x*a.y
		);
	}
	
	/**
	 * Finds the sum of an arbitrary number of Point3D
	 * @param points the points to sum
	 * @return A Point3D representing the sum of points
	 */
	public static Point3D sum(Point3D... points) {
		double xSum = 0;
		double ySum = 0;
		double zSum = 0;
		for(Point3D p: points) {
			xSum += p.x;
			ySum += p.y;
			zSum += p.z;
		}
		return new Point3D(xSum, ySum, zSum);
	}
	
	/**
	 * Finds the magnitude of a Point3D
	 * @return A double representing the magnitude
	 */
	public static double abs(Point3D a) {
		return sqrt(dot(a,a));
	}
	
	/**
	 * Finds the normalized Point3D of a Point3D
	 * @return A Point3D which has the same direction as the arguement but has a magnitude of one
	 */
	public static Point3D normalize(Point3D a) {
		return product(a,1/abs(a));
	}
	
	/**
	 * Finds the product of a scalar and a Point3D
	 * @param b a scalar
	 * @return A Point3D representing the product of the Point3D and the scalar
	 */
	public static Point3D product(Point3D a, double b) {
		return new Point3D(
			a.x * b,
			a.y * b,
			a.z * b
		);
	}
	
	/**
	 * Finds the positive distance between two Point3D
	 * @return A scalar representing the length of the distance between the two Point3D
	 */
	public static double dist(Point3D a, Point3D b) {
		return abs(sum(a,product(b,-1)));
	}
	
}