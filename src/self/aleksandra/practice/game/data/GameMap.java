package self.aleksandra.practice.game.data;

public class GameMap {
	public enum WallType {
		NONE, WOOD, BRICK, BLUESTONE, STONE
	};

	private int width;
	private int height;
	private WallType[][] layout;

	public GameMap(int width, int height, WallType[][] layout) {
		this.width = width;
		this.height = height;
		this.layout = layout;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	/**
	 * Get the wall at the provided coords. 0,0 origin is top left of map
	 * 
	 * @param x horizontal position of wall
	 * @param y vertical position of wall
	 * @return the wall at the x,y
	 */
	public WallType getWallAtCoords(int x, int y) {
		
		  if ((x > width) || (y > height) || (x < 0) || (y < 0)) { throw new
		  IndexOutOfBoundsException("Tried to access wall outside map bounds"); }
		 
		return this.layout[x][y];
	}
}
