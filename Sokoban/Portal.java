package coding.code;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Portal extends Actor {
	private int isActive=0;
	//private long startTime=0;
	private int availability=5;
	public Portal(int x, int y) {
        super(x, y);
        
        initPortal();
    }
    
    private void initPortal() {
    	isActive=0;
        ImageIcon iicon = new ImageIcon("pic/portal.png");
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
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
*/
	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		if(availability<0)return;
		this.availability = availability;
	}

}
