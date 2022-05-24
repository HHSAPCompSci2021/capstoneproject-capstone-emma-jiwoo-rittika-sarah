package circeIsland.elements;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class Pond extends Element{

	/**
	 * Creates a new Pond with the given Island, and x and y location on the island grid.
	 * @param i Island on which this Pond will draw
	 * @param xInput the x-index ion the grid
	 * @param yInput the y-index on the grid
	 */
	public Pond(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		setStandable(false);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		surface.push();
		surface.noFill();
		//surface.fill(210, 229, 246);
		surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth, cellHeight);
		//surface.fill(0);
		//surface.text("river", 6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight)+cellHeight);
		surface.pop();
	}

}
