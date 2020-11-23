package java2020.finalProject;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import java.io.IOException;
import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import java.awt.Dimension;

public class Portal extends Object {

	private int availability = 3;
	private int imageSeguence = 0;

	private boolean isActive = false;

	private Image[] images = new Image[16];

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

	public Portal(int x, int y) {
		super(x, y);

		File f = new File("");
		String path = f.getAbsolutePath();
		String temp;

		for(int i = 10; i < 26; i++) {
			temp = path.replaceAll("code", String.format("pic/portalAnimation/Portal%d.png", i));
			BufferedImage image;

			try {
				image = Thumbnails.of(temp).scale(scale).asBufferedImage();
				images[i-10] = image;
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		setImage(images[0]);
	}

	@Override
	public String getObjectName() {
		return "portal";
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		if (availability >= 0)
			this.availability = availability;
	}

	public void animation() {
		// circulation
		imageSeguence %= 16;
		setImage(images[imageSeguence++]);
	}

}
