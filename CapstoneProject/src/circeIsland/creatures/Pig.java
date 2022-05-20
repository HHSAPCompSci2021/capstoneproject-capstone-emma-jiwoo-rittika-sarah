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
	private boolean inPigPen;
	// 90*130

	public Pig(int x, int y) {
		this(null, x, y);
	}
	
	
	public Pig(PImage img, int x, int y) {
		super(img, x, y, PIG_WIDTH, PIG_HEIGHT, 3);
		inPigPen = false;
	}
	
	public void act() {
		super.act(-1);
	}
	
	public void setInPigPen() {
		inPigPen = !inPigPen; 
	}
	
	public boolean getInPigPen() {
		return inPigPen;
	}
	
	public boolean canStand(double xCoor, double yCoor) {
		if(inPigPen) {
			double rateX = super.getIsland().getWidth()/800;
			double rateY = super.getIsland().getHeight()/600;
			int[] gridTopLeft = coorToGrid(xCoor-2, yCoor + 4 );
			int[] gridBottomRight = coorToGrid(xCoor-2 + width*rateX, yCoor +11+ height*rateY);
			if(gridTopLeft[0] < 0 || gridBottomRight[0] > super.getIsland().getElements().length || 
				gridTopLeft[1] < 0 || gridBottomRight[1] > super.getIsland().getElements()[0].length) {
				return false;
			}
			if (super.getIsland().getElement(gridTopLeft[0], gridTopLeft[1]) == null || 
					super.getIsland().getElement(gridBottomRight[0], gridBottomRight[1]) == null) {
				return false;
			}
			return super.getIsland().getElement(gridTopLeft[0], gridTopLeft[1]) instanceof PigPen &&
					super.getIsland().getElement(gridBottomRight[0], gridBottomRight[1]) instanceof PigPen;

		}else {
			return super.canStand(xCoor, yCoor);
		}
	}
	
	public String getType() {
		return "Pig";
	}

}
