package circeIsland.creatures;

import circeIsland.screens.Island;

/**
 * 
 * @author Jiwoo Kim
 */
public abstract class Visitor extends Creature{
	
	public static final int VISITOR_WIDTH = 40;
	public static final int VISITOR_HEIGHT = 60;
	
	public Visitor(int x, int y) {
		super(x, y, VISITOR_WIDTH, VISITOR_HEIGHT);
	}

	public int[] checkCirceNearby() {
		int circeX = super.getIsland().getCirce().getXGrid();
		int circeY = super.getIsland().getCirce().getYGrid();
		int[] grid = coorToGrid(x, y);
		
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j<=2; j++) {
				if(grid[0]+i == circeX && grid[0]+j == circeY) {
					int[] circeCoor = {circeX, circeY};
					return circeCoor;
				}
			}
		}
		return null;
	}
	
	public abstract String getType();
}
