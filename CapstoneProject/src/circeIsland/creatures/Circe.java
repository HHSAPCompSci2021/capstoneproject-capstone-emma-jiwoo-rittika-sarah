package circeIsland.creatures;

import circeIsland.elements.*;
import processing.core.PApplet;

/**
 * 
 * @author Jiwoo Kim
 */
public class Circe extends Creature{

	public static final int CIRCE_WIDTH = 40;
	public static final int CIRCE_HEIGHT = 60;
	
	private Holdable[] holdings;
	private int currentHold;
	
	public Circe(int x, int y) {
		super(x, y, CIRCE_WIDTH, CIRCE_HEIGHT, 20);
		holdings = new Holdable[5];
		currentHold = 0;
		for(int i = 0; i < 5; i++) {
			holdings[i] = null;
		}
	}

	
	//METHODS
	public void grab() {
		
	}
	
	public void brew() {
		
	}
	
	public void plant() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand) {
			if(((GardenLand) e).getType().equals(""));
		}
	}
	
	public void water() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand) {
			((GardenLand) e).water();
		}
	}
	
	public void harvest() {
		Element e = super.getIsland().getElement(super.getXGrid(), super.getYGrid());
		if(e instanceof GardenLand) {
			if(((GardenLand) e).getLifeState() >= 3)
				((GardenLand) e).harvest();
				
		}
	}
	
	public void turnPig() {
		
	}
	
	public void draw(PApplet g) {
		super.draw(g);
	}
	
	
	
}
