package net.clonecomputers.lab.extra.draw3d;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;
import java.awt.*;
import java.awt.image.*;

public class Camera {
	private Ray direction;
	/**
	 * A vector that is normal to direction and right
	 * with a magnitude that when added to a unit
	 * vector gives the required view size
	 */
	private Point3D up;
	/**
	 * A vector that is normal to direction and up
	 * with a magnitude that when added to a unit
	 * vector gives the required view size
	 */
	private Point3D right;
	private double xViewSize;
	private double yViewSize;
	
	public Camera(Ray direction, double xViewSize, double yViewSize) {
		this.xViewSize = xViewSize;
		this.yViewSize = yViewSize;
		this.direction = direction;
		update();
	}
	
	public Ray getDirection() {
		return direction;
	}
	
	public void setDirection(Ray direction) {
		this.direction = direction;
		update();
	}

	/**
	 * set the viewangle in radians
	 * @param xViewSize IN RADIANS
	 */
	public void setXViewSize(double xViewSize) {
		this.xViewSize = xViewSize;
		update();
	}
	
	/**
	 * set the viewangle in radians
	 * @param yViewSize IN RADIANS
	 */
	public void setYViewSize(double yViewSize) {
		this.yViewSize = yViewSize;
		update();
	}
	
	public void update() {
		right = cross(direction.getDirection(), new Point3D(0,0,1));
		up = cross(right, direction.getDirection());
		right = prod(norm(right), Math.tan(xViewSize));
		up = prod(norm(up), Math.tan(yViewSize));
	}
	
	/**
	 * renders a world to a canvas, from it's viewpoint
	 * @param canvas must have the default INT_ARGB color model
	 * @param world the world of objects to render
	 */
	public void render(BufferedImage canvas, World world) {
		double w = canvas.getWidth();
		double h = canvas.getHeight();
		if(!canvas.getColorModel().equals(ColorModel.getRGBdefault())) {
			throw new IllegalArgumentException("Invalid color model of image");
		}
		int[] data = ((DataBufferInt)canvas.getRaster().getDataBuffer()).getData();
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				double xDeclination = (x - w/2)/(w/2);
				double yDeclination = (h/2 - y)/(h/2);
				Ray r = new Ray(direction.getLocation(),
						norm(sum(
								direction.getDirection(),
								prod(up,yDeclination),
								prod(right,xDeclination))));
				data[x+(y*canvas.getWidth())] = world.traceRay(r).getRGB();
			}
		}
	}
}
