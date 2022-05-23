package circeIsland.creatures;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import circeIsland.elements.Holdable;
import circeIsland.elements.House;
import circeIsland.main.DrawingSurface;
import processing.core.PImage;

/**
 * 
 * @author Jiwoo Kim
 */
public class MaliciousVisitor extends Visitor{
	
	private boolean stealing;
	private boolean running;
	private int stealingCount;
	private int[] circeHouse;
	private Rectangle2D.Double circeHouseRect;


	public MaliciousVisitor(PImage img, double x, double y) {
		super(img, x, y);
		stealing = false;
		running = true;
		stealingCount = 0;
		circeHouse = new int[2];
		circeHouse[0] = -1;
		circeHouse[1] = -1;
		circeHouseRect = null;
	}

	public String getType() {
		return "Malicious";
	}
	
	public void act() {
		if(super.getIsInGrid()) {
			if(circeHouse[0] == -1) {
				setHouse();
			}
			if(this.intersects(circeHouseRect)) {
				stealing = true;
				stealingCount++;
			}else if(running && !stealing) {
				int[] circeGrid = checkCirceNearby();
				int dir = -1;
				if(circeGrid == null) {
					dir = super.destinationDir(circeHouse);
				}else {
					dir = destinationDir(circeGrid);
				}
				
				super.act(dir);
			}
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
	
	public int getStealingCount() {
		return stealingCount;
	}
	
	
	public int destinationDir(int[] destination) {
		int[] grid = coorToGrid(x, y+height);
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
	
	public boolean getStolen() {
		return stealing;
	}
	
	public void draw(DrawingSurface g) {
		if(super.getIsInGrid()) {
			super.draw(g);
			if(circeHouse[0] == -1) {
				setHouse();
			}
			if(islandWidthResized() || islandHeightResized()) {
				double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
				double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
				circeHouseRect = new Rectangle2D.Double(circeHouse[0]*cellWidth-cellWidth/3,circeHouse[1]*cellHeight-cellHeight/3,cellWidth*8/3, cellHeight*8/3);
			}
			
			
		}
	}

	private void setHouse() {
		House h = super.getIsland().getCirceHouse();
		circeHouse[0] = h.getXCoor();
		circeHouse[1] = h.getYCoor();
		double cellWidth = (super.getIsland().getSurface().width - 11) / super.getIsland().getElements()[0].length;
		double cellHeight = (super.getIsland().getSurface().height - 17) / super.getIsland().getElements().length;
		circeHouseRect = new Rectangle2D.Double(circeHouse[0]*cellWidth-cellWidth/3,circeHouse[1]*cellHeight-cellHeight/3,cellWidth*8/3, cellHeight*8/3);
	}

	
}
