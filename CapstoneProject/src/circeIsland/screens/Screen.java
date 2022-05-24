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
	

	/**
	 * Creates a new Screen given its width, height, adn a drawing surface
	 * @param width the width of the screen
	 * @param height the height of the Screen
	 * @param the DrawingSurface on which this screen can be drawn
	 */
	public Screen(int width, int height, DrawingSurface surface) {
		this.HEIGHT = height;
		this.WIDTH = width;
		this.surface = surface;
	}
	
	
	//METHODS
	
	/**
	 * Draws the screen to the provided DrawingSurface
	 */
	public void draw() {
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

	/**
	 * If the mouse is  dragged, performs functions depending on the x and y location of the mouse
	 * @param mouseX x-coordinate of mouse
	 * @param mouseY y-coordinate of mouse
	 */
	public void processMouseDrag(int mouseX, int mouseY) {
		
	}

	/**
	 * If the mouse is pressed, performs functions depending on the x and y location of the mouse
	 * @param mouseX x-coordinate of mouse
	 * @param mouseY y-coordinate of mouse
	 */
	public void processMousePress(int mouseX, int mouseY) {
		
	}


	/**
	 * If the mouse button is released, performs functions depending on the x and y location of the mouse
	 * @param mouseX x-coordinate of mouse
	 * @param mouseY y-coordinate of mouse
	 */
	public void processMouseRelease(int mouseX, int mouseY) {
	}
	
	/**
	 * Returns the height of this screen
	 * @return height
	 */
	public int getHeight() {
		return HEIGHT;//surface.width;
	}
	
	/**
	 * Returns the width of this screen
	 * @return width
	 */
	public int getWidth() {
		return WIDTH;//surface.height;
	}
}
