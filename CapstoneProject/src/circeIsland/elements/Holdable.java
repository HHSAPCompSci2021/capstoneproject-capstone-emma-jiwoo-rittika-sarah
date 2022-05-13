package circeIsland.elements;

public class Holdable {
	int type;
	String details;
	public static final int BREAD = 1;
	public static final int WATER = 2;
	public static final int SEED_GRAPE = 3;
	public static final int SEED_BARLEY = 4;
	public static final int SEED_MARATHO = 5;
	public static final int SEED_ANITHOS = 6;
	public static final int GRAPE = 7;
	public static final int BARLEY = 8;
	public static final int MARATHO = 9;
	public static final int ANITHOS = 1;
	public static final int POTION = 1;

	public Holdable(int type) {
		
	}
	
	public Holdable(int type, String details) {
		
	}
	
	
	public int getType() {
		return type;
	}
}
