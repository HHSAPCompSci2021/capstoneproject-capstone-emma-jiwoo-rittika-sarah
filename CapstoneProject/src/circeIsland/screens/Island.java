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
	private Rectangle infoButton;
	private boolean landElementSelected, mouseClickEnabled, gardenElementSelected;
	private int[] selectedSpot;
	private PImage islandImage;	
	
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
		
		element = new Element[15][15];
		circeHouse = new House(this, houseX, houseX, "circe");
		circe = c;
		creatures = new ArrayList<Creature>();
		infoButton = new Rectangle(super.WIDTH-80, 20, super.WIDTH / element.length -10, super.HEIGHT /element[0].length - 10);
		
		landElementSelected = false;
		gardenElementSelected = false;
		mouseClickEnabled = true;
		selectedSpot = new int[2];
		
		fillElements(houseX, houseY);
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
		
		surface.strokeWeight(0);
		surface.image(islandImage, 0, 0, surface.width, surface.height);
		
		//drawing grid of elements
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
			GDropList list = new GDropList(surface, (borderX/2) + (selectedSpot[0] * cellWidth), (borderY/2) + (selectedSpot[1]*cellHeight), cellWidth, cellHeight*3, 0);  
			list.setItems(new String[] {"Choose", "House", "Garden", "None"}, 0);
			list.addEventHandler(this,  "handleElementChange");
		}
		if(gardenElementSelected) {
			gardenElementSelected = false;
			mouseClickEnabled = false;
			G4P.setGlobalColorScheme(4);
			GDropList list = new GDropList(surface, (borderX/2) + (selectedSpot[0] * cellWidth), (borderY/2)  + (selectedSpot[1]*cellHeight), cellWidth, cellHeight*3, 0);  
			list.setItems(new String[] {"Grape", "Barley", "Marathos", "Anithos"}, 0);
			list.addEventHandler(this,  "handlePlantChange");
		}
		
		
		surface.textSize(10);
		
		Nymph c1 = new Nymph(450, 400);
		Nymph c2 = new Nymph(300, 100);
		c1.putOnIsland(this);
		c2.putOnIsland(this);
		for(Creature c : creatures) {
			//c.act();
			c.draw(surface);
			//c.act();
		}
		circe.draw(surface);
		
		checkNewVisitors();
		
	}
	
	private void checkNewVisitors() {
		int days = super.getDays();
		
		if(days % 4 == 0 && days != 0) {
			Nymph c = new Nymph(760, 350);
			c.putOnIsland(this);
		}
		else if(days % 7 == 0 && days != 0) {
			MaliciousVisitor c = new MaliciousVisitor(760, 350);
			c.putOnIsland(this);
		}
		
	}
	
	
	
	
	
	
	public void processMouseClick(int mouseX, int mouseY) {
		//System.out.println("Processing click");
		if(!mouseClickEnabled) {
			//System.out.println("ENETERED FALSE CLICK");
			mouseClickEnabled = true;
			return;
		}
		
		float cellWidth = (surface.width - 12) / element[0].length;
		float cellHeight = (surface.height - 18) / element.length;
		
		int[] clickInGrid = coorToGrid(mouseX, mouseY);
		Element clickedElement = getElement(clickInGrid[0], clickInGrid[1]);

		//System.out.println("IMPORTANT : " + clickedElement.toString());
		
//		if((clickInGrid[0] == circeHouse.getXCoor() || clickInGrid[0] == circeHouse.getXCoor()+1) && (clickInGrid[1] == circeHouse.getYCoor() || clickInGrid[1] == circeHouse.getYCoor() + 1)) {
//			System.out.println("at circe's");
//			surface.switchScreen(0);
//		}
		if(element[clickInGrid[0]][clickInGrid[1]] instanceof GardenLand) {
			GardenLand e = (GardenLand)(element[clickInGrid[0]][clickInGrid[1]]);
			if(e.isAlive()) {
				circe.harvest();
			}
			else {
				gardenElementSelected = true;
				selectedSpot[0] = clickInGrid[0];
				selectedSpot[1] = clickInGrid[1];
			}
			
		}
		else if(mouseX >= infoButton.x && mouseX <= infoButton.x + infoButton.width && mouseY >= infoButton.y && mouseY <= infoButton.y + infoButton.height) {
			//System.out.println("info");
			surface.switchScreen(1);
		}
		else if(clickedElement != null && clickedElement instanceof Land){
			//System.out.println("here");
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
		}
		
	}
	
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
	
	public void setElement(Element e, int x, int y) {
		element[y][x] = e;
	}
	
	public void addCreature(Creature c) {
		creatures.add(c);
	}
	
	public void removeCreature(Creature c) {
		for(int i = 0; i<creatures.size(); i++) {
			if(creatures.get(i).equals(c)) {
				creatures.remove(i);
				break;
			}
		}
	}
	
	public Circe getCirce() {
		return circe;
	}
	
	public House getCirceHouse() {
		return circeHouse;
	}
	
	public Element[][] getElements(){
		return element;
	}
	
	//have to change to not include water outside island
	public boolean isValid(int rowLoc, int colLoc) {
        if(element[colLoc][rowLoc] == null)
        	return false;
        return true;
    }
	
	
	public void handleElementChange(GDropList list, GEvent event) {
		mouseClickEnabled = false;
		String text = list.getSelectedText();
		list.setVisible(false);
		if(text.equals("House")) {
			int x = selectedSpot[0];
			int y = selectedSpot[1];
			element[x][y] = new House(this, x, y, "norm");
		}
		else if(text.equals("Garden")) {
			int x = selectedSpot[0];
			int y = selectedSpot[1];
			element[x][y] = new GardenLand(this, x, y);
		}
	}
	
	
	public void handlePlantChange(GDropList list, GEvent event) {
		mouseClickEnabled = false;
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

	}
	
	
	
	private void fillElements(int hX, int hY) {
		circeHouse.putOnIsland(this);
		circe.putOnIsland(this);
		
//		new GardenLand(this, 12, 7).putOnIsland(this, 12, 7);
//		new GardenLand(this, 10, 8).putOnIsland(this, 10, 9);
//		new GardenLand(this, 10, 7).putOnIsland(this, 10, 7);
//		new GardenLand(this, 12, 8).putOnIsland(this, 12, 8);
////		element[15][7] = new GardenLand(this, 3, 2);
////		element[3][2] = new GardenLand(this, 2, 3);
////		element[3][3] = new GardenLand(this, 3, 3);
//		
//		
//		for(int i = 1; i<element.length - 1; i++) {
//			for(int j = 1; j<element.length - 1; j++) {
//				if(element[i][j] == null) {  // && i != cX && j!= cY) {
//					Land l = new Land(this, i, j);
//					element[i][j] = l;
//					l.setIsInGrid(true);
//				}
//			}
//		}
		
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
	
	
}
