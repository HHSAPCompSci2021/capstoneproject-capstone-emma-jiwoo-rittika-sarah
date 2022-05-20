package circeIsland.creatures;

import circeIsland.elements.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class Circe extends Creature{

	public static final double CIRCE_WIDTH = 35;
	public static final double CIRCE_HEIGHT = 62;
	// 70 * 125
	
	private Holdable[] holdings;
	private int currentHold;
	private boolean greeting;
	
	public Circe(PImage img, int x, int y) {
		super(img, x, y, CIRCE_WIDTH, CIRCE_HEIGHT, 8);
		holdings = new Holdable[6];
		greeting = true;
		currentHold = 0;
		for(int i = 0; i < holdings.length; i++) {
			holdings[i] = null;
		}
	}

	
	//METHODS
	public void act() {
	
	}
	
	public void grab(int boxNum) {
		currentHold = boxNum;
	}
	
	public void plant() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand && holdings[currentHold].getType() >= Holdable.GRAPE_SEED && holdings[currentHold].getType() <= Holdable.ANITHOS_SEED) {
			Holdable p = new Holdable(holdings[currentHold].getType()+4);
			GardenLand g = (GardenLand)e;
			g.plant(Integer.toString(holdings[currentHold].getType()+4));
		}
	}
	
	public boolean getGreeting() {
		return greeting;
	}
	
	public void setGreeting() {
		greeting = !greeting;
	}
	
	public void water() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand && holdings[currentHold].getType() == Holdable.WATER) {
			((GardenLand) e).water();
		}
	}
	
	public boolean harvest(GardenLand g) {
		if(g.isMature() && nextEmptySpace() != -1) {
			System.out.println("harvesting");
				addOnInventory(new Holdable(Integer.parseInt((g).getType())));
				g.harvest();
				return true;
		}
		return false;
	}
	
	public void turnPig(MaliciousVisitor visitor) {
		Pig pig = new Pig(super.getIsland().getImage("pig"),(int)visitor.getX(), (int)visitor.getY());
		pig.putOnIsland(visitor.getIsland());
		visitor.removeFromIsland(visitor.getIsland());
	}
	
	public void draw(PApplet g) {
		super.draw(g);
		g.fill(255);
		
//		float screenWidth = super.getIsland().WIDTH;
//		float screenHeight = super.getIsland().HEIGHT;
//		float cell = 20000/screenWidth;
//		float cellStartX =  (screenWidth - (holdings.length * cell))/2;
//		for(int i = 0; i<holdings.length; i++) {
//			g.rect(cellStartX, screenHeight-cell-20, cellStartX + cell, screenHeight-20);
//		}
	}
	
	public int nextEmptySpace() {
		for (int i = 0; i<holdings.length; i++) {
			if (holdings[i] == null)
				return i;
		}
		return -1;
	}
	
	public String getType() {
		return "Circe";
	}
	
	public boolean addOnInventory(Holdable h) {
		int next= nextEmptySpace();
		if(next != -1) {
			holdings[next] = h;
			return true;
		}
		return false;
	}
	
	public Holdable[] getInventory() {
		return holdings;
	}
	
	public void removeFromInventory(int i) {
		holdings[i] = null;
	}
	
	public void rearrangeInventory(int i, int j) {
		Holdable temp = holdings[i];
		holdings[i] = holdings[j];
		holdings[j] = temp;
	}
	
	public Holdable setInventory(int i, Holdable h) {
		Holdable temp = holdings[i];
		holdings[i] = h;
		return temp;
	}
	
	public int inventoryContains(int type) {
        for(int i = 0; i<holdings.length; i++) {
            if(holdings[i] != null && holdings[i].getType() == type) {
                return i;
            }
        }
        return -1;

    }
}
