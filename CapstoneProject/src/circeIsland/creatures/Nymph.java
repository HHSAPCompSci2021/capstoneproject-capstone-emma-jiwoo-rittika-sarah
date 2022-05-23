package circeIsland.creatures;

import java.awt.geom.Rectangle2D;

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
	private Rectangle2D.Double houseRect;
	private boolean inHouse;

	public Nymph(PImage img, double x, double y) {
		super(img, x, y);
		house = new int[2];
		house[0] = -1;
		house[1] = -1;
		inHouse = false;
		houseRect = null;
	}	
	
	public String getType() {
		return "Nymph";
	}
	
	public void draw(DrawingSurface g) {
		if(super.getIsInGrid()) {
			if(!inHouse) {
				super.draw(g);
			}
			if(house[0] == -1) {
				setHouse();
			}
			if(islandWidthResized() || islandHeightResized()) {
				double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
				double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
				houseRect = new Rectangle2D.Double(house[0]*cellWidth-cellWidth/2,house[1]*cellHeight-cellHeight/2,cellWidth*2, cellHeight*2);
			}
			
		}
	}
	
	public void act() {
		int[] circeGrid = checkCirceNearby();
		int[] myGrid = coorToGrid(x,y);
		int hours = super.getIsland().getSurface().getHours();
		if(hours<16) {
			inHouse = false;
		}
		
		if(house[0] != -1 && hours >= 16) {
			if(this.intersects(houseRect)) {
				inHouse = true;
			}else {
				int dir = destinationDir(house);
				super.act(dir);
			}
			return;
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
					if(!((House) elements[j][i]).getTaken()) {
						house[0] = j;
						house[1] = i;
						((House) elements[j][i]).setTaken(true);	
						double cellWidth = (super.getIsland().getSurface().width - 11) / elements[0].length;
						double cellHeight = (super.getIsland().getSurface().height - 17) / elements.length;
						houseRect = new Rectangle2D.Double(j*cellWidth-cellWidth/2,i*cellHeight-cellHeight/2,cellWidth*2, cellHeight*2);
						return true;
					}
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
