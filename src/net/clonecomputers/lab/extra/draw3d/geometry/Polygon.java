package net.clonecomputers.lab.extra.draw3d.geometry;

import java.util.*;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;

import net.clonecomputers.lab.extra.draw3d.*;
import net.clonecomputers.lab.extra.draw3d.geometry.*;

public class Polygon implements SurfaceGeometry {
	/**
	 * The first 3 points MUST not be colinear.
	 * All points are assumed to be coplanar.
	 */
	private List<Point3D> points;
	private Point3D normal; // vector
	
	/**
	 * Makes a polygon with the specified points.
	 * The first 3 points MUST not be colinear.
	 * All points are assumed to be coplanar.
	 * @param points
	 */
	public Polygon(List<Point3D> points) {
		this.points = points;
		this.normal = norm(cross(diff(points.get(1),points.get(0)),diff(points.get(2),points.get(0))));
	}
	
	@Override
	public Point3D getIntersection(Ray r) {
		double distance = dot(diff(points.get(0),r.getLocation()),normal) / dot(r.getDirection(), normal);
		Point3D intersection = sum(r.getLocation(), prod(r.getDirection(), distance));
		
		//TODO: figure out if it intersects
		
		return intersection;
	}

	@Override
	public Point3D getNormal(Point3D p) {
		return normal;
	}

}
