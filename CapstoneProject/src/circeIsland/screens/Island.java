package circeIsland.screens;

import java.awt.Rectangle;
import java.util.ArrayList;
import circeIsland.main.DrawingSurface;
import circeIsland.creatures.*;
import circeIsland.elements.Element;
import circeIsland.main.DrawingSurface;

public class Island extends Screen{

	private DrawingSurface surface;
	private Element[][] element;
	private ArrayList<Creature> creatures;
	private Circe circe;
	private int currentTime;
	
	
	/**
	 * Creates a new island in the provided surface, given the loation of Circe and her house.
	 * @param surface the PApplet on which this island may be drawn
	 * @param circeX the x coordinate of Circe
	 * @param circeY the y coordinate of Circe
	 * @param houseX the x coordinate of Circe's house
	 * @param houseY the y coordinate of Circe's house
	 * @param houseSize the size (length of the side) of Circe's house
	 */
	public Island(DrawingSurface surface, int circeX, int circeY, int houseX, int houseY, int houseSize) {
		super(800,600);
		this.surface = surface;
		element = new Element[10][10];
		fillElements(circeX, circeY, houseX, houseY, houseSize);
	}
	
	
	private void fillElements(int cX, int cY, int hX, int hY, int hSize) {
		circe = new Circe(cX, cY);
		//element[cX][cY] = circe; -- won't work, cause circe isn't an element
		House circeHouse = new House(hX, hY);
		
		for(int i = 0; i<hSize; i++) {
			it(j = 0; j<hSize; j++){
				
			}
		}
		
		
	}


	public void draw() {
		float cellWidth = (surface.width - 11) / element[0].length;
		float cellHeight = (surface.height - 17) / element.length;
		
		for(int i = 0; i<element.length; i++) {
			for(int j = 0; j<element[0].length; j++) {
				if(element[i][j] != null) {
					surface.fill(255);
					surface.stroke(22,  22,  222);;
					surface.rect(10 + (j * cellWidth), 10 + (i*cellHeight), cellWidth, cellHeight);
					
				}
				else {
					surface.fill(128, 128, 128);
					surface.rect(10 + (j * cellWidth), 10 + (i*cellHeight), cellWidth, cellHeight);
				}
			}
		}
	}
	
	
	
	
	
	public void setElement() {
		
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
	
	
}
