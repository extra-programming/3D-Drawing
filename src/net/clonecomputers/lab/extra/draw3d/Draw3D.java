package net.clonecomputers.lab.extra.draw3d;


import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.clonecomputers.lab.extra.draw3d.geometry.*;
import net.clonecomputers.lab.extra.draw3d.geometry.Polygon;
import net.clonecomputers.lab.extra.draw3d.texture.*;

public class Draw3D extends JPanel implements Runnable {
	private BufferedImage canvas;

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
		Camera c = new Camera(new Ray(new Point3D(-100, 0, 0), new Point3D(1, 0, 0)), 40*Math.PI/180, 40*Math.PI/180);
		World world = new World(Arrays.asList(
				new Surface(
					new SphereSection(new Point3D(0, 6, 0), new Point3D(-10,20,20), Math.PI/3),
					new SolidColor(Color.GREEN)
				),
				new Surface(
						new Polygon(Arrays.asList(new Point3D(0,20,0),new Point3D(0,-20,20),new Point3D(0, -20, -20))),
						new SolidColor(Color.RED)
					)
			), new Color(.2f,.3f,.8f));
		c.render(canvas, world);
		this.repaint();
	}

}
