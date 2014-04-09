package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

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
	public Color getRayColor(Ray r, Ray normal) {
		//TODO: implement me
		return null;
	}

}
