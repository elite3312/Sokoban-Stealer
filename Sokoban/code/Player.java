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

        try{
            upIcon = (Image)Thumbnails.of(up).scale(scale).asBufferedImage();
			leftIcon = (Image)Thumbnails.of(left).scale(scale).asBufferedImage();
			downIcon = (Image)Thumbnails.of(down).scale(scale).asBufferedImage();
			rightIcon = (Image)Thumbnails.of(right).scale(scale).asBufferedImage();

			setImage(upIcon);

        } catch (IOException e){
            System.out.println(e);
		}

		explosion = 0;
		String explodePath;
		for(int i = 0; i < 10; i++){
			Image temp;

			explodePath = path;
			if (!path.contains("code")){
				explodePath = String.format("pic/explode/explode%d.png", i);
			}
			else{
				explodePath = path.replaceAll("code", String.format("pic/explode/explode%d.png", i));
			}

			try{
				temp = (Image)Thumbnails.of(explodePath).scale(scale).asBufferedImage();
				explodImages[i] = temp;
			} catch (IOException e){
				System.out.println(e);
			}	
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
