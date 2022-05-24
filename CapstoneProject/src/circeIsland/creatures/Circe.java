package circeIsland.creatures;

import circeIsland.elements.*;
import circeIsland.main.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class represents Circe, extends from the Creature
 * It can grab the things and stole, plant, water, harvest, magic and so on.
 * 
 * @author Jiwoo Kim
 */
public class Circe extends Creature{

	public static final double CIRCE_WIDTH_RATIO = 28.57142857;
	public static final double CIRCE_HEIGHT_RATIO = 12.09677419;
	// 70 * 125
	
	private Holdable[] holdings;
	private int currentHold;
	private boolean greeting;
	
	/**
	 * Initilize the Circe 
	 * width and height ratio are setted to 28.57 and 12.10 
	 * other values are setted as a default that shown in Creature.java constructors
	 * @param img the Circe image
	 * @param x x coordinate of the circe
	 * @param y y coordinate of the circe
	 */
	public Circe(PImage img, double x, double y) {
		super(img, x, y, CIRCE_WIDTH_RATIO, CIRCE_HEIGHT_RATIO, 8);
		holdings = new Holdable[6];
		greeting = true;
		currentHold = 0;
		for(int i = 0; i < holdings.length; i++) {
			holdings[i] = null;
		}
	}

	
	//METHODS
	/**
	 * inherited method.
	 * Does not do anything
	 */
	public void act() {
	
	}
	
	/**
	 * Reset the Circe holding index of her inventory
	 * @param boxNum The new index of her inventory
	 */
	public void grab(int boxNum) {
		currentHold = boxNum;
	}
	
	/**
	 * Plant the garden that intersecting with the currently holding seeds.
	 * If Circe does not hold seeds or not intersect with the garden, it does nothing.
	 * 
	 * @pre island has to be not null
	 */
	public void plant() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand && holdings[currentHold].getType() >= Holdable.GRAPE_SEED && holdings[currentHold].getType() <= Holdable.ANITHOS_SEED) {
			GardenLand g = (GardenLand)e;
			g.plant(holdings[currentHold].getType()+4);
		}
	}
	
	/**
	 * @return true if Circe currently opens to welcoming the nymph, false if circe does not do welcoming the nymph.
	 */
	public boolean getGreeting() {
		return greeting;
	}
	
	/**
	 *  Set the Circe to opens for welcoming the nymphs, if the circe does not welcomes previously, and vice versa. 
	 */
	public void setGreeting() {
		greeting = !greeting;
	}
	
	/**
	 * Water the plants in the garden. (reset the hydrated level of the garden)
	 * It only water the plants if the circe is intersects with gardenland, and hold the water.
	 * If not, it does nothing.
	 * 
	 * @pre island has to be not null
	 */
	public void water() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand && holdings[currentHold].getType() == Holdable.WATER) {
			((GardenLand) e).water();
		}
	}
	
	/**
	 * Harvest the garden. 
	 * It only harvest if the plant is mature, and has the empty spots in inventory.
	 * After the harvest, it remove the plant from the garden, and add the mature plant to her inventory.
	 * 
	 * @pre the GardenLand has to have the crops inside
	 * @param g the Garden that she wants to harvest
	 * @return true if it suceed the planting, false otherwise.
	 */
	public boolean harvest(GardenLand g) {
		if(g.isMature() && nextEmptySpace() != -1) {
			addOnInventory(new Holdable(g.getType()));
			g.harvest();
			return true;
		}
		return false;
	}
	
	/**
	 * The circe turn malicious visitor to the pig.
	 * Malicious visitor would be deleted in the land, and pig will spawn
	 * @pre the Malicious visitor has to be in grid
	 * @param visitor the malicious visitor that circe needs to turn into pig
	 */
	public void turnPig(MaliciousVisitor visitor) {
		Pig pig = new Pig(super.getIsland().getImage("pig"),visitor.getX(), visitor.getY());
		pig.putOnIsland(visitor.getIsland());
		visitor.removeFromIsland();
	}
	
	/**
	 * The Circe will be drawn by Creature.java draw method,
	 * and Additionally, the circe inventory would be drawn
	 * If circe is not in the grid, it does not draw anything.
	 * 
	 * @pre Drawing Surface has to be not the null
	 * @param g Drawing surface object that the creature will be drawn on
	 * @post the drawing surface g.stroke would be set to 255,205,0, and the g.fill set to transparent
	 */
	public void draw(DrawingSurface g) {
		super.draw(g);
		if(super.getIsInGrid()) {
			g.push();
			g.fill(255);
			
			float screenWidth = super.getIsland().getSurface().width;
			float screenHeight = super.getIsland().getSurface().height;
			float cell = screenWidth/20;
			float cellStartX =  (screenWidth - (holdings.length * cell))*1/10;
			g.strokeWeight(cell/10);
			g.stroke(110,110,110);
			for(int i = 0; i<holdings.length; i++) {
				g.rect(cellStartX + cell*i,screenHeight-cell*3/2, cell, cell);
				if(holdings[i] != null)
					holdings[i].draw(g, cellStartX + cell*i, screenHeight-cell*3/2, cell*9/10, cell*9/10);
			}
			g.noFill();
			g.stroke(255,205,0);
			g.rect(cellStartX + cell*currentHold, screenHeight-cell*3/2,cell, cell);
			g.pop();
		}
	}
	
	/**
	 * Get the inventory's index by inputing the x and y coordinate
	 * 
	 * @pre the island has to be not null
	 * @param x x coordinate on the screen
	 * @param y y coordinate on the screen
	 * @return inventory's index that corresponds to x and y coordinates. if the coordinate does not belong to any of the inventory, it returns -1.
	 */
	public int getInventoryByCoor (int x, int y) {
		float screenWidth = super.getIsland().getSurface().width;
		float screenHeight = super.getIsland().getSurface().height;
		float cell = screenWidth/20;
		float cellStartX =  (screenWidth - (holdings.length * cell))*1/10;
		if(screenHeight-cell*3/2 < y && y < screenHeight-cell*1/2 &&
			cellStartX < x && cellStartX + holdings.length * cell > x) {
			return (int)((x-cellStartX)/cell);
		}
		return -1;
	}

	/**
	 * @return the type of this class ("Circe")
	 */
	public String getType() {
		return "Circe";
	}
	
	/**
	 * add the Holdable h to the circe inventory first empty space
	 * If the there is no empty space in the inventory, it would do nothing
	 * @param h Holdable that needs to put in circe's inventory
	 * @return true if the holdable successfully adds on the inventory. False otherwise
	 */
	public boolean addOnInventory(Holdable h) {
		int next= nextEmptySpace();
		if(next != -1) {
			holdings[next] = h;
			return true;
		}
		return false;
	}
	
	/**
	 * @return the whole inventory of the circe
	 */
	public Holdable[] getInventory() {
		return holdings;
	}
	
	/**
	 * remove the Holdable object that circe holding in the index of i
	 * @pre i has to be greater than -1, and less than 6
	 * @param i the index of the circe's inventory that want to set it as null
	 */
	public void removeFromInventory(int i) {
		holdings[i] = null;
	}
	
	/**
	 * Switiching the holdable that is on index i and holdabe that is on index j
	 * @pre i and j has to be greather than -1, and less than 6
	 * @param i the index of the circe inventory
	 * @param j the index of the circe inventory
	 */
	public void rearrangeInventory(int i, int j) {
		Holdable temp = holdings[i];
		holdings[i] = holdings[j];
		holdings[j] = temp;
	}
	
	/**
	 * set the index i with the holdable h
	 * @pre 0<= i < 6
	 * @param i index of the inventory
	 * @param h holdable that need to be set
	 * @return the holdable object that originally at the index i.
	 */
	public Holdable setInventory(int i, Holdable h) {
		Holdable temp = holdings[i];
		holdings[i] = h;
		return temp;
	}
	
	/**
	 * @return the index of circe's inventory that circe currently holding
	 */
	public int getCurrentHold() {
		return currentHold;
	}
	
	/**
	 * check the circe has the certain type of the holdable
	 * @param type holdable types
	 * @return -1 if the types of the holdable does not exist in her inventory, otherwise it returns index of first occurance holdable types.
	 */
	public int inventoryContains(int type) {
        for(int i = 0; i<holdings.length; i++) {
            if(holdings[i] != null && holdings[i].getType() == type) {
                return i;
            }
        }
        return -1;

    }
	
	private int nextEmptySpace() {
		for (int i = 0; i<holdings.length; i++) {
			if (holdings[i] == null)
				return i;
		}
		return -1;
	}
	
	
}
