package self.aleksandra.practice.game.data;

import self.aleksandra.practice.game.Camera;
import self.aleksandra.practice.game.data.GameMap.WallType;

public class Defaults {
	private static Defaults ME;
	private GameMap defaultMap;
	private Texture wood;
	private Texture brick;
	private Texture bluestone;
	private Texture stone;
	private Camera defaultMapCamera;
	
	private static void checkInit() {
		if (ME == null) {
			ME = new Defaults();
		
			ME.defaultMap = initMap();
			ME.defaultMapCamera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
			ME.wood = new Texture("resources/wood.png", 64);
			ME.brick = new Texture("resources/brick.png", 64);
			ME.bluestone = new Texture("resources/bluestone.png", 64);
			ME.stone = new Texture("resources/stone.png", 64);
		}
	}
	
	
	private static GameMap initMap() {
		int[][] wallInts = {
				{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
				{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
				{1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
				{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
				{1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
				{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
				{1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
				{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
				{1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
				{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
				{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
				{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
				{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
		};
		WallType[][] mapLayout = new WallType[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j< 15; j++) {
				switch (wallInts[i][j]) {
				case 0: mapLayout[i][j] = WallType.NONE;
				break;
				case 1: mapLayout[i][j] = WallType.WOOD;
				break;
				case 2: mapLayout[i][j] = WallType.BRICK;
				break;
				case 3: mapLayout[i][j] = WallType.BLUESTONE;
				break;
				case 4: mapLayout[i][j] = WallType.STONE;
				}
			}
		}
		return new GameMap(15, 15, mapLayout);
	}

	public static GameMap getDefaultMap() {
		checkInit();
		return ME.defaultMap;
	}
	
	public static Camera getDefaultMapCamera() {
		checkInit();

		return ME.defaultMapCamera;
	}


	public static Texture getWoodTex() {
		checkInit();

		return ME.wood;
	}


	public static Texture getBrickTex() {
		checkInit();

		return ME.brick;
	}


	public static Texture getBluestoneTex() {
		checkInit();

		return ME.bluestone;
	}


	public static Texture getStoneTex() {
		checkInit();

		return ME.stone;
	}
}
