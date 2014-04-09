package net.clonecomputers.lab.extra.draw3d;

import java.awt.*;

import net.clonecomputers.lab.extra.draw3d.geometry.*;
import net.clonecomputers.lab.extra.draw3d.texture.*;

public class Surface {
	private SurfaceGeometry geometry;
	private Texture texture;
	
	public Surface(SurfaceGeometry geometry, Texture texture) {
		this.geometry = geometry;
		this.texture = texture;
	}

	/**
	 * Find the intersection point of a ray with this surface.
	 * @param r the ray to intersect with this surface
	 * @return the point the ray intersects this surface,
	 * or null if it doesn't
	 */
	public Point3D getIntersection(Ray r) {
		return geometry.getIntersection(r);
	}

	/**
	 * Find the color that this surface will
	 * appear to have when a given ray collides with it.
	 * If necessary, do some reflection or refraction and
	 * return the color the reflected or refracted ray finds.
	 * <br />
	 * This method just calls getRayColor(Ray, Point3D)
	 * after finding the intersection, so that is
	 * mor efficient if the intersection is already known.
	 * @param r the ray
	 * @return The color that a ray will
	 * appear to have after colliding with this surface.
	 */
	public Color getRayColor(Ray r) {
		Point3D intersection = geometry.getIntersection(r);
		return getRayColor(r, intersection);
	}
	
	/**
	 * Find the color that this surface will
	 * appear to have when a given ray collides with it.
	 * If necessary, do some reflection or refraction and
	 * return the color the reflected or refracted ray finds.
	 * @param r the ray
	 * @param intersection the intersection of the ray with this surface
	 * @return The color that a ray will
	 * appear to have after colliding with this surface.
	 */
	public Color getRayColor(Ray r, Point3D intersection) {
		return texture.getRayColor(r, new Ray(intersection, geometry.getNormal(intersection)));
	}

}
