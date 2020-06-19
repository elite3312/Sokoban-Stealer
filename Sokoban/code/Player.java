package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Actor {

	private Bullet bullet = null;
	private int ammo =3;
	private int rifleAvailable = 1;

	private final int faceLeft = 1;
	private final int faceRight = 2;
	private final int faceUp = 3;
	private final int faceDown = 4;

	private final int playerSkinOne = 1;
	private final int playerSkinTwo = 2;

	private ImageIcon upIcon;
	private ImageIcon leftIcon;
	private ImageIcon downIcon;
	private ImageIcon rightIcon;

	public Player(int x, int y, int playerSkinChoosed) {
		super(x, y);

		initPlayer(playerSkinChoosed);
	}

	private void initPlayer(int playerSkinChoosed) {

		File f = new File("");
		String path = f.getAbsolutePath();
		String up, down, left, right;

		if(playerSkinChoosed == playerSkinOne){
			if (!path.contains("code")){
				up = "pic/playerOneUp.png";
				left = "pic/playerOneLeft.png";
				down = "pic/playerOneDown.png";
				right = "pic/playerOneRight.png";
			}
			else{
				up = path.replaceAll("code", "pic/playerOneUp.png");
				left = path.replaceAll("code", "pic/playerOneLeft.png");
				down = path.replaceAll("code", "pic/playerOneDown.png");
				right = path.replaceAll("code", "pic/playerOneRight.png");
			}
		}
		else{
			if (!path.contains("code")){
				up = "pic/playerTwoUp.png";
				left = "pic/playerTwoLeft.png";
				down = "pic/playerTwoDown.png";
				right = "pic/playerTwoRight.png";
			}
			else{
				up = path.replaceAll("code", "pic/playerTwoUp.png");
				left = path.replaceAll("code", "pic/playerTwoLeft.png");
				down = path.replaceAll("code", "pic/playerTwoDown.png");
				right = path.replaceAll("code", "pic/playerTwoRight.png");
			}
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
		if(getRifleAvailable() == 0)
			return 0;
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
}
