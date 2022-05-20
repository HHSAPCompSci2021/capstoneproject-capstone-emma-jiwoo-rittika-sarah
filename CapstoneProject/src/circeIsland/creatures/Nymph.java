package circeIsland.creatures;

import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class Nymph extends Visitor{

	public Nymph(PImage img, int x, int y) {
		super(img, x, y);
	}	
	
	public String getType() {
		return "Nymph";
	}
	
	public void act() {
		int[] circeGrid = checkCirceNearby();
		int[] myGrid = coorToGrid(x,y);
		if(circeGrid == null || !super.getIsland().getCirce().getGreeting()) {
			super.act();
			return;
		}
		if(circeGrid.equals(myGrid)) {
			return;

		}
		
		int dir = destinationDir(circeGrid);
		super.act(dir);

	}
	
	
	
}
