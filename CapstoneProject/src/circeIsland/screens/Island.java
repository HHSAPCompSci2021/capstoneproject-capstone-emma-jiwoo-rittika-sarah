package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import circeIsland.main.DrawingSurface;

import circeIsland.creatures.*;
import circeIsland.elements.Element;
import circeIsland.main.DrawingSurface;

public class Island extends Screen{

	private DrawingSurface surface;
	private Element[][] element;
	private ArrayList<Creature> creatures;
	private Circe circe;
	private int currentTime;
	
	
	public Island(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
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
