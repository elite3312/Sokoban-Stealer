package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Actor {

	private Bullet bullet = null;
	private int ammo = 10;
	private int rifleAvailable = 1;

	private final int faceLeft = 1;
	private final int faceRight = 2;
	private final int faceUp = 3;
	private final int faceDown = 4;

	private ImageIcon upIcon;
	private ImageIcon leftIcon;
	private ImageIcon downIcon;
	private ImageIcon rightIcon;

	public Player(int x, int y) {
		super(x, y);

		initPlayer();
	}

	private void initPlayer() {

		File f = new File("");
		String path = f.getAbsolutePath();
		String up, down, left, right;

		if (!path.contains("code")){
			up = "pic/sokobanUp.png";
			left = "pic/sokobanLeft.png";
			down = "pic/sokobanDown.png";
			right = "pic/sokobanRight.png";
		}
		else{
			up = path.replaceAll("code", "pic/sokobanUp.png");
			left = path.replaceAll("code", "pic/sokobanLeft.png");
			down = path.replaceAll("code", "pic/sokobanDown.png");
			right = path.replaceAll("code", "pic/sokobanRight.png");
		}

		upIcon = new ImageIcon(up);
		leftIcon = new ImageIcon(left);
		downIcon = new ImageIcon(down);
		rightIcon = new ImageIcon(right);

		setImage(upIcon.getImage());
	}

	@Override
	public String getActorName() {
		return "player";
	}

	public void setPlayerImage(int direction) {

		switch (direction) {
			case faceUp:
				setImage(upIcon.getImage());
				break;
			case faceLeft:
				setImage(leftIcon.getImage());
				break;
			case faceDown:
				setImage(downIcon.getImage());
				break;
			case faceRight:
				setImage(rightIcon.getImage());
				break;
		}

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
