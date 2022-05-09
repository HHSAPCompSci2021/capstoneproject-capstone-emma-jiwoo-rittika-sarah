package circeIsland.main;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import circeIsland.screens.*;
import processing.core.PApplet;
import processing.event.MouseEvent;


public class DrawingSurface extends PApplet {
	
	private Island island;
	private WorkTable workshop;
	private Screen currentScreen;
	
	
	public DrawingSurface() {
		island = new Island(this);
		workshop = new WorkTable(this);
		currentScreen = workshop;
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
	
	
	public void mouseWheel(MouseEvent event) {
		//maybe zoom functionality??
	}
	
	
	public void mouseClicked() {
		//system.out.println("mouse click");
		System.out.println('c');
		currentScreen.processClick(mouseX, mouseY);
	}
	
	
}










