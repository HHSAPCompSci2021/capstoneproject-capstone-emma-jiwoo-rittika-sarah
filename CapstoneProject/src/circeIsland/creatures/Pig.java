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
	
	public boolean canStand(int gridX, int gridY) {
		return super.getIsland().getElement(gridX, gridY) instanceof PigPen;
	}
	
	public String getType() {
		return "Pig";
	}

}
