package circeIsland.screens;

import circeIsland.main.DrawingSurface;
import g4p_controls.GDropList;
import g4p_controls.GEvent;
import processing.core.PApplet;

/**
 * 
 * @author Rittika Saha
 */
public class Screen{

	public final int HEIGHT;
	public final int WIDTH;
	protected DrawingSurface surface;
	private int drawCount, days;
	
	//CONSTRUCTOR
	/**
	 * Creates a new Screen given its width and height
	 * @param width the width of the screen
	 * @param height the height of the Screen
	 */
	public Screen(int width, int height, DrawingSurface surface) {
		this.HEIGHT = height;
		this.WIDTH = width;
		this.surface = surface;
		drawCount = 0;
		days = 0;
	}
	
	
	//METHODS
	
	/**
	 * Draws the screen to the provided DrawingSurface
	 */
	public void draw() {
		drawCount++;
		
		if(drawCount == 500) {
			drawCount = 0;
			System.out.println("DAY UP");
			days++;
		}
	}
	
	/**
	 * In the event that the mouse is clicked, takes in the coordinates of the click and executes the appropriate response
	 * @param mouseX x-coordinate of the mouse click
	 * @param mouseY y-coordinate of the mouse click
	 */
	public void processMouseClick(int mouseX, int mouseY) {
		
	}
	
	/**
	 * In the event that a key is pressed, takes in the key and executes the appropriate response
	 * @param key the key pressed
	 */
	public void processKey(char key) {
		
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getDays() {
		return days;
	}
	
}
