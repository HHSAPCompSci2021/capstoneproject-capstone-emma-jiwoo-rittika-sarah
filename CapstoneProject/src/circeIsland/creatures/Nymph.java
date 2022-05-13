package circeIsland.creatures;

/**
 * 
 * @author Jiwoo Kim
 */
public class Nymph extends Visitor{

	public Nymph(int x, int y) {
		super(x, y);
	}
	
	public void act(int dir) {
		
	}
	
	public String getType() {
		return "Nymph";
	}

}
