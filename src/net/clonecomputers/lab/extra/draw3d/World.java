package net.clonecomputers.lab.extra.draw3d;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;
import static java.lang.Math.*;
import java.awt.*;
import java.util.List;

public class World {
	private static World world = null;
	
	public static World getWorld() {
		if(world == null) throw new IllegalStateException("World does not exist yet");
		return world;
	}
	
	private List<Surface> surfaces;
	private Color voidColor;
	
	public World(List<Surface> surfaces, Color voidColor) {
		if(world != null) throw new IllegalStateException("There can only be one world");
		world = this;
		this.surfaces = surfaces;
		this.voidColor = voidColor;
	}
	
	public Color traceRay(Ray r) {
		Surface closestIntersectingSurface = null;
		Point3D closestIntersection = null;
		double closestDistance = Double.MAX_VALUE;
		for(Surface s: surfaces) {
			Point3D p = s.getIntersection(r);
			if(p == null) continue; // didn't intersect
			double distance = 
					(r.getDirection().x != 0)? (p.x-r.getLocation().x)/r.getDirection().x:
					(r.getDirection().y != 0)? (p.y-r.getLocation().y)/r.getDirection().y:
				   /*r.getDirection().y != 0*/ (p.z-r.getLocation().z)/r.getDirection().z;
			if(distance < closestDistance && distance > 0.0001) {
				closestDistance = distance;
				closestIntersection = p;
				closestIntersectingSurface = s;
			}
		}
		//System.out.printf("%s -> %s\n",r,closestIntersection);
		if(closestIntersectingSurface == null) return voidColor; // didn't hit a surface
		return closestIntersectingSurface.getRayColor(r, closestIntersection);
	}
}
