package circeIsland.elements;

import java.util.Timer;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

public class GardenLand extends Element{	

	private String type;//Plant types: grapes, barley, maratho, anithos
	int hydrationLvl; //watering plants increases the hydration level to 3. it decreases by 1 daily so you must
					  //water your plants periodically for them to survive. watering brings it back to 5 
	public int lifeState; //0 is unplanted, 1 is bud, 2 is sprout, 3 is ready to harvest, 4 is dead 
	private static final int UNPLANTED = 0;
	private static final int BUD = 1;
	private static final int SPROUT = 2;
	private static final int GROWN = 3;
	private static final int DEAD = 4;
	
	private int startDay;
	private int currentDay;
	
	private PImage budImage;
	private PImage sproutImage;
	private PImage grownImage;
	private PImage deadImage;

	
	public GardenLand(Island i, PImage[] p, int xInput, int yInput) {
		super(i, p[0], xInput, yInput);
		budImage = p[1];
		sproutImage = p[2];
		grownImage = p[3];
		deadImage = p[4];
		this.type = "";
		lifeState = 0; 
		setStandable(true);
		startDay = 0;
		currentDay = 0;
	}
	
	public String getType() {
		return type;
	}
		
	public void plant(String type, PImage[] images) {
		budImage = images[0];
		sproutImage = images[1];
		grownImage = images[2];
		deadImage = images[3];
		
		this.type = type;
		lifeState = BUD;
		hydrationLvl = 5;
		startDay = getIsland().getSurface().getDays();
	}
	
	public void water() {
		if (isAlive())
			hydrationLvl = 5;
	}
	
	public void grow() {
		int daysPassed = getIsland().getSurface().getDays()-startDay;
		if (hydrationLvl > 2) {
			if (lifeState == BUD && daysPassed == 2)
				lifeState = SPROUT;
			else if (lifeState == SPROUT && daysPassed == 4)
				lifeState = GROWN;
			else if (lifeState == GROWN && daysPassed == 6)
				lifeState = DEAD;
		}
	}
	
	public void harvest() {
		startDay = 0;
		type = "";
		hydrationLvl = 0;
		lifeState = 0;
	}

	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		act();
		surface.push();
		if(getIsInGrid()) {
			PImage toBeUsed;
			if (lifeState == UNPLANTED) {
				toBeUsed = getImage();
			if (lifeState == BUD)
				toBeUsed = budImage;
			if (lifeState == SPROUT)
				toBeUsed = sproutImage;
			if (lifeState == GROWN)
				toBeUsed = grownImage;
			if (lifeState == DEAD)
				toBeUsed = deadImage;
			
			if (toBeUsed != null) {
				double rateX = getIsland().getWidth()/800;
				double rateY = getIsland().getHeight()/600;
				float x = cellWidth*getXCoor();
				float y = cellHeight*getYCoor();
				x *= rateX;
				y *= rateY;
				surface.image(toBeUsed,(float)x + 6,(float)y + 9,(float)(cellWidth*rateX),(float)(cellHeight*rateY));
			}
			}
		}
		
//		surface.fill(105, 67, 45);
//		surface.rect(6+(getXCoor() * cellWidth), 9+(getYCoor()*cellHeight), cellWidth, cellHeight);
//		surface.fill(0);
//		surface.text("garden", 6+(getXCoor() * cellWidth), 9+(getYCoor()*cellHeight)+cellHeight);
		surface.pop();
	}
	
	public void act() { 
		if (currentDay != getIsland().getSurface().getDays() && isAlive()) {
			hydrationLvl --;
			currentDay = getIsland().getSurface().getDays();
		}
		if (hydrationLvl <=0 && isAlive())
			lifeState = DEAD;
		grow();
	}
	
	public int getLifeState() {
		return lifeState;
	}
	
	public boolean isAlive() {
		if (lifeState == BUD || lifeState == SPROUT || lifeState == GROWN)
			return true;
		return false;
	}
	
	public boolean isMature() {
		if (lifeState == GROWN)
			return true;
		return false;
	}
	
	public String toString() {
		return super.toString() + " type: "+type + " hydration level:"+hydrationLvl+" life state:" + lifeState;
	}

}
