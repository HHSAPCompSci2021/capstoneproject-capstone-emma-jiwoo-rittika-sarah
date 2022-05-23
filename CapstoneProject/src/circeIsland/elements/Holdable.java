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
	private static PImage[] holdableImages;
	

	public Holdable(int type) {
		this.type = type;
	}
	
	public Holdable(int type, PImage image) {
		this.type = type;
		this.image = image;
	}
	
	public void loadImage(PImage image) {
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
		if(holdableImages[type] != null)
			surface.image(holdableImages[type], xCoor,yCoor,cellWidth,cellHeight);
//		surface.fill(176, 54, 83);
//		surface.rect(10 + xCoor, 10 +yCoor, 10, 10);
//		surface.fill(0);
//		surface.text(getName(), 10 + xCoor, 10 + yCoor);
		surface.pop();
	}
	
	public String toString() {
		return "type:"+type;
	}
	
	public static void setImages(DrawingSurface surface) {
		holdableImages = new PImage[13];
		holdableImages[1] = surface.loadImage("Files/HoldableSeedGrape.png");
		holdableImages[2] = surface.loadImage("Files/HoldableSeedBarley.png");
		holdableImages[3] = surface.loadImage("Files/HoldableSeedMarathos.png");
		holdableImages[4] = surface.loadImage("Files/HoldableSeedAnithos.png");
		holdableImages[5] = surface.loadImage("Files/HoldableGrape.png");
		holdableImages[6] = surface.loadImage("Files/HoldableBarley.png");
		holdableImages[7] = surface.loadImage("Files/HoldableAnithos.png");
		holdableImages[8] = surface.loadImage("Files/HoldableMarathos.png");
		holdableImages[9] = surface.loadImage("Files/HoldableWater.png");
		holdableImages[10] = surface.loadImage("Files/HoldableWine.png");
		holdableImages[11] = surface.loadImage("Files/HoldableBread.png");
		holdableImages[12] = surface.loadImage("Files/HoldablePotion.png");
	}
}
