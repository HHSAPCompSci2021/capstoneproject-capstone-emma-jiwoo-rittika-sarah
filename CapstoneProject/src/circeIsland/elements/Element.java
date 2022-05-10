package circeIsland.elements;

public abstract class Element {

	private int x, y;
	private boolean isInGrid;
	private int currentTime;
	private boolean standable;

	public Element(int xInput, int yInput) {
		x = xInput;
		y = yInput;
	}
	//METHOD
	
	public void putOnIsland() {
		
	}
	
	public void removeFromIsland() {
		
	}
	
	public abstract void draw();
	
	 
}
