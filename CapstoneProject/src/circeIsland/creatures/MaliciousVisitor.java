package circeIsland.creatures;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import circeIsland.elements.Holdable;
import circeIsland.elements.House;
import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * This class represents malicious visitor, extends from the Visitor
 * 
 * @author Jiwoo Kim
 */
public class MaliciousVisitor extends Visitor{
	
	private boolean stealing;
	private boolean running;
	private int stealingCount;
	private int[] circeHouse;
	private Rectangle2D.Double circeHouseRect;

	/**
	 * Initilize the Malicious visitor 
	 * other values are setted as a default that shown in Creature.java constructors and visitor.java constructor
	 * @param img the visitor image
	 * @param x x coordinate of the visitor
	 * @param y y coordinate of the visitor
	 */
	public MaliciousVisitor(PImage img, double x, double y) {
		super(img, x, y);
		stealing = false;
		running = true;
		stealingCount = 0;
		circeHouse = new int[2];
		circeHouse[0] = -1;
		circeHouse[1] = -1;
		circeHouseRect = null;
	}

	/**
	 * @return the type of this class ("Malicious")
	 */
	public String getType() {
		return "Malicious";
	}
	
	/**
	 * Update the Malicious visitor movement
	 * if it is near the circe House, it will set isStelaing() true, 
	 * and increase the stealing count which represents the time for stealing
	 * If isRunning() is true and the isStealing() is false, it will do following actions:
	 *       if the circe is nearby, it will run away from circe
	 *       if the circe is not nearby, it will directed toward the circe house
	 */
	public void act() {
		if(super.getIsInGrid()) {
			if(circeHouse[0] == -1) {
				setHouse();
			}
			if(this.intersects(circeHouseRect)) {
				stealing = true;
				running = false;
				stealingCount++;
			}else if(running && !stealing) {
				int[] circeGrid = checkCirceNearby();
				int dir = -1;
				if(circeGrid == null) {
					dir = super.destinationDir(circeHouse);
				}else {
					dir = destinationDir(circeGrid);
				}
				
				super.act(dir);
			}
		}
	}
	
	/**
	 * set the Malicious visitor running state false
	 */
	public void stopRunning() {
		running= false;
	}
	
	/**
	 * set the Malicious visitor running state true
	 */
	public void beginRunning() {
		running = true;
	}
	
	/**
	 * 
	 * @return the wheter it is running or not (true if it is running false otherwise)
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * 
	 * @return the stealing count which represents the how many time taken for stealing
	 */
	public int getStealingCount() {
		return stealingCount;
	}

	/**
	 * It would guide what direction should be go to reach the destination
	 * If the destination is diagnoal direction from the malicious visitor,
	 * it would move the bigger distances 
	 * @param destination the grid coordinate for the destination
	 * @return the direction to reach the destination
	 */
	public int destinationDir(int[] destination) {
		int[] grid = coorToGrid(x, y+height);
		int diffX = grid[0] - destination[0];
		int diffY = grid[1] - destination[1];
		
		if(diffX == 0 && diffY == 0) {
			return (int) (Math.random()*4);
		}
		
		if(Math.abs(diffY) > Math.abs(diffX)) {
			if(diffY < 0) 
				return Creature.UP;
			return Creature.DOWN;
		}
		
		if(diffX<0)
			return Creature.LEFT;
		return Creature.RIGHT;
	}
	
	/**
	 * @return true if the malicious visitor is currently stealing something. false otherwise
	 */
	public boolean isStealing() {
		return stealing;
	}

	
	/**
	 * The MaliciousVisitor will be drawn by Creature.java draw method
	 * Additionally, it will update the the some private values inside the malicious visitor
	 * @pre Drawing Surface has to be not the null
	 * @param g Drawing surface object that the creature will be drawn on
	 */
	public void draw(DrawingSurface g) {
		if(super.getIsInGrid()) {
			super.draw(g);
			if(circeHouse[0] == -1) {
				setHouse();
			}
			if(islandWidthResized() || islandHeightResized()) {
				double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
				double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
				circeHouseRect = new Rectangle2D.Double(circeHouse[0]*cellWidth-cellWidth/3,circeHouse[1]*cellHeight-cellHeight/3,cellWidth*8/3, cellHeight*8/3);
			}
			
			
		}
	}

	/**
	 * It will draw the "Yay I Stole!" on top of the head
	 * @param g Drawing Surface object that the message will be drawn on
	 * @post g.fill() will be setted to 170, 10, 10
	 */
	public void stealMessage(DrawingSurface g) {
		g.fill(170,10,10);
		g.text("Yay! I Stole!", (float)(x+width/5), (float)(y-height/10));
		//g.text("huh!", (float)(x+width/5), (float)(y-height/10));
	}
	
	
	private void setHouse() {
		House h = super.getIsland().getCirceHouse();
		circeHouse[0] = h.getXCoor();
		circeHouse[1] = h.getYCoor();
		double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
		double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
		circeHouseRect = new Rectangle2D.Double(circeHouse[0]*cellWidth-cellWidth/3,circeHouse[1]*cellHeight-cellHeight/3,cellWidth*8/3, cellHeight*8/3);
	}

	
}
