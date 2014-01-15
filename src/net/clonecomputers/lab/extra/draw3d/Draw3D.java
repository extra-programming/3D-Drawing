package net.clonecomputers.lab.extra.draw3d;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class Draw3D extends JPanel implements Runnable {
	private BufferedImage canvas;

	public static void main(String[] args) {
		int w = 600, h = 600;
		if(args.length == 1) {
			args = args[0].split(",");
		}
		if(args.length == 2) {
			w = Integer.parseInt(args[0].trim());
			h = Integer.parseInt(args[1].trim());
		}
		new Draw3D(w, h).run();
	}
	

	public Draw3D(int width, int height) {
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void run() {
		//TODO: do stuff
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(canvas, 0, 0, this);
	}

}
