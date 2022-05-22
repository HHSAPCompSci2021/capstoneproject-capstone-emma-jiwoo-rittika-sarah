package circeIsland.screens;

import java.awt.Rectangle;
import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * 
 * @author Rittika Saha
 */
public class Information extends Screen{

	private Rectangle exitButton;
	private String info;
	private PImage island, circe, mal, nymph, potion;
	
	/**
	 * Creates a new information screen on the given PApplet
	 * @param surface teh PApplet on which the screen will be displayed
	 */
	public Information(DrawingSurface surface) {
		super(800, 600, surface);
		exitButton = new Rectangle(super.WIDTH/2 - 25, super.HEIGHT - 100, 50, 30);
		info = "Welcome to Circe's Island!"
				+ "\n- To move Circe, use the WASD keys."
				+ "\n- To enter Circe's workspace and make potions, move Circe to her house\n and press RETURN"
				+ "\n- To add elements to the Island, click on a location and select the desired element."
				+ "\n- To plant the garden, first move Circe to a garden plot. Ensure Circe is\n holding the desired seed, then click on the plot to plant the seed"
				+ "\n- When a Malicious Visitor enters the Island, turn them into pigs. First feed\n them with food "
				+ "to stop them from running away. Then feed them with the potion to convert them to\n pigs. Don't forget to give them a pig pen!";
		setupImages();
	}
	
	
	/**
	 * Draws the information screen to the PApplet. This displays the instructions for the game and an exit button
	 */
	public void draw() {
		surface.background(15, 163, 189);
		
		
		surface.push();
		exitButton.setBounds(surface.width/2-25, surface.height-100,  50,  30);
		
		//white box
		surface.strokeWeight(2);
		surface.fill(255);
		surface.rect(20, 20,  surface.width - 40,  surface.height -60);
		
		//exit button
		surface.fill(222, 139, 62);
		surface.rect(exitButton.x,  exitButton.y,  exitButton.width,  exitButton.height);
		surface.fill(0);
		surface.textSize(25);
		surface.text('X', exitButton.x + (exitButton.width / 2) - 7, exitButton.y + (exitButton.height / 2) + 10);
		
		//text
		surface.textSize(20);
		surface.stroke(0);
		surface.fill(0);
		surface.text(info,  50,  100);
		
		//images!
		surface.image(island,  surface.width / 2 + 80, 100, 450, 275);
		surface.image(circe, surface.width / 4, 2 * surface.height / 3 - 30, 50, 150);
		surface.image(mal, 2 * (surface.width / 4), 2 * surface.height / 3 - 30, 50, 150);
		surface.image(nymph, 3 * (surface.width / 4), 2 * surface.height / 3 - 30, 50, 150);
		//surface.image(island,  surface.width / 2 + 100, surface.height + 100);
		
		
		
		surface.pop();
	}
	
	
	
	public void processMouseClick(int mouseX, int mouseY) {
		if(mouseX > exitButton.x && mouseX <= exitButton.x + exitButton.width && mouseY > exitButton.y && mouseY <= exitButton.y + exitButton.height) {
			surface.switchScreen(1);
		}
	}
	
	
	private void setupImages() {
		island = surface.loadImage("Files/Island2.png");
		circe = surface.loadImage("Files/CirceFrontStand.png");
		mal = surface.loadImage("Files/MaliciousFrontStand.png");
		nymph = surface.loadImage("Files/NymphFrontStand.png");
		potion = surface.loadImage("Files/HoldablePotion.png");
		
	}
}
