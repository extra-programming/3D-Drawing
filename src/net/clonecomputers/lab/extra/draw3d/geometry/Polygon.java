package net.clonecomputers.lab.extra.draw3d.geometry;

import java.util.*;

import net.clonecomputers.lab.extra.draw3d.*;
import net.clonecomputers.lab.extra.draw3d.geometry.*;

public class Polygon implements SurfaceGeometry {
	/**
	 * No 3 consecutive points may be colinear
	 */
	private List<Point3D> points;
	private Point3D normal; // vector
	
	public Polygon(List<Point3D> points) {
		this.points = points;
	}
	
	@Override
	public Point3D getIntersection(Ray r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point3D getNormal(Point3D p) {
		// TODO Auto-generated method stub
		return null;
	}

}
