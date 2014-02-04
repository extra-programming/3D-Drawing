package net.clonecomputers.lab.extra.draw3d;


import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

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
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		//TODO: do stuff here
	}

}
