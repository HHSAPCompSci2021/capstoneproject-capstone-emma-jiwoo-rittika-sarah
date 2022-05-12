package circeIsland.elements;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class House extends Element{
	
	private String type; //Either "circe" or "norm"
	private int xSize;
	private int ySize;

	
	public House(Island i, int xInput, int yInput, String type) {
		super(i, xInput, yInput);
		setStandable(false);
		this.type = type;
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		if (type == "circe") {
			surface.push();
			surface.fill(191, 128, 111);
			surface.rect(10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight), cellWidth, cellHeight);
			surface.fill(0);
			surface.text("cHouse", 10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight)+cellHeight);
			surface.pop();
		}
		if (type == "norm") {
			surface.push();
			surface.fill(191, 128, 111);
			surface.rect(10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight), cellWidth, cellHeight);
			surface.fill(0);
			surface.text("house", 10 + (getXCoor() * cellWidth), 10 + (getYCoor()*cellHeight)+cellHeight);
			surface.pop();
		}
		
	}
	
	public void putOnIsland(Island i) { //different b/c houses take up more than 1 grid
		
		setIsInGrid(true);
	}
	
	public void removeFromIsland(Island i) {
		
		setIsInGrid(false);
	}

}
