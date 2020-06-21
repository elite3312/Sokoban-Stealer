package java2020.finalProject;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class Portal extends Actor {

	private int isActive = 0;
	// private long startTime = 0;
	private int availability = 3;
	private Image[] images = new Image[16];
	private int animater = 0;
	private int imageSeguence = 0;

	public Portal(int x, int y) {
		super(x, y);
		initPortal();
	}

	@Override
	public String getActorName() {
		return "portal";
	}

	private void initPortal() {

		animater = 0;
		imageSeguence = 0;

		File f = new File("");
		String path = f.getAbsolutePath();
		String temp = "";

		for(int i = 10; i < 26; i++){
			temp = path;

			if (!path.contains("code"))
				temp = String.format("pic/portalAnimation/Portal%d.png", i);
			else
				temp = path.replaceAll("code", String.format("pic/portalAnimation/Portal%d.png", i));

			ImageIcon iicon = new ImageIcon(temp);
			Image image = iicon.getImage();
			images[i-10] = image;
		}
		isActive = 0;

		setImage(images[0]);
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

	public void animation(){
		animater++;

		if(animater > 1){
			if(imageSeguence == 16){
				imageSeguence = 0;
			}
			animater = 0;
			setImage(images[imageSeguence++]);
		}
	}

}
