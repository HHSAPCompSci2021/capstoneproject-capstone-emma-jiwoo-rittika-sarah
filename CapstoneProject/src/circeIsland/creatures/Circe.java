package circeIsland.creatures;

import circeIsland.elements.*;

/**
 * 
 * @author Jiwoo Kim
 */
public class Circe extends Creature{

	public static final int CIRCE_WIDTH = 40;
	public static final int CIRCE_HEIGHT = 60;
	
	private Holdable[] holdings;
	
	public Circe(int x, int y) {
		super(x, y, CIRCE_WIDTH, CIRCE_HEIGHT);
	}

	
	//METHODS
	
	public void brew() {};
	
	public void garden() {};
	
	public void magic() {};
	
	public void detectKey() {}
}
