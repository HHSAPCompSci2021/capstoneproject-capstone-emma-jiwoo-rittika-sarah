package circeIsland.main;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import circeIsland.creatures.Circe;
import circeIsland.elements.Holdable;
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
	private WelcomeScreen welcome;
	private Screen currentScreen;
	
	private GButton brewer, recipe, exit;
	private PImage cImage, iImage;
	private PFont font;
	
	private int drawCount, newCreatureCounter;
	private int days, hours;
	
	
	/**
	 * Creates a new Drawing Surface, and initializes the time
	 */
	public DrawingSurface() {
		drawCount = 0;
		newCreatureCounter = 0;
		days = 0;
		hours = 0;
	}
	
	/**
	 * Sets up the buttons and the screens
	 */
	public void setup() {
		G4P.setGlobalColorScheme(3);
		Rectangle cookButton = new Rectangle(300, 700, 100, 50);
		Rectangle recipeButton = new Rectangle(600, 700, 100, 50);
		brewer = new GButton(this, cookButton.x, cookButton.y, cookButton.width, cookButton.height, "Brew");
		recipe = new GButton(this, recipeButton.x, recipeButton.y, recipeButton.width, recipeButton.height, "Recipes");
		exit = new GButton(this, 25, 45, 25, 25, "X");
		brewer.setVisible(false);
		recipe.setVisible(false);
		exit.setVisible(false);
		
		//font = createFont("Files/DrakalligroOriginal.ttf", 128);
		font = createFont("Files/Libra-Normal.otf", 128);
		cImage = loadImage("Files/CirceFrontStand.png");
		iImage = loadImage("Files/Island2.png");
		
		Holdable.setImages(this);
		
		circe = new Circe(cImage, 300, 300);
		island = new Island(this, iImage, circe, 4, 14);
		info = new Information(this);
		workshop = new WorkTable(this);
		welcome = new WelcomeScreen(this);
		
		workshop.add(circe);
		brewer.addEventHandler(workshop,  "handleButtonClick");
		recipe.addEventHandler(workshop,  "handleButtonClick");
		exit.addEventHandler(workshop,  "handleButtonClick");
		
		circe.putOnIsland(island);
		currentScreen = welcome;
	}
	
	/**
	 * Draws the current screen to the PApplet, and keeps track of time
	 */
	public void draw() { 
		if(!currentScreen.equals(welcome) && !currentScreen.equals(info)) {
			drawCount++;
			newCreatureCounter++;
		}
		
		if(newCreatureCounter % 5000 == 0 && newCreatureCounter != 0) {
			island.addNymph();
		}
		if(newCreatureCounter % 7000 == 0 && newCreatureCounter != 0) {
			island.addMaliciousVisitor();
		}
		
		//drawCount += 1/frameRate;
		if(drawCount >=90) {
			drawCount = 0;
			hours ++;
			System.out.println("HOUR UP : " + hours + " " + frameRate);
		}
		if(hours == 24) {
			drawCount = 0;
			hours = 0;
			System.out.println("DAY UP" + days + " " + frameRate);
			days++;
		}
		
		
		background(255);
		textFont(font);
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
		if(currentScreen.equals(welcome)) {
			welcome.draw();
		}
		
		drawTime();
	}
	
	
	private void drawTime() {
		if(!currentScreen.equals(info) && !currentScreen.equals(welcome)) {
			push();
			float w = textWidth("Days : " + days + "   Time: " + hours + ":00");
			fill(222, 139, 62);
			rect(20, 20, w + 30, 30);
			fill(0);
			text("Days : " + days + "   Time: " + hours + ":00", 35, 40);
			pop();
		}
	}
	
	
	/**
	 * Switches the current screen displayed to a different screen depending on the provided code. 
	 * If the code is 0, switches between Island and Workshop.
	 * If the code is 1, switches between island and Information
	 * If the code is 2 or 3, the screen switches from he welcome screen to the info screen or the island respectively
	 * @param x the code determining which switch to execute
	 */
	public void switchScreen(int x) {
		if(x == 1) {
			if(currentScreen.equals(island))
				currentScreen = info;
			else
				currentScreen = island;
		}
		else if(x == 0) {
			if(currentScreen.equals(workshop)) {
				currentScreen = island;
				workshop.setButtonVisible(false);
			} 
			else {
				currentScreen = workshop;
				workshop.setButtonVisible(true);
			}
		}
		else if(x == 2) {
			if(currentScreen.equals(welcome)) {
				currentScreen = info;
				welcome = null;
			}
		}
		else if(x == 3) {
			if(currentScreen.equals(welcome)) {
				currentScreen = island;
				welcome = null;
			}
		}
	}
	
	/**
	 * Executes when the mouse is dragged. The current screen processes the event.
	 */
	public void mouseDragged() {
		currentScreen.processMouseDrag(mouseX, mouseY);
	}
	
	/**
	 * Executes when the mouse button is pressed. The current screen processes the event.
	 */
	public void mousePressed() {
		currentScreen.processMousePress(mouseX, mouseY);
	}
	
	/**
	 * Executes when the mouse button is released. The current screen processes the event.
	 */
	public void mouseReleased() {
		currentScreen.processMouseRelease(mouseX, mouseY);
	}
	
	/**
	 * Executes when the mouse is clicked. The current screen processes the event.
	 */
	public void mouseClicked() {
		currentScreen.processMouseClick(mouseX, mouseY);
	}
	
	/**
	 * Executes when a key is pressed. The current screen processes the event.
	 */
	public void keyPressed() {
		//System.out.println(key);
		currentScreen.processKey(key);
	}
	
	
	/**
	 * Returns the Buttons that this DrawingSurface has, namely the brewer, recipe, and exit buttons
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
	
	/**
	 * Returns this DrawingSurface's WorkTable
	 * @return the WorkTable
	 */
	public WorkTable getWorkTable() {
		return workshop;
	}
	
//	public void handleButtonEvents(GButton button, GEvent event) {}
}










