package circeIsland.creatures;

import circeIsland.screens.Island;
import circeIsland.elements.*;

/**
 * 
 * @author Jiwoo Kim
 */
public class Creature {
	private int x, y;
	private int xVelocity, yVelocity;
	private Island myIsland;
	
	public Creature (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() {
		
	}
	
	public Element checkGrid() {
		double width = myIsland.getWidth();
		double height = myIsland.getHeight();
		
		return null;
	}
	
	
	
}
