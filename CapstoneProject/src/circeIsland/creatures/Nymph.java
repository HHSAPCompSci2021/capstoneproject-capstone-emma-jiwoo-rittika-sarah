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
	
	public void act() {
		int[] circeGrid = checkCirceNearby();
		int[] myGrid = coorToGrid(x,y);
		if(circeGrid == null || !super.getIsland().getCirce().getGreeting()) {
			super.act();
			return;
		}
		if(circeGrid.equals(myGrid)) {
			return;
		}
		
		int dir = destinationDir(circeGrid);
		super.act(dir);
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
	
}
