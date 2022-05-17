package circeIsland.creatures;

import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public abstract class Visitor extends Creature{
	
	public static final int VISITOR_WIDTH = 60000;
	public static final int VISITOR_HEIGHT = 81000;
	// 75*135
	
	public Visitor(PImage img, int x, int y) {
		super(img, x, y, VISITOR_WIDTH, VISITOR_HEIGHT);
	}
	
	public void act() {
		super.act(-1);
	}
	

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
	
	public abstract int destinationDir(int[] destination);
	
	public abstract String getType();
}
