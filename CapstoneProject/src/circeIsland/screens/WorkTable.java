package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import circeIsland.main.*;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import processing.core.PImage;
import circeIsland.creatures.Circe;
import circeIsland.elements.Holdable;

/**
 * 
 * @author Emma Yu, Rittika Saha
 *
 */
public class WorkTable extends Screen{

	private ArrayList<ArrayList<Holdable>> storage;
	//12 items: Grape seed, barley seed, marathos seed, anithos seed, grape, barley, marathos, anithos,
	//			water, wine, bread, potion
	
	private Rectangle inventoryButton, holdingsButton, cauldron;
	private ArrayList<String> recipes;
	private ArrayList<Holdable> cauldronItems;
	private boolean showRecipes;
	private Holdable curHoldable;
	private float curHoldableX;
	private float curHoldableY;
	private int gainSpot; //1 is inventory, 2 is personal, 3 is cauldron creation
	private Holdable brewedItem;
	private int brewedItemNum;
	private int brewStage; //0 is nothing in cauldron at first, 1 is adding to cauldron,
						   //2 is after successful brew, 3 is after unsuccessful brew
	private boolean locked; //to check whether something is being held or not
	Circe circe;
	
	private PImage backgroundImage;
	private PImage cauldronNeutralEmptyImage, cauldronNeutralFullImage, cauldronHappyImage, cauldronSadImage;
	private PImage[] holdableImages;
	
	public static ArrayList<Holdable> wineRecipe;
	public static ArrayList<Holdable> breadRecipe;
	public static ArrayList<Holdable> seedsRecipe;
	public static ArrayList<Holdable> potionRecipe;

	
	
	public WorkTable(DrawingSurface surface) {
		super(1200,600, surface);
		//this.surface = surface;
		//cookButton = new Rectangle(800/2-100,600/2-50,200,100);
		storage = new ArrayList<ArrayList<Holdable>>();
		//circe = c;
		inventoryButton = new Rectangle(620, 30, 150, 400);
		holdingsButton = new Rectangle(900, 450, 150, 100);
		inventoryButton = new Rectangle(630, 30, 200, 420);
		holdingsButton = new Rectangle(630, 450, 200, 100);
		recipes = new ArrayList<String>();
		cauldron = new Rectangle(300, 250, 250, 250);
		cauldronItems = new ArrayList<Holdable>();
		brewedItem = null;
		brewedItemNum = 0;
		wineRecipe = new ArrayList<Holdable>();
		breadRecipe = new ArrayList<Holdable>();
		seedsRecipe = new ArrayList<Holdable>();
		potionRecipe = new ArrayList<Holdable>();
		setImages();	
		declareRecipes();
		addRecipes();
		showRecipes = false;
		
		for (int i = 0; i<12; i++)
			storage.add(new ArrayList<Holdable>());
		
		// for testing:
		int i = 1;
		for (ArrayList<Holdable> h: storage) {
			h.add(new Holdable(i, holdableImages[i-1]));
			if (i == 1 || i == 2 || i == 3 || i==4) {
				h.add(new Holdable(i, holdableImages[i-1]));
				h.add(new Holdable(i, holdableImages[i-1]));
				h.add(new Holdable(i, holdableImages[i-1]));
				h.add(new Holdable(i, holdableImages[i-1]));
			}
			i++;
		}
	}
	
	public void setStartingInventory() {
		
	}
	
	public void add(Circe c) {
		circe = c;
	}
	
	private void setImages() {
		backgroundImage = surface.loadImage("Files/WorkTableBackground.png");
		cauldronNeutralEmptyImage = surface.loadImage("Files/CauldronNeutralEmpty.png");
		cauldronNeutralFullImage = surface.loadImage("Files/CauldronNeutralFull.png");
		cauldronHappyImage = surface.loadImage("Files/CauldronHappy.png");
		cauldronSadImage = surface.loadImage("Files/CauldronSad.png");
		holdableImages = new PImage[12];
		holdableImages[0] = surface.loadImage("Files/HoldableSeedGrape.png");
		holdableImages[1] = surface.loadImage("Files/HoldableSeedBarley.png");
		holdableImages[2] = surface.loadImage("Files/HoldableSeedMarathos.png");
		holdableImages[3] = surface.loadImage("Files/HoldableSeedAnithos.png");
		holdableImages[4] = surface.loadImage("Files/HoldableGrape.png");
		holdableImages[5] = surface.loadImage("Files/HoldableBarley.png");
		holdableImages[6] = surface.loadImage("Files/HoldableMarathos.png");
		holdableImages[7] = surface.loadImage("Files/HoldableAnithos.png");
		holdableImages[8] = surface.loadImage("Files/HoldableWater.png");
		holdableImages[9] = surface.loadImage("Files/HoldableWine.png");
		holdableImages[10] = surface.loadImage("Files/HoldableBread.png");
		holdableImages[11] = surface.loadImage("Files/HoldablePotion.png");
		
	}
	
//	private void setup() {
//		setImages();
//	}
	
	public void draw() {
		super.draw();
		surface.background(255,255,255);
		surface.image(backgroundImage, 0, 0, surface.width, surface.height);
		
//		surface.image(cauldronNeutralEmptyImage, cauldron.x, cauldron.y, cauldron.width, cauldron.height);
	//	surface.image(holdableImages[5], 0, 0, 50, 50);

		
		//for resizing

		inventoryButton.setBounds((int)(4 *surface.width / 5.2), surface.height / 13, surface.width / 6, (int)(surface.height / 1.6));
		holdingsButton.setBounds((int)(4 *surface.width / 5.2),(int)( 7 * surface.height / 9.5), surface.width / 6, surface.height / 7);
		cauldron.setBounds(3 * surface.width/9, 2 * surface.height/5, (int)(surface.width/3), surface.height/2 - 80);
		
		
		drawInventory();
		drawCirceInventory();
		drawAlchemy();
				
		float cellWidthInv = 150 / 2;
		float cellHeightInv = 400 / 6;
		float cellWidthCir = holdingsButton.width / 3;
		float cellHeightCir = holdingsButton.height / 2;
		
		if (locked) {
			if (curHoldable != null) {
				if (gainSpot == 1)
					curHoldable.draw(surface, curHoldableX, curHoldableY, cellWidthInv, cellHeightInv);
				if (gainSpot == 2)
					curHoldable.draw(surface, curHoldableX, curHoldableY, cellWidthCir, cellHeightCir);
				if (gainSpot == 3)
					curHoldable.draw(surface, curHoldableX, curHoldableY, cellWidthCir, cellHeightCir);
			}
		}
		
		if(showRecipes) {
			displayRecipes();
		}
 
		if (brewedItem != null) {
			brewedItem.draw(surface, cauldron.x + cauldron.width/2, cauldron.y + cauldron.height/2, cellWidthInv, cellHeightInv);
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
		boxX = inventoryButton.x;
		boxY = inventoryButton.y;
		
		
		float cellWidth = inventoryButton.width / inventory[0].length;
		float cellHeight = inventoryButton.height / inventory.length;
		
		int currentElement = 1;
		for(int i = 0; i<inventory.length; i++) { //RECT coordinates (top left) : 620, 30
			for(int j = 0; j<inventory[0].length; j++) {
				
				surface.push();
				//draws the grid for the inventory
				surface.fill(232, 224, 206);
				surface.rect(boxX + (j * cellWidth), boxY + (i*cellHeight), cellWidth, cellHeight);
				
				//draws element per grid
				Holdable h = new Holdable(currentElement, holdableImages[currentElement-1]);
				h.draw(surface, boxX+j*cellWidth, boxY + (i*cellHeight), cellWidth, cellHeight);
				
				//writes inventory numbers to drawing surface
				surface.textSize(15);
				surface.fill(0);
				surface.text("" + (inventory[i][j]), boxX + (j*cellWidth)+cellWidth-15, boxY + (i*cellHeight)+cellHeight-8);
				surface.textSize(12);
				surface.fill(255, 255, 255, 100);
				surface.noStroke();
				surface.rect(boxX + (j*cellWidth), boxY + (i*cellHeight)+cellHeight-18, cellWidth-20, 12);
				surface.fill(0);
				surface.text(h.getName(), boxX + (j*cellWidth)+5, boxY + (i*cellHeight)+cellHeight-8);
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
		boxX = holdingsButton.x;
		boxY = holdingsButton.y;
		
		float cellWidth = holdingsButton.width / inventory[0].length;
		float cellHeight = holdingsButton.height / inventory.length;
		
		
		for(int i = 0; i<inventory.length; i++) { 
			for(int j = 0; j<inventory[0].length; j++) {
				
				surface.push();
				//draws the grid for the inventory
				surface.fill(232, 224, 206);
				surface.rect(boxX + (j * cellWidth), boxY + (i*cellHeight), cellWidth, cellHeight);
				
				//draws item
				if (inventory[i][j] != null)
					inventory[i][j].draw(surface, boxX+j*cellWidth, boxY + (i*cellHeight), cellWidth, cellHeight);
				surface.pop();
			}
		}
		
	}
	
	public void drawAlchemy() {
		int bowlX = cauldron.x;
		int bowlY = cauldron.y;
		float bowlWidth = cauldron.width;
		float bowlHeight = cauldron.height;
			
		surface.noFill();
		surface.noStroke();
		surface.rect(bowlX, bowlY, bowlWidth, bowlHeight);
		
		if (brewStage == 0)
			surface.image(cauldronNeutralEmptyImage, cauldron.x, cauldron.y, cauldron.width, cauldron.height);
		if (brewStage == 1)
			surface.image(cauldronNeutralFullImage, cauldron.x, cauldron.y, cauldron.width, cauldron.height);
		if (brewStage == 2)
			surface.image(cauldronHappyImage, cauldron.x, cauldron.y, cauldron.width, cauldron.height);
		if (brewStage == 3)
			surface.image(cauldronSadImage, cauldron.x, cauldron.y, cauldron.width, cauldron.height);
		

		
		String contents = "";
		for (Holdable h: cauldronItems) {
			contents += h.getName()+" ";
		}
		surface.text(contents , bowlX, bowlY);
	}
	
	
	public void addToStorage(Holdable h) {
		if (h.getType() == 13) 
			return;
		if (storage.get(h.getType()-1).get(0).getType() == 13)
			storage.get(h.getType()-1).remove(0);
		storage.get(h.getType()-1).add(new Holdable(h.getType(), holdableImages[h.getType()-1]));
	}
	
	public void removeFromStorage(Holdable h) {
		if (h.getType() == 13)
			return;
		
		if (storage.get(h.getType()-1).size() == 1 && storage.get(h.getType()-1).get(0).getType() != 13)
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
		recipes.add(seeds);
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
		if (click.intersects(inventoryButton) && toSpotInv(mouseX, mouseY).getType() != 13) {
			locked = true;
			curHoldable = toSpotInv(mouseX, mouseY);
			curHoldableX = mouseX;
			curHoldableY = mouseY;
			removeFromStorage(toSpotInv(mouseX, mouseY));
			gainSpot = 1;
		}
		if (click.intersects(holdingsButton)) {
			locked = true;
			Holdable spot = toSpotCir(mouseX, mouseY);
			curHoldable = spot;
		//	System.out.println("going in");
			curHoldableX = mouseX;
			curHoldableY = mouseY;
			gainSpot = 2;
		}
		Rectangle brewedItemRect = new Rectangle(cauldron.x + cauldron.width/2 - 5, cauldron.y + cauldron.height/2 - 5, cauldron.x + cauldron.width/2 + 5, cauldron.y + cauldron.height/2 + 5);
		if (click.intersects(brewedItemRect) && brewedItem != null) {
			locked = true;
			curHoldable = brewedItem;
			brewedItem = null;
			curHoldableX = mouseX;
			curHoldableY = mouseY;
			gainSpot = 3;
		}
	}
	
	private Holdable toSpotInv(int mouseX, int mouseY) {
		int currentNum = 0;
		Holdable[][] inventory = new Holdable[6][2];
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				int type = storage.get(currentNum).get(0).getType();
				inventory[i][j] = new Holdable(type);
				if (type != 13)
					inventory[i][j].loadImage(holdableImages[type-1]);
				currentNum ++;
			}
		}
		
		float cellWidth = inventoryButton.width / 2;
		float cellHeight = inventoryButton.height / 6;
		for (int i = 0; i<6; i++) {
			for (int j = 0; j<2; j++) {
				if (inventoryButton.y+cellHeight*i <= mouseY && mouseY <= inventoryButton.y + cellHeight*i +cellHeight)
					if (inventoryButton.x+cellWidth*j <= mouseX && mouseX <= inventoryButton.x+cellWidth*j + cellWidth) {
					//	if (inventory[i][j].getType() != 13)
					//		removeFromStorage(inventory[i][j]);
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
						else
							return null;
						return inventory[i][j];
					}
			}
		}
		
		return null;
	}


	public void processMouseRelease(int mouseX, int mouseY) {
		
		Rectangle click = new Rectangle(mouseX, mouseY, 1, 1);
		if (curHoldable == null){}
		else if (click.intersects(cauldron)) {
			cauldronItems.add(curHoldable);
			brewStage = 1;
		}
		
		//check if the current holding type is the same as the inventory type. if so, add to that
		//if intersecting with holdingsButton and empty space, move into that space
		//otherwise, return to where it came from
		
		else if (click.intersects(inventoryButton) && toElementInv(mouseX, mouseY).getType() == curHoldable.getType()) {
			addToStorage(curHoldable);
				if (brewedItemNum != 0 && gainSpot == 3) {
				addToStorage(curHoldable);
				addToStorage(curHoldable);
				addToStorage(curHoldable);

				brewedItemNum = 0;
			//	brewedItem = null;
				}
		}
		
		else if (click.intersects(holdingsButton) && toIndexCir(mouseX, mouseY) > -1) {
			circe.setInventory(toIndexCir(mouseX, mouseY), curHoldable);
		}
		else {
			if (gainSpot == 1)
				addToStorage(curHoldable);
			if (gainSpot == 2)
				circe.addOnInventory(curHoldable);
			if (gainSpot == 3) {
				brewedItem = curHoldable;
				
			}
		}
		
		locked = false;
		curHoldable = null;
		curHoldableX = 0;
		curHoldableY = 0;
		gainSpot = -1;
	}

	private Holdable toElementInv(int mouseX, int mouseY) {
		int currentType = 1;
		Holdable[][] inventory = new Holdable[6][2];
		for (int i = 0; i<inventory.length; i++) {
			for (int j = 0; j<inventory[0].length; j++) {
				inventory[i][j] = new Holdable(currentType, holdableImages[currentType-1]);
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
	
	private int toIndexCir(int mouseX, int mouseY) {
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
						if (inventory[i][j] == null)
							return i*3+j;
					}
			}
		}
		
		
		return -1;
	}


	public void handleButtonClick(GButton b, GEvent event) {
		String buttonName = b.getText();
		if(buttonName.equals("Brew")) {
			brew();
		}
		else if(buttonName.equals("Recipes")) {
			showRecipes = !showRecipes;
			displayRecipes();
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
		if (matchesRecipe().getType() != 13) {
			Holdable h =  matchesRecipe();
			
			cauldronItems.clear();
			brewedItem = h;
		//	System.out.println(brewedItem.getType());
			brewStage = 2;
			if (brewedItem.getType() == 1 || brewedItem.getType() == 2 || brewedItem.getType() == 3 || brewedItem.getType() == 4)
				brewedItemNum = 4;		}
		else {
			for (Holdable h: cauldronItems)
				addToStorage(h);
			cauldronItems.clear();
			brewStage = 3;
		}
	}
	
	private Holdable matchesRecipe() {
		
		Holdable thing = new Holdable(13);
		
		ArrayList<Holdable> w = new ArrayList<>(wineRecipe);
//		System.out.println(w.toString());
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
		
//		System.out.println(w.toString());
		
		int size = cauldronItems.size();
		
		if (size == wineRecipe.size() && w.size() == 0) {
			return new Holdable(10, holdableImages[9]);
		}
		if (size == breadRecipe.size() && b.size() == 0)
			return new Holdable(11, holdableImages[10]);
		if (size == potionRecipe.size() && p.size() == 0)
			return new Holdable(12, holdableImages[11]);
		if (size == 1 && s.size() == 3) {
			int type = cauldronItems.get(0).getType()-4;
			thing = new Holdable(type, holdableImages[type-1]);
			thing = new Holdable(type, holdableImages[type-1]);
			thing = new Holdable(type, holdableImages[type-1]);
			thing = new Holdable(type, holdableImages[type-1]);
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
