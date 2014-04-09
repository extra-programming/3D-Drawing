package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

import net.clonecomputers.lab.extra.draw3d.*;

public interface Texture {
	/**
	 * Find the color that this surface will
	 * appear to have when a given ray collides with it.
	 * If necessary, do some reflection or refraction and
	 * return the color the reflected or refracted ray finds.
	 * @param r the ray
	 * @param normal the normal to the surface at the point of intersection
	 * @param textureCoordinates the coordinates of the point in "texture space"
	 * this should be the value of this point using some mapping from the
	 * surface of the object to euclidean space.
	 * @see net.clonecomputers.lab.extra.draw3d.geometry.SurfaceGeometry.getTextureCoordinates
	 * @return the color that a ray will
	 * appear to have after colliding with this surface.
	 */
	public Color getRayColor(Ray r, Ray normal, Point2D textureCoordinates);
}
