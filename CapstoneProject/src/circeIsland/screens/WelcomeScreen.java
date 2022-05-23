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
		infoButton = new Rectangle((int)(surface.width/5.106), (int)(surface.height/1.64), (int)(surface.width / 7.058), (int)(surface.height / 11.25) + 2);
		playButton = new Rectangle((int)(surface.width/2.285), (int)(surface.height/1.64), (int)(surface.width / 7.058), (int)(surface.height / 11.25) + 2);
		setupImages();
	}
	
	
	public void draw() {
		surface.push();
		
		surface.textSize(35);
		infoButton.setBounds((int)(surface.width/5.106) - 3, (int)(surface.height/1.64) - 2, (int)(surface.width / 7.058), (int)(surface.height / 11.25) + 2);
		playButton.setBounds((int)(surface.width/2.285) - 3, (int)(surface.height/1.64) - 2, (int)(surface.width / 7.058), (int)(surface.height / 11.25) + 2);
//		
		surface.image(img, 0, 0, surface.width, surface.height);
		surface.fill(216, 119, 129);
		surface.noStroke();
		surface.rect(infoButton.x, infoButton.y, infoButton.width, infoButton.height);
		surface.rect(playButton.x,  playButton.y,  playButton.width, playButton.height);
		
		surface.fill(255);
		String play = "PLAY";
		float pWidth = surface.textWidth(play);
		surface.text(play,  (playButton.x + playButton.width / 2) - (pWidth / 2), (playButton.y + playButton.height / 2) + 10);
		
		
		surface.fill(255);
		String info = "INFO";
		float iWidth = surface.textWidth(info);
		surface.text(info,  (infoButton.x + infoButton.width / 2) - (iWidth / 2), (infoButton.y + playButton.height / 2) + 10);
		
		
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
