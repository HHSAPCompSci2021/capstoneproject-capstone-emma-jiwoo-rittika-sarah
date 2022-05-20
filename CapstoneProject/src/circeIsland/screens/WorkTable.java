package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import circeIsland.main.*;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import circeIsland.creatures.Circe;
import circeIsland.elements.Holdable;

/**
 * 
 * @author Emma Yu, Rittika Saha
 *
 */
public class WorkTable extends Screen{

	//private DrawingSurface surface;
	private ArrayList<ArrayList<Holdable>> storage;
	//12 items: Grape seed, barley seed, marathos seed, anithos seed, grape, barley, marathos, anithos,
	//			water, wine, bread, potion
	
	private Rectangle cookButton, recipeButton, inventoryButton, holdingsButton, cauldron;
	//GButton brewer, recipe, exit;
	private ArrayList<String> recipes;
	private ArrayList<Holdable> cauldronItems;
	private boolean showRecipes;
	private Holdable curHoldable;
	private float curHoldableX;
	private float curHoldableY;
	private int gainSpot; //1 is inventory, 2 is personal, 3 is cauldron creation
	private Holdable brewedItem;
	private boolean locked; //to check whether something is being held or not
	Circe circe;
	
	public static ArrayList<Holdable> wineRecipe;
	public static ArrayList<Holdable> breadRecipe;
	public static ArrayList<Holdable> seedsRecipe;
	public static ArrayList<Holdable> potionRecipe;

	
	
	public WorkTable(DrawingSurface surface) {
		super(800,600, surface);
		//this.surface = surface;
		//cookButton = new Rectangle(800/2-100,600/2-50,200,100);
		storage = new ArrayList<ArrayList<Holdable>>();
		//circe = c;
		cookButton = new Rectangle(100, 500, 100, 50);
		recipeButton = new Rectangle(400, 500, 100, 50);
		inventoryButton = new Rectangle(620, 30, 150, 400);
		holdingsButton = new Rectangle(620, 450, 150, 100);
		recipes = new ArrayList<String>();
		cauldron = new Rectangle(200, 200, 300, 200);
		cauldronItems = new ArrayList<Holdable>();
		brewedItem = null;
		wineRecipe = new ArrayList<Holdable>();
		breadRecipe = new ArrayList<Holdable>();
		seedsRecipe = new ArrayList<Holdable>();
		potionRecipe = new ArrayList<Holdable>();

		declareRecipes();
		addRecipes();
		showRecipes = false;
		
		for (int i = 0; i<12; i++)
			storage.add(new ArrayList<Holdable>());
		
		// for testing:
		int i = 1;
		for (ArrayList<Holdable> h: storage) {
			h.add(new Holdable(i));
			if (i == 5) {
				h.add(new Holdable(i));
				h.add(new Holdable(i));
				h.add(new Holdable(i));
				h.add(new Holdable(i));

			}
				
			i++;
		}
	}
	

	public void add(Circe c) {
		circe = c;
		circe.setInventory(0, new Holdable(4));
		circe.setInventory(1, new Holdable(3));
		circe.setInventory(3, new Holdable(10));
		circe.setInventory(4, new Holdable(12));
		circe.setInventory(5, new Holdable(11));

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
		drawCirceInventory();
		drawAlchemy();
		
		if (locked) {
			if (curHoldable != null)
				curHoldable.draw(surface, curHoldableX, curHoldableY);
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
				if (storage.get(currentNum).get(0).getType() == 13)
					inventory[i][j] = 0;
				else
					inventory[i][j] = storage.get(currentNum).size();
				currentNum ++;
			}
		}
		
		//surface.fill(235, 213, 190);
		
		//top left coordinates of the inventory grid: surface.rect(620, 30, 150, 500)
		int boxX = 620;
		int boxY = 30;
		
		
		float cellWidth = 150 / inventory[0].length;
		float cellHeight = 400 / inventory.length;
		
		int currentElement = 1;
		for(int i = 0; i<inventory.length; i++) { //RECT coordinates (top left) : 620, 30
			for(int j = 0; j<inventory[0].length; j++) {
				float cellCenterX = (float)(boxX + (j*cellWidth) + (cellWidth/2.5));
				float cellCenterY = (float)(boxY + (i*cellHeight) + (cellHeight/2.5));
				
				surface.push();
				//draws the grid for the inventory
				surface.fill(235, 213, 190);
				surface.rect(620 + (j * cellWidth), 30 + (i*cellHeight), cellWidth, cellHeight);
				
				//draws element per grid
				Holdable h = new Holdable(currentElement);
				h.draw(surface, cellCenterX, cellCenterY);
				
				//writes inventory numbers to drawing surface
				surface.textSize(15);
				surface.fill(0);
				surface.text("" + (inventory[i][j]), boxX + (j*cellWidth)+cellWidth-15, boxY + (i*cellHeight)+cellHeight-8);
				surface.pop();
				
				currentElement++;
				
			}
		}
		
		
	}
	
	public void drawCirceInventory() {
		Holdable[][]inventory = new Holdable[2][3];
		int count = 0;
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				inventory[i][j] = circe.getInventory()[count];
				count++;
			}
		}
		
		int boxX = 620;
		int boxY = 450;
		
		float cellWidth = holdingsButton.width / inventory[0].length;
		float cellHeight = holdingsButton.height / inventory.length;
		
		
		for(int i = 0; i<inventory.length; i++) { 
			for(int j = 0; j<inventory[0].length; j++) {
				float cellCenterX = (float)(boxX + (j*cellWidth) + (cellWidth/2.5));
				float cellCenterY = (float)(boxY + (i*cellHeight) + (cellHeight/2.5));
				
				surface.push();
				//draws the grid for the inventory
				surface.fill(235, 213, 190);
				surface.rect(boxX + (j * cellWidth), boxY + (i*cellHeight), cellWidth, cellHeight);
				
				//draws item
				if (inventory[i][j] != null)
					inventory[i][j].draw(surface, cellCenterX, cellCenterY);
				surface.pop();
			}
		}
		
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
		
		String contents = "";
		for (Holdable h: cauldronItems) {
			contents += h.getName()+" ";
		}
		surface.text(contents , bowlX, bowlY);
	}
	
	
	public void addToStorage(Holdable h) {
		if (storage.get(h.getType()-1).size() == 1)
			storage.get(h.getType()-1).remove(0);
		storage.get(h.getType()-1).add(new Holdable(h.getType()));
	}
	
	public void removeFromStorage(Holdable h) {
		if (h.getType() == 13)
			return;
		
		if (storage.get(h.getType()-1).size() == 1)
			storage.get(h.getType()-1).add(new Holdable(13));
		
		storage.get(h.getType()-1).remove(0);
	}
	
	private void declareRecipes() {
		
		wineRecipe.add(new Holdable(5));
		wineRecipe.add(new Holdable(5));
		wineRecipe.add(new Holdable(5));
		wineRecipe.add(new Holdable(9));

		breadRecipe.add(new Holdable(6));
		breadRecipe.add(new Holdable(6));
		breadRecipe.add(new Holdable(6));
		breadRecipe.add(new Holdable(6));
		breadRecipe.add(new Holdable(6));
		breadRecipe.add(new Holdable(9));
		breadRecipe.add(new Holdable(9));
		
		seedsRecipe.add(new Holdable(5));
		seedsRecipe.add(new Holdable(6));
		seedsRecipe.add(new Holdable(7));
		seedsRecipe.add(new Holdable(8));
		
		potionRecipe.add(new Holdable(7));
		potionRecipe.add(new Holdable(7));
		potionRecipe.add(new Holdable(8));
		potionRecipe.add(new Holdable(8));
		potionRecipe.add(new Holdable(8));
		potionRecipe.add(new Holdable(9));

	}
	
	private void addRecipes() {
		String wine = "Wine: 3 grapes + 1 water";
		String bread = "Bread: 5 barley + 2 water";
		String swinePotion = "Potion: 2 maratho + 3 anithos + 1 water";
		String seeds = "Seeds: 1 grape/barley/maratho/anithos for 4 seeds";
		recipes.add(wine);
		recipes.add(bread);
		recipes.add(swinePotion);
	}
	
	public void processMouseDrag(int mouseX, int mouseY) {
//		System.out.println("dragging" + curHoldable.getName());
		if (locked) {
			curHoldableX = mouseX;
			curHoldableY = mouseY;
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
		if (click.intersects(inventoryButton)) {
			locked = true;
			curHoldable = toSpotInv(mouseX, mouseY);
			curHoldableX = mouseX;
			curHoldableY = mouseY;
			gainSpot = 1;
		}
		if (click.intersects(holdingsButton)) {
			locked = true;
			curHoldable = toSpotCir(mouseX, mouseY);
			curHoldableX = mouseX;
			curHoldableY = mouseY;
			gainSpot = 2;
		}
	}
	
	private Holdable toSpotInv(int mouseX, int mouseY) {
		int currentNum = 0;
		Holdable[][] inventory = new Holdable[6][2];
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				inventory[i][j] = new Holdable(storage.get(currentNum).get(0).getType());
				currentNum ++;
			}
		}
		
		float cellWidth = inventoryButton.width / 2;
		float cellHeight = inventoryButton.height / 6;
		for (int i = 0; i<6; i++) {
			for (int j = 0; j<2; j++) {
				if (inventoryButton.y+cellHeight*i <= mouseY && mouseY <= inventoryButton.y + cellHeight*i +cellHeight)
					if (inventoryButton.x+cellWidth*j <= mouseX && mouseX <= inventoryButton.x+cellWidth*j + cellWidth) {
						if (inventory[i][j].getType() != 13)
							removeFromStorage(inventory[i][j]);
						return inventory[i][j];
					}
			}
		}
		return null;
	}
	
	private Holdable toSpotCir(int mouseX, int mouseY) {
		Holdable[][]inventory = new Holdable[2][3];
		int count = 0;
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
			//	if (circe.getInventory()[count] == null)
			//		System.out.println("is null");
				inventory[i][j] = circe.getInventory()[count];
				count++;
			}
		}
		
		float cellWidth = holdingsButton.width / inventory[0].length;
		float cellHeight = holdingsButton.height / inventory.length;
		
		for (int i = 0; i<2; i++) {
			for (int j = 0; j<3; j++) {
				if (holdingsButton.y+cellHeight*i <= mouseY && mouseY <= holdingsButton.y + cellHeight*i +cellHeight)
					if (holdingsButton.x+cellWidth*j <= mouseX && mouseX <= holdingsButton.x+cellWidth*j + cellWidth) {
//						System.out.println(inventory[i][j].getName());
						if (inventory[i][j] != null)
							circe.setInventory(i*3+j, null);
						return inventory[i][j];
					}
			}
		}
		
		return null;
	}


	public void processMouseRelease(int mouseX, int mouseY) {
		Rectangle click = new Rectangle(mouseX, mouseY, 1, 1);
		if (click.intersects(recipeButton) || click.intersects(cookButton)){}
		else if (click.intersects(cauldron))
			cauldronItems.add(curHoldable);
		
		//check if the current holding type is the same as the inventory type. if so, add to that
		//if intersecting with holdingsButton and empty space, move into that space
		//otherwise, return to where it came from
		
		else if (click.intersects(inventoryButton) && toElementInv(mouseX, mouseY).getType() == curHoldable.getType()) { 
			storage.get(curHoldable.getType()-1).add(curHoldable);

		}
		
		else if (click.intersects(holdingsButton) && isEmptyHoldingsSpot(mouseX, mouseY)) {
			circe.addOnInventory(curHoldable);
		}
		else {
			if (gainSpot == 1)
				addToStorage(curHoldable);
			if (gainSpot == 2)
				circe.addOnInventory(curHoldable);
		}
		
		locked = false;
		curHoldable = null;
		curHoldableX = 0;
		curHoldableY = 0;
	}

	private Holdable toElementInv(int mouseX, int mouseY) {
		int currentType = 1;
		Holdable[][] inventory = new Holdable[6][2];
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				inventory[i][j] = new Holdable(currentType);
				currentType ++;
			}
		}
		
		float cellWidth = inventoryButton.width / 2;
		float cellHeight = inventoryButton.height / 6;
		for (int i = 0; i<6; i++) {
			for (int j = 0; j<2; j++) {
				if (inventoryButton.y+cellHeight*i <= mouseY && mouseY <= inventoryButton.y + cellHeight*i +cellHeight)
					if (inventoryButton.x+cellWidth*j <= mouseX && mouseX <= inventoryButton.x+cellWidth*j + cellWidth) {
				//		System.out.println(inventory[i][j].getName());
						return inventory[i][j];
					}
			}
		}
		return null;
	}
	
	private boolean isEmptyHoldingsSpot(int mouseX, int mouseY) {
		Holdable[][]inventory = new Holdable[2][3];
		int count = 0;
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				inventory[i][j] = circe.getInventory()[count];
				count++;
			}
		}
		
		float cellWidth = holdingsButton.width / inventory[0].length;
		float cellHeight = holdingsButton.height / inventory.length;
		
		for (int i = 0; i<2; i++) {
			for (int j = 0; j<3; j++) {
				if (holdingsButton.y+cellHeight*i <= mouseY && mouseY <= holdingsButton.y + cellHeight*i +cellHeight)
					if (holdingsButton.x+cellWidth*j <= mouseX && mouseX <= holdingsButton.x+cellWidth*j + cellWidth) {
//						System.out.println(inventory[i][j].getName());
						if (inventory[i][j] != null)
							circe.setInventory(i*3+j, null);
	//					return inventory[i][j];
					}
			}
		}
		
		
		return false;
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
		if (matchesRecipe()[0].getType() != 13) {
			System.out.println("Success");
			cauldronItems.clear();
			brewedItem = new Holdable(matchesRecipe()[0].getType());
		}
		else {
			for (Holdable h: cauldronItems)
				addToStorage(h);
		}
		surface.text("BREWING",  100, 100);
	}
	
	private Holdable[] matchesRecipe() {
		
		Holdable[] thing = new Holdable[4];
		thing[0] = new Holdable(13);
		thing[1] = new Holdable(13);
		thing[2] = new Holdable(13);
		thing[3] = new Holdable(13);

		
		ArrayList<Holdable> w = new ArrayList<>(wineRecipe);
		System.out.println(w.toString());
		ArrayList<Holdable> b = new ArrayList<>(breadRecipe);
		ArrayList<Holdable> s = new ArrayList<>(seedsRecipe);
		ArrayList<Holdable> p = new ArrayList<>(potionRecipe);

		for (Holdable h: cauldronItems) {
			for (int i = 0; i<w.size(); i++) {
				if (h.getType() == w.get(i).getType())
					w.remove(i);
			}
			for (int i = 0; i<b.size(); i++) {
				if (h.getType() == b.get(i).getType())
					b.remove(i);
			}
			for (int i = 0; i<s.size(); i++) {
				if (h.getType() == s.get(i).getType())
					s.remove(i);
			}
			for (int i = 0; i<p.size(); i++) {
				if (h.getType() == p.get(i).getType())
					p.remove(i);
			}
		}
		
		System.out.println(w.toString());
		
		int size = cauldronItems.size();
		
		if (size == wineRecipe.size() && w.size() == 0)
			thing[0] = new Holdable(10);
		if (size == breadRecipe.size() && b.size() == 0)
			thing[0] = new Holdable(11);
		if (size == potionRecipe.size() && p.size() == 0)
			thing[0] = new Holdable(12);
		if (size == 1 && s.size() == 3) {
			thing[0] = new Holdable(cauldronItems.get(0).getType()-4);
			thing[1] = new Holdable(cauldronItems.get(0).getType()-4);
			thing[2] = new Holdable(cauldronItems.get(0).getType()-4);
			thing[3] = new Holdable(cauldronItems.get(0).getType()-4);
		}
		
		return thing;
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
