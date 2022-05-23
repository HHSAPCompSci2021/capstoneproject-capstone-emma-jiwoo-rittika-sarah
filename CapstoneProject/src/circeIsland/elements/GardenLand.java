package circeIsland.elements;

import java.util.Timer;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

public class GardenLand extends Element{	

	private int type;//Plant types: grapes, barley, maratho, anithos
	int hydrationLvl; //watering plants increases the hydration level to 3. it decreases by 1 daily so you must
					  //water your plants periodically for them to survive. watering brings it back to 5 
	public int lifeState; //0 is unplanted, 1 is bud, 2 is sprout, 3 is ready to harvest, 4 is dead 
	public static final int GRAPE_SEED = 1;
	public static final int BARLEY_SEED = 2;
	public static final int MARATHOS_SEED = 3;
	public static final int ANITHOS_SEED = 4;
	
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
		this.type = 0;
		lifeState = 0; 
		setStandable(true);
		startDay = 0;
		currentDay = 0;
	}
	
	public int getType() {
		return type;
	}
	
	public void plant(int type) {
		this.type = type;
		lifeState = BUD;
		hydrationLvl = 5;
		startDay = getIsland().getSurface().getDays();
	}
		
	public void plant(int type, PImage[] images) {
		budImage = images[1];
		sproutImage = images[2];
		grownImage = images[3];
		deadImage = images[4];
		
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
		type = 0;
		hydrationLvl = 0;
		lifeState = 0;
	}

	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		act();
		surface.push();
		//System.out.println(lifeState);
		if(getIsInGrid()) {
			PImage toBeUsed = null;
			if (lifeState == UNPLANTED) {
				toBeUsed = getImage();
			}
			else if (lifeState == BUD) {
				//System.out.println("BUD");
				toBeUsed = budImage;
			}
			else if (lifeState == SPROUT) {
				//System.out.println("Sprout");
				toBeUsed = sproutImage;
			}
			else if (lifeState == GROWN) {
				//System.out.println("GRown");
				toBeUsed = grownImage;
			}
			else if (lifeState == DEAD) {
				//System.out.println("Dead");
				toBeUsed = deadImage;
			}
			
			if (toBeUsed != null) {
				double rateX = getIsland().getWidth()/800;
				double rateY = getIsland().getHeight()/600;
				float x = cellWidth*getXCoor();
				float y = cellHeight*getYCoor();
				x *= rateX;
				y *= rateY;
				surface.image(toBeUsed,(float)x + 6,(float)y + 9,(float)(cellWidth*rateX),(float)(cellHeight*rateY));
			}
			else {
				System.out.println("NO IMAGE????");
			}
		}
		
		surface.pop();
	}
		
//		surface.fill(105, 67, 45);
//		surface.rect(6+(getXCoor() * cellWidth), 9+(getYCoor()*cellHeight), cellWidth, cellHeight);
//		surface.fill(0);
//		surface.text("garden", 6+(getXCoor() * cellWidth), 9+(getYCoor()*cellHeight)+cellHeight);
		
	
	
	public void act() { 
		if (currentDay != getIsland().getSurface().getDays() && isAlive()) {
			//hydrationLvl --;
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
	
	public boolean isDead() {
		if(lifeState == DEAD)
			return true;
		return false;
	}
	
	public String toString() {
		return super.toString() + " type: "+type + " hydration level:"+hydrationLvl+" life state:" + lifeState;
	}

}
