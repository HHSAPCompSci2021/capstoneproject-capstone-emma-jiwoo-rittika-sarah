package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

/**
 * This class represents a Pig Pen, where pigs live
 * @author Emma Yu
 *
 */
public class PigPen extends Element{

	/**
	 * Creates a new PigPen with the given Island, and x and y location on the island grid.
	 * @param i Island on which this PigPen will draw
	 * @param p the PImage of this pig pen
	 * @param xInput the x-index ion the grid
	 * @param yInput the y-index on the grid
	 */
	public PigPen(Island i, PImage p, int xInput, int yInput) {
		super(i, p, xInput, yInput);
		setStandable(true);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
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
