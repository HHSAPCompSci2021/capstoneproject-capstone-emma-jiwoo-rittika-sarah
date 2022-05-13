package circeIsland.main;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import circeIsland.screens.*;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import processing.core.PApplet;
import processing.event.MouseEvent;


public class DrawingSurface extends PApplet {
	
	private Island island;
	private WorkTable workshop;
	private Screen currentScreen;
	GButton brewer, recipe, exit;
	
	public float ratioX, ratioY;
	
	public DrawingSurface() {
		//island = new Island(this);
		island = new Island(this, 300, 300, 7, 10);
		workshop = new WorkTable(this);
		//currentScreen = workshop;
		currentScreen = island;
	}
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		G4P.setGlobalColorScheme(3);
		Rectangle cookButton = new Rectangle(100, 500, 100, 50);
		Rectangle recipeButton = new Rectangle(400, 500, 100, 50);
		brewer = new GButton(this, cookButton.x, cookButton.y, cookButton.width, cookButton.height, "Brew");
		recipe = new GButton(this, recipeButton.x, recipeButton.y, recipeButton.width, recipeButton.height, "Recipes");
		exit = new GButton(this, 25, 25, 25, 25, "X");
		brewer.addEventHandler(this,  "handleButtonClick");
		recipe.addEventHandler(this,  "handleButtonClick");
		exit.addEventHandler(this,  "handleButtonClick");
	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		
//		ratioX = (float)width/currentScreen.WIDTH;
//		ratioY = (float)height/currentScreen.HEIGHT;
//
//		push();
//		
//		scale(ratioX, ratioY);
		
		background(255);   // Clear the screen with a white background
		
		textSize(12);
		fill(0);
		
		stroke(0);
		if(currentScreen.equals(workshop)) {
			workshop.draw();
		}
		if(currentScreen.equals(island)) {
			island.draw();
		}
		//System.out.println('c');
	}
	
	
	
	public void switchScreen() {
		
		
		if(currentScreen.equals(workshop)) {
			currentScreen = island;
			workshop.setButtonVisible(false);
		} 
		else {
			currentScreen = workshop;
			workshop.setButtonVisible(true);
		}
	}
	
	
	public void mouseWheel(MouseEvent event) {
		//maybe zoom functionality??
	}
	
	
	public void mouseClicked() {
		//system.out.println("mouse click");
		//System.out.println('c');
		currentScreen.processMouseClick(mouseX, mouseY);
	}
	
	public void keyPressed() {
		System.out.println("pressed key");
		System.out.println(key);
		currentScreen.processKey(key);
//		if(key == CODED)
//			currentScreen.processKey(key);
		
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










