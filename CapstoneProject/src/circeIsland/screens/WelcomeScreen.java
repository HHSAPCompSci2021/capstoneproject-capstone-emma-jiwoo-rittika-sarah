package circeIsland.screens;

import java.awt.Rectangle;

import circeIsland.main.DrawingSurface;
import processing.core.PImage;

public class WelcomeScreen extends Screen{

	private Rectangle infoButton, playButton;
	private PImage img;
	
	
	public WelcomeScreen(DrawingSurface surface) {
		super(1200, 900, surface);//235 548; 525, 548
		//width 1 -- 169; h1 = 80
		infoButton = new Rectangle((int)(surface.width/5.106), (int)(surface.height/1.64), (int)(surface.width / 7.058), (int)(surface.height / 11.25));
		playButton = new Rectangle((int)(surface.width/2.285), (int)(surface.height/1.64), (int)(surface.width / 7.058), (int)(surface.height / 11.25));
		setupImages();
	}
	
	
	public void draw() {
		surface.push();
//		infoButton.setBounds((int)(surface.width/5.106), (int)(surface.height/1.64), (int)(surface.width / 7.058), (int)(surface.height / 11.25));
//		playButton.setBounds((int)(surface.width/2.285), (int)(surface.height/1.64), (int)(surface.width / 7.058), (int)(surface.height / 11.25));
//		
		surface.image(img, 0, 0, 50, 50);
		surface.pop();
		
	}

	public void processMouseClick(int mouseX, int mouseY) {
		if(mouseX > infoButton.x && mouseX <= infoButton.x + infoButton.width && mouseY > infoButton.y && mouseY <= infoButton.y + infoButton.height) {
			surface.switchScreen(2);
		}
		if(mouseX > playButton.x && mouseX <= playButton.x + playButton.width && mouseY > playButton.y && mouseY <= playButton.y + playButton.height) {
			surface.switchScreen(3);
		}
	}
	
	private void setupImages() {
		img = surface.loadImage("Files/WelcomeImage.png");
	}
}
