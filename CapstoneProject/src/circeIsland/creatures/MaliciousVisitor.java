package circeIsland.creatures;

/**
 * 
 * @author Jiwoo Kim
 */
public class MaliciousVisitor extends Visitor{

	public MaliciousVisitor(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return "Malicious";
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
				return Creature.UP;
			return Creature.DOWN;
		}
		
		if(diffX<0)
			return Creature.LEFT;
		return Creature.RIGHT;

	}
	
}
