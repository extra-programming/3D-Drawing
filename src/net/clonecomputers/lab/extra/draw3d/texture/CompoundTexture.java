package net.clonecomputers.lab.extra.draw3d.texture;

import java.awt.*;

import net.clonecomputers.lab.extra.draw3d.*;

public class CompoundTexture implements Texture {
	
	private Mirror mirror;
	private Lens lens;
	private Texture base;
	
	private float lensPart;
	private float mirrorPart;
	private float basePart;
	
	public CompoundTexture(Texture base, double transparency, double reflectivity) {
		this(base, transparency, reflectivity, 1);
	}
	
	public CompoundTexture(Texture base, double transparency, double reflectivity, double n1, double n2) {
		this(base, transparency, reflectivity, n1/n2);
	}
	
	public CompoundTexture(Texture base, double transparency, double reflectivity, double indexOfRefractionRatio) {
		this(base, transparency, reflectivity, new Lens(indexOfRefractionRatio));
	}
	
	private CompoundTexture(Texture base, double transparency, double reflectivity, Lens lens) {
		this.base = base;
		this.lens = lens;
		this.mirror = new Mirror();
		if(transparency > 1) throw new IllegalArgumentException("transparency can not be more than 1");
		if(reflectivity > 1) throw new IllegalArgumentException("reflectivity can not be more than 1");
		if(transparency < 0) throw new IllegalArgumentException("transparency must be positive");
		if(reflectivity < 0) throw new IllegalArgumentException("reflectivity must be positive");
		if(transparency + reflectivity > 1) {
			throw new IllegalArgumentException("transparency+reflectivity can not be more than 1");
		}
		this.lensPart = (float)transparency;
		this.mirrorPart = (float)reflectivity;
		this.basePart = (float)(1-transparency-reflectivity);
	}
	
	@Override
	public Color getRayColor(Ray r, Ray normal, Point2D textureCoordinates) {
		Color baseColor = base.getRayColor(r, normal, textureCoordinates);
		Color reflectColor = mirror.getRayColor(r, normal, textureCoordinates);
		Color refractColor = lens.getRayColor(r, normal, textureCoordinates);
		return new Color(
				basePart*baseColor.getRed()/255f +
				mirrorPart*reflectColor.getRed()/255f +
				lensPart*refractColor.getRed()/255f
			,
				basePart*baseColor.getGreen()/255f +
				mirrorPart*reflectColor.getGreen()/255f +
				lensPart*refractColor.getGreen()/255f
			,
				basePart*baseColor.getBlue()/255f +
				mirrorPart*reflectColor.getBlue()/255f +
				lensPart*refractColor.getBlue()/255f
			);
	}

}
