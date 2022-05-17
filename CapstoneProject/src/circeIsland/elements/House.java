package circeIsland.elements;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class House extends Element{
	
	private String type; //Either "circe" or "norm"
	private int xSize; //circe: 3x3 Normal: 2x2 
	private int ySize;

	
	public House(Island i, int xInput, int yInput, String type) {
		super(i, xInput, yInput);
		setStandable(false);
		this.type = type;
		if (type == "circe") {
			xSize = 2;
			ySize = 2;
		}
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		if (type == "circe") {
			surface.push();
			surface.fill(191, 128, 111);
			surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth*2, cellHeight*2);
			surface.fill(0);
			surface.text("cHouse", 6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight)+cellHeight);
			surface.pop();
		}
		if (type == "norm") {
			surface.push();
			surface.fill(191, 128, 111);
			surface.rect(6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight), cellWidth, cellHeight);
			surface.fill(0);
			surface.text("house", 6 + (getXCoor() * cellWidth), 9 + (getYCoor()*cellHeight)+cellHeight);
			surface.pop();
		}
		
	}
	
	public void putOnIsland(Island i) { //different b/c houses take up more than 1 grid
		if (type == "circe") {
			for (int j = getYCoor(); j<getYCoor()+ySize; j++) {
				for (int k = getXCoor(); k<getXCoor()+xSize; k++) {
					i.setElement(this,  k,  j);
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

}
