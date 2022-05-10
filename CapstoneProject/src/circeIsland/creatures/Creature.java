package circeIsland.creatures;

import java.awt.geom.Rectangle2D;
import processing.core.PApplet;
import processing.core.PImage;

import circeIsland.screens.Island;


/**
 * 
 * @author Jiwoo Kim
 */
public class Creature extends Rectangle2D.Double{
	private double x, y;
	private int xVelocity, yVelocity;
	private PImage image;
	
	protected static final int LEFT = -1;
	protected static final int RIGHT = 1;
	protected static final int UP = -1;
	protected static final int DOWN = 1;

	
	
	public Creature (int xCoor, int yCoor) {
		this(null, xCoor, yCoor, 1, 1);
	}
	
	public Creature (PImage img, int xCoor, int yCoor) {
		this(img, xCoor, yCoor, 1, 1);
	}
	
	public Creature (int xCoor, int yCoor, int xVel, int yVel) {
		this(null, xCoor, yCoor, xVel, yVel);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int xVel, int yVel) {
		x = xCoor;
		y = yCoor;
		xVelocity = xVel;
		yVelocity = yVel;
		image = img;
	}
	
	public void moveX(int dir) {
		x += (dir * xVelocity);
	}
	
	public void moveY(int dir) {
		y += (dir * yVelocity);
	}
	
	
	public int[] coorToGrid(double xCoor, double yCoor,Island myIsland) {
		double width = myIsland.getWidth();
		double height = myIsland.getHeight();
		int xGrid = (int) (xCoor/(width/10));
		int yGrid = (int) (yCoor/(height/10));
		
		int[] grid = {xGrid, yGrid};
		return grid;
	}
	
	public double[] gridToCoor(int xGrid, int yGrid, Island myIsland) {
		double width = myIsland.getWidth();
		double height = myIsland.getHeight();
		double xCoor = (int) (xGrid * (width/10));
		double yCoor = (int) (yGrid * (height/10));
		
		double[] coor = {xCoor, yCoor};
		return coor;	
	}
	
	public void draw(PApplet g) {
		if (image != null)
			g.image(image,(float)x,(float)y,(float)width,(float)height);
		else {
			g.fill(100);
			g.rect((float)x,(float)y,(float)width,(float)height);
		}
	}
	
	
	
}
