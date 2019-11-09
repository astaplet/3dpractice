package self.aleksandra.practice.game;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import self.aleksandra.practice.game.data.Defaults;
import self.aleksandra.practice.game.data.GameMap;

public class GameRunner extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private GameMap map = Defaults.getDefaultMap();
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	private int[] pixels;
	private Camera camera;
	private Screen screen;
	
	public GameRunner() {
		this.thread = new Thread(this);
		this.image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		this.camera = Defaults.getDefaultMapCamera();
		this.addKeyListener(this.camera);
		this.screen = new Screen(this.map, 640, 480);
		this.setSize(640, 480);
		this.setResizable(false);
		this.setTitle("Aleksandra's dumb practice game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.black);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.start();
	}
	
	private synchronized void start() {
		this.running = true;
		this.thread.start();
	}
	
	public synchronized void stop() {
		this.running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), null);
		bs.show();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		this.requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1) {
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render();
		}
	}
	
	public static void main(String[] args) {
		GameRunner gr = new GameRunner();
	}

}
