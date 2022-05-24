package circeIsland.elements;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;
import processing.core.PImage;

/**
 * This class represents the House Element, which are either Nymph Houses or Circe Houses.
 * @author Emma Yu
 */
public class House extends Element{
	
	private String type; //Either "circe" or "norm"
	private int xSize; //circe: 3x3 Normal: 2x2 
	private int ySize;
	private boolean isTaken;

	/**
	 * Creates a new House
	 * @param i 
	 * @param p The image of the house
	 * @param xInput
	 * @param yInput
	 * @param type Type of House, either "circe" or "norm"
	 * @pre type must be either "circe" or "norm"
	 */
	public House(Island i,  PImage p, int xInput, int yInput, String type) {
		super(i, p, xInput, yInput);
		setStandable(false);
		this.type = type;
		if (type == "circe") {
			xSize = 2;
			ySize = 2;
			isTaken = true;
		}
		else
			isTaken = false;
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		if (type == "circe") {
			surface.push();
			if(getIsInGrid()) {
				if (getImage() != null) {
					double rateX = getIsland().getWidth()/800;
					double rateY = getIsland().getHeight()/600;
					float x = cellWidth*getXCoor();
					float y = cellHeight*getYCoor();
					x *= rateX;
					y *= rateY;
					surface.image(getImage(),(float)x + 6,(float)y + 9,(float)(2*cellWidth*rateX),(float)(2*cellHeight*rateY));
				}
			}
//			surface.fill(191, 128, 111);
//			surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth*2, cellHeight*2);
//			surface.fill(0);
//			surface.text("cHouse", 6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight)+cellHeight);
			surface.pop();
		}
		if (type == "norm") {
			if (getImage() != null) {
				double rateX = getIsland().getWidth()/800;
				double rateY = getIsland().getHeight()/600;
				float x = cellWidth*getXCoor();
				float y = cellHeight*getYCoor();
				x *= rateX;
				y *= rateY;
				surface.image(getImage(),(float)x + 6,(float)y + 9,(float)(cellWidth*rateX),(float)(cellHeight*rateY));
			}
		}
		
	}
	
	/**
	 * Puts the House on the given Island
	 * @param i
	 */
	public void putOnIsland(Island i) { //different b/c houses take up more than 1 grid
		if (type == "circe") {
			for (int j = getYCoor(); j<getYCoor()+ySize; j++) {
				for (int k = getXCoor(); k<getXCoor()+xSize; k++) {
					i.setElement(this,  j,  k);
				}
			}
		}
		setIsInGrid(true);
	}
	
	public void removeFromIsland(Island i) {
		
		setIsInGrid(false);
	}
	
	public String toString() {
		return super.toString() + " type: "+type;
	}
	
	/**
	 * @return Whether or not the House is taken
	 */
	public boolean getTaken() {
		return isTaken;
	}
	
	public void setTaken(boolean b) {
		isTaken = b;
	}

}
