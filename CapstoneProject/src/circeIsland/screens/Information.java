package circeIsland.screens;

import java.awt.Rectangle;
import circeIsland.main.DrawingSurface;

/**
 * 
 * @author Rittika Saha
 */
public class Information extends Screen{

	private Rectangle exitButton;
	private String info;
	
	/**
	 * Creates a new information screen on the given PApplet
	 * @param surface teh PApplet on which the screen will be displayed
	 */
	public Information(DrawingSurface surface) {
		super(800, 600, surface);
		exitButton = new Rectangle(super.WIDTH/2 - 25, super.HEIGHT - 100, 50, 30);
		info = "Welcome to Circe's Island!"
				+ "\n- To move Circe, use the WASD keys."
				+ "\n- To enter Circe's workspace and make potions,move Circe to her house\n and press RETURN"
				+ "\n- To add elements to the Island, click on a location and select the desired element."
				+ "\n- To plant the garden, move Circe to a garden plot, click on the plot,\n and select the desired plant.";
	}
	
	
	/**
	 * Draws the information screen to the PApplet. This displays the instructions for the game and an exit button
	 */
	public void draw() {
		surface.background(15, 163, 189);
		
		surface.strokeWeight(2);
		surface.fill(255);
		surface.rect(20, 20,  super.WIDTH - 40,  super.HEIGHT -60);
		
		surface.fill(222, 139, 62);
		surface.rect(exitButton.x,  exitButton.y,  exitButton.width,  exitButton.height);
		surface.fill(0);
		surface.textSize(25);
		surface.text('X', exitButton.x + (exitButton.width / 2) - 7, exitButton.y + (exitButton.height / 2) + 10);
		
		surface.textSize(20);
		surface.stroke(0);
		surface.fill(0);
		surface.text(info,  50,  100);
		
	}
	
	
	
	public void processMouseClick(int mouseX, int mouseY) {
		if(mouseX > exitButton.x && mouseX <= exitButton.x + exitButton.width && mouseY > exitButton.y && mouseY <= exitButton.y + exitButton.height) {
			surface.switchScreen(1);
		}
	}
}
