package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Actor {

	private Bullet bullet = null;
	private int ammo = 5;
	private int rifleAvailable = 1;

	private final int faceUp = 1;
	private final int faceLeft = 2;
	private final int faceDown = 3;
	private final int faceRight = 4;

	private ImageIcon upIcon;
	private ImageIcon leftIcon;
	private ImageIcon downIcon;
	private ImageIcon rightIcon;
	private Image image;

	public Player(int x, int y) {
		super(x, y);

		System.out.println("come in");

		initPlayer();
	}

	private void initPlayer() {

		File f = new File("");
		String path = f.getAbsolutePath();
		String temp;
       
		if(!path.contains("code")) temp="pic/sokobanUp.png";
		else temp = path.replaceAll("code", "pic/sokobanUp.png");
		upIcon = new ImageIcon(temp);

		if(!path.contains("code")) temp="pic/sokobanLeft.png";
		else temp = path.replaceAll("code", "pic/sokobanLeft.png");
		leftIcon = new ImageIcon(temp);

		if(!path.contains("code")) temp="pic/sokobanDown.png";
		else temp = path.replaceAll("code", "pic/sokobanDown.png");
		downIcon = new ImageIcon(temp);

		if(!path.contains("code")) temp="pic/sokobanRight.png";
		else temp = path.replaceAll("code", "pic/sokobanRight.png");
		rightIcon = new ImageIcon(temp);

		image = upIcon.getImage();
		
		setImage(image);
	}

	@Override
	public String getActorName(){
		return "player";
	}

	public void setPlayerImage(int direction){

		switch (direction){
			case faceUp:
				image = upIcon.getImage();
				break;
			case faceLeft:
				image = leftIcon.getImage();
				break;
			case faceDown:
				image = downIcon.getImage();
				break;
			case faceRight:
				image = rightIcon.getImage();
				break;
		}

		setImage(image);
	}

	public void move(int x, int y) {

		int dx = x() + x;
		int dy = y() + y;

		setX(dx);
		setY(dy);
	}

	public int getRifleAvailable() {
		return rifleAvailable;
	}

	public void setRifleAvailable(int rifleAvailable) {
		this.rifleAvailable = rifleAvailable;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
}
