package circeIsland.elements;

import circeIsland.main.DrawingSurface;

public class Holdable {
	int type;
	public static final int BREAD = 1;
	public static final int WATER = 2;
	public static final int SEED = 3;
	public static final int PLANT = 4;
	public static final int POTION = 5;
	
	String details; //only for seed and plant
	public static final String GRAPE = "grape";
	public static final String BARLEY = "barley";
	public static final String MARATHOS = "marathos";
	public static final String ANITHOS = "anithos";
	

	public Holdable(int type) {
		this.type = type;
		details = "";
	}
	
	public Holdable(int type, String details) {
		this.type = type;
		this.details = details;
	}
	
	
	public int getType() {
		return type;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void draw(DrawingSurface surface, float xCoor, float yCoor) {
		surface.push();
		surface.fill(176, 54, 83);
		surface.rect(10 + xCoor, 10 +yCoor, 10, 10);
		surface.fill(0);
		surface.text(type+details, 10 + xCoor, 10 + yCoor);
		surface.pop();
	}
	
	public String toString() {
		return "type:"+type+details;
	}
}
