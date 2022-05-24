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
	private Rectangle2D.Double deck;
	private boolean getOut;
	private int emotion;
	private boolean feeding;
	private boolean newDay;
	
	public static final int HAPPY = 3;
	public static final int OKAY = 2;
	public static final int BAD = 1;
	public static final int ANGRY = 0;
	

	public Nymph(PImage img, double x, double y) {
		super(img, x, y);
		house = new int[2];
		house[0] = -1;
		house[1] = -1;
		inHouse = false;
		houseRect = null;
		deck = null;
		emotion = 5;
		feeding = false;
		newDay = false;
	}	
	
	public void draw(DrawingSurface g) {
		if(super.getIsInGrid()) {
			if(!inHouse) {
				super.draw(g);
			}
			g.fill(170,10,10);
			if(emotion == BAD) {
				g.text("huh?", (float)(x+width/5), (float)(y-height/10));
			}
			if(emotion < BAD) {
				g.text("huh!", (float)(x+width/5), (float)(y-height/10));
			}
			if(house[0] == -1) {
				setHouse();
			}
			if(islandWidthResized() || islandHeightResized()) {
				double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
				double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
				houseRect = new Rectangle2D.Double(house[0]*cellWidth-cellWidth/2,house[1]*cellHeight-cellHeight/2,cellWidth*2, cellHeight*2);
				deck = new Rectangle2D.Double(3*cellWidth-cellWidth/2,5*cellHeight-cellHeight/2,cellWidth*2, cellHeight*2);
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
		if(hours == 0) {
			if(newDay) {
				if(feeding==false) {
					emotion--;
				}
				feeding = false;
				newDay =false;
			}
		}else {
			newDay = true;
		}
		
		if(emotion<=ANGRY) {
			if(this.intersects(deck)) {
				getOut = true;
				return;
			}
			int[] deck = {3, 5};
			int dir = destinationDir(deck);
			super.act(dir);
			return;
		}
		
		if(house[0] != -1 && hours >= 16) {
			if(this.intersects(houseRect)) {
				inHouse = true;
				if(emotion < HAPPY && feeding)
					emotion++;
			}else {
				int dir = destinationDir(house);
				super.act(dir);
			}
			return;
		}
		if(house[0] == -1 && hours>=16) {
			emotion--;
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
	public int getEmotion() {
		return emotion;
	}
	
	public void setEmotion(int i) {
		emotion = i;
	}
	
	public int[] getHouseLoc() {
		return house;
	}
	
	public void setGetOut(boolean b) {
		getOut = b;
	}
	
	public boolean getGetOut() {
		return getOut;
	}

	public String getType() {
		return "Nymph";
	}
	
	public void fed() {
		feeding = true;
		if(emotion < HAPPY)
			emotion++;
	}
	
	private boolean setHouse() {
		if(deck == null) {
			double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
			double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
			deck = new Rectangle2D.Double(3*cellWidth-cellWidth/2,5*cellHeight-cellHeight/2,cellWidth*2, cellHeight*2);
		}
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
		
}
