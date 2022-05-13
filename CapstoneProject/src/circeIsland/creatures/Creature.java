package circeIsland.creatures;

import java.awt.geom.Rectangle2D;
import processing.core.PApplet;
import processing.core.PImage;
import circeIsland.elements.Land;
import circeIsland.screens.Island;


/**
 * 
 * @author Jiwoo Kim
 */
public abstract class Creature extends Rectangle2D.Double{
	private int velocity;
	private PImage image;
	private Island myIsland;
	private boolean isInGrid;
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	// CONSTRUCTOR
	
	public Creature (int xCoor, int yCoor, int width, int height) {
		this(null, xCoor, yCoor, width, height, 10);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int width, int height) {
		this(img, xCoor, yCoor, width, height, 10);
	}
	
	public Creature (int xCoor, int yCoor, int width, int height, int vel) {
		this(null, xCoor, yCoor,width, height, vel);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, int width, int height, int vel) {
		super(xCoor,yCoor, width, height);
		velocity = vel;
		image = img;
		isInGrid = false;
		myIsland = null;
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
		if(dir == LEFT) {
			super.x += (-1 * velocity);
		}else if (dir == RIGHT){
			super.x += velocity;
		}
	}
	
	public void moveY(int dir) {
		if(dir == UP) {
			super.y += (-1 * velocity);
		}else if (dir == DOWN){
			super.y += velocity;
		}
	}
	
	public void moveToXY(int x, int y) {
		double[] coor = gridToCoor(x,y);
		super.x = coor[0];
		super.y = coor[1];
	}
	
	public void putOnIsland(Island i) {
		if(i != null)
			isInGrid = true;
			myIsland = i;
	}
	
	public void removeFromIsland(Island i) {
		isInGrid = false;
		myIsland = null;
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
		if(isInGrid) {
			if (image != null)
				g.image(image,(float)x,(float)y,(float)width,(float)height);
			else {
				g.fill(100);
				g.rect((float)x,(float)y,(float)width,(float)height);
				g.fill(0);
				g.text(getType(), (float)x, (float)y);
			}
		}
	}
	
	public void act() {
		act(-1);
	}
	
	public void act(int dir) {
		if(isInGrid) {
			int[] grid = coorToGrid(x, y);
			if(dir == -1) 
				dir = (int)Math.random() * 4;
			
			if(dir == LEFT) {
				if(canStand(grid[0]-1, grid[1]))
					moveX(Creature.LEFT);
			}else if(dir == RIGHT) {
				if(canStand(grid[0]+1, grid[1]))
					moveX(Creature.RIGHT);
			}else if(dir == UP) {
				if(canStand(grid[0]-1, grid[1]-1))
					moveX(Creature.UP);
			}else if(dir == DOWN) {
				if(canStand(grid[0], grid[1]+1))
					moveX(Creature.DOWN);
			}
		}
	}
	
	public boolean canStand(int gridX, int gridY) {
		return myIsland.getElement(gridX, gridY).getStandable();
	}
	
	public abstract String getType();
	
	
	
}
