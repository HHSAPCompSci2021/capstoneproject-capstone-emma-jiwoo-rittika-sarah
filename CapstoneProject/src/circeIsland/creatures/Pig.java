package circeIsland.creatures;

import java.awt.geom.Rectangle2D;

import circeIsland.elements.*;
import processing.core.PImage;

/**
 * This class represents pig, extends from the Creature
 * 
 * @author Jiwoo Kim
 */
public class Pig extends Creature{
	public static final double PIG_WIDTH_RATIO = 22.22222222;
	public static final double PIG_HEIGHT_RATIO = 11.53846154;
	private boolean inPigPen;
	private int lifeState;
	private boolean feeding;
	private boolean newDay;
	// 90*130

	/**
	 * Initilize the Pig 
	 * width and height ratio are setted to 22.22 and 11.54 and image setted to null
	 * velocity will setted to 3
	 * other values are setted as a default that shown in Creature.java constructors
	 * @param x x coordinate of the pig
	 * @param y y coordinate of the pig
	 */
	public Pig(int x, int y) {
		this(null, x, y);
	}
	
	/**
	 * Initilize the Pig 
	 * width and height ratio are setted to 22.22 and 11.54 
	 * velocity will setted to 3
	 * other values are setted as a default that shown in Creature.java constructors
	 * @param img the Circe image
	 * @param x x coordinate of the pig
	 * @param y y coordinate of the pig
	 */
	public Pig(PImage img, double x, double y) {
		super(img, x, y, PIG_WIDTH_RATIO, PIG_HEIGHT_RATIO, 3);
		inPigPen = false;
		lifeState = 3;
		feeding = false;
		newDay = false;
	}
	
	/**
	 * Update the pig movement
	 * if the pig is not in the pigpen, it keep searching for pig pen
	 * If the pig pen is founded, and not in the pig pen, it would go through the pig pen
	 * If the feeding does not happen for 3 days, it would die
	 * 
	 * @pre island has to be not null
	 */
	public void act() {
		int[] grid= coorToGrid(x,y);
		if(super.getIsland().getElement(grid[0]+1, grid[1]+1) instanceof PigPen) {
			inPigPen = true;
		}
		if(super.getIsland().getSurface().getHours() == 0) {
			if(newDay) {
				if(feeding==false) {
					lifeState--;
				}
				feeding = false;
				newDay =false;
			}
		}else {
			newDay = true;
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
	
	/**
	 * inherited method of the super class.
	 * If it is in pig pen, it only can stands in the pig pen
	 * if it is not, it just inherited from the super class
	 * @param xCoor the left side x coordinate of the creature
	 * @param yCoor the up side y coordinate of the creature
	 * @return if the (xCoor, yCoor) is null, or out of bounds in the island, or if the bottom left and bottom right of the creature is not standable, it will return false. otherwise, it will return true.
	 */
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
	
	/**
	 * 
	 * @return wheter it is not dead or not. true if it is dead false otherwise
	 */
	public boolean isDead() {
		return (lifeState<1);
	}
	
	/**
	 * @return the type of this class ("Pig")
	 */
	public String getType() {
		return "Pig";
	}
	
	/**
	 * it update the lifestate and feed
	 */
	public void fed() {
		feeding = true;
		if(lifeState < 3)
			lifeState++;
	}
	

	private int[] findPigPen() {
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

}
