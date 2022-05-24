package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

public class PigPen extends Element{

	public PigPen(Island i, PImage p, int xInput, int yInput) {
		super(i, p, xInput, yInput);
		setStandable(true);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
//		surface.push();
//		surface.fill(253, 221, 230);
//		surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth, cellHeight);
//		surface.fill(0);
//		surface.text("pig pen", 10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight)+cellHeight);
//		surface.pop();
		
		
		if (getImage() != null) {
			double rateX = getIsland().getWidth()/800;
			double rateY = getIsland().getHeight()/600;
			float x = cellWidth*getXCoor();
			float y = cellHeight*getYCoor();
			x *= rateX;
			y *= rateY;
			surface.image(getImage(),(float)x + 6,(float)y + 9,(float)(cellWidth*rateX),(float)(cellHeight*rateY));
		}
	}

}
