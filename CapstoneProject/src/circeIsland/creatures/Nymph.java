package circeIsland.creatures;

import circeIsland.elements.Element;
import circeIsland.elements.House;
import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class Nymph extends Visitor{
	
	private int[] house;

	public Nymph(PImage img, int x, int y) {
		super(img, x, y);
		house = new int[2];
//		setHouse();
	}	
	
	public String getType() {
		return "Nymph";
	}
	
	public void draw(DrawingSurface g) {
		if(super.getIsInGrid()) {
			super.draw(g);
			if(house[0] == -1) {
				setHouse();
			}
		}
	}
	
	public void act() {
		int[] circeGrid = checkCirceNearby();
		int[] myGrid = coorToGrid(x,y);
//		int hours = super.getIsland().getSurface().getHours();
		if(house[0] != -1 && super.getIsland().getSurface().getHours() >= 16) {
			int dir = destinationDir(house);
			super.act(dir);
		}
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
	
	private boolean setHouse() {
		Element[][] elements = super.getIsland().getElements();
		for(int i = 0; i<elements.length; i++) {
			for(int j = 0; j<elements[0].length; j++) {
				if(elements[j][i] instanceof House) {
					if(!((House) elements[j][i]).getTaken())
						house[0] = j;
						house[1] = i;
						((House) elements[j][i]).setTaken(true);
						return true;
				}
			}
		}
		house[0] = -1;
		house[1] = -1;
		return false;
	}
	
	public int[] getHouseLoc() {
		return house;
	}
	
	
}
