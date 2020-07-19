package java2020.finalProject;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class Player extends Actor {

	private Bullet bullet = null;
	private int ammo = 3;
	private int rifleAvailable = 1;
	private int explosion = 0;

	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWN = 4;

	private final int playerSkinOne = 1;
	private final int playerSkinTwo = 2;

	private Image upIcon;
	private Image leftIcon;
	private Image downIcon;
	private Image rightIcon;
	private Image[] explodImages = new Image[10];

	private File f = new File("");
	private String path = f.getAbsolutePath();

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

	public Player(int x, int y, int playerSkinChoosed) {
		super(x, y);

		initPlayer(playerSkinChoosed);
	}

	private void initPlayer(int playerSkinChoosed) {

		String up, down, left, right;
		ImageManager imanager = new ImageManager(0);

		if(playerSkinChoosed == playerSkinOne){
			up = imanager.pathConfig(path, "character/playerOneUp.png");
			left = imanager.pathConfig(path, "character/playerOneLeft.png");
			down = imanager.pathConfig(path, "character/playerOneDown.png");
			right = imanager.pathConfig(path, "character/playerOneRight.png");
		}
		else if(playerSkinChoosed == playerSkinTwo){
			up = imanager.pathConfig(path, "character/playerTwoUp.png");
			left = imanager.pathConfig(path, "character/playerTwoLeft.png");
			down = imanager.pathConfig(path, "character/playerTwoDown.png");
			right = imanager.pathConfig(path, "character/playerTwoRight.png");
		}
		else{
			up = imanager.pathConfig(path, "character/playerThreeUp.png");
			left = imanager.pathConfig(path, "character/playerThreeLeft.png");
			down = imanager.pathConfig(path, "character/playerThreeDown.png");
			right = imanager.pathConfig(path, "character/playerThreeRight.png");
		}

		upIcon = imanager.getImageFromPath(up);
		leftIcon = imanager.getImageFromPath(left);
		downIcon = imanager.getImageFromPath(down);
		rightIcon = imanager.getImageFromPath(right);

		setImage(upIcon);

		explosion = 0;
		String explodePath;

		for(int i = 0; i < 10; i++){
			Image temp;

			explodePath = path;
			explodePath = imanager.pathConfig(path, String.format("explode/explode%d.png", i));
			
			explodImages[i] = imanager.getImageFromPath(explodePath);
		}
	}

	@Override
	public String getActorName() {
		return "player";
	}

	public void setPlayerImage(int direction) {

		switch (direction) {
			case UP:
				setImage(upIcon);
				break;
			case LEFT:
				setImage(leftIcon);
				break;
			case DOWN:
				setImage(downIcon);
				break;
			case RIGHT:
				setImage(rightIcon);
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

	public void playerExplode(){
		setImage(explodImages[explosion / 2]);
		explosion++;

		if(explosion > 18)
			explosion = 18;
	}
}
