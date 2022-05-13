package circeIsland.elements;

import java.awt.Rectangle;

import circeIsland.creatures.Circe;
import circeIsland.creatures.Creature;
import circeIsland.main.DrawingSurface;
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
	
	public void putOnIsland(Island i, int x, int y) { //even though is already done in fill? 
		if (i.getElement(x,  y) == null)
			i.setElement(this, x, y);
		isInGrid = true;
		island = i;
	}
	
	public void removeFromIsland(Island i) {
		i.setElement(null, x, y);
		isInGrid = false;
		island = null;
	}
	
	public abstract void draw(DrawingSurface surface, float cellWidth, float cellHeight);
	
	public boolean intersects(Creature c, float cellWidth, float cellHeight) {
		Rectangle r = new Rectangle((int)(10 + (getXCoor() * cellWidth)), (int)(10 + (getYCoor()*cellHeight)), (int)cellWidth, (int)cellHeight);
		if (c.intersects(r))
			return true;
		return false;
	}
	
	public void setStandable (boolean b) {
		standable = b;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setIsInGrid(boolean b) {
		isInGrid = b;
	}
	
	public Island getIsland() {
		return island;
	}
	
	public boolean getStandable() {
		return standable;
	}
	
	public int getXCoor() {
		return x;
	}
	
	public int getYCoor() {
		return y;
	}
	
	 
}
