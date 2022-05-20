package circeIsland.creatures;

import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public abstract class Visitor extends Creature{
	
	public static final double VISITOR_WIDTH = 36;
	public static final double VISITOR_HEIGHT = 67;
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
	
	public int destinationDir(int[] destination) {
		int[] grid = coorToGrid(x, y);
		int diffX = grid[0] - destination[0];
		int diffY = grid[1] - destination[1];
		
		if(diffX == 0 && diffY == 0) {
			return -1;
		}
		
		if(Math.abs(diffY) > Math.abs(diffX)) {
			if(diffY < 0) 
				return Creature.DOWN;
			return Creature.UP;
		}
		
		if(diffX<0)
			return Creature.RIGHT;
		return Creature.LEFT;

	}	
	public abstract String getType();
}
