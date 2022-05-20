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
 * 
 * @author Rittika Saha
 */
public class Island extends Screen{

	private Element[][] element;
	private ArrayList<Creature> creatures;
	private Circe circe;
	private House circeHouse;
	private Rectangle infoButton, warningBox;
	private boolean landElementSelected, mouseClickEnabled, gardenElementSelected, dropDone, showWarning;
	private int[] selectedSpot;
	private PImage islandImage, malImage, nymphImage, pigImage;
	private PImage gardenEmpty, gardenA1, gardenA2, gardenA3, gardenA4, gardenM1, gardenM2, gardenM3, gardenM4, gardenB1, gardenB2, gardenB3, gardenB4, gardenG1, gardenG2, gardenG3, gardenG4;
	private PImage houseImage, penImage;
	private PImage[] allGarden;
	GDropList list;
	
	/**
	 * Creates a new island in the provided surface, given the location of Circe and her house.
	 * @param surface the PApplet on which this island will be drawn
	 * @param islandImage
	 * @param cImage the Image of Circe
	 * @param circeX the x coordinate of Circe
	 * @param circeY the y coordinate of Circe
	 * @param houseX the x coordinate of Circe's house
	 * @param houseY the y coordinate of Circe's house
	 */
	public Island(DrawingSurface surface, PImage islandImage, Circe c, int houseX, int houseY) {
		super(800,600, surface);
		this.islandImage = islandImage;
		circeHouse = new House(this, houseImage, houseX, houseY, "circe");
		element = new Element[15][15];
		circe = c;
		creatures = new ArrayList<Creature>();
		infoButton = new Rectangle(surface.width-80, 20, surface.width / element.length -10, surface.height /element[0].length - 10);
		
		landElementSelected = false;
		gardenElementSelected = false;
		mouseClickEnabled = true;
		selectedSpot = new int[2];
		
		setupIsland(houseX, houseY);
	}
	
	

	/**
	 * Draws the island with all its elements and creatures to the screen.
	 * @post The properties of the provided PApplet provided will be modified
	 */
	public void draw() {
		super.draw();
		
		int borderX = 12;
		int borderY = 18;
		float cellWidth = (surface.width - borderX) / element[0].length;
		float cellHeight = (surface.height - borderY) / element.length;
		
		
		surface.push();
		//int days = surface.getDays();
		int hours = surface.getHours();
		int factor = 1;
		surface.image(islandImage, 0, 0, surface.width, surface.height);
		if(hours >= 16 && hours <= 19) {
			surface.fill(5, (hours - 7) * 10/factor);
			surface.rect(0, 0, surface.width, surface.height);
			factor++;
		}
		if(hours > 19) {
			surface.fill(5, (10 + factor)* 10);
			surface.rect(0, 0, surface.width, surface.height);
			factor--;
		}
		surface.pop();
		
		
		
		//drawing grid of elements
		surface.strokeWeight(0);
		for(int i = 0; i<element.length; i++) { //x
			for(int j = 0; j<element[0].length; j++) { //y
				if(element[i][j] == null) {
					//surface.fill(15, 163, 189); //the ocean around the island
					surface.noFill();
					surface.rect(6 + (j * cellWidth), 9 + (i*cellHeight), cellWidth, cellHeight);
				}
				else {
					surface.noFill();
					if(element[i][j] instanceof Land) {
						surface.rect(6 + (j * cellWidth), 9 + (i*cellHeight), cellWidth,cellHeight);
					}
					else {
						element[i][j].draw(surface, cellWidth, cellHeight);
					}
					//element[i][j].draw(surface, cellWidth, cellHeight);
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
			G4P.setGlobalColorScheme(4);
			list = new GDropList(surface, (borderX/2) + (selectedSpot[0] * cellWidth), (borderY/2) + (selectedSpot[1]*cellHeight), cellWidth, cellHeight*3, 0);  
			list.setItems(new String[] {"Choose", "House", "Garden", "Pig pen", "Land", "None"}, 0);
			list.addEventHandler(this,  "handleElementChange");
		}
		if(gardenElementSelected) {
			System.out.println("garden drop down");
			gardenElementSelected = false;
			mouseClickEnabled = false;
			G4P.setGlobalColorScheme(4);
			list = new GDropList(surface, (borderX/2) + (selectedSpot[0] * cellWidth), (borderY/2)  + (selectedSpot[1]*cellHeight), cellWidth, cellHeight*3, 0);  
			list.setItems(new String[] {"Grape", "Barley", "Marathos", "Anithos"}, 0);
			list.addEventHandler(this,  "handlePlantChange");
		}
		if (dropDone) {
			mouseClickEnabled = true;
			list.setVisible(false);
			//list.dispose();
			dropDone = false;
		}
		

		for(Creature c : creatures) {
			//c.act();
			c.draw(surface);
			//c.act();
			//c.act();
		}
		circe.draw(surface);
		
		//show any required warning every four days
		if(surface.getDays() % 4 == 0) {
			int numHouses = getNumHouses();
			int numNymphs = getNumCreature('n');
			if(numNymphs - numHouses >= 4)
				drawWarningMessage(1);
		}
		
		
	}
	
	private void checkNewVisitors() {
		int days = surface.getDays();
		
		if(days % 4 == 0 && days != 0) {
			Nymph c = new Nymph(nymphImage, 200, 200);
			c.putOnIsland(this);
		}
		else if(days % 7 == 0 && days != 0) {
			MaliciousVisitor c = new MaliciousVisitor(malImage, 200, 200);
			c.putOnIsland(this);
		}
		
	}
	
	
	private int getNumHouses(){
		int count = 0;
		for(Element[] arr : element) {
			for(Element e : arr) {
				if(e instanceof House && e.getXCoor() != circeHouse.getXCoor() && e.getYCoor() != circeHouse.getYCoor())
					count ++;
			}
		}
		//System.out.println(count);
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
		surface.rect(60,  50,  surface.width - 120,  surface.height - 120);
		String msg;
		
		switch(code) {
		case 1: //too many nymphs
		}
		
	}
	
	
	
	public void processMouseClick(int mouseX, int mouseY) {
		//System.out.println("Processing click");
		if(!mouseClickEnabled) {
			//System.out.println("ENETERED FALSE CLICK");
			//mouseClickEnabled = true;
			return;
		}
		
		float cellWidth = (surface.width - 12) / element[0].length;
		float cellHeight = (surface.height - 18) / element.length;
		
		int[] clickInGrid = coorToGrid(mouseX, mouseY);
		Element clickedElement = getElement(clickInGrid[0], clickInGrid[1]);

		//System.out.println("IMPORTANT : " + clickedElement.toString());
		
		 
		if((clickInGrid[0] == circeHouse.getXCoor() || clickInGrid[0] == circeHouse.getXCoor()+1) && (clickInGrid[1] == circeHouse.getYCoor() || clickInGrid[1] == circeHouse.getYCoor() + 1)) {
			System.out.println("at circe's");
			surface.switchScreen(0);
		}
		
		else if(element[clickInGrid[0]][clickInGrid[1]] instanceof GardenLand){//clickedElement != null && clickedElement instanceof GardenLand){//element[clickInGrid[0]][clickInGrid[1]] instanceof GardenLand) {
			System.out.println("IT IS GARDEN");
			GardenLand e = (GardenLand)(element[clickInGrid[0]][clickInGrid[1]]);
			if(e.isAlive()) {
				System.out.println(e.getLifeState());
				System.out.println("HERE To HARVEST");
				circe.harvest(e);
			}
			else {
				System.out.println("let's add a plant");
				gardenElementSelected = true;
				selectedSpot[0] = clickInGrid[0];
				selectedSpot[1] = clickInGrid[1];
			}
			
		}
		else if(element[clickInGrid[0]][clickInGrid[1]] instanceof Pond) {
			System.out.println("ADDING WATER TO INVENTORY");
			circe.addOnInventory(new Holdable(9));
		}
		else if(mouseX >= infoButton.x && mouseX <= infoButton.x + infoButton.width && mouseY >= infoButton.y && mouseY <= infoButton.y + infoButton.height) {
			System.out.println("info");
			//list.dispose();
			surface.switchScreen(1);
		}
		else if(clickedElement != null && clickedElement instanceof Land){
			//System.out.println("here");
			System.out.println("LAND HO");
			landElementSelected = true;
			selectedSpot[0] = clickInGrid[0];
			selectedSpot[1] = clickInGrid[1];
		}
		
	}
	
	
	
	/**
	 * In the event that a key is pressed, takes in the key that is pressed and executes the correct response
	 * - if the key is W,A, S, D, then moves Circe in the corresponding direction
	 * - if the key is enter and Circe is at her house, then switches the DrawingSurface screen
	 * @param key the key pressed
	 */
	public void processKey(char key) {
		for(int i = 0; i<6; i++) {
			if(circe.getInventory()[i] == null) {
				break;
			}
			System.out.println(circe.getInventory()[i].getName());
		}
		System.out.println();
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
		
		if(key == '\n') {
			float cellWidth = (surface.width - 11) / element[0].length;
			float cellHeight = (surface.height - 17) / element.length;
			
			Rectangle2D.Double cHouseRect = new Rectangle2D.Double(circeHouse.getXCoor() * cellWidth, circeHouse.getYCoor() * cellHeight, 2*cellWidth, 2*cellHeight);
			if(circe.intersects(cHouseRect)) {
				System.out.println("switching?");
				surface.switchScreen(0);
			}
			
			//checking if she is near the malicious visitor
			for(int i = 0; i<creatures.size(); i++) {
				if(creatures.get(i) instanceof MaliciousVisitor) {
					MaliciousVisitor mv = (MaliciousVisitor)(creatures.get(i));
					boolean isNear = Math.abs(mv.getXGrid() - circe.getXGrid()) < 3 && Math.abs(mv.getYGrid() - circe.getYGrid()) < 3;
					//near enough, has food and wine
					int bread = circe.inventoryContains(Holdable.BREAD);
					int potion = circe.inventoryContains(Holdable.POTION);
					if(isNear && bread != -1 && potion != -1) {
						circe.turnPig(mv);
						circe.removeFromInventory(bread);
						circe.removeFromInventory(potion);
						break; //can only turn one at a time
					}
					
				}
			}
		}
		
	}
	
	
//	private boolean circeInventoryContains(int type) { //should be in circe
//		for(int i = 0; i<circe.getInventory().length; i++) {
//			if(circe.getInventory()[i] != null && circe.getInventory()[i].getType() == type) {
//				return true;
//			}
//		}
//		
//		return false;
//		
//	}
	
	/**
	 * Returns the indexes of the location in the 2DArray of elements, given the x and y coordinates on the screen, 
	 * @param xCoor x-coordinate on the screen
	 * @param yCoor y-coordinate on the screen
	 * @return int array with the the specific indexes in element[][] at which the given coordinate point occurs.
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
	 * @return Element at elements[y][x]
	 */
	public Element getElement(int x, int y) {
		return element[y][x];
	}
	
	
	/**
	 * Puts the given element into the given location in the 2D array of elements
	 * @param e the element to be places
	 * @param x the x-coordinate of the element in the array
	 * @param y the y-coordinate of the element in the array
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
	public Element[][] getElements(){
		return element;
	}
	
	/**
	 * Returns the DrawingSurface on which this Island is displayed
	 * @return
	 */
	public DrawingSurface getSurface() {
		return surface;
	}
	
	//have to change to not include water outside island
	public boolean isValid(int rowLoc, int colLoc) {
        if(element[colLoc][rowLoc] == null)
        	return false;
        return true;
    }
	
	
	/**
	 * Changes the element at the given location to the Element type chosen from the drop down list
	 * @param list the list of options in the drop-down menu
	 * @param event
	 */
	public void handleElementChange(GDropList list, GEvent event) {
		String text = list.getSelectedText();
		if(text.equals("Choose"))
			return;
		
		if(text.equals("House")) {
			int x = selectedSpot[0];
			int y = selectedSpot[1];
			element[x][y] = new House(this, houseImage, x, y, "norm");
		}
		else if(text.equals("Garden")) {
			int x = selectedSpot[0];
			int y = selectedSpot[1];
			element[x][y] = new GardenLand(this, allGarden, x, y);
		}
		else if(text.equals("Pig pen")) {
			int x = selectedSpot[0];
			int y = selectedSpot[1];
			element[x][y] = new PigPen(this, x, y);
		}
		else if(text.equals("Land")) {
			int x = selectedSpot[0];
			int y = selectedSpot[1];
			element[x][y] = new Land(this, x, y);
		}
		
		dropDone = true;
	}
	
	/**
	 * Changes the growing plant at the garden land selected by the user to the plant option selected by the user
	 * @param list the list of options in the drop-down menu
	 * @param event
	 */
	public void handlePlantChange(GDropList list, GEvent event) {
		System.out.println("plant change handle");
		//mouseClickEnabled = false;
		String text = list.getSelectedText();
		list.setVisible(false);
		int x = selectedSpot[0];
		int y = selectedSpot[1];
		
		if(text.equals("Grape")) {
			GardenLand gl = (GardenLand)element[x][y];
			gl.plant("grape");
		}
		else if(text.equals("Barley")) {
			GardenLand gl = (GardenLand)element[x][y];
			gl.plant("barley");
		}
		else if(text.equals("Marathos")) {
			GardenLand gl = (GardenLand)element[x][y];
			gl.plant("marathos");
		}
		else if(text.equals("Anithos")) {
			GardenLand gl = (GardenLand)element[x][y];
			gl.plant("barley");
		}

		dropDone = true;
	}
	
	
	private void setImages() {
		nymphImage = surface.loadImage("Files/NymphFrontStand.png");
		malImage = surface.loadImage("Files/MaliciousFrontStand.png");
		pigImage = surface.loadImage("Files/PigFrontStand.png");
		gardenEmpty = surface.loadImage("Files/GardenEmpty.png");
		gardenA1 = surface.loadImage("Files/AnithosBud.png"); 
		gardenA2 = surface.loadImage("Files/AnithosSproud.png"); 
		gardenA3 = surface.loadImage("Files/AnithosGrown.png");
		gardenA4 = surface.loadImage("Files/AnithosDead.png");
		houseImage = surface.loadImage("Files/HouseVisitor.png");
		penImage = surface.loadImage("Files/GardenEmpty.png");
		allGarden = new PImage[5];
		allGarden[0] = gardenEmpty;
		allGarden[1] = gardenA1;
		allGarden[2] = gardenA2;
		allGarden[3] =  gardenA3;
		allGarden[4] =  gardenA4;
		
	}
	
	private void setupIsland(int hX, int hY) {
		setImages(); // creates all the required images
		
		circeHouse.setImage(houseImage);
		circeHouse.putOnIsland(this);
		
		circe.putOnIsland(this);
		Nymph c1 = new Nymph(nymphImage, 450, 400);
		MaliciousVisitor c2 = new MaliciousVisitor(malImage, 300, 100);
		Pig c3 = new Pig(pigImage, 200, 250);
		c1.putOnIsland(this);
		c2.putOnIsland(this);
		c3.putOnIsland(this);
		
		
		circe.addOnInventory(new Holdable(Holdable.BREAD));
		circe.addOnInventory(new Holdable(Holdable.POTION));
		//circe.addOnInventory(new Holdable(Holdable.ANITHOS_SEED));
		//circe.addOnInventory(new Holdable(Holdable.WATER));
		//circe.getInventory()[1] = new Holdable(Holdable.BREAD);
		
		
		//fill with water
		for(int i = 7; i<=8; i++) {
			for(int j = 7; j<= 10; j++) {
				element[j][i] = new Pond(this, j, i);
				element[j][i].setIsInGrid(true);
			}
		}
		
//		element[8][7] = new Pond(this, 0, 8);
//		element[0][8].setIsInGrid(true);
		
		
		//fill with land
		element[0][8] = new Land(this, 0, 8);
		element[0][8].setIsInGrid(true);
		element[1][4] = new Land(this, 1, 4);
		element[1][4].setIsInGrid(true);
		element[1][5] = new Land(this, 1, 5);
		element[1][5].setIsInGrid(true);
		
		for(int i = 8; i<= 11; i++) {
			Land l = new Land(this, 1, i);
			element[1][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 3; i<=5; i++) {
			Land l = new Land(this, 2, i);
			element[2][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 7; i<=11; i++) {
			Land l = new Land(this, 2, i);
			element[2][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 3; i<=12; i++) {
			Land l = new Land(this, 3, i);
			element[3][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 3; i<=13; i++) {
			Land l = new Land(this, 4, i);
			element[4][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 2; i<=13; i++) {
			Land l = new Land(this, 5, i);
			element[5][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 6; i<=9; i++) {
			for(int j = 1; j<=6; j++) {
				Land l = new Land(this, i, j);
				element[i][j] = l;
				l.setIsInGrid(true);
			}
		}
		for(int i = 6; i<=7; i++) {
			for(int j = 7; j<=13; j++) {
				Land l = new Land(this, i, j);
				element[i][j] = l;
				l.setIsInGrid(true);
			}
		}
		for(int i = 9; i<=13; i++) {
			Land l = new Land(this, 8, i);
			element[8][i] = l;
			l.setIsInGrid(true);
		}
		for(int i = 9; i<=12; i++) {
			Land l = new Land(this, 9, i);
			element[9][i] = l;
			l.setIsInGrid(true);
		}
		
		element[10][3] = new Land(this, 10, 3);
		element[10][3].setIsInGrid(true);
		
		element[10][4] = new Land(this, 10, 4);
		element[10][4].setIsInGrid(true);
		
		element[10][6] = new Land(this, 10, 6);
		element[10][6].setIsInGrid(true);
		
		element[10][9] = new Land(this, 10, 9);
		element[10][9].setIsInGrid(true);
		
		element[11][6] = new Land(this, 11, 6);
		element[11][6].setIsInGrid(true);
		element[11][9] = new Land(this, 11, 9);
		element[11][9].setIsInGrid(true);
		
		for(int i = 6; i<=10; i++) {
			Land l = new Land(this, 11, i);
			element[12][i] = l;
			l.setIsInGrid(true);
		}
		
		element[13][7] = new Land(this, 13, 7);
		element[13][7].setIsInGrid(true);
		element[13][8] = new Land(this, 13, 8);
		element[13][8].setIsInGrid(true);
	}
	
	/**
	 * Returns the PImage of the given type of element or creature.
	 * The types include: "pig"; "nymph"; "malicious visitor"
	 * @param type the type of element/creature whose image is to be returned
	 * @return the PImage of the given type
	 * @pre types must be one of the specified types above
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
}
