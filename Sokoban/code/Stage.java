package java2020.finalProject;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.FontMetrics;

import java.beans.beancontext.BeanContextEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.util.Date;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Dimension;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class Stage extends JPanel {

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

	// constant
	private final int MARGIN = (int)(40 * scale);
	private final int SPACE = (int)(40 * scale); // actor side length
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWN = 4;
	private final int playerSkinOne = 1;
	private final int playerSkinTwo = 2;
	private final int playerSkinThree = 3;
	private final int LevelCount = 6;
	private BackgroundMP3Player sounds;

	// int variable
	private int currentlyFacing = DOWN;
	public int executetime = 0; // repaint time
	public static int forbutton = 0;
	private int width = 0; // Stage width
	private int height = 0; // Stage height
	private int policePeriod;
	private int toward = 1;
	private int Achived = 0;
	private int playerSkin;
	private int selection; // map selection
	private int bufferedFrames = 0; // for arrow image
	private int pauseSelect = 1; // pause button(manual)
	private int mapX, mapY;
	private int lossBuffer = 0; // loss buffer(don't close immediately)
	private int wonBuffer = 0; // don't switch immediately

	private long collisionIgnoreTime;
	private Long restartTime;
	private Long lossTime;
	private Long wonTime;

	private ArrayList<Police> cops;
	private ArrayList<Wall> walls;
	private ArrayList<Treasure> treasures;
	private ArrayList<Goal> goals;
	private ArrayList<HardWall> hardWalls;

	// private Police cop;
	private Player stealer;
	private Portal portal;

	private enum sound {bulletSound, bagSound};

	private boolean isCompletedBool = false;
	private boolean lost = false;
	private boolean collisionIgnore = false; // penetrate skill
	private boolean penetrateNotUsed = true; // penetrate skill
	private boolean restarted = false; // restart frame
	private boolean restartBuffer = false; // restart buffering(for 0.3sec)
	private boolean gamePause = false;
	private boolean nextStage = false;
	private boolean closeSignal = false;
	private boolean justCloseTheMusicPls = false;

	private Graphics graphic; // for the global using
	private Image arrowImage = new ImageIcon().getImage();
	private FontMetrics metrics;

	private ImageManager imageManager = new ImageManager();
	private CheatManager cheater = new CheatManager();

	private EndingAnimation endAnimate = new EndingAnimation();
	
	public Stage(int playerSkinChoosen, int level) {
		selection = level;

		if (playerSkinChoosen == playerSkinTwo)
			playerSkin = playerSkinTwo;
		else if(playerSkinChoosen == playerSkinThree)
			playerSkin = playerSkinThree;
		else
			playerSkin = playerSkinOne; // default

		this.width = (int)dimension.getWidth();
		this.height = (int)dimension.getHeight();

		initStage();
	}

	private void initStage() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		initWorld();
	}

	public int getStageWidth() {
		return this.width;
	}

	public int getStageHeight() {
		return this.height;
	}

	private void initWorld() {

		arrowImage = imageManager.getArrowImage();					

		walls = new ArrayList<>();
		treasures = new ArrayList<>();
		goals = new ArrayList<>();
		hardWalls = new ArrayList<>();
		cops = new ArrayList<>();

		String level;

		int x = 0;
		int y = MARGIN + 50;

		Map maptest;

		policePeriod = 10;

		maptest = new Map();
		level = (String) (maptest.getMap(selection));
		portal = new Portal(0, 0);

		Achived = 0;

		penetrateNotUsed = true; // penetrate init
		collisionIgnore = false; // penetrate init
		nextStage = false;
		bufferedFrames = 0;
		pauseSelect = 1;
		lossBuffer = 0;
		wonBuffer = 0;

		mapX = level.indexOf("\n", 0); // len of map width
		mapY = level.length() / mapX; // len of map height

		mapX = mapX * SPACE; // pixel of map width
		mapY = mapY * SPACE; // pixel of map height

		int modifyX = (this.width - mapX) / 2;
		int modifyY = 20;

		for (int i = 0; i < level.length(); i++) { // set width, height, actors specified by the string
			char item = level.charAt(i);
			switch (item) {

				case 'H':
					HardWall newHardWall = new HardWall(x + modifyX, y + modifyY);
					newHardWall.setImage(imageManager.getHardWallImage());
					hardWalls.add(newHardWall); // hard wall cannot be penetrated
					x += SPACE;
					break;

				case '#':
					Wall newWall = new Wall(x + modifyX, y + modifyY);
					newWall.setImage(imageManager.getWallImage());
					walls.add(newWall); // create wall at (x,y)
					x += SPACE;
					break;

				case ' ':
					x += SPACE;
					break;

				case '\n':
					y += SPACE;
					x = 0;
					break;

				case '$':
					Treasure treasure = new Treasure(x + modifyX, y + modifyY);
					treasure.setImage(imageManager.getTreasureImage());
					treasures.add(treasure);
					x += SPACE;
					break;

				case '.':
					Goal newGoal = new Goal(x + modifyX, y + modifyY);
					newGoal.setImage(imageManager.getGoalImage());
					goals.add(newGoal); // target goal
					x += SPACE;
					break;

				case '!':
					Police newCop = new Police(x + modifyX, y + modifyY);
					Image[] fourDir = imageManager.getPoliceImages();

					newCop.getFourDirectionImage(fourDir);
					newCop.setImage(fourDir[3]);
					cops.add(newCop);
					x += SPACE;
					break;
				
				case '%':
					Goal newGoal2 = new Goal(x + modifyX, y + modifyY);
					newGoal2.setImage(imageManager.getGoalImage());
					goals.add(newGoal2); // target goal

					Treasure treasure2 = new Treasure(x + modifyX, y + modifyY);
					treasure2.setImage(imageManager.getTreasureImage());
					treasures.add(treasure2);

					x += SPACE;
					break;

				case '@':
					stealer = new Player(x + modifyX, y + modifyY, playerSkin); // player
					x += SPACE;
					break;

				default:
					break;
			}
		}
	}

	private void buildWorld(Graphics g) {

		if(selection == LevelCount + 1){ // all completed
			justCloseTheMusicPls = true;
			endAnimate.ending(g);
			if(endAnimate.over())
				closeSignal = true;
			return;
		}

		int playerX = 0, playerY = 0;

		if(lost){
			stealer.playerExplode();
		}

		if(restarted){
			Long time = new Date().getTime();
			String stateNow = "";

			if(time - restartTime < 200){
				stateNow = "Loading     ";
			}
			else if(time - restartTime >= 200 && time - restartTime < 400){
				stateNow = "Loading.    ";
			}
			else if(time - restartTime >= 400 && time - restartTime < 600){
				stateNow = "Loading..   ";
			}
			else if(time - restartTime >= 600 && time - restartTime < 800){
				stateNow = "Loading...  ";
			}
			else if(time - restartTime >= 800 && time - restartTime < 1000){
				stateNow = "Loading.... ";
			}
			else if(time - restartTime >= 1000 && time - restartTime < 1200){
				stateNow = "Loading.....";
			}

			Font font = new Font("Microsoft JhengHei", Font.BOLD, (int)(64 * scale));
			metrics = g.getFontMetrics(font);
			int strWidth = metrics.stringWidth(stateNow);

			g.setColor(new Color(0, 0, 0));
			g.setFont(font);

			g.drawString(stateNow, this.width / 2 - strWidth / 2, this.height / 2);
			
			if(time - restartTime < 1200){
				return;
			}
			else{
				restarted = false;
			}
		}

		if(lossBuffer > 15){
			if(lost){
				
				Long time = new Date().getTime();

				String infoShow = "關卡失敗 !!!";

				Font font = new Font("Microsoft JhengHei", Font.BOLD, (int)(64 * scale));
				metrics = g.getFontMetrics(font);
				int strWidth = metrics.stringWidth(infoShow);

				g.setColor(new Color(0, 0, 0));
				g.setFont(font);
				g.drawString(infoShow, this.width / 2 - strWidth / 2, this.height / 2);

				if(time - lossTime < 1000){
					return;
				}
				else{
					lost = false;
					restartLevel();
					return;
				}
			}
		}

		if(wonBuffer > 15){
			if(isCompletedBool){
				Long time = new Date().getTime();

				if(time - wonTime < 1000){

					String infoShow = "關卡勝利 !!!";
					Font font = new Font("Microsoft JhengHei", Font.BOLD, (int)(64 * scale));
					metrics = g.getFontMetrics(font);
					int strWidth = metrics.stringWidth(infoShow);

					g.setColor(new Color(0, 0, 0));
					g.setFont(font);
					g.drawString(infoShow, this.width / 2 - strWidth / 2, this.height / 2);
					return;
				}
				else if(time - wonTime > 1000 && time - wonTime < 2200){
					String stateNow = "";

					if(time - wonTime < 1200){
						stateNow += "Loading     ";
					}
					else if(time - wonTime >= 1200 &&time - wonTime < 1400){
						stateNow += "Loading.    ";
					}
					else if(time - wonTime >= 1400 &&time - wonTime < 1600){
						stateNow += "Loading..   ";
					}
					else if(time - wonTime >= 1600 &&time - wonTime < 1800){
						stateNow += "Loading...  ";
					}
					else if(time - wonTime >= 1800 &&time - wonTime < 2000){
						stateNow += "Loading.... ";
					}
					else if(time - wonTime >= 2000 &&time - wonTime < 2200){
						stateNow += "Loading.....";
					}
					
					Font font = new Font("Microsoft JhengHei", Font.BOLD, (int)(64 * scale));
					metrics = g.getFontMetrics(font);
					int strWidth = metrics.stringWidth(stateNow);

					g.setColor(new Color(0, 0, 0));
					g.setFont(font);
					g.drawString(stateNow, this.width / 2 - strWidth / 2, this.height / 2);

					return;
				}
				else{
					isCompletedBool = false;
					nextStage = true;
					selection++;
					initWorld();
				}
			}
		}

		if(gamePause){
			String state = String.format("LEVEL %d", selection);
			String pau = "【 暫停 】";

			Font font = new Font("Microsoft JhengHei", Font.BOLD, (int)(80 * scale));
			metrics = g.getFontMetrics(font);
			int strWidth = metrics.stringWidth(state);

			g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(state, this.width / 2 - strWidth / 2, this.height / 5);

			font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(64 * scale));
			g.setFont(font);

			metrics = g.getFontMetrics(font);
			strWidth = metrics.stringWidth(pau);
			g.drawString(pau, this.width / 2 - strWidth / 2, this.height / 2 - 70);

			String choose1, choose2, choose3;

			choose1 = "繼續遊戲";
			choose2 = "重新開始";
			choose3 = "回到主畫面";

			if(pauseSelect == 1){
				font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(40 * scale));
				g.setColor(Color.RED);
				g.setFont(font);
				choose1 = ">>" + choose1 + "<<";
			}
			else{
				font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(36 * scale));
				g.setColor(Color.BLACK);
				g.setFont(font);
			}
			metrics = g.getFontMetrics(font);
			strWidth = metrics.stringWidth(choose1);
			g.drawString(choose1, this.width / 2 - strWidth / 2, this.height / 2 + 40);

			if(pauseSelect == 2){
				font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(40 * scale));
				g.setColor(Color.RED);
				g.setFont(font);
				choose2 = ">>" + choose2 + "<<";
			}
			else{
				font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(36 * scale));
				g.setColor(Color.BLACK);
				g.setFont(font);
			}
			metrics = g.getFontMetrics(font);
			strWidth = metrics.stringWidth(choose2);
			g.drawString(choose2, this.width / 2 - strWidth / 2, this.height / 2 + 90);

			if(pauseSelect == 3){
				font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(40 * scale));
				g.setColor(Color.RED);
				g.setFont(font);
				choose3 = ">>" + choose3 + "<<";
			}
			else{
				font = new Font("Microsoft JhengHei", Font.PLAIN, (int)(36 * scale));
				g.setColor(Color.BLACK);
				g.setFont(font);
			}
			metrics = g.getFontMetrics(font);
			strWidth = metrics.stringWidth(choose3);
			g.drawString(choose3, this.width / 2 - strWidth / 2, this.height / 2 + 140);

			return;
		}

		g.setColor(new Color(225, 225, 225));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		String info = String.format("傳送門：%d", portal.getAvailability());
		info += String.format("        子彈：%2d", stealer.getAmmo());

		if (collisionIgnore) {
			Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime;
			checkCollisonTime = 3000 - checkCollisonTime;
			if (checkCollisonTime <= 0) {
				collisionIgnore = false;
			}
			double temp = checkCollisonTime / 1000.0;
			if (temp >= 0)
				info += String.format("        技能時間：%.2f", temp);
		} else {
			if (penetrateNotUsed) {
				info += "        穿牆技能：可用";
			} else {
				info += "        穿牆技能：不可用";
			}
		}

		if (cheater.checkCondition()) {
			info = "傳送門：∞        子彈：∞";
			penetrateNotUsed = true;
			stealer.setAmmo(99997);
			portal.setAvailability(99999);

			if(collisionIgnore){
				Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime;
				checkCollisonTime = 3000 - checkCollisonTime;
				if (checkCollisonTime <= 0) {
					collisionIgnore = false;
				}
				double temp = checkCollisonTime / 1000.0;
				if (temp >= 0)
					info += String.format("        時間倒數：%.2f", temp);
			}
			else{
				info += "         穿牆技能：∞";
			}
		}

		String info2 = String.format("進度：%d / %d", Achived, goals.size());

		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("Microsoft JhengHei", Font.BOLD, (int)(25 * scale)));
		g.drawString(info, this.width * 5 / 16, 60);
		g.setColor(new Color(0, 204, 0));
		g.drawString(info2, this.width * 5 / 16, 90);


		ArrayList<Actor> world = new ArrayList<>();

		world.addAll(goals);

		if (stealer.getBullet() != null)
			world.add(stealer.getBullet());

		world.addAll(walls);
		world.addAll(hardWalls);
		world.addAll(treasures);

		if (cops.isEmpty() != true) {

			world.addAll(cops);
		}

		world.add(stealer);
		world.add(portal);

		int tempBulletX = -500, tempBulletY = -500;
		/*
		 * record new bullet x,y. If it collides with a wall, delete bullet, initialized
		 * to negative numbers to avoid error
		 */

		for (int i = 0; i < world.size(); i++) {

			Actor item = world.get(i);
			if (item != null && item instanceof Police && forbutton == 0 && executetime % policePeriod == 1 && !cheater.checkUserCommand()) {

				Police cop = (Police) item;
				int policeCanGo = 0; // means next direction police can move
				int accumulate = 0; // avoid police surrounded by box
				while (policeCanGo == 0) {
					if ((accumulate += 1) == 100) {
						
						world.remove(cop);
						cops.remove(cop);
						cop = null;
						break;
					}
					policeCanGo = 1;

					if(isCompletedBool)
						policeCanGo = 0;
					
					toward = cop.nextStep();


					if (checkHardWallCollision(cop, toward)) {
						policeCanGo = 0;
					} else if (checkWallCollision(cop, toward)) {
						policeCanGo = 0;
					} else if (checkBagCollisionforPolice(cop, toward)) {
						policeCanGo = 0;
					} else if (checkPersonAndPersonCollision(cop, stealer, toward)) {
						playerLoss();
						return;
					}
					for (int c = 0; i < cops.size(); c++) { // 做每個警衛比較
						Police pol = cops.get(c);
						if (cop.equals(pol))
							continue;
						if (checkPersonAndPersonCollision(cop, pol, toward)) {
							policeCanGo = 0;

						}

					}
					if (cop.x() == tempBulletX && cop.y() == tempBulletY) {
						stealer.setBullet(null);
						world.remove(cop);
						cop = null;
						break;
					}
					if (stealer.getBullet() != null && cop.x() == stealer.getBullet().x()
							&& cop.y() == stealer.getBullet().y()) {
						stealer.setBullet(null);
						world.remove(cop);
						cop = null;
						break;
					}
					
				}
				if (cop == null) {
					continue;
				}
				cop.setsituation_change(toward);

				g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
			}

			if (item instanceof Treasure) {

				g.drawImage(item.getImage(), item.x(), item.y(), this);
				if (item.x() == tempBulletX && item.y() == tempBulletY) // bullet collides with treasure
					stealer.setBullet(null);

			} else if (item instanceof Portal) {

				Portal portalRef = (Portal) item;
				if (portalRef.getIsActive() == 1){
					portalRef.animation();
					g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
				}

			} else if (item instanceof Bullet) {

				Bullet bulletRef = (Bullet) item;
				if (bulletRef != null && bulletRef.getMaxRange() > 0) {
					if(forbutton == 0)
						bulletRef.updateXY();
					tempBulletX = bulletRef.x();
					tempBulletY = bulletRef.y();
					int bulletExist = 1;
					if (!cops.isEmpty()) {
						for (int k = 0; k < cops.size(); k++) {
							Police cop = cops.get(k);
							if (cop.x() == tempBulletX && cop.y() == tempBulletY) {
								stealer.setBullet(null);
								cops.remove(k);
								world.remove(cop);
								cop = null;
								bulletExist = 0;
								continue;
							}
						}

					}
					if (bulletExist == 1)
						g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
				} else
					stealer.setBullet(null);

			} else if (item instanceof Wall || item instanceof HardWall) { // wall

				g.drawImage(item.getImage(), item.x(), item.y(), this);
				if (item.x() == tempBulletX && item.y() == tempBulletY) // bullet collides with wall
					stealer.setBullet(null);
			

			} else if (item instanceof Player) {
				playerX = item.x() + 2;
				playerY = item.y() + 2;
				
				int tempX = playerX, tempY = playerY;
				if(lost)
					g.drawImage(item.getImage(), tempX - 20, tempY - 20, this);
				else
					g.drawImage(item.getImage(), tempX, tempY, this);

			} else { // goal
				g.drawImage(item.getImage(), item.x(), item.y(), this);
			}


			if(bufferedFrames < 21000){ // arrow image(for opening)
				if((bufferedFrames / 3000) % 2 == 0){
					g.drawImage(arrowImage, playerX - 5, playerY - 60, this);
				}
				bufferedFrames++;
			}

			g.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
			g.setColor(new Color(0, 0, 0));
			String information = "[ESC or P]-選單    [X]-穿牆技能    [Z]-傳送門    [SPACE]-武器";
			g.drawString(information, (int)(scale * this.width / 2 - 320), this.height - 40);

		}
		if (forbutton == 1)
			forbutton = 0; // prevent repeated execution when bottom is clicked

		if(lost) // buffered frames
			lossBuffer++;
		if(isCompletedBool)
			wonBuffer++;

		isCompleted();
	}

	@Override
	public void paintComponent(Graphics g) {
		graphic = g;
		super.paintComponent(g);
		buildWorld(g);
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (lost || isCompletedBool || restarted) {
				return;
			}

			int key = e.getKeyCode();

			switch (key) {
				case KeyEvent.VK_LEFT:

					currentlyFacing = LEFT;
					cheater.pushCommand(LEFT);
					stealer.setPlayerImage(LEFT);

					if (checkCollisions(stealer, LEFT)) {
						return;
					}

					stealer.move(-SPACE, 0);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							Police cop = cops.get(i);
							if (checkPersonAndPersonCollision(stealer, cop, LEFT)) {
								playerLoss();
								return;
							}
						}

					}
					
					break;

				case KeyEvent.VK_RIGHT:

					currentlyFacing = RIGHT;
					cheater.pushCommand(RIGHT);
					stealer.setPlayerImage(RIGHT);

					if (checkCollisions(stealer, RIGHT)) {
						return;
					}

					stealer.move(SPACE, 0);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							Police cop = cops.get(i);
							if (checkPersonAndPersonCollision(stealer, cop, RIGHT)) {
								playerLoss();
								return;
							}
						}

					}
					
					break;

				case KeyEvent.VK_UP:

					if(gamePause){
						if(pauseSelect != 1)
							pauseSelect--;

						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bagSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}
						return;
					}
					currentlyFacing = UP;
					cheater.pushCommand(UP);
					stealer.setPlayerImage(UP);
					
					if (checkCollisions(stealer, UP)) {
						return;
					}

					stealer.move(0, -SPACE);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							Police cop = cops.get(i);
							if (checkPersonAndPersonCollision(stealer, cop, UP)) {
								playerLoss();
								return;
							}
						}

					}
					
					break;

				case KeyEvent.VK_DOWN:
					
					if(gamePause){
						if(pauseSelect != 3)
							pauseSelect++;
					
						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bagSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}
						return;
					}
					currentlyFacing = DOWN;
					cheater.pushCommand(DOWN);
					stealer.setPlayerImage(DOWN);

					if (checkCollisions(stealer, DOWN)) {
						return;
					}

					stealer.move(0, SPACE);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							Police cop = cops.get(i);
							if (checkPersonAndPersonCollision(stealer, cop, DOWN)) {
								playerLoss();
								return;
							}
						}

					}
					
					break;

				case KeyEvent.VK_Z: // portal

					if (portal.getIsActive() == 1) {
						for (int i = 0; i < treasures.size(); i++) {
							Treasure ref = treasures.get(i);
							if (ref.x() == portal.x() && ref.y() == portal.y()) /* check if portal is blocked by box */
								return;

						}
						stealer.setX(portal.x());
						stealer.setY(portal.y());
						portal.setIsActive(0);
					} else {
						if (portal.getAvailability() == 0)
							return;
						portal.setAvailability(portal.getAvailability() - 1);
						portal.setX(stealer.x());
						portal.setY(stealer.y());
						portal.setIsActive(1);
					}
					break;

				case KeyEvent.VK_SPACE: // bullet

					if (stealer.getBullet() != null)
						return;
					else if (stealer.getRifleAvailable() == 1 && stealer.getAmmo() > 0) {
						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bulletSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}
						
						Bullet newBullet = new Bullet(stealer.x(), stealer.y(), currentlyFacing);
						newBullet.setImage(imageManager.getBulletImage());
						stealer.setBullet(newBullet);
						stealer.setAmmo(stealer.getAmmo() - 1);
					}
					
					break;

				case KeyEvent.VK_X: // penetrate

					if (penetrateNotUsed) {
						collisionIgnore = true;
						collisionIgnoreTime = new Date().getTime();
						penetrateNotUsed = false;
					}
					break;

				case KeyEvent.VK_ESCAPE:// pause
					gamePause = true;
					break;
				
				case KeyEvent.VK_P: // pause
					gamePause = true;
					break;
				
				case KeyEvent.VK_ENTER:
					if(gamePause){

						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bagSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}

						switch(pauseSelect){
							case 1:
								gamePause = false;
								break;
							case 2:
								gamePause = false;
								restartLevel();
								break;
							case 3:
								gamePause = false;
								closeSignal = true;
							default:
								break;
						}
						
						pauseSelect = 1;
					}
					break;
				
				case KeyEvent.VK_S:
					cheater.pushChar('s');
					break;
				
				case KeyEvent.VK_O:
					cheater.pushChar('o');
					break;
				
				case KeyEvent.VK_K:
					cheater.pushChar('k');
					break;
				
				case KeyEvent.VK_B:
					cheater.pushChar('b');
					break;
				
				case KeyEvent.VK_A:
					cheater.pushChar('a');
					break;
				
				case KeyEvent.VK_N:
					cheater.pushChar('n');
					break;

				default:
					break;
			}
			forbutton = 1;
			repaint();
		}
	}

	private boolean checkCollisions(Actor a, int d) {
		// a -> actor, d -> direction
		if (checkHardWallCollision(a, d) || checkWallCollision(a, d) || checkBagCollision(d))
			return true;
		return false;
	}

	private boolean checkHardWallCollision(Actor actor, int type) {
		int i;

		switch (type) {
			case LEFT:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isLeftCollision(hardWall)) {
						return true;
					}
				}
				break;

			case RIGHT:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isRightCollision(hardWall)) {
						return true;
					}
				}
				break;

			case UP:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isTopCollision(hardWall)) {
						return true;
					}
				}
				break;

			case DOWN:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isBottomCollision(hardWall)) {
						return true;
					}
				}
				break;

			default:
				break;
		}

		return false;
	}

	private boolean checkWallCollision(Actor actor, int type) {

		if (actor.getActorName() == "player") {
			if (collisionIgnore) {
				return false;
			}
		}

		switch (type) {
			case LEFT:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isLeftCollision(wall)) {
						return true;
					}
				}
				break;

			case RIGHT:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isRightCollision(wall)) {
						return true;
					}
				}
				break;

			case UP:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isTopCollision(wall)) {
						return true;
					}
				}
				break;

			case DOWN:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isBottomCollision(wall)) {
						return true;
					}
				}
				break;

			default:
				break;
		}

		return false;
	}

	private boolean checkBagCollision(int type) {
		try {
			sounds = new BackgroundMP3Player();
			sounds.setSound(sound.bagSound.ordinal());
			
		} catch (FileNotFoundException | JavaLayerException e1) {
			System.out.printf("music err");
		}
		switch (type) {

			case LEFT:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (stealer.isLeftCollision(box)) {
						for (int j = 0; j < treasures.size(); j++) {
							Treasure item = treasures.get(j);
							if (!box.equals(item)) {
								if (box.isLeftCollision(item)) {
									return true;
								}
							}
							if (checkWallCollision(box, LEFT) || checkHardWallCollision(box, LEFT)) {
								return true;
							}
						}

						if (cops != null && !checkBagCollisiontoPolice(box.getX() - SPACE, box.getY())) {
							box.move(-SPACE, 0);
							sounds.play();
						} else if (cops.isEmpty()) { // when police death ,the way can prevent bug
							box.move(-SPACE, 0);
							sounds.play();
						} else
							return true;
					}
				}
				return false;

			case RIGHT:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (stealer.isRightCollision(box)) {
						for (int j = 0; j < treasures.size(); j++) {
							Treasure item = treasures.get(j);
							if (!box.equals(item)) {
								if (box.isRightCollision(item)) {
									return true;
								}
							}
							if (checkWallCollision(box, RIGHT) || checkHardWallCollision(box, RIGHT)) {
								return true;
							}
						}
						if (cops != null && !checkBagCollisiontoPolice(box.getX() + SPACE, box.getY())) {
							box.move(SPACE, 0);
							sounds.play();
						} else if (cops.isEmpty()) {
							box.move(SPACE, 0);
							sounds.play();
						} else
							return true;

					}
				}
				return false;

			case UP:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (stealer.isTopCollision(box)) {
						for (int j = 0; j < treasures.size(); j++) {
							Treasure item = treasures.get(j);
							if (!box.equals(item)) {
								if (box.isTopCollision(item)) {
									return true;
								}
							}

							if (checkWallCollision(box, UP) || checkHardWallCollision(box, UP)) {
								return true;
							}
						}

						if (cops != null && !checkBagCollisiontoPolice(box.getX(), box.getY() - SPACE)) {
							box.move(0, -SPACE);
							sounds.play();
						} else if (cops.isEmpty()) {
							box.move(0, -SPACE);
							sounds.play();
						} else
							return true;

					}
				}
				return false;

			case DOWN:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (stealer.isBottomCollision(box)) {
						for (int j = 0; j < treasures.size(); j++) {
							Treasure item = treasures.get(j);
							if (!box.equals(item)) {
								if (box.isBottomCollision(item)) {
									return true;
								}
							}

							if (checkWallCollision(box, DOWN) || checkHardWallCollision(box, DOWN)) {
								return true;
							}
						}
						if (cops != null && !checkBagCollisiontoPolice(box.getX(), box.getY() + SPACE)) {
							box.move(0, SPACE);
							sounds.play();
						} else if (cops.isEmpty()) {
							box.move(0, SPACE);
							sounds.play();
						} else
							return true;

					}
				}
				break;

			default:
				break;
		}
		
		return false;
	}

	private boolean checkBagCollisiontoPolice(int bag_x, int bag_y) {
		for (int i = 0; i < cops.size(); i++) {
			Police temp = cops.get(i);
			if (judge_XandY_Collision(temp.getx(), temp.gety(), bag_x, bag_y))
				return true;

		}
		return false;
	}

	private boolean judge_XandY_Collision(int x, int y, int x1, int y1) {
		if (x1 == x && y1 == y)
			return true;
		else
			return false;
	}

	private boolean checkBagCollisionforPolice(Actor actor, int type) {

		switch (type) {
			case LEFT:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (actor.isLeftCollision(box)) {
						return true;
					}
				}
				return false;

			case RIGHT:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (actor.isRightCollision(box)) {
						return true;
					}
				}
				return false;

			case UP:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (actor.isTopCollision(box)) {
						return true;
					}
				}
				return false;

			case DOWN:
				for (int i = 0; i < treasures.size(); i++) {
					Treasure box = treasures.get(i);
					if (actor.isBottomCollision(box)) {
						return true;
					}
				}
				return false;

			default:
				break;
		}
		return false;
	}

	private Boolean checkPersonAndPersonCollision(Actor actor, Actor actor1, int type) {
		if (actor.x() == actor1.x() && actor.y() == actor1.y()) {
			return true;
		}
		return false;
	}

	public void playerLoss() {
		lost = true;
		lossTime = new Date().getTime();
		cheater.deactivate(); // deactivate both cheating
	}

	public void isCompleted() {
		int finishedBags = 0;
		int canGetAmmocount = 0;

		for (int i = 0; i < treasures.size(); i++) {
			Treasure box = treasures.get(i);

			for (int j = 0; j < goals.size(); j++) {
				Goal goal = goals.get(j);
				if (box.x() == goal.x() && box.y() == goal.y()) {
					finishedBags += 1;
					if(box.canGetAmmo()){
						box.getAmmo();
						canGetAmmocount++;
					}
				}
			}
		}
		if (finishedBags > Achived) {
			Achived += 1;
			stealer.setAmmo(stealer.getAmmo() + 2 * canGetAmmocount);
		}
		else if(finishedBags < Achived){
			Achived--;
		}
		if (finishedBags == goals.size()) {
			isCompletedBool = true;
			wonTime = new Date().getTime();
			cheater.deactivate();
		}
	}

	private void restartLevel() {

		cheater.deactivate();

		goals.clear();
		treasures.clear();
		walls.clear();
		cops.clear();
		hardWalls.clear();

		isCompletedBool = false;
		Achived = 1;
		lossBuffer = 0;
		
		restarted = true;
		restartTime = new Date().getTime();

		initWorld();
	}

	public boolean isLost() {
		return lost;
	}

	public boolean getisCompleted() {
		return isCompletedBool;
	}

	public boolean goNextStage(){
		
		return nextStage;
	}

	public boolean closeAct(){
		return closeSignal;
	}

	public boolean needCloseMusic(){
		return justCloseTheMusicPls;
	}
}
