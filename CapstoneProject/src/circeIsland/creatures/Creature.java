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
	
	public Creature (int xCoor, int yCoor, double width, double height) {
		this(null, xCoor, yCoor, width, height, 10);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, double width, double height) {
		this(img, xCoor, yCoor, width, height, 10);
	}
	
	public Creature (int xCoor, int yCoor, double width, double height, int vel) {
		this(null, xCoor, yCoor,width, height, vel);
	}
	
	public Creature (PImage img, int xCoor, int yCoor, double width, double height, int vel) {
		super(xCoor,yCoor, 1200/width, 900/height);
		widthRatio = width;
		heightRatio = height;
		velocity = vel;
		image = img;
		isInGrid = false;
		myIsland = null;
		prevWidth = 1200;
		prevHeight = 900;
	}
	
	
	// METHOD
	
	public boolean getIsInGrid() {
		return isInGrid;
	}
	
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
		
//		if(dir == LEFT) {
//			if(canStand(x, y))
//				super.x += (-1 * velocity);
//			
//		}else if(dir == RIGHT) {
//			if(canStand(x, y))
//				super.x += velocity;
//		}
	}
	
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
	
	public void moveToXY(int x, int y) {
		double[] coor = gridToCoor(x,y);
		super.x = coor[0];
		super.y = coor[1];
	}
	
	public void putOnIsland(Island i) {
		if(i != null)
			isInGrid = true;
			myIsland = i;
			if(!(this instanceof Circe))
				myIsland.addCreature(this);
	}
	
	public void removeFromIsland(Island i) {
		isInGrid = false;
		//myIsland = null;
		if(!(this instanceof Circe))
			myIsland.removeCreature(this);
		myIsland = null;
	}
	
	
	public int getVel() {
		return velocity;
	}
	
	public int getCount() {
		return count;
	}
	
	public void increaseCount() {
		count++;
	}

	public void setVel(int vel) {
		velocity = vel;
	}
	
	public int[] coorToGrid(double xCoor, double yCoor) {
		double cellWidth = (myIsland.getSurface().width )/myIsland.getElements()[0].length;
		double cellHeight = (myIsland.getSurface().height )/myIsland.getElements().length;
		int xGrid = (int) (xCoor/cellWidth);
		int yGrid = (int) (yCoor/cellHeight);
////		System.out.println(xGrid + "," + yGrid);
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
				g.rect((float)x,(float)y,(float)(width),(float)(height));
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
	
	public boolean canStand(double xCoor, double yCoor) {
//		System.out.println(xCoor + "," + yCoor );
//		System.out.println("Island Width: " + myIsland.getWidth() + "Island Height" + myIsland.getHeight());
		
		Element[][] elements = myIsland.getElements();
		
		int[] gridBottomLeft = coorToGrid(xCoor, yCoor+height);
		int[] gridBottomRight = coorToGrid(xCoor+width, yCoor+height);
//		System.out.println("Left: " + gridBottomLeft[0] + ", Right: " + gridBottomRight[0] + ", Down: " + gridBottomLeft[1]);
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
		
		
//		System.out.println(myWidth + ", " + myHeight);

		
//		double feetX = this.x + width / 2;
//		double feetY = this.y + height / 2;
//		
//		//int[] coord = myIsland.coorToGrid(x + 6,  y + 9);
//		int[] coord = myIsland.coorToGrid(xCoor,  yCoor);
//		System.out.println(Arrays.toString(coord));
//		if(elements[coord[0]][coord[1]] instanceof Land) {
//			System.out.println("CAN MOVE");
//			return true;
//		}
//		else {
//			return false;
//		}
		
		
	}
	
	public abstract void act();
	
	public abstract String getType();
	
	public void setIsland(Island island) {
		myIsland = island;
	}
	
}
