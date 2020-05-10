package coding.code;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet extends Actor {
	private final int SPACE = 40;//actor side length
	private final int LEFT= 1;
	private final int RIGHT = 2;
	private final int TOP= 3;
	private final int BOTTOM = 4;	    
	private int dir;
	private int maxRange=5;
	public Bullet(int x, int y,int initDir) {
		super(x, y);
		dir=initDir;
		initBullet();
	}
	private void initBullet() {
		ImageIcon iicon = new ImageIcon("pic/bullet.png");
	    Image image = iicon.getImage();
	    setImage(image);
	 }
	public void updateXY() {
		int dx;
        int dy;
        setMaxRange(getMaxRange() - 1);
        
		switch (dir) {
			
			case LEFT:
				dx = x() - SPACE;
				dy = y();	
				break;
			case RIGHT:
				dx = x() + SPACE;
				dy = y();
				break;
			case TOP:
				dx = x();
				dy = y() - SPACE;
				break;
			case BOTTOM:
				dx = x();
				dy = y() + SPACE;
				break;
			default:
				dx = x();
				dy = y();	
				break;
				
		}
		setX(dx);
        setY(dy);
	}
	public int getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}
}
