package circeIsland.creatures;

import circeIsland.elements.House;

/**
 * 
 * @author Jiwoo Kim
 */
public class MaliciousVisitor extends Visitor{
	
	private boolean stealing;

	public MaliciousVisitor(int x, int y) {
		super(x, y);
		stealing = false;
	}

	public String getType() {
		return "Malicious";
	}
	
	public void act() {
		int[] currentPos = super.coorToGrid(x,y);
		if(super.getIsland().getElement(currentPos[0], currentPos[1]) instanceof House) {
			stealing = true;
			stealing();
		}
		
		int[] circeGrid = checkCirceNearby();
		int dir = -1;
		if(circeGrid == null) {
			House circeHouse = super.getIsland().getCirceHouse();
			int[] circeHouseCoor = {circeHouse.getXCoor(), circeHouse.getYCoor()};
			dir = destinationDir(circeHouseCoor);
		}else {
			dir = destinationDir(circeGrid);
		}
		
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
				return Creature.UP;
			return Creature.DOWN;
		}
		
		if(diffX<0)
			return Creature.LEFT;
		return Creature.RIGHT;
	}
	
	public void stealing() {
		
	}
	

	
}
