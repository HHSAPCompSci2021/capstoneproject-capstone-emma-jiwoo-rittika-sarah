package circeIsland.main;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import circeIsland.screens.*;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import processing.core.PApplet;
import processing.event.MouseEvent;


public class DrawingSurface extends PApplet {
	
	private Island island;
	private WorkTable workshop;
	private Screen currentScreen;
	
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
		if(currentScreen.equals(workshop))
			currentScreen = island;
		else
			currentScreen = workshop;
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
	
	
	
	//useless
	public void handleButtonEvents(GButton button, GEvent event) { /* code */ }
	
	
}










