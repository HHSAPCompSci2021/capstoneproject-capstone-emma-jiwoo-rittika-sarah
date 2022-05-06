package circeIsland.creatures;

import circeIsland.screens.Island;


import circeIsland.elements.*;

/**
 * 
 * @author Jiwoo Kim
 */
public abstract class Creature {
	private double x, y;
	private int xVelocity, yVelocity;
	
	public Creature (int x, int y) {
		this.x = x;
		this.y = y;
		xVelocity = 1;
		yVelocity = 1;
	}
	
	public void move() {
		
	}
	
	public int[] checkGrid(Island myIsland) {
		double width = myIsland.getWidth();
		double height = myIsland.getHeight();
		int xGrid = (int) (x/(width/10));
		int yGrid = (int) (y/(height/10));
		
		int[] coor = {xGrid, yGrid};
		return coor;
	}
	
	public abstract void draw();
	
	
	
}
