package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import circeIsland.main.*;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import circeIsland.creatures.Circe;
import circeIsland.elements.Holdable;

public class WorkTable extends Screen{

	//private DrawingSurface surface;
	private ArrayList<ArrayList<Holdable>> storage;
	//12 items: Grape seed, barley seed, marathos seed, anithos seed, grape, barley, marathos, anithos,
	//			water, wine, bread, potion
	
	private Rectangle cookButton, recipeButton, inventoryButton;
	//GButton brewer, recipe, exit;
	private ArrayList<String> recipes;
	private boolean showRecipes;
	private float curElementX;
	private float curElementY;
	private boolean locked;
	Circe circe;
	
	
	public WorkTable(DrawingSurface surface, Circe c) {
		super(800,600, surface);
		//this.surface = surface;
		//cookButton = new Rectangle(800/2-100,600/2-50,200,100);
		storage = new ArrayList<ArrayList<Holdable>>();
		circe = c;
		cookButton = new Rectangle(100, 500, 100, 50);
		recipeButton = new Rectangle(400, 500, 100, 50);
		inventoryButton = new Rectangle(620, 30, 150, 500);		
		recipes = new ArrayList<String>();
		addRecipes();
		showRecipes = false;
		
		for (int i = 0; i<12; i++)
			storage.add(new ArrayList<Holdable>());
	}
	

	
	public void draw() {

		surface.background(255,255,255);
		
//		surface.fill(235, 213, 190);
//		surface.rect(cookButton.x, cookButton.y, cookButton.width, cookButton.height);
//		surface.rect(recipeButton.x, recipeButton.y, recipeButton.width, recipeButton.height);
//		surface.fill(0);
//		String str = "BREW"; 
//		float w = surface.textWidth(str);
//		surface.textSize(20);
//		surface.text(str, cookButton.x+cookButton.width/2-w/2, cookButton.y+cookButton.height/2);

		
		drawInventory();
		drawAlchemy();
		
		if (locked) {
			Holdable h = new Holdable(1);
			h.draw(surface, curElementX, curElementY);
		}
		
		if(showRecipes) {
			displayRecipes();
		}
		
	}
	
	
	
	public void drawInventory(){
		
		int currentNum = 0;
		//draw grid and holdables for each grid
		int[][] inventory = new int[6][2];
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				inventory[i][j] = storage.get(currentNum).size();
				currentNum ++;
			}
		}
		
		//surface.fill(235, 213, 190);
		
		//top left coordinates of the inventory grid: surface.rect(620, 30, 150, 500)
		int boxX = 620;
		int boxY = 30;
		
		
		float cellWidth = 150 / inventory[0].length;
		float cellHeight = 500 / inventory.length;
		
		int currentElement = 1;
		for(int i = 0; i<inventory.length; i++) { //RECT coordinates (top left) : 620, 30
			for(int j = 0; j<inventory[0].length; j++) {
				float cellCenterX = (float)(boxX + (j*cellWidth) + (cellWidth/2.5));
				float cellCenterY = (float)(boxY + (i*cellHeight) + (cellHeight/1.5));
				
				surface.push();
				//draws the grid for the inventory
				surface.fill(235, 213, 190);
				surface.rect(620 + (j * cellWidth), 30 + (i*cellHeight), cellWidth, cellHeight);
				
				//draws element per grid
				Holdable h = new Holdable(currentElement);
				h.draw(surface, cellCenterX, cellCenterY);
				
				//writes inventory numbers to drawing surface
				surface.textSize(25);
				surface.fill(0);
				surface.text("" + (inventory[i][j]), cellCenterX, cellCenterY);
				surface.pop();
				
				currentElement++;
				
			}
		}
		
		
	}
	
	public void drawCirceInventory() {
		
	}
	
	public void drawElement(int num) {
		switch(num) {
		case 0:
		}
	}
	
	public void drawAlchemy() {
		int bowlX = 200;
		int bowlY = 200;
		float bowlWidth = 300;
		float bowlHeight = 200;
				
		surface.noFill();
		surface.rect(bowlX, bowlY, bowlWidth, bowlHeight);
	}
	
	
	public void addToStorage(Holdable h) {
		storage.get(h.getType()-1).add(h);
	}
	
	
	private void addRecipes() {
		String wine = "Wine: 3 grapes + 1 water";
		String bread = "Bread: 5 barley + 2 water";
		String swinePotion = "Potion: 2 maratho + 3 anithos + 1 water";
		recipes.add(wine);
		recipes.add(bread);
		recipes.add(swinePotion);
	}
	
	public void processMouseDrag(int mouseX, int mouseY) {
		System.out.println("dragging");
		if (locked) {
			curElementX = mouseX;
		curElementY = mouseY;
		}
	}
	
	public void processMouseClick(int mouseX, int mouseY) {
//		//inside the brew button
		System.out.println("processing");
//		if(mouseX>cookButton.x && mouseX<cookButton.x + cookButton.width && mouseY>cookButton.y && mouseY<cookButton.y + cookButton.height){
//			brew();
//		}
//		else if(mouseX>recipeButton.x && mouseX<recipeButton.x + recipeButton.width && mouseY>recipeButton.y && mouseY<recipeButton.y + recipeButton.height) {
//			System.out.println(showRecipes);
//			showRecipes = !showRecipes;
//		}
	}
	
	public void processMousePress(int mouseX, int mouseY) {
		Rectangle click = new Rectangle(mouseX, mouseY, 1, 1);
	//	if (click.intersects(inventoryButton)) {
			locked = true;
			curElementX = mouseX;
			curElementY = mouseY;
	//	}
	}
	
	public void processMouseRelease(int mouseX, int mouseY) {
		locked = false;
		
	}
	
	public void handleButtonClick(GButton b, GEvent event) {
		String buttonName = b.getText();
		if(buttonName.equals("Brew")) {
			brew();
		}
		else if(buttonName.equals("Recipes")) {
			showRecipes = !showRecipes;
		}
		else if(buttonName.equals("X")) {
			surface.switchScreen(0);
		}
	}
	
	
	public void setButtonVisible(boolean b) {
		ArrayList<GButton> buttonList = surface.getButtons();
		for(GButton button : buttonList) {
			button.setVisible(b);
		}
	}
	
	
	
	private void brew() {
		surface.text("BREWING",  100, 100);
	}
	
	private void displayRecipes() {
		surface.text("RECIPES YAYYY",  100, 100);
		surface.fill(250, 249, 200);
		surface.rect(50, 50, 410, 300);
		surface.fill(0);
		surface.textSize(20);
		for(int i = 0; i<recipes.size(); i++) {
			surface.text(recipes.get(i) + "\n", 60, 80 + (30 * i));
		}
		
	}
}
