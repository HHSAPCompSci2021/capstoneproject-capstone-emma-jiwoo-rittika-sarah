package circeIsland.creatures;

import circeIsland.elements.*;

/**
 * 
 * @author Jiwoo Kim
 */
public class Pig extends Creature{
	public static final int PIG_WIDTH = 60;
	public static final int PIG_HEIGHT = 40;

	public Pig(int x, int y) {
		super(x, y, PIG_WIDTH, PIG_HEIGHT, 3);
	}
	
	public void act() {
		super.act(-1);
	}
	
	public boolean canStand(double coorX, double coorY) {
		int[] grid = coorToGrid(x, y);
		if (super.getIsland().getElement(grid[0], grid[1]) == null) {
			return false;
		}
		return super.getIsland().getElement(grid[0], grid[1]) instanceof PigPen;
	}
	
	public String getType() {
		return "Pig";
	}

}
