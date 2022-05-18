package circeIsland.creatures;

import circeIsland.elements.*;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class Pig extends Creature{
	public static final double PIG_WIDTH = 45;
	public static final double PIG_HEIGHT = 65;
	// 90*130

	public Pig(int x, int y) {
		super(x, y, PIG_WIDTH, PIG_HEIGHT, 3);
	}
	
	
	public Pig(PImage img, int x, int y) {
		super(img, x, y, PIG_WIDTH, PIG_HEIGHT, 3);
	}
	
	public void act() {
		super.act(-1);
	}
	
	public boolean canStand(double xCoor, double yCoor) {
		int[] gridTopLeft = coorToGrid(xCoor, yCoor );
		int[] gridBottomRight = coorToGrid(xCoor + width, yCoor + height);
		if (super.getIsland().getElement(gridTopLeft[0], gridTopLeft[1]) == null || 
			super.getIsland().getElement(gridBottomRight[0], gridBottomRight[1]) == null) {
			return false;
		}
		return super.getIsland().getElement(gridTopLeft[0], gridTopLeft[1]) instanceof PigPen &&
				super.getIsland().getElement(gridBottomRight[0], gridBottomRight[1]) instanceof PigPen;
	}
	
	public String getType() {
		return "Pig";
	}

}
