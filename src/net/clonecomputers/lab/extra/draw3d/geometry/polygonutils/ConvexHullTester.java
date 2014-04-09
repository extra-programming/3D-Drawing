package net.clonecomputers.lab.extra.draw3d.geometry.polygonutils;

import static net.clonecomputers.lab.extra.draw3d.Point2D.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import net.clonecomputers.lab.extra.draw3d.*;

public class ConvexHullTester {
	private static final boolean D = false;
	public static List<Point2D> getConvexHull(List<Point2D> points) {
		List<Point2D> path = new ArrayList<Point2D>();
		Point2D p = points.get(0);
		int i = 0;
		while(!path.contains(p)) {
			Point2D ref = getDistinct(points, p);
			double maxCross = -Double.MAX_VALUE;
			Point2D nextPoint = null;
			for(Point2D p2: points) {
				if(p.equals(p2)) continue;
				if(crossAgainstRef(p,p2,ref) > maxCross) {
					maxCross = crossAgainstRef(p,p2,ref);
					nextPoint = p2;
				}
			}
			path.add(p);
			p = nextPoint;
		}
		path.subList(0, path.lastIndexOf(p)).clear();
		return path;
	}
	
	private static double crossAgainstRef(Point2D p, Point2D p2, Point2D ref) {
		return p(cross(norm(diff(p2,p)), norm(diff(ref,p))),D?"%4$s against [%2$s, %3$s] is %1$.2f\n":"",p,p2,ref);
	}
	
	private static <T> T p(T t, String format, Object... objs) {
		Object[] args = new Object[objs.length+1];
		args[0] = t;
		for(int i = 1; i < args.length; i++) {
			args[i] = objs[i-1];
		}
		System.out.printf(format, args);
		return t;
	}
	
	private static <T> T getDistinct(List<T> list, T... items) {
		T t = list.get(0);
		for(int i = 0; contains(items,t); i++) {
			t = list.get(i);
		}
		return t;
	}
	
	private static <T> boolean contains(T[] list, T item) {
		for(T t: list) {
			if(t.equals(item)) return true;
		}
		return false;
	}
	
	public static boolean isInsideConvexHull(List<Point2D> hull, Point2D center) {
		Point2D p = hull.get(hull.size()-1);
		for(Point2D p2: hull) {
			if(crossAgainstRef(p,p2,center) <= 0) {
				//display(center,hull,points);
				//p("","%s%s:\n\t%s\n\t%s\n",center,hull,points);
				return false;
			}
			p = p2;
		}
		return true;
	}
	
	private static void display(final Point2D center, final List<Point2D> hull,
			final List<Point2D> points) {
		JFrame window = new JFrame("points");
		window.setContentPane(new JPanel() {
			private int xgp(double x) {
				return getWidth()/2 + (int)(getWidth()*x/2);
			}
			private int ygp(double y) {
				return getHeight()/2 - (int)(getHeight()*y/2);
			}
			@Override public void paintComponent(Graphics g) {
				g.fillOval(xgp(center.x)-5, ygp(center.y)-5, 10, 10);
				for(Point2D p: points) {
					g.drawOval(xgp(p.x)-5, ygp(p.y)-5, 10, 10);
				}
				Point2D p = hull.get(hull.size()-1);
				for(Point2D p2: hull) {
					g.drawLine(xgp(p.x), ygp(p.y), xgp(p2.x), ygp(p2.y));
					p = p2;
				}
			}
		});
		window.setSize(600,600);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		System.out.println(
			isInsideConvexHull(new ArrayList<Point2D>(Arrays.asList(
				new Point2D(0.85,0.31), 
				new Point2D(-0.85,0.31), 
				new Point2D(0.53,0.68), 
				new Point2D(-0.52,0.68), 
				new Point2D(-0.00,0.09)
		)), new Point2D(0,0)));
	}
}
