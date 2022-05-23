package circeIsland.creatures;

import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public abstract class Visitor extends Creature{
	
	public static final double VISITOR_WIDTH_RATIO = 27.77777778;
	public static final double VISITOR_HEIGHT_RATIO = 11.19402985;
			// 75*135
	
	public Visitor(PImage img, double x, double y) {
		super(img, x, y, VISITOR_WIDTH_RATIO, VISITOR_HEIGHT_RATIO);
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
	

	public abstract String getType();
}
