package java2020.finalProject;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class Portal extends Actor {
	private int isActive = 0;
	// private long startTime = 0;
	private int availability = 3;

	public Portal(int x, int y) {
		super(x, y);
		initPortal();
	}

	@Override
	public String getActorName() {
		return "portal";
	}

	private void initPortal() {

		File f = new File("");
		String path = f.getAbsolutePath();

		if (!path.contains("code"))
			path = "pic/portal.png";
		else
			path = path.replaceAll("code", "pic/portal.png");

		isActive = 0;
		ImageIcon iicon = new ImageIcon(path);
		Image image = iicon.getImage();
		setImage(image);
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	/*
	 * public long getStartTime() { return startTime; }
	 * 
	 * public void setStartTime(long startTime) { this.startTime = startTime; }
	 */
	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		if (availability < 0)
			return;
		this.availability = availability;
	}

}
