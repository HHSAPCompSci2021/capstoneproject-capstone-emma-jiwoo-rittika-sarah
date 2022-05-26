package circeIsland.creatures;

import processing.core.PImage;

/**
 * Visitor extends the Creature.java
 * It recognize wheter circe is nearby the visitor
 * @author Jiwoo Kim
 */
public class Visitor extends Creature{
	
	public static final double VISITOR_WIDTH_RATIO = 27.77777778;
	public static final double VISITOR_HEIGHT_RATIO = 11.19402985;
	private boolean getOut;

			// 75*135
	
	/**
	 * Initilize the visitor 
	 * width and height ratio are setted to 27.78 and 11.19 
	 * other values are setted as a default that shown in Creature.java constructors
	 * @param img the visitor image
	 * @param x x coordinate of the visitor
	 * @param y y coordinate of the visitor
	 */
	public Visitor(PImage img, double x, double y) {
		super(img, x, y, VISITOR_WIDTH_RATIO, VISITOR_HEIGHT_RATIO);
		getOut = false;

	}
	
	/**
	 * Creature.java act method with parameter -1 
	 */
	public void act() {
		super.act(-1);
	}
	
	/**
	 * Check is circe is near by visitor (in the 5*5 square where this visitor is in center)
	 * @pre the island has to be not null
	 * @return circe Grid coordinate if the circe is nearby visitor. null if the circe is not nearby the visitor
	 */
	public int[] checkCirceNearby() {
		int circeX = super.getIsland().getCirce().getXGrid();
		int circeY = super.getIsland().getCirce().getYGrid();
		int[] grid = coorToGrid(x, y);
		
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j<=2; j++) {
				if(grid[0]+i == circeX && grid[0]+j == circeY) {
					int[] circeGrid = {circeX, circeY};
					return circeGrid;
				}
			}
		}
		return null;
	}
	
	/**
	 * @return the type of this class ("Visitor")
	 */
	public String getType() {
		return "Visitor";
	}
	
	/**
	 * @return true if Visitor wants to get out of the island, false otherwise
	 */
	public boolean getGetOut() {
		return getOut;
	}
	
	/**
	 * Set up the visitor willingness to get out
	 * @param b  true if Visitor wants to get out of the island, false otherwise
	 */
	public void setGetOut(boolean b) {
		getOut = b;
	}
}
