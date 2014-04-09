package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;

import net.clonecomputers.lab.extra.draw3d.*;

public class Lens implements Texture {
	private double indexOfRefractionRatio;
	
	public Lens(double indexOfRefractionRatio) {
		this.indexOfRefractionRatio = indexOfRefractionRatio;
	}
	
	public Lens(double index1, double index2) {
		this.indexOfRefractionRatio = index1 / index2;
	}

	@Override
	public Color getRayColor(Ray ray, Ray normal) {
		Point3D l = ray.getDirection();
		Point3D n = normal.getDirection();
		double r = indexOfRefractionRatio;
		double c = -dot(n,l);
		Point3D refractedDir = sum(prod(l,r), prod(n,r*c - Math.sqrt(1-r*r*(1-c*c))));
		Ray refracted = new Ray(normal.getLocation(), refractedDir);
		return World.getWorld().traceRay(refracted);
	}

}
