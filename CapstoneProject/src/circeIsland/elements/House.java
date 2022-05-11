package circeIsland.elements;

import circeIsland.screens.Island;

public class House extends Element{
	
	private String type; //Either "circe" or "norm"

	
	public House(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		setStandable(false);
	}

	@Override
	public void draw() {
		if (type == "circe")
		if (type == "norm") {}
		
	}
	
	public void putOnIsland(Island i) { //different b/c houses take up more than 1 grid
		
		setIsInGrid(true);
	}
	
	public void removeFromIsland(Island i) {
		
		setIsInGrid(false);
	}

}
