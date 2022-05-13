package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;

import circeIsland.main.*;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import circeIsland.elements.Holdable;

public class WorkTable extends Screen{

	//private DrawingSurface surface;
	private ArrayList<Holdable> storage;
	private Rectangle cookButton, recipeButton;
	//GButton brewer, recipe, exit;
	private ArrayList<String> recipes;
	private boolean showRecipes;
	
	
	
	public WorkTable(DrawingSurface surface) {
		super(800,600, surface);
		//this.surface = surface;
		//cookButton = new Rectangle(800/2-100,600/2-50,200,100);
		storage = new ArrayList<Holdable>();
		
		cookButton = new Rectangle(100, 500, 100, 50);
		recipeButton = new Rectangle(400, 500, 100, 50);
		recipes = new ArrayList<String>();
		addRecipes();
		showRecipes = false;
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
		
		if(showRecipes) {
			displayRecipes();
		}
		
	}
	
	
	
	public void drawInventory(){
		int[][] inventory = new int[10][2];
		for(int k = 0; k<inventory.length; k++) {
			inventory[k][0] = k;
		}
		for(int i = 0; i<storage.size(); i++) {
			Holdable current = storage.get(i);
			int currentType = current.getType();
			inventory[currentType][1] = inventory[currentType][1] + 1;
		}
		
		//surface.fill(235, 213, 190);
		
		//top left coordinates of the inventory grid: surface.rect(620, 30, 150, 500)
		int boxX = 620;
		int boxY = 30;
		
		
		float cellWidth = 150 / inventory[0].length;
		float cellHeight = 500 / inventory.length;
		
		for(int i = 0; i<inventory.length; i++) { //RECT coordinates (top left) : 620, 30
			for(int j = 0; j<inventory[0].length; j++) {
				
				float cellCenterX = (float)(boxX + (j*cellWidth) + (cellWidth/2.5));
				float cellCenterY = (float)(boxY + (i*cellHeight) + (cellHeight/1.5));
				
				//draws the grid for the inventory
				surface.fill(235, 213, 190);
				surface.rect(620 + (j * cellWidth), 30 + (i*cellHeight), cellWidth, cellHeight);
				
				
				//writes inventory numbers to drawing surface
				surface.textSize(25);
				surface.fill(0);
				surface.text("" + (inventory[i][j] + 1), cellCenterX, cellCenterY);
				
			}
		}
		
	}
	
	
	public void addToStorage(Holdable h) {
		storage.add(h);
	}
	
	
	private void addRecipes() {
		String wine = "Wine: 3 grapes + 1 water";
		String bread = "Bread: 5 barley + 2 water";
		String swinePotion = "Potion: 2 maratho + 3 anithos + 1 water";
		recipes.add(wine);
		recipes.add(bread);
		recipes.add(swinePotion);
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
	
	public void handleButtonClick(GButton b, GEvent event) {
		String buttonName = b.getText();
		System.out.println(buttonName);
		System.out.println("CLICK");
		if(buttonName.equals("Brew")) {
			brew();
		}
		else if(buttonName.equals("Recipes")) {
			System.out.println(showRecipes);
			showRecipes = !showRecipes;
		}
		else if(buttonName.equals("X")) {
			System.out.println("EXIT");
//			brewer.setVisible(false);
//			b.setOpaque(false);
//			b.setEnabled(false);
			//System.out.println("Visible: " + brewer.isVisible());
			surface.switchScreen();
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
