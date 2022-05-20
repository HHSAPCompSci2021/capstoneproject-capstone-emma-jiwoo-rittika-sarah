package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

public class Land extends Element{

	public Land(Island i,  PImage p, int xInput, int yInput) {
		super(i, p, xInput, yInput);
		setStandable(true);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		surface.push();
		surface.fill(191, 227, 154);
		surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth, cellHeight);
//		surface.fill(0);
//		surface.text("land", 6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight)+cellHeight);
		surface.pop();
	}

	public String toString() {
		return "LAND  " + super.toString();
	}
}
