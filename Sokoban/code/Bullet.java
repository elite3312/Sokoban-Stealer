package java2020.finalProject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.Dimension;

public class Bullet extends Actor {

	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int TOP = 3;
	private final int BOTTOM = 4;

	private final int dir;
	private int maxRange = 40;

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size
	private final int SPACE = (int)(40 * scale); // actor side length

	public Bullet(int x, int y, int initDir) {
		super(x, y);
		dir = initDir;
		initBullet();
	}

	@Override
    public String getActorName(){
        return "Bullet";
    }

	private void initBullet() {

		File f = new File("");
        String path = f.getAbsolutePath();
		
		if(!path.contains("code"))
			path = "pic/bullet.png";
		else
			path = path.replaceAll("code", "pic/bullet.png");
	

		BufferedImage image;
		try{
			image = Thumbnails.of(path).scale(scale).asBufferedImage();
			setImage(image);
		} catch (IOException e){
			System.out.println(e);
		}
		
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
