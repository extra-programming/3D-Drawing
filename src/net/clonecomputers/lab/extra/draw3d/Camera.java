package net.clonecomputers.lab.extra.draw3d;

import static java.lang.Math.*;

public class Camera {
	private Point3D location;
	private Point3D direction; // vector
	
	/**
	 * MIGHT BE BROKEN
	 * @param p the point to translate
	 * @return where it should be rendered with the camera at the origin facing in the negative x direction
	 */
	public Point3D translateToViewframe(Point3D p) { //TODO: test me
		Point3D translated = Point3D.sum(p,Point3D.product(location, -1));
		
		Point3D p2 = new Point3D(translated.getPhi(), translated.getTheta() - direction.getTheta(), translated.getR());
		double xzDistance = hypot(p2.x, p2.z);
		double xzAngle = atan2(p2.z,p2.x);
		Point3D rotated = new Point3D(xzDistance*cos(xzAngle+direction.getPhi()),p2.y,xzDistance*sin(xzAngle+direction.getPhi()));
		
		Point3D scaled = Point3D.product(rotated, direction.getR());
		
		return scaled;
	}
}
