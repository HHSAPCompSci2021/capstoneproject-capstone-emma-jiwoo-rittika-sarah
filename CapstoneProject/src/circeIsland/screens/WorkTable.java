package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;

import circeIsland.main.*;
import circeIsland.elements.Holdable;

public class WorkTable extends Screen{

	private DrawingSurface surface;
	private Holdable[] storage;
	private Rectangle button;
	private ArrayList<String> recipes;
	
	public WorkTable(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		button = new Rectangle(800/2-100,600/2-50,200,100);
		recipes = new ArrayList<String>();
		addRecipes();
	}
	
	public void draw() {
		surface.background(255,255,255);
		surface.fill(255);
		surface.rect(button.x, button.y, button.width, button.height, 10, 10, 10, 10);
		surface.fill(0);
		String str = "BREW";
		float w = surface.textWidth(str);
		surface.text(str, button.x+button.width/2-w/2, button.y+button.height/2);
		
	}
	
	
	public void doAlchemy(){
		
	}
	
	private void addRecipes() {
		String wine = "3 grapes. 1 water.";
		String bread = "5 barley. 2 water.";
		String swinePotion = "2 maratho. 3 anithos. 1 water.";
		recipes.add(wine);
		recipes.add(bread);
		recipes.add(swinePotion);
	}
}
