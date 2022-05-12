package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import circeIsland.main.DrawingSurface;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import circeIsland.creatures.*;
import circeIsland.elements.Element;
import circeIsland.elements.*;
import circeIsland.main.DrawingSurface;

public class Island extends Screen{

	private DrawingSurface surface;
	private Element[][] element;
	private ArrayList<Creature> creatures;
	private Circe circe;
	//private int currentTime;
	private House circeHouse;
	//private GButton b1, b2, b3;
	
	private boolean elementSelected;
	private int[] selectedSpot;
	
	
	/**
	 * Creates a new island in the provided surface, given the location of Circe and her house.
	 * @param surface the PApplet on which this island may be drawn
	 * @param circeX the x coordinate of Circe
	 * @param circeY the y coordinate of Circe
	 * @param houseX the x coordinate of Circe's house
	 * @param houseY the y coordinate of Circe's house
	 * @param houseSize the size (length of the side) of Circe's house
	 */
	public Island(DrawingSurface surface, int circeX, int circeY, int houseX, int houseY) {
		super(800,600);
		this.surface = surface;
		element = new Element[15][15];
		circeHouse = new House(this, houseX, houseX, "circe");
		circe = new Circe(circeX, circeY);
		elementSelected = false;
		selectedSpot = new int[2];
		//circe = new Circe(circeX, circeY);
		//creatures.add(circe);
		fillElements(circeX, circeY, houseX, houseY);
	}
	
	
	private void fillElements(int cX, int cY, int hX, int hY) {
		
		//element[cX][cY] = circe; -- won't work, cause circe isn't an element
		
		element[hY][hX] = circeHouse;
		
		element[2][2] = new GardenLand(this, 2, 2);
		element[2][3] = new GardenLand(this, 3, 2);
		element[3][2] = new GardenLand(this, 2, 3);
		element[3][3] = new GardenLand(this, 3, 3);
		
		
		for(int i = 1; i<element.length - 1; i++) {
			for(int j = 1; j<element.length - 1; j++) {
				if(element[i][j] == null) {  // && i != cX && j!= cY) {
					element[i][j] = new Land(this, i, j); //this might also be backwards
				}
			}
		}
		
	}


	public void draw() {
//		b1 = new GButton(surface, 650, 60, 100, 40, "House");
//		b2 = new GButton(surface, 650, 120, 100, 40, "Garden Plot");
//		b3 = new GButton(surface, 650, 180, 100, 40, "None");
//		
//		b1.addEventHandler(this,  "handleElementChange");
//		b2.addEventHandler(this,  "handleElementChange");
//		b3.addEventHandler(this,  "handleElementChange");
		
		
		
		float cellWidth = (surface.width - 11) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
//		float cellWidth = ((surface.width - 11) - 150) / element[0].length;
//		float cellHeight = (surface.height - 17) / element.length;
		
		surface.fill(24, 24, 24);
		surface.rect(10 + (7 * cellWidth), 10 + (10*cellHeight), cellWidth, cellHeight);
		
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
		
		circe.draw(surface);
		
		
		//menu
//		surface.fill(235, 213, 190);
//		surface.rect(20 + (element.length * cellWidth),  10, 140, surface.height - 50);
		
	}
	
	
	public void processMouseClick(int mouseX, int mouseY) {
		float cellWidth = (surface.width - 11) / element[0].length;
		//float cellWidth = ((surface.width - 11) - 150) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
		int[] clickInGrid = coorToGrid(mouseX, mouseY);
		
		
		
		System.out.println(circeHouse.getXCoor() + " " + circeHouse.getYCoor());
		
		Element clickedElement = getElement(clickInGrid[0], clickInGrid[1]);
		
		if(clickInGrid[0] == circeHouse.getXCoor() && clickInGrid[1] == circeHouse.getYCoor()) {
			System.out.println("here");
			surface.switchScreen();
		}
		else if(clickedElement != null && clickedElement instanceof Land){
			System.out.println("here");
			elementSelected = true;
			selectedSpot[0] = clickInGrid[0];
			selectedSpot[1] = clickInGrid[1];
		}
	}
	
	
	public void processKey(char key) {
		if(key == 'w' || key == 'W' || key == 'a' || key == 'A' || key == 's' || key == 'S' || key == 'd' || key == 'D') {
			//circe.move(key);
		}
		
	}
	
	
	public int[] coorToGrid(double xCoor, double yCoor) {
		float cellWidth = (surface.width - 11) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
		int xGrid = (int)(xCoor/cellWidth);
		int yGrid = (int)(yCoor/cellHeight);
		
		int[] grid = {xGrid, yGrid};
//		System.out.println(Arrays.toString(grid));
		return grid;
	}
	
	public Element getElement(int x, int y) {
		return element[y][x];
	}
	
	public void setElement(Element e, int x, int y) {
		element[y][x] = e;
	}
	
	public void keepTime() {
		
	}
	
	public int getHeight() {
		return super.HEIGHT;
	}
	
	public int getWidth() {
		return super.WIDTH;
	}
	
	//have to change to not include water outside island
	public boolean isValid(int rowLoc, int colLoc)
    {
        return 0 <= rowLoc && rowLoc < 10 && 0 <= colLoc && colLoc < 10;
    }
	
	
	
	
	
//	public void handleButtonEvents(GButton button, GEvent event) { 
//		System.out.println("handled!!");
//		if(elementSelected) {
//			if(button == b1 && event == GEvent.CLICKED) {
//				setElement(new House(this, selectedSpot[0], selectedSpot[1], "norm"), selectedSpot[0], selectedSpot[1]);
//			}
//			else if(button == b2 && event == GEvent.CLICKED) {
//				//should be garden
//				setElement(new GardenLand(this, selectedSpot[0], selectedSpot[1]), selectedSpot[0], selectedSpot[1]);
//			}
//			elementSelected = false;
//		}
//		
//	}
	
//	public void handleButtonEvents(GButton button, GEvent event) { /* code */ }
//	
//	public void handleElementChange(GButton button, GEvent event) {
//		
//		System.out.println("handled!!");
//		System.out.println(elementSelected);
//		System.out.println(Arrays.toString(selectedSpot));
//		//element[0][0] = new Land(this, 0, 0);
//		if(elementSelected) {
//			if(button == b1) {// && event == GEvent.CLICKED) {
//				element[0][0] = new Land(this, 0, 0);
//				setElement(new House(this, selectedSpot[0], selectedSpot[1], "norm"), selectedSpot[0], selectedSpot[1]);
//			}
//			else if(button == b2) {// && event == GEvent.CLICKED) {
//				element[0][0] = new Land(this, 1, 0);
//				//should be garden
//				setElement(new GardenLand(this, selectedSpot[0], selectedSpot[1]), selectedSpot[0], selectedSpot[1]);
//			}
//			elementSelected = false;
//		}
//	}
	
}
