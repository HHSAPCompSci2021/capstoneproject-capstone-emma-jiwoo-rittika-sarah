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
	private int velocity;
	private PImage image;
	private Island myIsland;
	
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int UP = -1;
	public static final int DOWN = 1;

	// CONSTRUCTOR
	
	public Creature (int xCoor, int yCoor, int width, int height) {
		this(null, xCoor, yCoor, width, height, 1);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int width, int height) {
		this(img, xCoor, yCoor, width, height, 1);
	}
	
	public Creature (int xCoor, int yCoor, int width, int height, int vel) {
		this(null, xCoor, yCoor,width, height, vel);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int width, int height, int vel) {
		super(xCoor,yCoor, width, height);
		velocity = vel;
		image = img;
	}
	
	
	// METHOD
	
	
	public int getXGrid() {
		return coorToGrid(x,y)[0];
	}
	
	public int getYGrid() {
		return coorToGrid(x,y)[1];
	}
	
	public Island getIsland() {
		return myIsland;
	}
	
	public void moveX(int dir) {
		super.x += (dir * velocity);
	}
	
	public void moveY(int dir) {
		super.y += (dir * velocity);
	}
	
	public void spawn(Island i, int x, int y) {
		double[] coor = gridToCoor(x,y);
		super.x = coor[0];
		super.y = coor[1];
	}
	
	public int getVel() {
		return velocity;
	}

	public void setVel(int vel) {
		velocity = vel;
	}
	
	public int[] coorToGrid(double xCoor, double yCoor) {
		double width = myIsland.getWidth();
		double height = myIsland.getHeight();
		int xGrid = (int) (xCoor/(width/10));
		int yGrid = (int) (yCoor/(height/10));
		
		int[] grid = {xGrid, yGrid};
		return grid;
	}
	
	public double[] gridToCoor(int xGrid, int yGrid) {
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
