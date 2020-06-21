package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Actor {

	private Bullet bullet = null;
	private int ammo =3;
	private int rifleAvailable = 1;

	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWN = 4;

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
				up = "pic/character/playerOneUp.png";
				left = "pic/character/playerOneLeft.png";
				down = "pic/character/playerOneDown.png";
				right = "pic/character/playerOneRight.png";
			}
			else{
				up = path.replaceAll("code", "pic/character/playerOneUp.png");
				left = path.replaceAll("code", "pic/character/playerOneLeft.png");
				down = path.replaceAll("code", "pic/character/playerOneDown.png");
				right = path.replaceAll("code", "pic/character/playerOneRight.png");
			}
		}
		else if(playerSkinChoosed == playerSkinTwo){
			if (!path.contains("code")){
				up = "pic/character/playerTwoUp.png";
				left = "pic/character/playerTwoLeft.png";
				down = "pic/character/playerTwoDown.png";
				right = "pic/character/playerTwoRight.png";
			}
			else{
				up = path.replaceAll("code", "pic/character/playerTwoUp.png");
				left = path.replaceAll("code", "pic/character/playerTwoLeft.png");
				down = path.replaceAll("code", "pic/character/playerTwoDown.png");
				right = path.replaceAll("code", "pic/character/playerTwoRight.png");
			}
		}
		else{
			if (!path.contains("code")){
				up = "pic/character/playerThreeUp.png";
				left = "pic/character/playerThreeLeft.png";
				down = "pic/character/playerThreeDown.png";
				right = "pic/character/playerThreeRight.png";
			}
			else{
				up = path.replaceAll("code", "pic/character/playerThreeUp.png");
				left = path.replaceAll("code", "pic/character/playerThreeLeft.png");
				down = path.replaceAll("code", "pic/character/playerThreeDown.png");
				right = path.replaceAll("code", "pic/character/playerThreeRight.png");
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
			case UP:
				setImage(upIcon.getImage());
				break;
			case LEFT:
				setImage(leftIcon.getImage());
				break;
			case DOWN:
				setImage(downIcon.getImage());
				break;
			case RIGHT:
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
