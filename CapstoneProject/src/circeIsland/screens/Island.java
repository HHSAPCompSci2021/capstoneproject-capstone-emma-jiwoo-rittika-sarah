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

public class Island extends Screen{

	private Element[][] element;
	private ArrayList<Creature> creatures;
	private Circe circe;
	//private int currentTime;
	private House circeHouse;
	private Rectangle infoButton;
	private boolean elementSelected, mouseClickEnabled, showInfo;
	private int[] selectedSpot;
	//GButton brewer, recipe, exit;
	
	
	/**
	 * Creates a new island in the provided surface, given the location of Circe and her house.
	 * @param surface the PApplet on which this island may be drawn
	 * @param circeX the x coordinate of Circe
	 * @param circeY the y coordinate of Circe
	 * @param houseX the x coordinate of Circe's house
	 * @param houseY the y coordinate of Circe's house
	 * @param houseSize the size (length of the side) of Circe's house
	 */
	public Island(DrawingSurface surface, PImage cImage, int circeX, int circeY, int houseX, int houseY) {
		super(800,600, surface);
		
		element = new Element[15][15];
		circeHouse = new House(this, houseX, houseX, "circe");
		//PImage cImage = super.surface.loadImage("Images\tempImage.png");
		circe = new Circe(cImage, circeX, circeY);
		creatures = new ArrayList<Creature>();
		infoButton = new Rectangle(super.WIDTH+40, 20, super.WIDTH / element.length + 30, super.HEIGHT /element[0].length - 10);
		
		elementSelected = false;
		mouseClickEnabled = true;
		showInfo = false;
		
		selectedSpot = new int[2];
		//creatures.add(circe);
		fillElements(circeX, circeY, houseX, houseY);
	}
	
	
	private void fillElements(int cX, int cY, int hX, int hY) {
		circeHouse.putOnIsland(this);
		circe.putOnIsland(this);
		
		new GardenLand(this, 2, 2).putOnIsland(this, 2, 2);
		element[2][3] = new GardenLand(this, 3, 2);
		element[3][2] = new GardenLand(this, 2, 3);
		element[3][3] = new GardenLand(this, 3, 3);
		
		
		for(int i = 1; i<element.length - 1; i++) {
			for(int j = 1; j<element.length - 1; j++) {
				if(element[i][j] == null) {  // && i != cX && j!= cY) {
					Land l = new Land(this, i, j);
					element[i][j] = l;
					l.setIsInGrid(true);
				}
			}
		}
		
	}


	/**
	 * Draws the island with all its elements and creatures to the screen.
	 * @post The properties of the provided PApplet provided will be modified
	 */
	public void draw() {
		
		float cellWidth = (surface.width - 11) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
//		float cellWidth = ((surface.width - 11) - 150) / element[0].length;
//		float cellHeight = (surface.height - 17) / element.length;
		surface.strokeWeight(0);
		//surface.stroke(173, 227, 154);
		for(int i = 0; i<element.length; i++) { //x
			for(int j = 0; j<element[0].length; j++) { //y
				if(element[i][j] == null) {
					surface.fill(15, 163, 189); //the ocean around the island
					//surface.stroke(22,  22,  222);;
					surface.rect(10 + (j * cellWidth), 10 + (i*cellHeight), cellWidth, cellHeight);
				}
				else {
					element[i][j].draw(surface, cellWidth, cellHeight);
				}
			}
		}
		
		surface.fill(222, 139, 62);
		surface.rect(infoButton.x,  infoButton.y,  infoButton.width,  infoButton.height); 
		float w = surface.textWidth("info");
		surface.textSize(20);
		surface.fill(225);
		surface.text("info", infoButton.x+infoButton.width/2-w/2, infoButton.y+infoButton.height/2);
		
		
		if(elementSelected) {
			elementSelected = false;
			mouseClickEnabled = false;
			G4P.setGlobalColorScheme(4);
			GDropList list = new GDropList(surface, 13 + (selectedSpot[0] * cellWidth), 13 + (selectedSpot[1]*cellHeight), cellWidth-6, cellHeight*4 - 10, 0);  
			list.setItems(new String[] {"Choose", "House", "Garden", "None"}, 0);
			list.addEventHandler(this,  "handleElementChange");
		}
		
		Nymph c1 = new Nymph(450, 700);
		c1.putOnIsland(this);
		c1.draw(surface);
//		creatures.add(new Nymph(450, 700));
//		creatures.add(new Nymph(200, 200));
//		for(Creature c : creatures) {
//			c.putOnIsland(this);
//			c.draw(surface);
//		}
		circe.draw(surface);
	}
	
	
	public void processMouseClick(int mouseX, int mouseY) {
		//System.out.println("Processing click");
		if(!mouseClickEnabled) {
			//System.out.println("ENETERED FALSE CLICK");
			mouseClickEnabled = true;
			return;
		}
		
		float cellWidth = (surface.width - 11) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
		int[] clickInGrid = coorToGrid(mouseX, mouseY);
		Element clickedElement = getElement(clickInGrid[0], clickInGrid[1]);
		
		if((clickInGrid[0] == circeHouse.getXCoor() || clickInGrid[0] == circeHouse.getXCoor()+1) && (clickInGrid[1] == circeHouse.getYCoor() || clickInGrid[1] == circeHouse.getYCoor() + 1)) {
			System.out.println("at circe's");
			surface.switchScreen();
		}
		else if(mouseX >= infoButton.x && mouseX <= infoButton.x + infoButton.width && mouseY >= infoButton.y && mouseY <= infoButton.y + infoButton.height) {
			System.out.println("info");
		}
		else if(clickedElement != null && clickedElement instanceof Land){
			System.out.println("here");
			elementSelected = true;
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
			System.out.println("ww");
			circe.moveY(Creature.UP);
		}
		if(key == 'a' || key == 'A') {
			System.out.println("aa");
			circe.moveX(Creature.LEFT);
		}
		if(key == 's' || key == 'S') {
			System.out.println("ss");
			circe.moveY(Creature.DOWN);
		}
		if(key == 'd' || key == 'D') {
			System.out.println("dd");
			circe.moveX(Creature.RIGHT);
		}
		
		if(key == '\n') {
			System.out.println("ENTER");
			float cellWidth = (surface.width - 11) / element[0].length;
			float cellHeight = (surface.height - 17) / element.length;
			
			Rectangle2D.Double cHouseRect = new Rectangle2D.Double(circeHouse.getXCoor() * cellWidth, circeHouse.getYCoor() * cellHeight, 2*cellWidth, 2*cellHeight);
			if(circe.intersects(cHouseRect)) {
				System.out.println("switching?");
				surface.switchScreen();
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
		float cellWidth = (surface.width - 11) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
		int xGrid = (int)(xCoor/cellWidth);
		int yGrid = (int)(yCoor/cellHeight);
		
		int[] grid = {xGrid, yGrid};
//		System.out.println(Arrays.toString(grid));
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
	
	public void keepTime() {
		
	}
	
	public int getHeight() {
		return super.HEIGHT;
	}
	
	public int getWidth() {
		return super.WIDTH;
	}
	
	public Circe getCirce() {
		return circe;
	}
	
	public House getCirceHouse() {
		return circeHouse;
	}
	
	//have to change to not include water outside island
	public boolean isValid(int rowLoc, int colLoc)
    {
        return 0 <= rowLoc && rowLoc < 10 && 0 <= colLoc && colLoc < 10;
    }
	
	
	public void handleElementChange(GDropList list, GEvent event) {
		mouseClickEnabled = false;
		System.out.println(mouseClickEnabled);
		String text = list.getSelectedText();
		System.out.println(text);
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
			System.out.println("Executing land ");
			//mouseClickEnabled = true;
		}

	}
	
	
	//to prevent the text from coming
	
}
