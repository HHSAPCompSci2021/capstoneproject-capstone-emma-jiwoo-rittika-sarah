package circeIsland.creatures;

import java.awt.geom.Rectangle2D;

import circeIsland.elements.Element;
import circeIsland.elements.House;
import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * This class represents nymph, extends from the Visitor
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
	
	/**
	 * Initilize the nymph 
	 * other values are setted as a default that shown in Creature.java constructors and visitor.java constructor
	 * @param img the visitor image
	 * @param x x coordinate of the visitor
	 * @param y y coordinate of the visitor
	 */
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
	
	/**
	 * The nymph will be drawn by Creature.java draw method
	 * It will try to set nymph's house if nymph does not have a house
	 * It will draw "huh?" or "huh!" on top of its head when it feels bad 
	 * @pre Drawing Surface has to be not the null
	 * @param g Drawing surface object that the creature will be drawn on
	 */
	public void draw(DrawingSurface g) {
		if(super.getIsInGrid()) {
			if(!inHouse) {
				super.draw(g);
			}
			g.fill(170,10,10);
			if(emotion == BAD) {
				g.text("huh!", (float)(x+width/5), (float)(y-height/10));
			}
			if(emotion < BAD) {
				g.text("huh?", (float)(x+width/5), (float)(y-height/10));
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
	
	/**
	 * If the nymph wants to go out the island, she goes toward the deck to leave the island
	 * If nymph has a house, and the times is later than 16, she would go to home.
	 * If nymph does not fed for a day, she would be depressed a little bit more
	 * If nymph does not have a house after 16, she would be angry and wanting to go out the island.
	 * If the checkCirceNearby() returns not a null, it  will chase the circe
	 * @pre island has to be not null
	 */
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
	
	/**
	 * @return the integer that represents nymph's feeling
	 */
	public int getEmotion() {
		return emotion;
	}
	
	/**
	 * Set the nymph's emotion with the parameter
	 * @param i integer that represents Nymph's new emotion state
	 */
	public void setEmotion(int i) {
		emotion = i;
	}
	
	/**
	 * @return the grid coordinate if nymph has a house, null if nymph does not have a house
	 */
	public int[] getHouseLoc() {
		return house;
	}
	
	/**
	 * Set wheter nymph wants to go out or not by boolean b
	 * true represents nymph wants to go out, and false represents nymph does not want to go out the island
	 * @param b new nymph wanting go out state
	 */
	public void setGetOut(boolean b) {
		getOut = b;
	}
	
	/**
	 * @return true if nymph wants to get out of the island, false otherwise
	 */
	public boolean getGetOut() {
		return getOut;
	}

	/**
	 * @return the type of this class ("Nymph")
	 */
	public String getType() {
		return "Nymph";
	}
	
	/**
	 * updating the emotion state.
	 */
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
