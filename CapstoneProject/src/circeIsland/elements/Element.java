package circeIsland.elements;

import java.awt.Rectangle;

import circeIsland.creatures.Circe;
import circeIsland.creatures.Creature;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

/**
 * 
 * @author Emma Yu
 *
 *This class represents an Element, which makes up each grid of the island. 
 */
public abstract class Element {

	private Island island;
	private int x, y;
	private boolean isInGrid;
	private boolean standable;
	private PImage image;

	/**
	 * Creates a new Element object
	 * @param i Island that the Element belongs to
	 * @param xInput X-Coordinate of the Element (in terms of Array)
	 * @param yInput Y-Coordinate of the Element (in terms of Array)
	 */
	public Element(Island i, int xInput, int yInput) {
		island = i;
		image = null;
		x = xInput;
		y = yInput;
		isInGrid = true;
		standable = false;
	}
	
	/**
	 * Creates a new Element object
	 * @param i Island that the Element belongs to
	 * @param p Image of the Element
	 * @param xInput X-Coordinate of the Element (in terms of Array)
	 * @param yInput Y-Coordinate of the Element (in terms of Array)
	 */

	public Element(Island i, PImage p, int xInput, int yInput) {
		island = i;
		image = p;
		x = xInput;
		y = yInput;
		isInGrid = true;
		standable = false;
	}

	/**
	 * Sets the image parameter for the Element
	 * @param p Image to set the image parameter to
	 */
	public void setImage(PImage p) {
		image = p;
	}
	
	/**
	 * Puts the Element on the island
	 * @param i Island to put the Element on
	 * @param x X-Coordinate of the Element (in terms of Array)
	 * @param y Y-Coordinate of the Element (in terms of Array)
	 */
	public void putOnIsland(Island i, int x, int y) { 
		if (i.getElement(x,  y) == null)
			i.setElement(this, x, y);
		isInGrid = true;
		island = i;
	}
	
	/**
	 * Removes the Element from the Island
	 * @param i Island to remove the Element from
	 */
	public void removeFromIsland(Island i) {
		i.setElement(null, x, y);
		isInGrid = false;
		island = null;
	}
	
	/**
	 * Draws the Element to the given Drawing Surface
	 * @param surface DrawingSurface on which to draw the Element
	 * @param cellWidth Width of the cell
	 * @param cellHeight Height of the cell
	 */
	public abstract void draw(DrawingSurface surface, float cellWidth, float cellHeight);
	
	/**
	 * Sets the Element's standable boolean, which determines if creatures can stand on it
	 * @param b Boolean to set standable to
	 */
	public void setStandable (boolean b) {
		standable = b;
	}
	
	/**
	 * Sets the Element's x-coordinate
	 * @param x X-coordinate to set the Element to
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Sets the Element's y-coordinate
	 * @param y Y-coordinate to set the Element to
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns the Element's image
	 * @return The Element's image
	 */
	public PImage getImage() {
		return image;
	}
	
	/**
	 * @return whether or not the ELement is displayed in the current Island
	 */
	public boolean getIsInGrid(){
		return isInGrid;
	}
	
	/**
	 * Sets whether or not the Element is displayed in the current Island
	 * @param b The boolean to set isInGrid to
	 */
	public void setIsInGrid(boolean b) {
		isInGrid = b;
	}
	
	/**
	 * @return the Element's Island
	 */
	public Island getIsland() {
		return island;
	}
	
	/**
	 * @return Whether or not the Element can be stood on by creatures
	 */
	public boolean getStandable() {
		return standable;
	}
	
	/**
	 * @return The Element's X-Coordinate (of the Array)
	 */
	public int getXCoor() {
		return x;
	}
	
	/**
	 * @return The Element's Y-Coordinate (of the Array)
	 */
	public int getYCoor() {
		return y;
	}
	
	/**
	 * @return Element fields as a String
	 */
	public String toString() {
		return "x="+x + " y="+y + " in grid?:"+isInGrid + " standable?"+standable;
	}
}
