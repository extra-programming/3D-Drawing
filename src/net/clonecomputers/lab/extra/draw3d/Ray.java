package net.clonecomputers.lab.extra.draw3d;

import static java.lang.Math.*;
import static net.clonecomputers.lab.extra.draw3d.Point3D.*;

public class Ray {
	private Point3D location;
	private Point3D direction; // vector
	
	public Ray(Point3D location, Point3D directon) {
		this.location = location;
		this.direction = directon;
	}
	
	public Point3D getLocation() {
		return location;
	}
	
	public Point3D getDirection() {
		return direction;
	}
	
	/**
	 * MIGHT BE BROKEN
	 * @param vector the vector to rotate
	 * @return the relative vector when this ray is at the origin facing in the negative x direction
	 */
	public Point3D rotateToViewFrame(Point3D vector) { //TODO: test me
		Point3D p2 = polar(vector.getPhi(), vector.getTheta() - direction.getTheta(), vector.getR());
		double xzDistance = hypot(p2.x, p2.z);
		double xzAngle = atan2(p2.z,p2.x);
		return new Point3D(xzDistance*cos(xzAngle+direction.getPhi()),p2.y,xzDistance*sin(xzAngle+direction.getPhi()));
		
	}
	
	/**
	 * MIGHT BE BROKEN
	 * @param p the point to translate
	 * @return the relative vector when this ray is at the origin facing in the negative x direction
	 */
	public Point3D translateToViewframe(Point3D p) { //TODO: test me
		Point3D translated = sum(p,product(location, -1));
		
		Point3D p2 = polar(translated.getPhi(), translated.getTheta() - direction.getTheta(), translated.getR());
		double xzDistance = hypot(p2.x, p2.z);
		double xzAngle = atan2(p2.z,p2.x);
		Point3D rotated = new Point3D(xzDistance*cos(xzAngle+direction.getPhi()),p2.y,xzDistance*sin(xzAngle+direction.getPhi()));
		
		Point3D scaled = product(rotated, direction.getR());
		
		return scaled;
	}
	
	public String toString() {
		return String.format("Ray from %s to %s, with direction %s", location, sum(location,direction), direction);
	}
}
