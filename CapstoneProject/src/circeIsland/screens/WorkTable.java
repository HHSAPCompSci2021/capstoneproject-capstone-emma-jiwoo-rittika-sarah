package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;

import circeIsland.main.*;
import circeIsland.elements.Holdable;

public class WorkTable extends Screen{

	private DrawingSurface surface;
	private ArrayList<Holdable> storage;
	private Rectangle cookButton, recipeButton;
	private ArrayList<String> recipes;
	
	public WorkTable(DrawingSurface surface) {
		super(800,600);
		this.surface = surface;
		//cookButton = new Rectangle(800/2-100,600/2-50,200,100);
		storage = new ArrayList<Holdable>();
		
		
		cookButton = new Rectangle(100, 500, 200, 100);
		recipeButton = new Rectangle(400, 500, 200, 100);
		recipes = new ArrayList<String>();
		addRecipes();
	}
	
	public void draw() {
//		surface.background(255,255,255);
//		surface.fill(255);
//		surface.rect(cookButton.x, cookButton.y, cookButton.width, cookButton.height, 10, 10, 10, 10);
//		surface.fill(0);
//		String str = "BREW";
//		float w = surface.textWidth(str);
//		surface.text(str, cookButton.x+cookButton.width/2-w/2, cookButton.y+cookButton.height/2);


		surface.background(255,255,255);
		surface.fill(235, 213, 190);
		surface.rect(cookButton.x, cookButton.y, cookButton.width, cookButton.height);
		surface.rect(recipeButton.x, recipeButton.y, recipeButton.width, recipeButton.height);
		
		drawInventory();
		
	}
	
	
	
	
	public void drawInventory(){
		int[][] inventory = new int[8][2];
		for(int i = 0; i<storage.size(); i++) {
			Holdable current = storage.get(i);
			int currentType = current.getType();
		}
		
	}
	
	
	private void addToStorage(Holdable h) {
		storage.add(h);
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
