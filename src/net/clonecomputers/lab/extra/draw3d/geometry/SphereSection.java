package net.clonecomputers.lab.extra.draw3d.geometry;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;
import static java.lang.Math.*;
import net.clonecomputers.lab.extra.draw3d.*;
import net.clonecomputers.lab.extra.draw3d.geometry.*;

public class SphereSection implements SurfaceGeometry {
	private Point3D center;
	/**
	 * a vector from the center of the sphere to the center of the sphereSection
	 */
	private Point3D radius;
	private double arcsize;
	
	/**
	 * the length of the radius
	 */
	private double radiusL;
	
	private double maximumDistanceFromRadius;
	
	/**
	 * 
	 * @param center the center of the sphere
	 * @param radius a vector from the center of the sphere to the center of the sphereSection
	 * @param arcsize the arc size in radians (pi for a full circle)
	 */
	public SphereSection(Point3D center, Point3D radius, double arcsize) {
		this.center = center;
		this.radius = radius;
		this.arcsize = arcsize;
		this.radiusL = abs(radius);
		this.maximumDistanceFromRadius = radiusL*abs(2*sin(arcsize/2));
	}
	
	@Override
	public Point3D getIntersection(Ray ray) {
		double distanceFromCenter = abs(cross(diff(center,ray.getLocation()),ray.getDirection()));
		if(distanceFromCenter >= radiusL) return null;
		
		//now we know it intersects the sphere – but where?
		Point3D l = ray.getDirection();
		Point3D o = ray.getLocation();
		Point3D c = center;
		double r = radiusL;
		Point3D omc = diff(o,c);
		double ldomc = dot(l, omc);
		double distance1 = -ldomc - sqrt(ldomc*ldomc - dot(omc,omc) + r*r);
		double distance2 = -ldomc + sqrt(ldomc*ldomc - dot(omc,omc) + r*r);
		Point3D intersection1 = sum(ray.getLocation(), prod(ray.getDirection(), distance1));
		Point3D intersection2 = sum(ray.getLocation(), prod(ray.getDirection(), distance2));
		
		Point3D intersection = intersection1;
		if(dist(intersection,sum(center,radius)) > maximumDistanceFromRadius) intersection = intersection2;
		if(dist(intersection,sum(center,radius)) > maximumDistanceFromRadius) return null;
		
		return intersection;
	}

	@Override
	public Point3D getNormal(Point3D p) {
		return norm(diff(p,center));
	}

	@Override
	public Point2D getTextureCoordinates(Point3D p) {
		double r = 2*asin(dist(p,radius)/2);
		Point3D angle0 = cross(radius, new Point3D(1,0,0));
		if(abs(angle0) < .0001) angle0 = cross(diff(radius,center),new Point3D(0,1,0));
		Point3D angle = cross(radius,diff(p,center));
		double theta = dot(norm(angle0), norm(angle));
		return new Point2D(r*cos(theta), r*sin(theta));
	}
}
