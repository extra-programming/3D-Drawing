package net.clonecomputers.lab.extra.draw3d;

import static net.clonecomputers.lab.extra.draw3d.Point3D.*;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.concurrent.*;

public class Camera {
	private static final int THREADNUM = 40;
	//private ExecutorService exec = Executors.newFixedThreadPool(THREADNUM);
	
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
	
	private int width = 200;
	private int height = 200;
	private Set<RayRenderer> renderers;
	
	private BufferedImage canvas;
	private int iWidth, iHeight;
	private int[] data;
	
	private World world;
	
	public Camera(BufferedImage canvas, Ray direction, double xViewSize, double yViewSize) {
		this.xViewSize = xViewSize;
		this.yViewSize = yViewSize;
		this.direction = direction;
		iWidth = canvas.getWidth();
		iHeight = canvas.getHeight();
		if(!canvas.getColorModel().equals(ColorModel.getRGBdefault())) {
			throw new IllegalArgumentException("Invalid color model of image");
		}
		data = ((DataBufferInt)canvas.getRaster().getDataBuffer()).getData();
		update();
		regenRenderers();
		//for(int i = 0; i < THREADNUM; i++) exec.execute(new Runnable(){public void run(){}});
	}
	
	public Ray getRay() {
		return direction;
	}
	
	public Point3D getUp() {
		return up;
	}
	
	public Point3D getRight() {
		return right;
	}
	
	public Point3D getForwards() {
		return direction.getDirection();
	}
	
	public void setRay(Ray direction) {
		this.direction = direction;
		update();
		//regenRenderers();
	}

	/**
	 * set the viewangle in radians
	 * @param xViewSize IN RADIANS
	 */
	public void setXViewSize(double xViewSize) {
		this.xViewSize = xViewSize;
		update();
		regenRenderers();
	}
	
	/**
	 * set the viewangle in radians
	 * @param yViewSize IN RADIANS
	 */
	public void setYViewSize(double yViewSize) {
		this.yViewSize = yViewSize;
		update();
		regenRenderers();
	}
	
	public void update() {
		right = cross(direction.getDirection(), new Point3D(0,0,1));
		up = cross(right, direction.getDirection());
		right = prod(norm(right), Math.tan(xViewSize));
		up = prod(norm(up), Math.tan(yViewSize));
		//regenRenderers();
	}
	
	/**
	 * renders a world to a canvas, from it's viewpoint
	 * @param canvas must have the default INT_ARGB color model
	 * @param world the world of objects to render
	 */
	public void render(World world) {
		/*if(width != canvas.getWidth() || height != canvas.getHeight()) {
			width = canvas.getWidth();
			height = canvas.getHeight();
			regenRenderers();
		}*/
		long millis = System.currentTimeMillis();
		this.world = world;
		for(RayRenderer r: renderers) r.run();
		System.out.println(System.currentTimeMillis() - millis);
	}
	
	private void regenRenderers() {
		renderers = new HashSet<RayRenderer>();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				renderers.add(new RayRenderer(x,y));
			}
		}
	}
	
	public class RayRenderer implements Runnable {
		private final int xd;
		private final int yd;
		private final int x0;
		private final int y0;
		private final int x1;
		private final int y1;
		private Ray r;
		
		public RayRenderer(int x, int y) {
			this.xd = x;
			this.yd = y;
			this.x0 = x*(iWidth/width);
			this.y0 = y*(iHeight/height);
			this.x1 = (x+1)*(iWidth/width);
			this.y1 = (y+1)*(iHeight/height);
		}
		
		@Override
		public void run() {
			double xDeclination = (xd - width/2.)/(width/2.);
			double yDeclination = (height/2. - yd)/(height/2.);
			r = new Ray(direction.getLocation(),
					norm(sum(
							direction.getDirection(),
							prod(up,yDeclination),
							prod(right,xDeclination))));
			int c = world.traceRay(r).getRGB();
			for(int x = x0; x < x1; x++) {
				for(int y = y0; y < y1; y++) {
					data[x+(y*iWidth)] = c;
					//System.out.println(x+y*iWidth);
				}
			}
			/*synchronized(Camera.this) {
				donenum--;
			}*/
		}
		
	}
}
