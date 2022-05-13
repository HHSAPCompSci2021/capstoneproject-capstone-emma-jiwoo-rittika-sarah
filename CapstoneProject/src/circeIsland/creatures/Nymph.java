package circeIsland.creatures;

/**
 * 
 * @author Jiwoo Kim
 */
public class Nymph extends Visitor{

	public Nymph(int x, int y) {
		super(x, y);
	}	
	
	public String getType() {
		return "Nymph";
	}
	
	public int circeDir(int[] circeGrid) {
		int[] grid = coorToGrid(x, y);
		int diffX = grid[0] - circeGrid[0];
		int diffY = grid[1] - circeGrid[1];
		
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
	
}
