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

public class Player extends Object {

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

	private String path = new File("").getAbsolutePath();

	private boolean penetrateSkill = true;

	public Player(int x, int y, int playerSkinChoosed) {
		super(x, y);

		initPlayer(playerSkinChoosed);
	}

	private void initPlayer(int playerSkinChoosed) {

		String up, down, left, right, charseq;
		ImageManager imanager = new ImageManager(false);

		switch(playerSkinChoosed) {
			case playerSkinOne:
				charseq = "One";
				break;
			case playerSkinTwo:
				charseq = "Two";
				break;
			default:
				charseq = "Three";
				break;
		}

		up = imanager.pathConfig(path, "character/player" + charseq + "Up.png");
		left = imanager.pathConfig(path, "character/player" + charseq + "Left.png");
		down = imanager.pathConfig(path, "character/player" + charseq + "Down.png");
		right = imanager.pathConfig(path, "character/player" + charseq + "Right.png");

		upIcon = imanager.getImageFromPath(up);
		leftIcon = imanager.getImageFromPath(left);
		downIcon = imanager.getImageFromPath(down);
		rightIcon = imanager.getImageFromPath(right);

		setImage(upIcon);

		explosion = 0;
		String explodePath;

		for(int i = 0; i < 10; i++) {
			explodePath = path;
			explodePath = imanager.pathConfig(path, String.format("explode/explode%d.png", i));
			
			explodImages[i] = imanager.getImageFromPath(explodePath);
		}
	}

	@Override
	public String getObjectName() {
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

	public void playerExplode() {
		setImage(explodImages[explosion / 2]);
		explosion++;

		if(explosion > 18)
			explosion = 18;
	}

	public void setPenetrateSkill(boolean state) {
		penetrateSkill = state;
	}

	public boolean getPenetrateSkill() {
		return penetrateSkill;
	}
}
