package java2020.finalProject;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Time;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.util.Date;
import java.util.Random;
import java.util.ArrayList;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class Stage extends JPanel {

	public int executetime = 0; // repaint time
	public static int forbutton = 0;

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

	private boolean trigger = false;
	private boolean isCompletedBool = false;
	private boolean lost = false;
	private boolean restarted = false; // restart frame
	private boolean restartBuffer = false; // restart buffering(for 0.3sec)
	private boolean gamePause = false;
	private boolean nextStage = false;
	private boolean closeSignal = false;
	private boolean ending = false;
	private boolean checkLost = false;

	private final int MARGIN = (int) (40 * scale);
	private final int SPACE = (int) (40 * scale); // Object side length
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWN = 4;
	private final int LevelCount;

	private int width; // Stage width
	private int height; // Stage height
	private int policePeriod;
	private int toward = 1;
	private int achived = 0; // treasures achived
	private int playerSkin;
	private int mapSelection;
	private int bufferedFrames = 0; // for arrow image
	private int mapX, mapY;
	private int lossBuffer = 0; // loss buffer(don't close immediately)
	private int wonBuffer = 0; // don't switch immediately
	private int explodeTime = 0;
	
	private long collisionIgnoreTime;
	private long restartTime;
	private long lossTime;
	private long wonTime;
	private long timeStart;
	private long timeMin;
	private long timeSec;

	private ArrayList<Police> cops;
	private ArrayList<Wall> walls;
	private ArrayList<Treasure> treasures;
	private ArrayList<Goal> goals;
	private ArrayList<HardWall> hardWalls;
	
	private Player stealer;
	private Portal portal;
	private Bomb bomb;
	private Map map;

	private EndingAnimation animate = new EndingAnimation();
	private CollisionDetector collisions = new CollisionDetector();
	private Panel panel;
	private PausePanel pause;

	private BackgroundMP3Player sounds;

	private Graphics graphic; // for global using
	private Image arrowImage = new ImageIcon().getImage();

	private ImageManager imageManager = new ImageManager(true);
	private CheatManager cheater;

	private enum sound {
		bulletSound, bagSound, bombSound
	};

	public Stage(int playerSkinChoosen, int level) {
		timeStart = System.currentTimeMillis();

		this.mapSelection = level;
		this.playerSkin = playerSkinChoosen;

		this.width = (int)dimension.getWidth();
		this.height = (int)dimension.getHeight();

		panel = new Panel(scale, width, height);
		pause = new PausePanel(scale, width, height);
		map = new Map();

		this.LevelCount = map.getMapCount();

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

		walls = new ArrayList<>();
		treasures = new ArrayList<>();
		goals = new ArrayList<>();
		hardWalls = new ArrayList<>();
		cops = new ArrayList<>();

		cheater = new CheatManager();
		portal = new Portal(0, 0);

		arrowImage = imageManager.getArrowImage();

		int x = 0;
		int y = MARGIN + 50;

		String level = map.getMap(mapSelection);
		if (level == "") // if map is none, return
			return;

		policePeriod = 10;
		achived = 0;

		collisions.setCollisionIgnore(false); // penatrate init
		nextStage = false;
		ending = false;
		bufferedFrames = 0;
		pause.setSelection(1);
		lossBuffer = 0;
		wonBuffer = 0;

		mapX = level.indexOf("\n", 0); // len of map width
		mapY = level.length() / mapX; // len of map height

		mapX = mapX * SPACE; // pixel of map width
		mapY = mapY * SPACE; // pixel of map height

		int modifyX = (this.width - mapX) / 2;
		int modifyY = 20;

		for (int i = 0; i < level.length(); i++) { // set width, height, Objects specified by the string
		
			switch (level.charAt(i)) {

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

				case '^':
					bomb = new Bomb(x + modifyX, y + modifyY);
					bomb.setImageArray(imageManager.getBombImage());
					x += SPACE;
					break;

				case ' ':
					x += SPACE;
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
					stealer = new Player(x + modifyX, y + modifyY, playerSkin);
					x += SPACE;
					break;

				// go next line and set x = 0
				case '\n':
					y += SPACE;
					x = 0;
					break;

				default:
					System.out.printf("unexpected item: \"%c\" in map %d\n", level.charAt(i), mapSelection);
					break;
			}
		}
	}

	private void buildWorld(Graphics g) {
		
		// all completed, play ending animation
		if (mapSelection == LevelCount + 1) {
			nextStage = true;
			ending = true;
			animate.ending(g);
			if (animate.over())
				closeSignal = true;
			repaint();
			return;
		}

		if (lost) {
			checkLost = true;
			stealer.playerExplode();
		}

		if (restarted) {
			trigger = false;
			checkLost = false;
			explodeTime = 0;
			timeStart = System.currentTimeMillis();
			if (panel.drawRestart(g, restartTime))
				return;
			else
				restarted = false;
		}

		if (lossBuffer > 15 && lost) {
			Long time = new Date().getTime();
			if (!panel.drawLoss(g, time - lossTime)) {
				lost = false;
            	restartLevel();
			}
			return;
		}

		if (wonBuffer > 15 && isCompletedBool) {
			Long time = new Date().getTime();
			if (panel.drawWon(g, time - wonTime)) {
				return;
			} else {
				isCompletedBool = false;
				nextStage = true;
				mapSelection++;
				initWorld();
			}
		}

		if (gamePause) {
			pause.draw(g, mapSelection);
			return;
		}

		String info = String.format("傳送門：%d        子彈：%2d", portal.getAvailability(), stealer.getAmmo());

		if (collisions.getCollisionIgnore()) {
			Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime;
			checkCollisonTime = 3000 - checkCollisonTime;

			if (checkCollisonTime <= 0)
				collisions.setCollisionIgnore(false);

			double temp = checkCollisonTime / 1000.0;
			if (temp >= 0)
				info += String.format("        技能時間：%.2f", temp);
		} else {
			if (stealer.getPenetrateSkill())
				info += "        穿牆技能：可用";
			else
				info += "        穿牆技能：不可用";
		}

		if (cheater.available()) {
			info = "傳送門：∞        子彈：∞";
			stealer.setPenetrateSkill(true);;
			stealer.setAmmo(99997);
			portal.setAvailability(99999);

			if (collisions.getCollisionIgnore()) {
				Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime;
				checkCollisonTime = 3000 - checkCollisonTime;
				if (checkCollisonTime <= 0)
					collisions.setCollisionIgnore(false);

				double temp = checkCollisonTime / 1000.0;
				if (temp >= 0)
					info += String.format("        時間倒數：%.2f", temp);
			} else {
				info += "         穿牆技能：∞";
			}
		}

		String info2 = String.format("進度：%d / %d", achived, goals.size());

		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("Microsoft JhengHei", Font.BOLD, (int) (25 * scale)));
		g.drawString(info, this.width * 5 / 16, 60);
		g.setColor(new Color(0, 204, 0));
		g.drawString(info2, this.width * 5 / 16, 90);

		ArrayList<Object> world = new ArrayList<>();

		world.addAll(walls);
		world.addAll(hardWalls);
		world.addAll(treasures);
		world.addAll(goals);
		world.add(stealer);
		world.add(portal);

		if (stealer.getBullet() != null)
			world.add(stealer.getBullet());

		if (cops.isEmpty() != true)
			world.addAll(cops);

		if (bomb != null)
			world.add(bomb);

		// record new bullet x,y. If it collides with a wall, delete bullet,
		// initialized to negative numbers to avoid error
		int tempBulletX = -500, tempBulletY = -500;

		for (int i = 0; i < world.size(); i++) {

			Object item = world.get(i);

			if (item != null && item instanceof Police && forbutton == 0 && executetime % policePeriod == 1
					&& !cheater.checkUserCommand()) {

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

					if (isCompletedBool)
						policeCanGo = 0;

					toward = cop.nextStep();

					if (collisions.checkHardWallCollision(cop, toward, hardWalls)) {
						policeCanGo = 0;
					} else if (collisions.checkWallCollision(cop, toward, walls)) {
						policeCanGo = 0;
					} else if (collisions.checkBagCollisionforPolice(cop, toward, treasures)) {
						policeCanGo = 0;
					} else if (collisions.checkPersonAndPersonCollision(cop, stealer, toward)) {
						playerLoss();
						return;
					}

					for (int c = 0; i < cops.size(); c++) { // 做每個警衛比較
						if (!cop.equals(cops.get(c)))
							if (collisions.checkPersonAndPersonCollision(cop, cops.get(c), toward))
								policeCanGo = 0;
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

				if (cop != null)
					cop.setSituation(toward);

				g.drawImage(item.getImage(), item.x(), item.y(), this);

			} else if (item instanceof Bomb) {
				if (lost)
					g.drawImage(item.getImage(), item.x(), item.y(), this);
				else if ((executetime / 10) % 2 == 1)
					g.drawImage(item.getImageArray(1), item.x(), item.y(), this);
				else
					g.drawImage(item.getImageArray(0), item.x(), item.y(), this);

			} else if (item instanceof Treasure) {

				g.drawImage(item.getImage(), item.x(), item.y(), this);
				if (item.x() == tempBulletX && item.y() == tempBulletY) // bullet collides with treasure
					stealer.setBullet(null);

			} else if (item instanceof Portal) {

				Portal portalRef = (Portal) item;
				if (portalRef.getIsActive()) {
					portalRef.animation();
					g.drawImage(item.getImage(), item.x(), item.y(), this);
				}

			} else if (item instanceof Bullet) {

				Bullet bulletRef = (Bullet) item;
				if (bulletRef != null && bulletRef.getMaxRange() > 0) {
					if (forbutton == 0)
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
						g.drawImage(item.getImage(), item.x(), item.y(), this);
				} else
					stealer.setBullet(null);

			} else if (item instanceof Wall || item instanceof HardWall) { // wall

				g.drawImage(item.getImage(), item.x(), item.y(), this);
				if (item.x() == tempBulletX && item.y() == tempBulletY) // bullet collides with wall
					stealer.setBullet(null);

			} else if (item instanceof Player) {
				if (checkLost)
					g.drawImage(item.getImage(), item.x() - 20, item.y() - 20, this);
				else
					g.drawImage(item.getImage(), item.x(), item.y(), this);

			} else { // goal
				g.drawImage(item.getImage(), item.x(), item.y(), this);
			}

			if (bufferedFrames < 24000) { // arrow image (for opening)
				if ((bufferedFrames / 3000) % 2 == 0) {
					g.drawImage(arrowImage, stealer.x() - 5, stealer.y() - 60, this);
				}
				bufferedFrames++;
			}

			if (bomb != null) {
				g.setFont(new Font("Microsoft JhengHei", Font.BOLD, 50));
				g.setColor(new Color(255, 0, 0));
				int spendingTime = (int)(System.currentTimeMillis() - timeStart) / 1000;

				int remainingTime = ((mapSelection / 3) + 1) * 200 - spendingTime;
				if (remainingTime <= 0) {
					remainingTime = 0;
					playerLoss();
				}
				g.drawString(String.format("%d:%02d", remainingTime / 60, remainingTime % 60), 100, 100);
			}

			g.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
			g.setColor(new Color(0, 0, 0));
			g.drawString("[ESC or P]-選單    [X]-穿牆技能    [Z]-傳送門    [SPACE]-武器", (int)(scale * this.width/2 - 320), this.height - 40);

		}
		if (forbutton == 1)
			forbutton = 0; // prevent repeated execution when bottom is clicked

		if (lost) { // buffered frames

			lossBuffer++;
			if (bomb != null) {
				Image[] bombPics = imageManager.getExplodeImage();

				g.drawImage(bombPics[explodeTime], bomb.x() - 60, bomb.y() - 60, 120, 120, this);

				if (explodeTime++ > 9)
					explodeTime = 10;

				playExploSound();
				bomb.setImage(bombPics[0]);
			}

		}
		if (isCompletedBool)
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

			// don't do anything when special condition
			if(lost || isCompletedBool || restarted || ending) {
				return;
			}

			int key = e.getKeyCode();

			switch (key) {
				case KeyEvent.VK_LEFT:

					cheater.pushCommand(LEFT);
					stealer.setPlayerDir(LEFT);

					if (collisions.checkCollisions(stealer, LEFT, hardWalls, walls, treasures, cops))
						return;

					stealer.move(-SPACE, 0);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							if (collisions.checkPersonAndPersonCollision(stealer, cops.get(i), LEFT)) {
								playerLoss();
								return;
							}
						}

					}

					break;

				case KeyEvent.VK_RIGHT:

					cheater.pushCommand(RIGHT);
					stealer.setPlayerDir(RIGHT);

					if (collisions.checkCollisions(stealer, RIGHT, hardWalls, walls, treasures, cops))
						return;

					stealer.move(SPACE, 0);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							if (collisions.checkPersonAndPersonCollision(stealer, cops.get(i), RIGHT)) {
								playerLoss();
								return;
							}
						}

					}

					break;

				case KeyEvent.VK_UP:

					if (gamePause) {
						pause.selectionUp();

						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bagSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}
						return;
					}

					cheater.pushCommand(UP);
					stealer.setPlayerDir(UP);

					if (collisions.checkCollisions(stealer, UP, hardWalls, walls, treasures, cops))
						return;

					stealer.move(0, -SPACE);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							if (collisions.checkPersonAndPersonCollision(stealer, cops.get(i), UP)) {
								playerLoss();
								return;
							}
						}

					}

					break;

				case KeyEvent.VK_DOWN:

					if (gamePause) {
						pause.selectionDown();

						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bagSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}
						return;
					}

					cheater.pushCommand(DOWN);
					stealer.setPlayerDir(DOWN);

					if (collisions.checkCollisions(stealer, DOWN, hardWalls, walls, treasures, cops))
						return;

					stealer.move(0, SPACE);
					if (!cops.isEmpty()) {
						for (int i = 0; i < cops.size(); i++) {
							if (collisions.checkPersonAndPersonCollision(stealer, cops.get(i), DOWN)) {
								playerLoss();
								return;
							}
						}

					}

					break;

				case KeyEvent.VK_Z: // portal

					if (portal.getIsActive()) {
						for (int i = 0; i < treasures.size(); i++) {
							Treasure ref = treasures.get(i);
							if (ref.x() == portal.x() && ref.y() == portal.y()) /* check if portal is blocked by box */
								return;

						}
						stealer.setX(portal.x());
						stealer.setY(portal.y());
						portal.setIsActive(false);
					} else {
						if (portal.getAvailability() == 0)
							return;
						portal.setAvailability(portal.getAvailability() - 1);
						portal.setX(stealer.x());
						portal.setY(stealer.y());
						portal.setIsActive(true);
					}
					break;

				case KeyEvent.VK_SPACE: // bullet

					if (stealer.getBullet() != null)
						return;
					else if (stealer.getAmmo() > 0) {
						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bulletSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}

						Bullet newBullet = new Bullet(stealer.x(), stealer.y(), stealer.getDir());
						newBullet.setImage(imageManager.getBulletImage());
						stealer.setBullet(newBullet);
						stealer.setAmmo(stealer.getAmmo() - 1);
					}

					break;

				case KeyEvent.VK_X: // penetrate skill

					if (stealer.getPenetrateSkill()) {
						collisions.setCollisionIgnore(true);
						collisionIgnoreTime = new Date().getTime();
						stealer.setPenetrateSkill(false);
					}
					break;

				case KeyEvent.VK_ESCAPE: // game pause
					gamePause = true;
					break;

				case KeyEvent.VK_P: // as same as esc
					gamePause = true;
					break;

				case KeyEvent.VK_ENTER:
					if (gamePause) {

						try {
							sounds = new BackgroundMP3Player();
							sounds.setSound(sound.bagSound.ordinal());
							sounds.play();
						} catch (FileNotFoundException | JavaLayerException e1) {
							System.out.printf("music err");
						}

						switch (pause.getSelection()) {
							case 1:
								break;
							case 2:
								restartLevel();
								break;
							case 3:
								closeSignal = true;
							default:
								break;
						}

						gamePause = false;
						pause.setSelection(1);
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

	private void playExploSound() {
		if (!trigger) {
			trigger = true;

			try {
				sounds = new BackgroundMP3Player();
				sounds.setSound(sound.bombSound.ordinal());
				sounds.play();
			} catch (FileNotFoundException | JavaLayerException e1) {
				System.out.printf("music err");
			}
		}
	}

	public void playerLoss() {
		lost = true;
		lossTime = new Date().getTime();
		cheater.deactivate(); // deactivate both cheating
	}

	public void isCompleted() {
		int finishedBags = 0;
		int canGetAmmoCount = 0;

		for (int i = 0; i < treasures.size(); i++) {
			Treasure box = treasures.get(i);

			for (int j = 0; j < goals.size(); j++) {
				Goal goal = goals.get(j);
				if (box.x() == goal.x() && box.y() == goal.y()) {
					finishedBags += 1;
					if (box.canGetAmmo()) {
						box.getAmmo();
						canGetAmmoCount++;
					}
				}
			}
		}
		if (finishedBags > achived) {
			achived++;
			stealer.setAmmo(stealer.getAmmo() + 2 * canGetAmmoCount);
		} else if (finishedBags < achived) {
			achived--;
		}
		if (finishedBags == goals.size()) {
			isCompletedBool = true;
			wonTime = new Date().getTime();
			cheater.deactivate();
		}
	}

	private void restartLevel() {
		isCompletedBool = false;
		lossBuffer = 0;

		restarted = true;
		restartTime = new Date().getTime();

		initWorld();
	}

	public boolean getisCompleted() {
		return isCompletedBool;
	}

	public boolean goNextStage() {
		return nextStage;
	}

	public boolean closeAct() {
		return closeSignal;
	}

	public void setNextStage(Boolean b) {
		nextStage = b;
	}
}
