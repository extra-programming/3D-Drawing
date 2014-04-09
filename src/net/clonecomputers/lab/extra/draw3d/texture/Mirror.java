package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;
import net.clonecomputers.lab.extra.draw3d.*;

public class Mirror implements Texture {

	@Override
	public Color getRayColor(Ray r, Ray normal, Point2D textureCoordinates) {
		Point3D d = r.getDirection();
		Point3D n = normal.getDirection();
		Point3D reflectionDir = diff(d, prod(n, 2*dot(d, n)));
		Ray reflection = new Ray(normal.getLocation(), reflectionDir);
		return World.getWorld().traceRay(reflection);
	}

}
