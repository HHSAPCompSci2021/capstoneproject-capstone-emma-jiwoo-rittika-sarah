package circeIsland.screens;

import java.awt.Rectangle;
import circeIsland.main.DrawingSurface;

public class Information extends Screen{

	private Rectangle exitButton;
	private String info;
	
	
	public Information(DrawingSurface surface) {
		super(800, 600, surface);
		exitButton = new Rectangle(25, 25, 40, 40);
		info = "Welcome to Circe's Island!"
				+ "\n- To move Circe, use the WASD keys."
				+ "\n- To enter Circe's workspace and make potions,move Circe to her house and press RETURN"
				+ "\n- To add elements to the Island, click on a location and select the desired element."
				+ "\n- To plant the garden, move Circe to a garden plot, click on the plot, and select the desired plant.";
	}
	
	
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
		
		surface.textSize(15);
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
