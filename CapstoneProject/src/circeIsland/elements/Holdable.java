package circeIsland.elements;

import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * This class represents all of the items that are able to be held by Circe and added to storage. 
 * @author Emma Yu
 */
public class Holdable {
	private int type;
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
	
	/**
	 * Creates a new Holdable object given the type of object that it is
	 * @param type The type of object
	 * @pre type must be from 1-13
	 */
	public Holdable(int type) {
		this.type = type;
	}
	
	/**
	 * Creates a new Holdable object given the type of object that it is and a PImage
	 * @param type The type of object
	 * @param image Image of object
	 * @pre type must be from 1-13
	 */
	public Holdable(int type, PImage image) {
		this.type = type;
		this.image = image;
	}
	
	/**
	 * Sets the image parameter to the given image
	 * @param image Picture to be set as image
	 */
	public void loadImage(PImage image) {
		this.image = image;
	}
	
	/**
	 * Returns the Holdable object's type
	 * @return Type of object from 1-13
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Returns the Holdable object's name, mostly for testing purposes
	 * @return Name of object as a String
	 */
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
	
	/**
	 * Draws the Holdable object to the given surface depending on its x and y coordinates, width, and height.
	 * Draws the object by using its image. If there is no initialized image, does not draw anything
	 * @param surface DrawingSurface to draw the object on
	 * @param xCoor x-coordinate to draw at
	 * @param yCoor y-coordinate to draw at
	 * @param cellWidth Width to draw at
	 * @param cellHeight Height to draw at
	 */
	public void draw(DrawingSurface surface, float xCoor, float yCoor, float cellWidth, float cellHeight) {
		surface.push();
		if(holdableImages[type] != null)
			surface.image(holdableImages[type], xCoor,yCoor,cellWidth,cellHeight);
//		surface.fill(176, 54, 83);
//		surface.rect(10 + xCoor, 10 +yCoor, 10, 10);
//		surface.fill(0);
//		surface.text(getName(), 10 + xCoor, 10 + yCoor);
		surface.pop();
	}
	
	/**
	 * Returns the type as a string
	 * @return Holdable's type as a String
	 */
	public String toString() {
		return "type:"+type;
	}
	
	/**
	 * Loads and initializes all images
	 * @param surface The DrawingSurface to load the images from
	 */
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
