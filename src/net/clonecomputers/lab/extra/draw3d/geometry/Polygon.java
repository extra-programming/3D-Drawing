package net.clonecomputers.lab.extra.draw3d.geometry;

import java.util.*;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;
import static net.clonecomputers.lab.extra.draw3d.Point2D.*;
import net.clonecomputers.lab.extra.draw3d.*;
import net.clonecomputers.lab.extra.draw3d.geometry.*;
import net.clonecomputers.lab.extra.draw3d.geometry.polygonutils.*;

public class Polygon implements SurfaceGeometry {
	/**
	 * The first 3 points MUST not be colinear.
	 * All points are assumed to be coplanar.
	 */
	private List<Point3D> points;
	private Point3D normal; // vector
	
	private List<Point2D> hull;
	
	/**
	 * Makes a polygon with the specified points.
	 * The first 3 points MUST not be colinear.
	 * All points are assumed to be coplanar.
	 * @param points
	 */
	public Polygon(List<Point3D> points) {
		if(points.size() < 3) throw new IllegalArgumentException("Must have at least 3 points");
		if(abs(cross(diff(points.get(1),points.get(0)),diff(points.get(2),points.get(0)))) < .0001) {
			throw new IllegalArgumentException("First 3 points must not be colinear");
		}
		this.points = points;
		this.normal = norm(cross(diff(points.get(1),points.get(0)),diff(points.get(2),points.get(0))));
		List<Point2D> projectedPoints = new ArrayList<Point2D>();
		for(Point3D p: points) {
			projectedPoints.add(getTextureCoordinates(p));
		}
		hull = ConvexHullTester.getConvexHull(projectedPoints);
	}
	
	@Override
	public Point2D getTextureCoordinates(Point3D p) {
		int axis = 0;
		if(dot(normal, new Point3D(1,0,0)) < .0001) axis = 1;
		if(axis == 1 && dot(normal, new Point3D(0,1,0)) < .0001) axis = 2;
		Point3D axisVector;
		switch(axis) {
		case 0: // x axis
			axisVector = new Point3D(1,0,0);
			break;
		case 1: // y axis
			axisVector = new Point3D(0,1,0);
			break;
		case 2: // z axis
			axisVector = new Point3D(0,0,1);
			break;
		default:
			throw new InternalError("invalid axis: this should never happen");
	}
		Point3D paralell = cross(axisVector, normal);
		Point3D inPlane;
		if(abs(paralell) > .001) { // distorted
			Point3D slope = cross(paralell, normal);
			inPlane = new Point3D(p.x*abs(slope)/slope.x, p.y*abs(slope)/slope.y, p.z*abs(slope)/slope.z);
		}
		switch(axis) {
			case 0: // project onto yz plane
				return new Point2D(p.y,p.z);
			case 1: // project onto yz plane
				return new Point2D(p.x,p.z);
			case 2: // project onto yz plane
				return new Point2D(p.x,p.y);
			default:
				throw new InternalError("invalid axis: this should never happen");
		}
	}
	
	@Override
	public Point3D getIntersection(Ray r) {
		double distance = dot(diff(points.get(0),r.getLocation()),normal) / dot(r.getDirection(), normal);
		Point3D intersection = sum(r.getLocation(), prod(r.getDirection(), distance));
		
		if(!ConvexHullTester.isInsideConvexHull(hull, getTextureCoordinates(intersection))) return null;
		
		return intersection;
	}

	@Override
	public Point3D getNormal(Point3D p) {
		return normal;
	}

}
