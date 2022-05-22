package circeIsland.creatures;

import java.util.ArrayList;

import circeIsland.elements.Holdable;
import circeIsland.elements.House;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class MaliciousVisitor extends Visitor{
	
	private boolean stealing;
	private boolean running;
	private Holdable[] stolen;

	public MaliciousVisitor(PImage img, int x, int y) {
		super(img, x, y);
		stealing = false;
		running = true;
		stolen = new Holdable[3];
		for(int i = 0; i < stolen.length; i++) {
			stolen[i] = null;
		}
	}

	public String getType() {
		return "Malicious";
	}
	
	public void act() {
		if(running && !stealing) {
			int[] currentPos = super.coorToGrid(x,y);
			if(super.getIsland().getElement(currentPos[0], currentPos[1]) instanceof House) {
				stealing = true;
			}
			
			int[] circeGrid = checkCirceNearby();
			int dir = -1;
			if(circeGrid == null) {
				House circeHouse = super.getIsland().getCirceHouse();
				int[] circeHouseCoor = {circeHouse.getXCoor(), circeHouse.getYCoor()};
				dir = super.destinationDir(circeHouseCoor);
			}else {
				dir = destinationDir(circeGrid);
			}
			
			super.act(dir);
		}
	}
	
	public void stopRunning() {
		running= false;
	}
	
	public void beginRunning() {
		running = true;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	
	public int destinationDir(int[] destination) {
		int[] grid = coorToGrid(x, y);
		int diffX = grid[0] - destination[0];
		int diffY = grid[1] - destination[1];
		
		if(diffX == 0 && diffY == 0) {
			return (int) (Math.random()*4);
		}
		
		if(Math.abs(diffY) > Math.abs(diffX)) {
			if(diffY < 0) 
				return Creature.UP;
			return Creature.DOWN;
		}
		
		if(diffX<0)
			return Creature.LEFT;
		return Creature.RIGHT;
	}
	
	public boolean getStealingStatus() {
		return stealing;
	}
	
	public void stealing(ArrayList<ArrayList<Holdable>> storage) {
		int i = (int) (Math.random() * storage.size());
		int j = (int) (Math.random() * storage.get(0).size());
//		if(storage)

		stealing = false;
	}
	

	
}
