package circeIsland.creatures;

import circeIsland.elements.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class Circe extends Creature{

	public static final int CIRCE_WIDTH = 25;
	public static final int CIRCE_HEIGHT = 55;
	
	private Holdable[] holdings;
	private int currentHold;
	
	public Circe(PImage img, int x, int y) {
		super(img, x, y, CIRCE_WIDTH, CIRCE_HEIGHT, 8);
		holdings = new Holdable[5];
		currentHold = 0;
		for(int i = 0; i < 5; i++) {
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
		if(e instanceof GardenLand && holdings[currentHold].getType() == Holdable.SEED) {
			if(((GardenLand) e).getType().equals(holdings[currentHold].getDetails()));
		}
	}
	
	public void water() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand && holdings[currentHold].getType() == Holdable.WATER) {
			((GardenLand) e).water();
		}
	}
	
	public void harvest() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand) {
			if(((GardenLand) e).getLifeState() >= 3)
				holdings[nextEmptySpace()] = new Holdable(Holdable.SEED, ((GardenLand) e).getType());
				((GardenLand) e).harvest();
		}
	}
	
	public void turnPig(MaliciousVisitor visitor) {
		int diffX = Math.abs(visitor.getXGrid() - this.getXGrid());
		int diffY =Math.abs(visitor.getYGrid() - this.getYGrid());
		if(diffX < 3 && diffY < 3) {
			Pig pig = new Pig((int)visitor.getX(), (int)visitor.getY());
			pig.putOnIsland(visitor.getIsland());
			visitor.removeFromIsland(visitor.getIsland());

		}
	}
	
	public void draw(PApplet g) {
		super.draw(g);
		
	}
	
	public int nextEmptySpace() {
		for (int i = 0; i<5; i++) {
			if (holdings[i] == null)
				return i;
		}
		return -1;
	}
	
	public String getType() {
		return "Circe";
	}
	
	
}
