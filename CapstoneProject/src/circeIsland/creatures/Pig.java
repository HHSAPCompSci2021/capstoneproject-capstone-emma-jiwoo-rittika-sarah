package circeIsland.creatures;

import java.awt.geom.Rectangle2D;

import circeIsland.elements.*;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class Pig extends Creature{
	public static final double PIG_WIDTH_RATIO = 22.22222222;
	public static final double PIG_HEIGHT_RATIO = 11.53846154;
	private boolean inPigPen;
	// 90*130

	public Pig(int x, int y) {
		this(null, x, y);
	}
	
	public Pig(PImage img, double x, double y) {
		super(img, x, y, PIG_WIDTH_RATIO, PIG_HEIGHT_RATIO, 3);
		inPigPen = false;
	}
	
	public void act() {
		int[] grid= coorToGrid(x,y);
		if(super.getIsland().getElement(grid[0]+1, grid[1]+1) instanceof PigPen) {
			inPigPen = true;
		}
		if(inPigPen) {
			super.act(-1);
			return;
		}
		
		int[] pigPen = findPigPen();
		if(pigPen[0] != -1) {
			act(super.destinationDir(pigPen));
			
			return;
		}
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
			Element[][] elements = super.getIsland().getElements();
			
			int[] gridBottomLeft = coorToGrid(xCoor, yCoor+height);
			int[] gridBottomRight = coorToGrid(xCoor+width, yCoor+height);
			if(gridBottomLeft[0] < 0 || gridBottomRight[0] > elements.length || 
				gridBottomLeft[1] < 0 || gridBottomRight[1] > elements.length) {
				return false;
			}
			if (elements[gridBottomLeft[0]][gridBottomLeft[1]] == null || 
				elements[gridBottomRight[0]][gridBottomRight[1]] == null) {
				return false;
			}
			return super.getIsland().getElement(gridBottomLeft[0], gridBottomLeft[1]) instanceof PigPen &&
					super.getIsland().getElement(gridBottomRight[0], gridBottomRight[1]) instanceof PigPen;

		}else {
			return super.canStand(xCoor, yCoor);
		}
	}
	
	
	public int[] findPigPen() {
		Element[][] elements = super.getIsland().getElements();
		for(int i = 0; i<elements.length; i++) {
			for(int j = 0; j<elements[0].length; j++) {
				if(elements[j][i] instanceof PigPen) {
					int[] pigPen = {j, i};
					return pigPen;
				}
			}
		}
		int[] pigPen = {-1, -1};
		return pigPen;
	}	
	

	
	public String getType() {
		return "Pig";
	}

}
