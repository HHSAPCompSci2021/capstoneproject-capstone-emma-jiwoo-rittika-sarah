package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * 
 * @author Emma Yu
 *
 */
public class Holdable {
	int type;
	public static final int GRAPE_SEED = 1;
	public static final int BARLEY_SEED = 2;
	public static final int MARATHOS_SEED = 3;
	public static final int ANITHOS_SEED = 4;
	
	public static final int GRAPE_PLANT = 5;
	public static final int BARLEY_PLANT = 6;
	public static final int MARATHOS_PLANT = 7;
	public static final int ANITHOS_PLANT = 8;

	public static final int WATER = 9;
	public static final int WINE = 10;
	public static final int BREAD = 11;
	public static final int POTION = 12;
	
	public static final int PLACEHOLDER = 13;
	
	private PImage image;
	

	public Holdable(int type) {
		this.type = type;
	}
	
	public Holdable(int type, PImage image) {
		this.type = type;
		this.image = image;
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() { //dont use this for actual code, just to display names
		switch(type) {
		case 1:
			return "Grape Seed";
		case 2:
			return "Barley Seed";
		case 3:
			return "Marathos Seed";
		case 4:
			return "Anithos Seed";
		case 5:
			return "Grape";
		case 6:
			return "Barley";
		case 7:
			return "Marathos";
		case 8:
			return "Anithos";
		case 9:
			return "Water";
		case 10:
			return "Wine";
		case 11:
			return "Bread";
		case 12:
			return "Potion";
		}
		return "";
	}
	
	public void draw(DrawingSurface surface, float xCoor, float yCoor, float cellWidth, float cellHeight) {
		surface.push();
//		if (image != null) {
//			surface.image(image, xCoor,yCoor,cellWidth,cellHeight);
//		}
		surface.fill(176, 54, 83);
		surface.rect(10 + xCoor, 10 +yCoor, 10, 10);
		surface.fill(0);
		surface.text(getName(), 10 + xCoor, 10 + yCoor);
		surface.pop();
	}
	
	public String toString() {
		return "type:"+type;
	}
}
