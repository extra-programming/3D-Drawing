package net.clonecomputers.lab.extra.draw3d.geometry;

import java.awt.*;

import net.clonecomputers.lab.extra.draw3d.*;

public interface SurfaceGeometry {
	/**
	 * Find the intersection point of a ray with this surface.
	 * @param r the ray to intersect with this surface
	 * @return the point the ray intersects this surface,
	 * or null if it doesn't
	 */
	public Point3D getIntersection(Ray r);
	
	/**
	 * Finds the normal of this surface at a given point
	 * the point is assumed to be on the surface, but
	 * not checked because of efficiency reasons
	 * @param p the point on the surface to find the normal of.
	 * Assumed to be on the surface
	 * @return the normal vector
	 */
	public Point3D getNormal(Point3D p);
}
