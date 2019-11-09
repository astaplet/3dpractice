package self.aleksandra.practice.game.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	private int[] pixels;
	private String loc;
	private final int SIZE;
	
	public Texture(String location, int size) {
		this.loc = location;
		this.SIZE = size;
		this.pixels = new int[SIZE * SIZE];
		this.load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(new File(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int pixelAt(int index) {
		return this.pixels[index];
	}
	
	public int[] getPixels() {
		return this.pixels;
	}
	
	public int getSize() {
		return this.SIZE;
	}
}
