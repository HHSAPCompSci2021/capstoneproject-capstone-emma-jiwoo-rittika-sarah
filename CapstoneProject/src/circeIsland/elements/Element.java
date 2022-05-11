package circeIsland.elements;

import circeIsland.screens.Island;

public abstract class Element {

	private Island island;
	private int x, y;
	private boolean isInGrid;
	private boolean standable;

	public Element(Island i, int xInput, int yInput) {
		island = i;
		x = xInput;
		y = yInput;
		isInGrid = true;
		standable = false;
	}
	//METHOD
	
	public void putOnIsland(Island i) { //even though is already done in fill? 
		
		isInGrid = true;
	}
	
	public void removeFromIsland(Island i) {
		isInGrid = false;
	}
	
	public abstract void draw();
	
	public void setStandable (boolean b) {
		standable = b;
	}
	
	public void setIsInGrid(boolean b) {
		isInGrid = b;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Island getIsland() {
		return island;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	 
}
