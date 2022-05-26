package circeIsland.screens;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import circeIsland.main.DrawingSurface;
import g4p_controls.*;
import processing.core.PImage;
import circeIsland.creatures.*;
import circeIsland.elements.Element;
import circeIsland.elements.*;
import circeIsland.main.DrawingSurface;

/**
 * Has the island, with all the elements and creatures
 * @author Rittika Saha
 */
public class Island extends Screen{

	private Element[][] element;
	private ArrayList<Creature> creatures;
	private Circe circe;
	private House circeHouse;
	private Rectangle infoButton;
	private Creature nymphVisitor, malVisitor;
	
	private boolean landElementSelected, mouseClickEnabled, dropDone, showWarning;
	private int[] selectedSpot;
	private GDropList list;
	
	private PImage islandImage, malImage, nymphImage, pigImage;
	private PImage gardenEmpty, gardenA1, gardenA2, gardenA3, gardenA4, gardenM1, gardenM2, gardenM3, gardenM4, gardenB1, gardenB2, gardenB3, gardenB4, gardenG1, gardenG2, gardenG3, gardenG4;
	private PImage houseImage, cHImage, penImage;
	private PImage[] allAGarden, allBGarden, allGGarden, allMGarden;
	
	
	/**
	 * Creates a new island in the provided surface, given the image of the island, Circe and the location of her house.
	 * @param surface the PApplet on which this island will be drawn
	 * @param islandImage the PImage of this island
	 * @param c Circe
	 * @param houseX the x-coordinate of circe's house
	 * @param houseY the y-coordinate of circe's house
	 */
	public Island(DrawingSurface surface, PImage islandImage, Circe c, int houseX, int houseY) {
		super(1200, 900, surface);
		this.islandImage = islandImage;
		
		element = new Element[25][25];
		
		circeHouse = new House(this, null, houseX, houseY, "circe");
		
		circe = c;
		creatures = new ArrayList<Creature>();
		infoButton = new Rectangle(surface.width-80, 20, surface.width / element.length -10, surface.height /element[0].length - 10);
		
		landElementSelected = false;
		mouseClickEnabled = true;
		selectedSpot = new int[2];
		setupIsland(houseX, houseY);
	}
	
	

	/**
	 * Draws the island with all its elements and creatures to the screen.
	 * @post The properties of the provided PApplet provided may be modified
	 */
	public void draw() {
		super.draw();
		
		infoButton.setBounds(surface.width-80, 20, surface.width / element.length -10, surface.height /element[0].length - 10);
		
		int borderX = 12;
		int borderY = 18;
		float cellWidth = (surface.width - borderX) / element[0].length;
		float cellHeight = (surface.height - borderY) / element.length;
		
		
		changeWithTime();
		
		//drawing grid of elements
		surface.strokeWeight(0);
		for(int i = 0; i<element.length; i++) { //x
			for(int j = 0; j<element[0].length; j++) { //y
				if(element[j][i] == null) {
					surface.noFill();
					surface.rect(6 + (j * cellWidth), 9 + (i*cellHeight), cellWidth, cellHeight);
				}
				else {
					surface.noFill();
					if(element[j][i] instanceof Land) {
						surface.rect(6 + (j * cellWidth), 9 + (i*cellHeight), cellWidth,cellHeight);
					}
					else {
						element[j][i].draw(surface, cellWidth, cellHeight);
					}
				}
			}
		}
		

		surface.textSize(10);
		
		//info button
		surface.fill(222, 139, 62);
		surface.rect(infoButton.x,  infoButton.y,  infoButton.width,  infoButton.height);
		float w = surface.textWidth("i");
		surface.textSize(20);
		surface.fill(255);
		surface.text("i", infoButton.x+infoButton.width/2 - w/2, infoButton.y+infoButton.height/3 * 2);
		
		if(landElementSelected) {
			landElementSelected = false;
			mouseClickEnabled = false;
			G4P.setGlobalColorScheme(3);
			list = new GDropList(surface, (borderX/2) + (selectedSpot[0] * cellWidth), (borderY/2) + (selectedSpot[1]*cellHeight), cellWidth, cellHeight*3, 0);  
			list.setItems(new String[] {"Choose", "None", "House", "Garden", "Pig pen", "Land"}, 0);
			list.addEventHandler(this,  "handleElementChange");
		}
		if (dropDone) {
			mouseClickEnabled = true;
			list.setVisible(false);
			dropDone = false;
		}
		

		for(Creature c : creatures) {
			c.act();
			c.draw(surface);
			
			if(c instanceof MaliciousVisitor) {
				performSteal(c);
			}
		}
		circe.draw(surface);
		
		//show any required warning every two days
		if(surface.getDays() % 2 == 0) {
			int numHouses = getNumHouses();
			int numNymphs = getNumCreature('n');
			if(numNymphs - numHouses >= 3)
				drawWarningMessage(1);
		}
		
	}
	
	private void performSteal(Creature c) {
		MaliciousVisitor mv = (MaliciousVisitor)c;
		double prob = Math.random();
		if(mv.isStealing() && prob < 0.2 && surface.getDays() % 2 == 1 && surface.getHours() % 6 == 0){ 
			WorkTable w = surface.getWorkTable();
			int randType = (int)(Math.random() * 13);
			if(randType == 0 || randType == 13) {
				return;
			}
			else if(randType != 0) {
				w.removeFromStorage(new Holdable(randType));
				mv.stealMessage(this.surface);
			}
		}
		
	}
	
	
	private void changeWithTime() {
		surface.push();
		int hours = surface.getHours();
		int factor = 1;
		surface.image(islandImage, 0, 0, surface.width, surface.height);
		if(hours >= 16 && hours <= 19) {
			surface.fill(5, (hours - 7) * 10/factor);
			surface.rect(0, 0, surface.width, surface.height);
			factor++;
		}
		else if(hours > 19) {
			surface.fill(5, (factor + 9) * 10);
			surface.rect(0, 0, surface.width, surface.height);
			factor--;
		}
		surface.pop();
	}

	/**
	 * Adds a Nymph to this Island
	 */
	public void addNymph() {
		//Nymph newVisitor = new Nymph(nymphImage, 200, 200);
		//newVisitor.putOnIsland(this);
		nymphVisitor = new Nymph(nymphImage, 200, 200);
		nymphVisitor.putOnIsland(this);
	}
	
	/**
	 * Adds a malicious visitor to this island
	 */
	public void addMaliciousVisitor() {
		//MaliciousVisitor newVisitor = new MaliciousVisitor(malImage, 200, 200);
		//newVisitor.putOnIsland(this);
		malVisitor = new MaliciousVisitor(malImage, 200, 200);
		malVisitor.putOnIsland(this);
	}
	
	
	private int getNumHouses(){
		int count = 0;
		for(Element[] arr : element) {
			for(Element e : arr) {
				if(e instanceof House && e.getXCoor() != circeHouse.getXCoor() && e.getYCoor() != circeHouse.getYCoor())
					count ++;
			}
		}
		return count;
	}
	
	
	private int getNumCreature(char code) {
		int count = 0;
		for(Creature c : creatures) {
			if(code == 'n') {
				if(c instanceof Nymph)
					count++;
			}
			else {
				if(c instanceof MaliciousVisitor)
					count++;
			}
		}
		return count;
	}
	
	
	
	private void drawWarningMessage(int code) {
		surface.fill(255);
		surface.rect(140,  20,  surface.width/4,  surface.height/7 - 120);
		String msg = "";
		
		switch(code) {
		case 1: //too many nymphs
			msg = "Build more houses to accommodate the nymphs!";
		case 2:
			
		}
		
		double w = surface.textWidth(msg);
		surface.fill(0);
		surface.text(msg,  (float)(140 + (surface.width/4) - w / 2), (50 + (surface.height/7 - 120)) / 2 + 10);
		
		
	}
	
	
	
	/**
	 * Executes when the mouse is clicked, depending on the x,y location of the mouse click.
	 * - If the click is on land, allows drop down menu to be created
	 * - If the click is on pond, adds water to Circe's inventory
	 * - If the click is on a malicious visitor, checks if it can be fed or turned to a pig
	 * - If the click is on a garden, plants or harvests the garden depending on circe's inventory and the state of the garden
	 * @param mouseX the x-coordinate of the mouse click
	 * @param mouseY the y-coordinate of the mouse click
	 * @post the provided DrawingSurface may be modified
	 */
	public void processMouseClick(int mouseX, int mouseY) {
		if(!mouseClickEnabled) {
			return;
		}
		
		float cellWidth = (surface.width - 12) / element[0].length;
		float cellHeight = (surface.height - 18) / element.length;
		
		int[] clickInGrid = coorToGrid(mouseX, mouseY);

		if(clickInGrid[0] < 0 || clickInGrid[0] > element.length -1 || clickInGrid[1] < 0 || clickInGrid[1] > element[0].length - 1) {
			return;
		}
		
		Element clickedElement = getElement(clickInGrid[1], clickInGrid[0]);

		//click on malicious visitor with bread/wine/potion
		for (int i = 0; i<creatures.size(); i++) {
			if (creatures.get(i) instanceof MaliciousVisitor) {
				MaliciousVisitor mv = (MaliciousVisitor)(creatures.get(i));
				boolean isNear = Math.abs(mv.getXGrid() - circe.getXGrid()) < 3 && Math.abs(mv.getYGrid() - circe.getYGrid()) < 3;
				Holdable holding = circe.getInventory()[circe.getCurrentHold()];
				if(holding == null) {
				}
				else{
					int hold = holding.getType();
					
					if(mv.contains(mouseX, mouseY) && isNear) {
						if(mv.isRunning() && (hold == Holdable.BREAD || hold == Holdable.WINE)){
							mv.stopRunning();
							circe.removeFromInventory(circe.getCurrentHold());
							return;
						}
						else if(!mv.isRunning() && hold == Holdable.POTION) {
							circe.turnPig(mv);
							circe.removeFromInventory(circe.getCurrentHold());
							return;
						}
					}
				}
			}
			else if(creatures.get(i) instanceof Nymph) {
				Nymph n = (Nymph)(creatures.get(i));
				boolean isNear = Math.abs(n.getXGrid() - circe.getXGrid()) < 3 && Math.abs(n.getYGrid() - circe.getYGrid()) < 3;
				Holdable holding = circe.getInventory()[circe.getCurrentHold()];
				if(holding != null) {
					int hold = holding.getType();
					if(n.contains(mouseX, mouseY) && isNear && (hold == Holdable.BREAD || hold == Holdable.WINE)) {
						n.fed();
						circe.removeFromInventory(circe.getCurrentHold());
						return;
					}
				}
			}
			else if(creatures.get(i) instanceof Pig) {
				Pig p = (Pig)(creatures.get(i));
				boolean isNear = Math.abs(p.getXGrid() - circe.getXGrid()) < 3 && Math.abs(p.getYGrid() - circe.getYGrid()) < 3;
				Holdable holding = circe.getInventory()[circe.getCurrentHold()];
				if(holding != null) {
					int hold = holding.getType();
					if(p.contains(mouseX, mouseY) && isNear && (hold == Holdable.BARLEY_PLANT)) {
						p.fed();
						circe.removeFromInventory(circe.getCurrentHold());
						return;
					}
				}
			}
		}
		
		if(circe.getInventoryByCoor(mouseX, mouseY) != -1) {
            circe.grab(circe.getInventoryByCoor(mouseX, mouseY));
		}
		//click on garden
		else if(clickedElement instanceof GardenLand && circe.intersects(clickedElement.getXCoor() * cellWidth, clickedElement.getYCoor() * cellHeight, cellWidth, cellHeight)){
			GardenLand e = (GardenLand)(element[clickInGrid[0]][clickInGrid[1]]);
			Holdable holding = circe.getInventory()[circe.getCurrentHold()];
			
			if(e.isAlive()) {
				if(holding != null && holding.getType() == Holdable.WATER) {
					e.water();
					circe.removeFromInventory(circe.getCurrentHold());
				}
				circe.harvest(e);
			}
			else if(e.isDead()) {
				element[clickInGrid[0]][clickInGrid[1]] = new GardenLand(this, allAGarden, clickInGrid[0], clickInGrid[1]);
			}
			else {
				if(holding != null) {
					int hold = holding.getType();
					if(hold == Holdable.ANITHOS_SEED) {
						e.plant(hold + 4, allAGarden);
						circe.removeFromInventory(circe.getCurrentHold());
					}
					else if(hold == Holdable.BARLEY_SEED) {
						e.plant(hold + 4, allBGarden);
						circe.removeFromInventory(circe.getCurrentHold());
					}
					else if (hold == Holdable.GRAPE_SEED) {
						e.plant(hold + 4, allGGarden);
						circe.removeFromInventory(circe.getCurrentHold());
					}
					else if(hold == Holdable.MARATHOS_SEED) {
						e.plant(hold + 4, allMGarden);
						circe.removeFromInventory(circe.getCurrentHold());
					}					
				}
			}
		}
		else if(element[clickInGrid[0]][clickInGrid[1]] instanceof Pond) {
			circe.addOnInventory(new Holdable(9));
		}
		else if(mouseX >= infoButton.x && mouseX <= infoButton.x + infoButton.width && mouseY >= infoButton.y && mouseY <= infoButton.y + infoButton.height) {
			surface.switchScreen(1);
		}
		else if(clickedElement != null && clickedElement instanceof Land){
			landElementSelected = true;
			selectedSpot[0] = clickInGrid[0];
			selectedSpot[1] = clickInGrid[1];
		}
		
		checkNymphLeaving();
	}
	
	private void checkNymphLeaving() {
		for(int i = 0; i<creatures.size(); i++) {
			if(creatures.get(i) instanceof Nymph) {
				Nymph n = (Nymph)(creatures.get(i));
				if(n.getGetOut()) {
					creatures.remove(i);
					i--;
				}
			}
			else if(creatures.get(i) instanceof Pig) {
				Pig p = (Pig)(creatures.get(i));
				if(p.isDead()) {
					creatures.remove(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * In the event that a key is pressed, takes in the key that is pressed and executes the correct response
	 * - if the key is W, A, S, D, then moves Circe in the corresponding direction
	 * - if the key is enter and Circe is at her house, then switches the DrawingSurface screen
	 * @param key the key pressed
	 * @post the provided DrawingSurface may be modified
	 */
	public void processKey(char key) {
		
		if(key == 'w' || key == 'W') {
			circe.moveY(Creature.UP);
		}
		if(key == 'a' || key == 'A') {
			circe.moveX(Creature.LEFT);
		}
		if(key == 's' || key == 'S') {
			circe.moveY(Creature.DOWN);
		}
		if(key == 'd' || key == 'D') {
			circe.moveX(Creature.RIGHT);
		}
		
		int[] coor = circe.coorToGrid(circe.getCenterX(), circe.getCenterY());
		
		if(key == '\n') {
			float cellWidth = (surface.width - 11) / element[0].length;
			float cellHeight = (surface.height - 17) / element.length;
			
			Rectangle2D.Double cHouseRect = new Rectangle2D.Double(circeHouse.getXCoor() * cellWidth - 20, circeHouse.getYCoor() * cellHeight - 20, 2*cellWidth + 40, 2*cellHeight + 40);
			if(circe.intersects(cHouseRect)) {
				surface.switchScreen(0);
			}
			
		}
		
	}
	
	
	/**
	 * Returns the indexes of the location in the 2DArray of elements, given the x and y coordinates on the screen. 
	 * Returned as {x-index in grid, y-index in grid}
	 * @param xCoor x-coordinate on the screen
	 * @param yCoor y-coordinate on the screen
	 * @return int[] with the the specific indexes in element[][] at which the given coordinate point occurs. 
	 */
	public int[] coorToGrid(double xCoor, double yCoor) {
		float cellWidth = (surface.width - 12) / element[0].length;
		float cellHeight = (surface.height - 18) / element.length;
		
		int xGrid = (int)((xCoor-6)/cellWidth);
		int yGrid = (int)((yCoor-9)/cellHeight);
		
		int[] grid = {xGrid, yGrid};
		return grid;
	}
	
	
	
	/**
	 * Returns the Element at a given location in the 2D array of Elements
	 * @param x the x-index
	 * @param y the y-index
	 * @return Element at element[y][x]
	 */
	public Element getElement(int x, int y) {
		return element[y][x];
	}
	
	
	/**
	 * Puts the given element into the given location in the 2D array of elements
	 * @param e the element to be places
	 * @param x the x-index of the element in the array
	 * @param y the y-index of the element in the array
	 */
	public void setElement(Element e, int x, int y) {
		element[y][x] = e;
	}
	
	/**
	 * Adds the given Creature to the ArrayList of creatures on this Island
	 * @param c the Creature to be added
	 */
	public void addCreature(Creature c) {
		creatures.add(c);
	}
	
	
	/**
	 * Removes the given creature from the ArrayList of Creatures on this Island
	 * @param c the Creature to be removed
	 */
	public void removeCreature(Creature c) {
		for(int i = 0; i<creatures.size(); i++) {
			if(creatures.get(i).equals(c)) {
				creatures.remove(i);
				return;
			}
		}
	}
	
	/**
	 * Returns this Island's circe
	 * @return circe
	 */
	public Circe getCirce() {
		return circe;
	}
	
	/**
	 * Returns Circe's House on this Island
	 * @return circe's house
	 */
	public House getCirceHouse() {
		return circeHouse;
	}
	
	/**
	 * Returns the ArrayList of Elements representing all the locations on this Island
	 * @return the ArrayList of Elements
	 */
	public Element[][] getElements() {
		return element;
	}
	
	/**
	 * Returns the DrawingSurface on which this Island is displayed
	 * @return
	 */
	public DrawingSurface getSurface() {
		return surface;
	}
	
	/**
	 * Changes the element at the location of the drop-down menu to the Element type chosen from the drop down list
	 * @param list the list of options in the drop-down menu
	 * @param event the GEvent that occurred
	 */
	public void handleElementChange(GDropList list, GEvent event) {
		String text = list.getSelectedText();
		int x = selectedSpot[0];
		int y = selectedSpot[1];
		if(text.equals("Choose"))
			return;
		
		if(text.equals("House")) {
			element[x][y] = new House(this, houseImage, x, y, "norm");
		}
		else if(text.equals("Garden")) {
			element[x][y] = new GardenLand(this, allAGarden, x, y);
		}
		else if(text.equals("Pig pen")) {
			element[x][y] = new PigPen(this, penImage, x, y);
		}
		else if(text.equals("Land")) {
			element[x][y] = new Land(this, x, y);
		}
		dropDone = true;
	}

	
	
	/**
	 * Returns the PImage of the given type of element or creature.
	 * The types include: "pig"; "nymph"; "malicious visitor"
	 * @param type the type of creature whose image is to be returned
	 * @return the PImage of the given type
	 * @pre types must be one of the types specified above
	 */
	public PImage getImage(String type) {
		switch(type) {
		case "pig":
			return pigImage;
		case "nymph":
			return nymphImage;
		case "malicious visitor":
			return malImage;
		}
		return null;
	}
	
	
	
	private void setImages() {
		nymphImage = surface.loadImage("Files/NymphFrontStand.png");
		malImage = surface.loadImage("Files/MaliciousFrontStand.png");
		pigImage = surface.loadImage("Files/PigFrontStand.png");
		
		gardenEmpty = surface.loadImage("Files/GardenEmpty.png");
		gardenA1 = surface.loadImage("Files/GardenAnithosBud.png"); 
		gardenA2 = surface.loadImage("Files/GardenAnithosSprout.png"); 
		gardenA3 = surface.loadImage("Files/GardenAnithosGrown.png");
		gardenA4 = surface.loadImage("Files/GardenAnithosDead.png");
		allAGarden = new PImage[5];
		allAGarden[0] = gardenEmpty;
		allAGarden[1] = gardenA1;
		allAGarden[2] = gardenA2;
		allAGarden[3] =  gardenA3;
		allAGarden[4] =  gardenA4;
		
		gardenB1 = surface.loadImage("Files/GardenBarleyBud.png"); 
		gardenB2 = surface.loadImage("Files/GardenBarleySprout.png"); 
		gardenB3 = surface.loadImage("Files/GardenBarleyGrown.png");
		gardenB4 = surface.loadImage("Files/GardenBarleyDead.png");
		allBGarden = new PImage[5];
		allBGarden[0] = gardenEmpty;
		allBGarden[1] = gardenB1;
		allBGarden[2] = gardenB2;
		allBGarden[3] = gardenB3;
		allBGarden[4] = gardenB4;
		
		gardenG1 = surface.loadImage("Files/GardenGrapeBud.png"); 
		gardenG2 = surface.loadImage("Files/GardenGrapeSprout.png"); 
		gardenG3 = surface.loadImage("Files/GardenGrapeGrown.png");
		gardenG4 = surface.loadImage("Files/GardenGrapeDead.png");
		allGGarden = new PImage[5];
		allGGarden[0] = gardenEmpty;
		allGGarden[1] = gardenG1;
		allGGarden[2] = gardenG2;
		allGGarden[3] = gardenG3;
		allGGarden[4] = gardenG4;
		
		gardenM1 = surface.loadImage("Files/GardenMarathosBud.png"); 
		gardenM2 = surface.loadImage("Files/GardenMarathosSprout.png"); 
		gardenM3 = surface.loadImage("Files/GardenMarathosGrown.png");
		gardenM4 = surface.loadImage("Files/GardenMarathosDead.png");
		allMGarden = new PImage[5];
		allMGarden[0] = gardenEmpty;
		allMGarden[1] = gardenM1;
		allMGarden[2] = gardenM2;
		allMGarden[3] = gardenM3;
		allMGarden[4] = gardenM4;
		
		houseImage = surface.loadImage("Files/HouseVisitor.png");
		cHImage = surface.loadImage("Files/HouseCirce.png");
		penImage = surface.loadImage("Files/PigPen.png");
		
		
	}
	
	
	private void setupIsland(int hX, int hY) {
		setImages(); // creates all the required images
		circeHouse.setImage(cHImage);
		
		circe.putOnIsland(this);
		Nymph c1 = new Nymph(nymphImage, 450, 250);
		MaliciousVisitor c2 = new MaliciousVisitor(malImage, 600, 400);
		Pig c3 = new Pig(pigImage, 575, 250);
		c1.putOnIsland(this);
		c2.putOnIsland(this);
		c3.putOnIsland(this);
		
		
		
		//ALL LAND
		element[14][1] = new Land(this, 14, 1);
		element[14][1].setIsInGrid(true);
		
		element[13][2] = new Land(this, 13, 2);
		element[13][2].setIsInGrid(true);
		element[14][2] = new Land(this, 14, 2);
		element[14][2].setIsInGrid(true);
		element[15][2] = new Land(this, 15, 2);
		element[15][2].setIsInGrid(true);
		
		element[7][3] = new Land(this, 7, 3);
		element[7][3].setIsInGrid(true);
		element[8][3] = new Land(this, 8, 3);
		element[8][3].setIsInGrid(true);
		element[9][3] = new Land(this, 9, 3);
		element[9][3].setIsInGrid(true);
		
		for(int i = 13; i<=19; i++) {
			Land l = new Land(this, i, 3);
			element[i][3] = l;
			l.setIsInGrid(true);
		}
		
		for(int i = 6; i<=9; i++) {
			Land l = new Land(this, i, 4);
			element[i][4] = l;
			l.setIsInGrid(true);
		}
		
		for(int i = 13; i<=20; i++) {
			Land l = new Land(this, i, 4);
			element[i][4] = l;
			l.setIsInGrid(true);
		}
		
		for(int i = 5; i<=18; i++) {
			for(int j = 3; j<=21; j++) {
				Land l = new Land(this, j, i);
				element[j][i] = l;
				l.setIsInGrid(true);
			}
		}
		
		for(int i = 10; i<=12; i++) {
			element[i][5].setIsInGrid(false);
			element[i][5] = null;
		}
		element[11][6].setIsInGrid(false);
		element[11][6] = null;
		
		element[21][5].setIsInGrid(false);
		element[21][5] = null;
		element[21][6].setIsInGrid(false);
		element[21][6] = null;
		
		element[3][9].setIsInGrid(false);
		element[3][9] = null;
		element[4][9].setIsInGrid(false);
		element[4][9] = null;
		element[3][10].setIsInGrid(false);
		element[3][10] = null;
		
		element[8][18].setIsInGrid(false);
		element[8][18] = null;
		element[9][18].setIsInGrid(false);
		element[9][18] = null;
		
		element[21][17].setIsInGrid(false);
		element[21][17] = null;
		element[21][18].setIsInGrid(false);
		element[21][18] = null;
		
		for(int i = 11; i<=17; i++) {
			Land l = new Land(this, 2, i);
			element[2][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 12; i<=15; i++) {
			Land l = new Land(this, 1, i);
			element[1][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 8; i<=15; i++) {
			Land l = new Land(this, 22, i);
			element[22][i] = l;
			l.setIsInGrid(true);
		}
		element[23][8] = new Land(this, 23, 8);
		element[23][8].setIsInGrid(true);
		
		for(int i = 12; i<=15; i++) {
			Land l = new Land(this, 23, i);
			element[23][i] = l;
			l.setIsInGrid(true);
		}
		
		element[6][19] = new Land(this, 6, 19);
		element[6][19].setIsInGrid(true);
		element[7][19] = new Land(this, 7, 19);
		element[7][19].setIsInGrid(true);
		
		for(int i = 11; i<=20; i++) {
			Land l = new Land(this, i, 19);
			element[i][19] = l;
			l.setIsInGrid(true);
		}
		
		for(int i = 20; i<=22; i++) {
			for(int j = 11; j<=18; j++) {
				Land l = new Land(this, j, i);
				element[j][i] = l;
				l.setIsInGrid(true);
			}
		}
		
		element[19][20] = new Land(this, 19, 20);
		element[19][20].setIsInGrid(true);
		element[20][20] = new Land(this, 20, 20);
		element[20][20].setIsInGrid(true);
		
		for(int i = 12; i<=	15; i++) {
			Land l = new Land(this, i, 23);
			element[i][23] = l;
			l.setIsInGrid(true);
		}
		
		
		//POND
		element[16][5] = new Pond(this, 16, 5);
		element[16][5].setIsInGrid(true);
		element[17][5] = new Pond(this, 17, 5);
		element[17][5].setIsInGrid(true);
		
		for(int i = 6; i<=8; i++) {
			for(int j = 14; j<=18; j++) {
				Pond p = new Pond(this, j, i);
				element[j][i] = p;
				p.setIsInGrid(true);
			}
		}
		
		element[19][7] = new Pond(this, 19, 7);
		element[19][7].setIsInGrid(true);
		element[19][8] = new Pond(this, 19, 8);
		element[19][8].setIsInGrid(true);
		
		for(int i = 14; i<=	18; i++) {
			if(i != 16) {
				Pond p = new Pond(this, i, 9);
				element[i][9] = p;
				p.setIsInGrid(true);
			}
		}
		
		
		//Circe's House
		circeHouse.putOnIsland(this);
		
	}

}
