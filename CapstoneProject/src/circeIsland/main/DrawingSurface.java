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
	private Circe circe;
	private Information info;
	private Screen currentScreen;
	
	private GButton brewer, recipe, exit;
	private PImage cImage, iImage;
	private PFont font;
	
	public float ratioX, ratioY;
	public int drawCount;
	public int days, hours;
	
	
	/**
	 * Creates a new Drawing Surface, with the workshop and information screens
	 */
	public DrawingSurface() {
		//island = new Island(this);
		//island = new Island(this, 300, 300, 7, 10);
		//circe = new Circe()
		
		
	}
	
	/**
	 * Sets up the buttons and the island and work table screens
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
		iImage = loadImage("Files/Island2.png");
		
		circe = new Circe(cImage, 300, 300);
		island = new Island(this, iImage, circe, 4, 14);
		info = new Information(this);
		workshop = new WorkTable(this);
		
		workshop.add(circe);
		circe.setIsland(island);
		currentScreen = island;
	}
	
	/**
	 * Draws the current screen to the PApplet, and keeps track of time
	 */
	public void draw() { 
		drawCount++;
		
		//drawCount += 1/frameRate;
		if(drawCount >=5) {
			drawCount = 0;
			hours ++;
	//		System.out.println("HOUR UP : " + hours + " " + frameRate);
		}
		if(hours == 24) {
			drawCount = 0;
			hours = 0;
			System.out.println("DAY UP" + days + " " + frameRate);
			days++;
//			if(days % 5 == 0 && days != 0) {
//				island.addNymph();
//			}
		}
		
		textFont(font);
//		ratioX = (float)width/currentScreen.WIDTH;
//		ratioY = (float)height/currentScreen.HEIGHT;
//
//		push();
//		
//		scale(ratioX, ratioY);
		
		background(255);
		
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
	
	
	/**
	 * Returns the Buttons that this DrawingSurface has, namely the brewer, recipe, and exit button
	 * @return
	 */
	public ArrayList<GButton> getButtons(){
		ArrayList<GButton> buttons = new ArrayList<GButton>();
		buttons.add(brewer);
		buttons.add(recipe);
		buttons.add(exit);
		return buttons;
	}
	
	
	
	/**
	 * Returns the number of days that have passed since the beginning of the program
	 * @return number of days
	 */
	public int getDays() {
		return days;
	}
	
	/**
	 * Returns the number of hours that have passed in the current day
	 * @return the number of hours
	 */
	public int getHours() {
		return hours;
	}
	
}










