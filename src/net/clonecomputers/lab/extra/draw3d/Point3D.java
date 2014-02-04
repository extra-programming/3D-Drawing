package net.clonecomputers.lab.extra.draw3d;

import static java.lang.Math.*;

public class Point3D {
	public final double x;
	public final double y;
	public final double z;
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public String toString() {
		return String.format("(%f,%f,%f)", x,y,z);
	}
	
	public static double dot(Point3D a, Point3D b) {
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	public static Point3D cross(Point3D a, Point3D b) {
		return new Point3D(
			a.y*b.z - b.y*a.z,
			a.z*b.x - b.z*a.x,
			a.x*b.y - b.x*a.y
		);
	}
	public static Point3D sum(Point3D a, Point3D b) {
		return new Point3D(
			a.x + b.x,
			a.y + b.y,
			a.z + b.z
		);
	}
	public static Point3D sum(Point3D... points) {
		Point3D sumSoFar = new Point3D(0,0,0);
		for(Point3D p: points) {
			sumSoFar = sum(sumSoFar,p);
		}
		return sumSoFar;
	}
	public static double abs(Point3D a) {
		return sqrt(dot(a,a));
	}
	public static Point3D normalize(Point3D a) {
		return product(a,1/abs(a));
	}
	public static Point3D product(Point3D a, double b) {
		return new Point3D(
			a.x * b,
			a.y * b,
			a.z * b
		);
	}
	public static double dist(Point3D a, Point3D b) {
		return abs(sum(a,product(b,-1)));
	}
	
	
	
	
	public double getPhi() {
		return asin(z/abs(this));
	}
	
	public double getTheta() {
		return atan2(x,y);
	}
	
	public double getR() {
		return abs(this);
	}
	
	public static Point3D polar(double phi, double theta, double r) {
		return new Point3D(r*cos(phi)*cos(theta), r*cos(phi)*sin(theta), r*sin(phi));
	}
	
}