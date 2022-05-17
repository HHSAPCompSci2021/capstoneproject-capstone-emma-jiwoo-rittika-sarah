package circeIsland.main;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import circeIsland.creatures.Circe;
import circeIsland.screens.*;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.MouseEvent;

/**
 * 
 * @author Rittika Saha
 *
 */
public class DrawingSurface extends PApplet {
	
	private Island island;
	private WorkTable workshop;
	private Information info;
	private Screen currentScreen;
	
	private GButton brewer, recipe, exit;
	private PImage cImage, iImage;
	private PFont font;
	
	public float ratioX, ratioY;
	public int drawCounter;
	public int time;
	
	
	/**
	 * Creates a new Drawing Surface, with the workshop and information screens
	 */
	public DrawingSurface() {
		//island = new Island(this);
		//island = new Island(this, 300, 300, 7, 10);
		workshop = new WorkTable(this);
		info = new Information(this);
		
		
	}
	
	/**
	 * Sets up the buttons and the island screen
	 */
	public void setup() {
		G4P.setGlobalColorScheme(3);
		Rectangle cookButton = new Rectangle(100, 500, 100, 50);
		Rectangle recipeButton = new Rectangle(400, 500, 100, 50);
		brewer = new GButton(this, cookButton.x, cookButton.y, cookButton.width, cookButton.height, "Brew");
		recipe = new GButton(this, recipeButton.x, recipeButton.y, recipeButton.width, recipeButton.height, "Recipes");
		exit = new GButton(this, 25, 25, 25, 25, "X");
		brewer.addEventHandler(workshop,  "handleButtonClick");
		recipe.addEventHandler(workshop,  "handleButtonClick");
		exit.addEventHandler(workshop,  "handleButtonClick");
		brewer.setVisible(false);
		recipe.setVisible(false);
		exit.setVisible(false);
		
		//font = createFont("Files/DrakalligroOriginal.ttf", 128);
		font = createFont("Files/Libra-Normal.otf", 128);
		cImage = loadImage("Files/CirceFrontStand.png");
		iImage = loadImage("Files/IslandImg.png");
		island = new Island(this, iImage, cImage, 300, 300, 10, 5);
		currentScreen = island;
	}
	
	/**
	 * Draws the current screen to the PApplet
	 */
	public void draw() { 
		drawCounter++;
		textFont(font);
//		ratioX = (float)width/currentScreen.WIDTH;
//		ratioY = (float)height/currentScreen.HEIGHT;
//
//		push();
//		
//		scale(ratioX, ratioY);
		
		background(255);// Clear the screen with a white background
		
		textSize(12);
		fill(0);
		
		stroke(0);
		if(currentScreen.equals(workshop)) {
			workshop.setButtonVisible(true);
			workshop.draw();
		}
		if(currentScreen.equals(island)) {
			island.draw();
		}
		if (currentScreen.equals(info)){
			info.draw();
		}
	}
	
	
	/**
	 * Switches the current screen displayed to a different screen depending on the provided code. 
	 * If the code is 0, switches between Island and Workshop.
	 * If the code is 1, switches between island and information
	 * @param x the code determining which switch to execute
	 */
	public void switchScreen(int x) {
		if(x == 1) {
			if(currentScreen.equals(island))
				currentScreen = info;
			else
				currentScreen = island;
		}
		else {
			if(currentScreen.equals(workshop)) {
				currentScreen = island;
				workshop.setButtonVisible(false);
			} 
			else {
				currentScreen = workshop;
				workshop.setButtonVisible(true);
			}
		}
	}
	
	
	public void mouseWheel(MouseEvent event) {
		//maybe zoom functionality??
	}
	
	public void mouseDragged() {
		currentScreen.processMouseDrag(mouseX, mouseY);
	}
	
	public void mousePressed() {
		currentScreen.processMousePress(mouseX, mouseY);
	}
	
	public void mouseReleased() {
		currentScreen.processMouseRelease(mouseX, mouseY);
	}
	
	
	public void mouseClicked() {
		currentScreen.processMouseClick(mouseX, mouseY);
	}
	
	public void keyPressed() {
		System.out.println(key);
		currentScreen.processKey(key);
	}
	
	
	public ArrayList<GButton> getButtons(){
		ArrayList<GButton> buttons = new ArrayList<GButton>();
		buttons.add(brewer);
		buttons.add(recipe);
		buttons.add(exit);
		return buttons;
	}
	
	//useless
	public void handleButtonEvents(GButton button, GEvent event) { /* code */ }
	
	
}










