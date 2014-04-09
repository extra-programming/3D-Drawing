package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

import net.clonecomputers.lab.extra.draw3d.*;

public class Mirror implements Texture {

	@Override
	public Color getRayColor(Ray r, Ray normal) {
		Point3D rVec = r.getDirection();
		//TODO: not implemented
		return null;
	}

}
