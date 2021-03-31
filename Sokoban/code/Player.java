package java2020.finalProject;

import java.awt.Image;
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
	private int explosion = 0;

	private int playerDir;

	private Image[] icon = new Image[4];
	private Image[] explodImages = new Image[10];

	private String path = new File("").getAbsolutePath();

	private boolean penetrateSkill = true;

	public Player(int x, int y, int playerSkinChoosed) {
		super(x, y);

		ImageManager imanager = new ImageManager(false);

		String charseq;
		switch(playerSkinChoosed) {
			case 1:
				charseq = "One";
				break;
			case 2:
				charseq = "Two";
				break;
			default:
				charseq = "Three";
				break;
		}

		String[] dirs = new String[]{"Left", "Right", "Up", "Down"};
		for(int i = 0; i < 4; i++) {
			String realPath = imanager.pathConfig(path, "character/player" + charseq + dirs[i] + ".png");
			icon[i] = imanager.getImageFromPath(realPath);
		}
		setImage(icon[2]);

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

	public void setPlayerDir(int direction) {
		setImage(icon[direction-1]);
		this.playerDir = direction;
	}

	public int getDir() {
		return this.playerDir;
	}

	public void move(int x, int y) {
		setX(x() + x);
		setY(y() + y);
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

	public void playerExplode() {
		setImage(explodImages[explosion / 2]);
		explosion++;
		if(explosion >= 18) {
			explosion -= 18;
		}
	}

	public void setPenetrateSkill(boolean state) {
		penetrateSkill = state;
	}

	public boolean getPenetrateSkill() {
		return penetrateSkill;
	}
}
