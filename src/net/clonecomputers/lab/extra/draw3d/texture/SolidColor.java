package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

import net.clonecomputers.lab.extra.draw3d.*;

public class SolidColor implements Texture {
	private Color color;
	
	public SolidColor(Color color) {
		this.color = color;
	}
	
	@Override
	public Color getRayColor(Ray r, Ray normal, Point2D textureCoordinates) {
		return color;
	}

}
