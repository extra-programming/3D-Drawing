package net.clonecomputers.lab.extra.draw3d.geometry;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;
import net.clonecomputers.lab.extra.draw3d.*;
import net.clonecomputers.lab.extra.draw3d.geometry.*;

public class SphereSection implements SurfaceGeometry {
	private Point3D center;
	private Point3D radius;
	private double arcsize;
	
	/**
	 * the length of the radius
	 */
	private double radiusL;
	
	public SphereSection(Point3D center, Point3D radius, double arcsize) {
		this.center = center;
		this.radius = radius;
		this.arcsize = arcsize;
		this.radiusL = dist(center, radius);
	}
	
	@Override
	public Point3D getIntersection(Ray r) {
		Point3D tCenter = r.translateToViewframe(center);
		//TODO: not implemented
		return null;
	}

	@Override
	public Point3D getNormal(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}
}
