package self.aleksandra.practice.game;

import java.awt.Color;

import self.aleksandra.practice.game.data.Defaults;
import self.aleksandra.practice.game.data.GameMap;
import self.aleksandra.practice.game.data.GameMap.WallType;
import self.aleksandra.practice.game.data.Texture;

public class Screen {
	private GameMap map;
	private int screenWidth;
	private int screenHeight;
	
	public Screen(GameMap map, int screenWidth, int screenHeight) {
		this.map = map;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public int[] update(Camera camera, int[] pixels) {
		for(int n=0; n<pixels.length/2; n++) {
			if(pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.DARK_GRAY.getRGB();
		}
		for(int i=pixels.length/2; i<pixels.length; i++){
			if(pixels[i] != Color.gray.getRGB()) pixels[i] = Color.gray.getRGB();
		}

		for (int x= 0 ; x < this.screenWidth; x++) {
			double cameraX = 2 * x/ (double)(this.screenWidth) - 1;
			double rayDirX = camera.getxDir() + camera.getxPlane() * cameraX;
			double rayDirY = camera.getyDir() + camera.getyPlane() * cameraX;
			
			int mapX = (int)camera.getxPos();
			int mapY = (int)camera.getyPos();
			
			double sideDistX;
			double sideDistY;
			
			double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
			double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
			double perpWallDist;
			
			int stepX, stepY;
			boolean hit = false;
			int side = 0;
			
			if (rayDirX < 0) {
				stepX = -1;
				sideDistX = (camera.getxPos() - mapX) * deltaDistX;
			} else {
				stepX = 1;
				sideDistX = (mapX + 1.0 - camera.getxPos()) * deltaDistX;
			}
			
			if (rayDirY < 0) {
				stepY = -1;
				sideDistY = (camera.getyPos() - mapY) * deltaDistY;
			} else {
				stepY = 1;
				sideDistY = (mapY + 1.0 - camera.getyPos()) * deltaDistY;
			}
			
			while(!hit) {
				if (sideDistX < sideDistY) {
					sideDistX += deltaDistX;
					mapX += stepX;
					side = 0;
				}
				else {
					sideDistY += deltaDistY;
					mapY += stepY;
					side = 1;
				}
				
				if(map.getWallAtCoords(mapX, mapY) != WallType.NONE) {
					hit = true;
				}
			}
			
			if (side == 0) {
				perpWallDist = Math.abs((mapX - camera.getxPos() + (1 - stepX) /2) / rayDirX);
			} else {
				perpWallDist = Math.abs((mapY - camera.getyPos() + (1 - stepY) /2) / rayDirY);
			}
			int lineHeight;
			if (perpWallDist > 0) {
				lineHeight = Math.abs((int)(this.screenHeight / perpWallDist));
			} else {
				lineHeight = this.screenHeight;
			}
			
			int drawStart = Math.max(-lineHeight/2 + this.screenHeight / 2, 0);

			int drawEnd = Math.min(lineHeight/2 + this.screenHeight / 2, this.screenHeight - 1);
			
			WallType wall = map.getWallAtCoords(mapX, mapY);
			Texture tex = Defaults.getWoodTex();
			switch (wall) {
			case BLUESTONE: tex = Defaults.getBluestoneTex();
				break;
			case BRICK: tex = Defaults.getBrickTex();
				break;
			case NONE: 
				break;
			case STONE: tex = Defaults.getStoneTex();
				break;
			case WOOD: tex = Defaults.getWoodTex();
				break;
			default:
				break;
			
			}
				
			double wallX;
			if (side == 1) {
				wallX = (camera.getxPos() + ((mapY - camera.getyPos() + (1 - stepY) / 2) / rayDirY) * rayDirX);
			}
			else {
			    wallX = (camera.getyPos() + ((mapX - camera.getxPos() + (1 - stepX) / 2) / rayDirX) * rayDirY);
			}
			
			wallX-=Math.floor(wallX);
			int texX = (int)(wallX * tex.getSize());
			
			if(side == 0 && rayDirX > 0) {
				texX = tex.getSize() - texX - 1;
			}
			if (side == 1 && rayDirY < 0) {
				texX = tex.getSize() - texX - 1;
			}
			
			int[] texPixels = tex.getPixels();
			for (int y=drawStart; y < drawEnd; y++) {
				int texY = (((y*2 - this.screenHeight + lineHeight) << 6) / lineHeight) / 2;
				int color;
				if (side == 0) {
					color = texPixels[texX + (texY * tex.getSize())];
				} else {
					color = (texPixels[texX + (texY * tex.getSize())]>>1) & 8355711;
				}
				int pixelOffset = x + y *(this.screenWidth);
				pixels[pixelOffset] = color;
			}
			
		}
		return pixels;

	}
}
