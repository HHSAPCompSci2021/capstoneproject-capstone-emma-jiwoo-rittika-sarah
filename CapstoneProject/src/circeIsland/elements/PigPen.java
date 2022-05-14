package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class PigPen extends Element{

	public PigPen(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		setStandable(false);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		surface.push();
		surface.fill(253, 221, 230);
		surface.rect(10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight), cellWidth, cellHeight);
		surface.fill(0);
		surface.text("pig pen", 10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight)+cellHeight);
		surface.pop();
	}

}
