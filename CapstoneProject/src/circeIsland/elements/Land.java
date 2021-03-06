package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

/**
 * This class represents land, which makes up the bulk of the island
 * @author Emma Yu
 *
 */
public class Land extends Element{

	/**
	 * Creates a new Land with the given Island, and x and y location on the island grid.
	 * @param i Island on which this Land will draw
	 * @param xInput the x-index ion the grid
	 * @param yInput the y-index on the grid
	 */
	public Land(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		setStandable(true);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		surface.push();
		surface.fill(191, 227, 154);
		surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth, cellHeight);
		surface.pop();
	}

	public String toString() {
		return "LAND  " + super.toString();
	}
}
