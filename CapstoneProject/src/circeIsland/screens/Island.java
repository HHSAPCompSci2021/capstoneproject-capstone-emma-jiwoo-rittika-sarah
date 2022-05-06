package circeIsland.screens;

import java.util.ArrayList;
import circeIsland.main.DrawingSurface;

import circeIsland.creatures.*;
import circeIsland.elements.Element;
import circeIsland.main.DrawingSurface;

public class Island extends Screen{

	DrawingSurface surface;
	Element[][] element;
	ArrayList<Creature> creatures;
	Circe circe;
	int currentTime;
	
	
	public Island() {
		super(5, 5);
	}
	
	public void setElement() {
		
	}
	
	public void keepTime() {
		
	}
	
	public int getHeight() {
		return super.HEIGHT;
	}
	
	public int getWidth() {
		return super.WIDTH;
	}
	
}
