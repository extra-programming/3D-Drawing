package net.clonecomputers.lab.extra.draw3d;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.clonecomputers.lab.extra.draw3d.geometry.*;
import net.clonecomputers.lab.extra.draw3d.geometry.Polygon;
import net.clonecomputers.lab.extra.draw3d.texture.*;
import static net.clonecomputers.lab.extra.draw3d.Point3D.*;

public class Draw3D extends JPanel implements Runnable, KeyListener, MouseMotionListener, MouseListener {
	private BufferedImage canvas;
	private Camera camera;
	private World world;

	public static void main(String[] args) {
		if(args.length == 1) args = args[0].split("[,]");
		Dimension dim = new Dimension();
		if(args.length >= 2) {
			try{
				dim.width = Integer.parseInt(args[0].trim());
				dim.height = Integer.parseInt(args[1].trim());
			} catch(NumberFormatException e) {
				System.err.println("Invalid dimensions, using {600, 600}");
				dim = new Dimension(600, 600);
			}
		} else {
			dim = new Dimension(600, 600);
		}
		JFrame window = new JFrame("3D Drawing Program!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Draw3D draw = new Draw3D(dim);
		window.setContentPane(draw);
		window.addMouseListener(draw);
		window.addMouseMotionListener(draw);
		window.addKeyListener(draw);
		window.pack();
		window.setVisible(true);
		draw.run();
	}
	
	public Draw3D(Dimension dim) {
		this.setPreferredSize(dim);
		canvas = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
	}

	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(canvas, 0, 0, Color.WHITE, this);
	}

	@Override
	public void run() {
		camera = new Camera(new Ray(new Point3D(-100, 0, 0), new Point3D(1, 0, 0)), 40*Math.PI/180, 40*Math.PI/180);
		world = new World(Arrays.asList(
				new Surface(
					new SphereSection(new Point3D(0, 6, 0), new Point3D(-10,20,20), Math.PI/3),
					new SolidColor(Color.GREEN)
				),
				new Surface(
					new Polygon(Arrays.asList(new Point3D(0,20,0),new Point3D(0,-20,20),new Point3D(0, -20, -20))),
					new SolidColor(Color.RED)
				),
				new Surface(
					new SphereSection(new Point3D(-40,0,0),new Point3D(-40,0,0),Math.PI/4),
					new CompoundTexture(new SolidColor(Color.MAGENTA),.4,.2,.74)
				)
			), new Color(.2f,.3f,.8f));
		long millis = System.currentTimeMillis();
		camera.render(canvas, world);
		//System.out.println(System.currentTimeMillis() - millis);
		this.repaint();
		
		while(true) {
			synchronized(this) {
				updatePos();
				camera.render(canvas, world);
			}
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void updatePos() {
		if(facingRay != null) camera.setRay(facingRay);
		double speed = 5;
		Point3D currentLoc = camera.getRay().getLocation();
		Point3D newLoc = currentLoc;
		if(W_DOWN) newLoc = sum(newLoc,prod(camera.getForwards(),speed));
		if(S_DOWN) newLoc = sum(newLoc,prod(camera.getForwards(),-speed));
		if(A_DOWN) newLoc = sum(newLoc,prod(camera.getRight(),-speed));
		if(D_DOWN) newLoc = sum(newLoc,prod(camera.getRight(),speed));
		if(Q_DOWN) newLoc = sum(newLoc,prod(camera.getUp(),speed));
		if(E_DOWN) newLoc = sum(newLoc,prod(camera.getUp(),-speed));
		camera.setRay(new Ray(newLoc,camera.getRay().getDirection()));
		facingRay = camera.getRay();
	}
	
	private boolean W_DOWN = false;
	private boolean S_DOWN = false;
	private boolean A_DOWN = false;
	private boolean D_DOWN = false;
	private boolean Q_DOWN = false;
	private boolean E_DOWN = false;
	
	private Point lastMousePosition = null;
	private Ray facingRay;

	// Don't care about any of these, but need them anyways
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseMoved(MouseEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		lastMousePosition = e.getLocationOnScreen();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		lastMousePosition = null;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(camera == null) return;
		if(lastMousePosition == null) return;
		Point currentMouseLocation = e.getLocationOnScreen();
		
		double dx = lastMousePosition.x - currentMouseLocation.x;
		double dy = lastMousePosition.y - currentMouseLocation.y;
		
		Point3D currentDir = facingRay.getDirection();
		Point3D right = norm(cross(currentDir, new Point3D(0,0,1)));
		Point3D up = norm(cross(right, currentDir));
		Point3D newDir = sum(currentDir,prod(right,dx*.0001),prod(up,-dy*.0001));
		facingRay = new Ray(facingRay.getLocation(), newDir);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()) {
		case 'w': W_DOWN = true; break;
		case 's': S_DOWN = true; break;
		case 'a': A_DOWN = true; break;
		case 'd': D_DOWN = true; break;
		case 'q': Q_DOWN = true; break;
		case 'e': E_DOWN = true; break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyChar()) {
		case 'w': W_DOWN = false; break;
		case 's': S_DOWN = false; break;
		case 'a': A_DOWN = false; break;
		case 'd': D_DOWN = false; break;
		case 'q': Q_DOWN = false; break;
		case 'e': E_DOWN = false; break;
		}
	}
}
