package circeIsland.creatures;

import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;
import circeIsland.elements.Land;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import circeIsland.elements.*;


/**
 * The class represents the creature.
 * All the moving creatures are extended from this class.
 * 
 * @author Jiwoo Kim
 */
public abstract class Creature extends Rectangle2D.Double{
	private int velocity;
	private PImage image;
	private Island myIsland;
	private boolean isInGrid;
	private int count;
	private double prevWidth, prevHeight;
	private double widthRatio, heightRatio;
	
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	// CONSTRUCTOR

	/**
	 * initialize the creatures.
	 * The creatures DOES NOT initialize the island they belong to
	 * velocity of the creatures are set to 10 (default)
	 * 
	 * @param xCoor left coordinate for the creatures
	 * @param yCoor up coordinate for the creatures
	 * @param ratioX the number represents how many creatures fit horizontally in the island 
	 * @param ratioY the number represents how many creatures fit vertically in the island
	 */
	public Creature (int xCoor, int yCoor, double ratioX, double ratioY) {
		this(null, xCoor, yCoor, ratioX, ratioY, 10);
	}
	
	/**
	 * initialize the creatures.
	 * The creatures DOES NOT initialize the island they belong to
	 * velocity of the creatures are set to 10 (default)
	 * 
	 * @param img image for creature
	 * @param xCoor left coordinate for the creatures
	 * @param yCoor up coordinate for the creatures
	 * @param ratioX the number represents how many creatures fit horizontally in the island 
	 * @param ratioY the number represents how many creatures fit vertically in the island
	 */
	public Creature (PImage img, int xCoor, int yCoor, double ratioX, double ratioY) {
		this(img, xCoor, yCoor, ratioX, ratioY, 10);
	}
	
	/**
	 * initialize the creatures.
	 * The creatures DOES NOT initialize the island they belong to
	 * 
	 * @param xCoor left coordinate for the creatures
	 * @param yCoor up coordinate for the creatures
	 * @param ratioX the number represents how many creatures fit horizontally in the island 
	 * @param ratioY the number represents how many creatures fit vertically in the island
	 * @param vel velocity of the creature
	 */
	public Creature (int xCoor, int yCoor, double ratioX, double ratioY, int vel) {
		this(null, xCoor, yCoor,ratioX, ratioY, vel);
	}
	
	/**
	 * initialize the creatures.
	 * The creatures DOES NOT initialize the island they belong to
	 * 
	 * @param img image for creature
	 * @param xCoor left coordinate for the creatures
	 * @param yCoor up coordinate for the creatures
	 * @param ratioX the number represents how many creatures fit horizontally in the island 
	 * @param ratioY the number represents how many creatures fit vertically in the island
	 * @param vel velocity of the creature
	 */
	public Creature (PImage img, int xCoor, int yCoor, double ratioX, double ratioY, int vel) {
		super(xCoor,yCoor, 1200/ratioX, 900/ratioY);
		widthRatio = ratioX;
		heightRatio = ratioY;
		velocity = vel;
		image = img;
		isInGrid = false;
		myIsland = null;
		prevWidth = 1200;
		prevHeight = 900;
	}
	
	
	// METHOD
	
	/**
	 * @return true if creature is in the island. false otherwise. 
	 */
	public boolean getIsInGrid() {
		return isInGrid;
	}
	
	/**
	 * @return get the x grid coordinate of the creature
	 */
	public int getXGrid() {
		return coorToGrid(x,y)[0];
	}
	
	/**
	 * @return get the y grid coordinate of the creature
	 */
	public int getYGrid() {
		return coorToGrid(x,y)[1];
	}
	
	/**
	 * @return get the island that creature is belong to. return null if it is not assigned yet.
	 */
	public Island getIsland() {
		return myIsland;
	}
	
	/**
	 * If the creatures can move to desire direction, it will move. Stay other wise
	 * 
	 * @param dir the horizontal direction the creatures will move
	 */
	public void moveX(int dir) {
		if(dir == LEFT) {
			x += (-1 * velocity);
			if(!canStand(x,y)) {
				x += velocity;
			}
			
		}else if(dir == RIGHT) {
			x += velocity;
			if(!canStand(x,y)) {
				x += (-1 * velocity);
			}
		}
	}
	
	/**
	 * If the creatures can move to desire direction, it will move. Stay other wise
	 * 
	 * @param dir the vertical direction the creatures will move
	 */
	public void moveY(int dir) {
		if(dir == UP) {
			y += (-1 * velocity);
			if(!canStand(x,y)) {
				y += velocity;
			}
		}else if(dir == DOWN) {
			y += velocity;
			if(!canStand(x,y)) {
				y += (-1 * velocity);
			}
		}
	}
	
	/**
	 * The creatures teleport to the spot (x,y)
	 * @param x x grid coordinate that creature teleports to
	 * @param y y grid coordinate that creature teleports to
	 */
	public void moveToXY(int x, int y) {
		double[] coor = gridToCoor(x,y);
		super.x = coor[0];
		super.y = coor[1];
	}
	
	/**
	 * Initialize the island that creature belong to
	 * If the creature is not the instance of circe, it will also added into island array
	 * 
	 * @param i island that creature belong to
	 */
	public void putOnIsland(Island i) {
		if(i != null)
			isInGrid = true;
			myIsland = i;
			if(!(this instanceof Circe))
				myIsland.addCreature(this);
	}
	
	/**
	 * removing the belonged island from the creature.
	 * not only that, it will be removed from the island entirely
	 * @pre the creature is not the instance of Circe
	 */
	public void removeFromIsland() {
		if(myIsland != null) {
			isInGrid = false;
			myIsland.removeCreature(this);
			myIsland = null;
		}
	}
	
	/**
	 * 
	 * @return velocity of the creature
	 */
	public int getVel() {
		return velocity;
	}
	
	/**
	 * Set the velocity of the creature
	 * @param vel new velocity that creature will get
	 */
	public void setVel(int vel) {
		velocity = vel;
	}
	
	/**
	 * change the actual coordinate system to the grid coordinate 
	 * @param xCoor x coordinate of the creature
	 * @param yCoor y coordinate of the creature
	 * @return int array that has length 2. first index would be x grid coordinate, and second index would be y grid coordinate.
	 */
	public int[] coorToGrid(double xCoor, double yCoor) {
		double cellWidth = (myIsland.getSurface().width )/myIsland.getElements()[0].length;
		double cellHeight = (myIsland.getSurface().height )/myIsland.getElements().length;
		int xGrid = (int) (xCoor/cellWidth);
		int yGrid = (int) (yCoor/cellHeight);
		int[] grid = {xGrid, yGrid};
		return grid;
	}
	
	/**
	 * change the grid coordinate system to the actual coordinate 
	 * @param xGrid x grid coordinate of the creature
	 * @param yGrid y grid coordinate of the creature
	 * @return double array that has length 2. first index would be x actual coordinate, and second index would be y actual coordinate.
	 */
	public double[] gridToCoor(int xGrid, int yGrid) {
		double width = myIsland.getSurface().width;
		double height = myIsland.getSurface().height;
		double xCoor = (int) (xGrid * (width/10));
		double yCoor = (int) (yGrid * (height/10));
		
		double[] coor = {xCoor, yCoor};
		return coor;	
	}
	
	/**
	 * It will draw the creature on the drawing surface.
	 * If the image is null, the gray rectangle with the description of the crature would be drawn.
	 * If the image is not null, the image will be drawn.
	 * The drawing would be completed upward left coordinate (getX(), getY()) to getWidth() and getHeight(). 
	 * Not only drawing the creatures, it will update the value of x and y and width and height if the screen width and height are changed.
	 * If the grid is not set yet, it will do nothing
	 * 
	 * @param g Drawing surface object that the creature will be drawan on
	 * @post g.fill() would be set to 0(black) if the image is null.
	 */
	public void draw(DrawingSurface g) {
		if(isInGrid) {
			g.push();
			double islandHeight = myIsland.getSurface().height;
			double islandWidth =myIsland.getSurface().width;
			if(prevHeight != islandHeight) {
				y *= islandHeight/prevHeight;
				prevHeight = islandHeight;
				height = islandHeight/heightRatio;
			}
			if(prevWidth != islandWidth) {
				x *= islandWidth/prevWidth;
				prevWidth = islandWidth;
				width = islandWidth/widthRatio;
			}			
			if (image != null) {
//				g.rect((float)x,(float)y,(float)(width),(float)(height));
				g.image(image,(float)x,(float)y,(float)(width),(float)(height));
			} else {
				g.fill(100);
				g.rect((float)x,(float)y,(float)width,(float)height);
				g.fill(0);
				g.text(getType(), (float)x, (float)y);
			}
			g.pop();
		}
	}
	
	/**
	 * Check if the input x coordinate and y coordinate are standable or not
	 * 
	 * @param xCoor the left side x coordinate of the creature
	 * @param yCoor the up side y coordinate of the creature
	 * @return if the (xCoor, yCoor) is null, or out of bounds in the island, or if the bottom left and bottom right of the creature is not standable, it will return false. otherwise, it will return true.
	 */
	public boolean canStand(double xCoor, double yCoor) {	
		Element[][] elements = myIsland.getElements();
		
		int[] gridBottomLeft = coorToGrid(xCoor, yCoor+height);
		int[] gridBottomRight = coorToGrid(xCoor+width, yCoor+height);
		if(gridBottomLeft[0] < 0 || gridBottomRight[0] > elements.length || 
			gridBottomLeft[1] < 0 || gridBottomRight[1] > elements.length) {
			return false;
		}
		if (elements[gridBottomLeft[0]][gridBottomLeft[1]] == null || 
			elements[gridBottomRight[0]][gridBottomRight[1]] == null) {
			return false;
		}
		return elements[gridBottomLeft[0]][gridBottomLeft[1]].getStandable() &&
				elements[gridBottomRight[0]][gridBottomRight[1]].getStandable();
		
	}
	
	/**
	 * It will move once in a while in random direction if the dir is -1
	 * If the direction is set specifically, it will move into the direction
	 * If the grid is not set yet, it will do nothing
	 * 
	 * @param dir the direction that the creature will move to (no matter horizontal or vertical. but not a diagonal)
	 */
	public void act(int dir) {
		if(isInGrid) {
			if(dir == -1 && (count%10 == 0 || count%11 == 0 || count% 9 == 0)) 
				dir = (int)(Math.random() * 30);
			
			
			if(dir == LEFT || dir == RIGHT) {
				moveX(dir);
			}else if(dir == UP || dir == DOWN) {
				moveY(dir);
			}
			count++;
		}
	}
	
	/**
	 * The specified random movement of the creatures
	 */
	public abstract void act();
	
	/**
	 * @return the String that describe the type of the creatures (Circe, Pig, Visitor...)
	 */
	public abstract String getType();
	
	
}
