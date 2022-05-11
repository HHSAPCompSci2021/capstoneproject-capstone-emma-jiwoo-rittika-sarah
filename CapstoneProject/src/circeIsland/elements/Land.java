package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class Land extends Element{

	public Land(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		setStandable(true);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		surface.fill(191, 227, 154);
		surface.rect(10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight), cellWidth, cellHeight);
	}

}
