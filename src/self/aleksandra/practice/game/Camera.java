package self.aleksandra.practice.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import self.aleksandra.practice.game.data.GameMap;
import self.aleksandra.practice.game.data.GameMap.WallType;

public class Camera implements KeyListener{
	private double xPos, yPos, xDir, yDir, xPlane, yPlane;
	private boolean left, right, forward, back, strafeLeft, strafeRight;
	private final double MOVE_SPEED = .08;
	private final double ROTATION_SPEED = .045;
	
	public Camera(double x, double y, double xd, double yd, double xp, double yp) 
	{	
		this.xPos = x;
		this.yPos = y;
		this.xDir = xd;
		this.yDir = yd;
		this.xPlane = xp;
		this.yPlane = yp;
	}	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT: this.left = true; 
		break;
		case KeyEvent.VK_RIGHT: this.right = true;
		break;
		case KeyEvent.VK_W: this.forward = true; 
		break;
		case KeyEvent.VK_S: this.back = true; 
		break;
		case KeyEvent.VK_A: this.strafeLeft = true;
		break;
		case KeyEvent.VK_D: this.strafeRight = true;
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT: this.left = false; 
		break;
		case KeyEvent.VK_RIGHT: this.right = false;
		break;
		case KeyEvent.VK_W: this.forward = false; 
		break;
		case KeyEvent.VK_S: this.back = false; 
		break;
		case KeyEvent.VK_A: this.strafeLeft = false;
		break;
		case KeyEvent.VK_D: this.strafeRight = false;
		break;
		}
	}
	
	public void update(GameMap map) {
		if(this.forward) {
			if(map.getWallAtCoords((int)(this.xPos + this.xDir * this.MOVE_SPEED),(int)this.yPos) == WallType.NONE) {
				this.xPos+=this.xDir*MOVE_SPEED;
			}
			if(map.getWallAtCoords((int)this.xPos,(int)(this.yPos + this.yDir * this.MOVE_SPEED)) == WallType.NONE) {
				this.yPos+=this.yDir*this.MOVE_SPEED;
			}
		}
		if(this.back) {
			if(map.getWallAtCoords((int)(this.xPos - this.xDir * this.MOVE_SPEED),(int)this.yPos) == WallType.NONE) {
				this.xPos-=this.xDir*this.MOVE_SPEED;
			}
			if(map.getWallAtCoords((int)this.xPos,(int)(this.yPos - this.yDir * this.MOVE_SPEED)) == WallType.NONE) {
				this.yPos-=this.yDir*this.MOVE_SPEED;
			}
		}
		if (this.strafeRight) {
			if(map.getWallAtCoords((int)(this.xPos + this.xPlane * this.MOVE_SPEED),(int)this.yPos) == WallType.NONE) {
				this.xPos+=this.xPlane*this.MOVE_SPEED;
			}
			if(map.getWallAtCoords((int)this.xPos,(int)(this.yPos + this.yPlane * this.MOVE_SPEED)) == WallType.NONE) {
				this.yPos+=this.yPlane*this.MOVE_SPEED;
			}
		}
		if(this.right) {
			double oldxDir=this.xDir;
			this.xDir=this.xDir*Math.cos(-this.ROTATION_SPEED) - this.yDir*Math.sin(-this.ROTATION_SPEED);
			this.yDir=oldxDir*Math.sin(-this.ROTATION_SPEED) + this.yDir*Math.cos(-this.ROTATION_SPEED);
			double oldxPlane = this.xPlane;
			this.xPlane=this.xPlane*Math.cos(-this.ROTATION_SPEED) - this.yPlane*Math.sin(-this.ROTATION_SPEED);
			this.yPlane=oldxPlane*Math.sin(-this.ROTATION_SPEED) + this.yPlane*Math.cos(-this.ROTATION_SPEED);
			
		}
		if (this.strafeLeft) {
			if(map.getWallAtCoords((int)(this.xPos - this.xPlane * this.MOVE_SPEED),(int)this.yPos) == WallType.NONE) {
				xPos-=xPlane*MOVE_SPEED;
			}
			if(map.getWallAtCoords((int)this.xPos,(int)(this.yPos - this.yPlane * this.MOVE_SPEED)) == WallType.NONE) {
				this.yPos-=this.yPlane*this.MOVE_SPEED;
			}
		}
		if(this.left) {
			double oldxDir=this.xDir;
			this.xDir=this.xDir*Math.cos(this.ROTATION_SPEED) - this.yDir*Math.sin(this.ROTATION_SPEED);
			this.yDir=oldxDir*Math.sin(this.ROTATION_SPEED) + this.yDir*Math.cos(this.ROTATION_SPEED);
			double oldxPlane = this.xPlane;
			this.xPlane=this.xPlane*Math.cos(this.ROTATION_SPEED) - this.yPlane*Math.sin(this.ROTATION_SPEED);
			this.yPlane=oldxPlane*Math.sin(this.ROTATION_SPEED) + this.yPlane*Math.cos(this.ROTATION_SPEED);
			
		}
	}
	public double getxDir() {
		return xDir;
	}
	public double getyDir() {
		return yDir;
	}
	public double getxPlane() {
		return xPlane;
	}
	public double getyPlane() {
		return yPlane;
	}
	public double getxPos() {
		return xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public double getROTATION_SPEED() {
		return ROTATION_SPEED;
	}

}
