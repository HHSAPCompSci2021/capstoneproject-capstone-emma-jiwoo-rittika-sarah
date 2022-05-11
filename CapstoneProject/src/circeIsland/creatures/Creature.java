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
	private int xVelocity, yVelocity;
	private PImage image;
	
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int UP = -1;
	public static final int DOWN = 1;

	// CONSTRUCTOR
	
	public Creature (int xCoor, int yCoor, int width, int height) {
		this(null, xCoor, yCoor, width, height, 1, 1);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int width, int height) {
		this(img, xCoor, yCoor, width, height, 1, 1);
	}
	
	public Creature (int xCoor, int yCoor, int width, int height, int xVel, int yVel) {
		this(null, xCoor, yCoor,width, height, xVel, yVel);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int width, int height, int xVel, int yVel) {
		super(xCoor,yCoor, width, height);
		xVelocity = xVel;
		yVelocity = yVel;
		image = img;
	}
	
	
	// METHOD
	
	public void moveX(int dir) {
		super.x += (dir * xVelocity);
	}
	
	public void moveY(int dir) {
		super.y += (dir * yVelocity);
	}
	
	public void spawn(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	public int getXVel() {
		return xVelocity;
	}
	
	public int getYVel() {
		return yVelocity;
	}
	
	public void setXVel(int xVel) {
		xVelocity = xVel;
	}
	
	public void setYVel(int yVel) {
		yVelocity = yVel;
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
