package circeIsland.elements;
import circeIsland.main.DrawingSurface;
import circeIsland.screens.Island;

public class River extends Element{

	public River(Island i, int xInput, int yInput) {
		super(i, xInput, yInput);
		setStandable(false);
	}

	@Override
	public void draw(DrawingSurface surface, float cellWidth, float cellHeight) {
		
	}

}
