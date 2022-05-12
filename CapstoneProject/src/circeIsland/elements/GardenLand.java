package circeIsland.elements;

import java.util.Timer;

import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class GardenLand extends Element{	

	private String type;//Plant types: grapes, barley, maratho, anithos
	int hydrationLvl; //watering plants increases the hydration level to 5. it decreases by 1 daily so you must
					  //water your plants periodically for them to survive. watering brings it back to 5 
	public int lifeState; //0 is unplanted, 1 is bud, 2 is sprout, 3 is ready to harvest, 4 is dead 
	
	
	public GardenLand(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		this.type = "";
		lifeState = 0; 
		setStandable(true);
	}
	
	public String getType() {
		return type;
	}
	
	public void plant(String type) {
		this.type = type;
		lifeState = 1;
		hydrationLvl = 5;
	}
	
	public void water() {
		if (isAlive())
			hydrationLvl = 5;
	}
	
	public void grow() {
		if (hydrationLvl > 3 && (lifeState == 1 || lifeState == 2)) 
			lifeState ++;
		//some call to island.keepTime() in here somewhere probably
	}
	
	public void harvest() {
		
		type = "";
		hydrationLvl = 0;
		lifeState = 0;
	}

	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		act();
		surface.push();
		surface.fill(105, 67, 45);
		surface.rect(10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight), cellWidth, cellHeight);
		surface.fill(0);
		surface.text(type, 10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight)+cellHeight);
		surface.pop();
	}
	
	public void act() {  
		
	}
	
	public int getLifeState() {
		return lifeState;
	}
	
	private boolean isAlive() {
		if (lifeState == 1 || lifeState == 2 || lifeState == 3)
			return true;
		return false;
	}

}
