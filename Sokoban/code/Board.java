package java2020.finalProject;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.beancontext.BeanContextEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Date;
import java.awt.Font;
import java.awt.Color;
import java.util.Random;

public class Board extends JPanel {

	// constant
	private final int OFFSET = 30;
	private final int SPACE = 40;// actor side length
	private final int LEFT_COLLISION = 1;
	private final int RIGHT_COLLISION = 2;
	private final int TOP_COLLISION = 3;
	private final int BOTTOM_COLLISION = 4;
	private final int selection;
	private final int faceLeft = 1;
	private final int faceRight = 2;
	private final int faceUp = 3;
	private final int faceDown = 4;

	private int currentlyFacing = 3;
	public int executetime = 0;// repaint time
	public static int forbutton = 0;
	private ArrayList<Police> cops;
	private ArrayList<Wall> walls;
	private ArrayList<Baggage> baggs;
	private ArrayList<Area> areas;
	private ArrayList<HardWall> hardWalls;

	//private Police cop;
	private Player soko;
	private Portal portal;

	private int width = 0;// board width
	private int height = 0;// board height
	private long collisionIgnoreTime;

	private boolean isCompleted = false;
	private boolean lost = false;
	private boolean collisionIgnore = false;// penetrate skill
	private boolean penetrateNotUsed = true;// penetrate skill

	Random ran = new Random(new Date().getTime());
	private int policePeriod;
	private int policeStep = 2;
	private int stepsNow = policeStep;
	private int toward = (ran.nextInt(40) % 4) + 1;

	private final int playerSkinOne = 1;
	private final int playerSkinTwo = 2;
	private int playerSkin;

	public Board(int playerSkinChoosen, int level) {
		selection = level;
		if (level == 3) {
			policePeriod = 4;
			File temp = new File("");
			String path = temp.getAbsolutePath();

			if (!path.contains("code"))
				path = "BGM/tempBGM.mp3";
			else
				path = path.replaceAll("code", "BGM/tempBGM.mp3");

			// play music here(failed (tried once) )

		} else if (level == 2)
			policePeriod = 8;
		else
			policePeriod = 12;

		if(playerSkinChoosen == playerSkinTwo)
			playerSkin = playerSkinTwo;
		else // playerSkinChoosen == playerSkinOne, become a default
			playerSkin = playerSkinOne;

		initBoard();
	}

	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		initWorld();
	}

	public int getBoardWidth() {
		return this.width;
	}

	public int getBoardHeight() {
		return this.height;
	}

	private void initWorld() {

		walls = new ArrayList<>();
		baggs = new ArrayList<>();
		areas = new ArrayList<>();
		hardWalls = new ArrayList<>();
		cops =new ArrayList<>();
		String level;
		int x = OFFSET;
		int y = OFFSET;

		Map maptest;

		maptest = new Map();
		level = (String) (maptest.getMap(selection));
		portal = new Portal(0, 0);

		penetrateNotUsed = true;// penetrate init
		collisionIgnore = false;// penetrate init

		for (int i = 0; i < level.length(); i++) {// set width,height, actors specified by the string
			char item = level.charAt(i);
			switch (item) {
				case '\n':
					y += SPACE;
					if (this.width < x) {
						this.width = x;
					}
					x = OFFSET;
					break;
				case '!':
					cops.add(new Police(x, y));
					System.out.printf("x=%d,y=%d",x,y);
					x += SPACE;
					;
					break;
				case '#':
					walls.add(new Wall(x, y));// create wall at (x,y)
					x += SPACE;
					break;

				case 'H':
					hardWalls.add(new HardWall(x, y)); // hard wall cannot be penetrated
					x += SPACE;
					break;

				case '$':
					baggs.add(new Baggage(x, y));
					x += SPACE;
					break;

				case '.':
					areas.add(new Area(x, y)); // target area
					x += SPACE;
					break;

				case '@':
					soko = new Player(x, y, playerSkin);// player
					x += SPACE;
					break;

				case ' ':
					x += SPACE;
					break;

				case '%':
					areas.add(new Area(x, y));
					baggs.add(new Baggage(x, y));
					x += SPACE;

				default:
					break;
			}

			height = y;
		}
	}

	private void buildWorld(Graphics g) {

		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (collisionIgnore) {
			// formal version
			/*
			 * Long checkCollisonTime = new Date().getTime() - collisionIgnoreTime; if
			 * (checkCollisonTime > 3000) { collisionIgnore = false; }
			 */

			// do nothing(debug version)
			int do_nothing;
		}

		String info = "\"portals left:" + portal.getAvailability();

		info += "\"    \"rifle availabilty = " + soko.getRifleAvailable();
		info += "\"    \"ammo = " + soko.getAmmo() + "\"";
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font("default", Font.PLAIN, 25));
		g.drawString(info, 25, 20);

		ArrayList<Actor> world = new ArrayList<>();
		world.addAll(areas);

		if (soko.getBullet() != null)
			world.add(soko.getBullet());
		world.addAll(walls);
		world.addAll(hardWalls);
		world.addAll(baggs);

		//if (cop != null)
		world.addAll(cops);
		world.add(soko);
		world.add(portal);

		int tempBulletX = -500, tempBulletY = -500;
		/*
		 * record new bullet x,y. If it collides with a wall, delete bullet, initialized
		 * to negative numbers to avoid error
		 */

		for (int i = 0; i < world.size(); i++) {

			Actor item = world.get(i);
			if (item != null && item instanceof Police && forbutton == 0 && executetime % policePeriod == 1) {
				Police cop = (Police) item;
				int policeCanGo=0;//means next direction police can move  
				while (policeCanGo==0) {
					policeCanGo=1;
					if (stepsNow == 0) {
						toward = (ran.nextInt(40) % 4) + 1;
						stepsNow = policeStep;
					} else {
						stepsNow--;
					}

					System.out.print(toward);
					if (checkHardWallCollision(cop, toward)) {
						policeCanGo=0;
					} else if (checkWallCollision(cop, toward)) {
						policeCanGo=0;
					} else if (checkBagCollisionforPolice(cop, toward)) {
						policeCanGo=0;
					} else if (checkPersonAndPersonCollision(cop, soko, toward)) {
						policeCanGo=0;
					}
					for (int c = 0; i < cops.size(); c++) {//做每個警衛比較
						Police pol= cops.get(c);
						if(cop.equals(pol))continue;
						if (checkPersonAndPersonCollision(cop,pol,toward)) {
							policeCanGo=0;
						}
					}
					if (cop.x() == tempBulletX && cop.y() == tempBulletY) {
						soko.setBullet(null);
						System.out.printf("2cop numbefor%d",cops.size());
						world.remove(cop);
						System.out.printf("2cop numafter%d",cops.size());
						cop = null;
						System.out.printf("cop lead");
						break;
					} 
					/*else
						break;*/
				}
				if (cop == null) {
					System.out.print("bullet contact cop!!\n");
					continue;
				}
				cop.setsituation_change(toward);

				g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
			}

			if (item instanceof Player) {

				g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);

			} else if (item instanceof Baggage) {

				g.drawImage(item.getImage(), item.x(), item.y(), this);
				if (item.x() == tempBulletX && item.y() == tempBulletY)// bullet collides with wall
					soko.setBullet(null);

			} else if (item instanceof Portal) {

				Portal portalRef = (Portal) item;
				if (portalRef.getIsActive() == 1)
					g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);

			} else if (item instanceof Bullet && forbutton == 0) {

				Bullet bulletRef = (Bullet) item;
				if (bulletRef != null && bulletRef.getMaxRange() > 0) {
					bulletRef.updateXY();
					tempBulletX = bulletRef.x();
					tempBulletY = bulletRef.y();
					int bulletExist=1;
					if(!cops.isEmpty()){
						for(int k=0;k<cops.size();k++){
							Police cop=cops.get(k);
							if(judge_XandY_Collision(tempBulletX, tempBulletY, cop.getx(), cop.gety())){
								soko.setBullet(null);
								System.out.printf("1cop numbefor%d",cops.size());
								cops.remove(k);
								world.remove(cop);
								System.out.printf("1cop numafter%d",cops.size());
								cop = null;
								System.out.printf("bullet lead");
								bulletExist=0;
								continue;
							}
						}
						
					}
					if(bulletExist==1)
						g.drawImage(item.getImage(), item.x() + 2 + SPACE / 2, item.y() + 2 + SPACE / 3, this);
				} else
					soko.setBullet(null);

			} else if (item instanceof Wall)/* wall */ {

				g.drawImage(item.getImage(), item.x(), item.y(), this);
				if (item.x() == tempBulletX && item.y() == tempBulletY)// bullet collides with wall
					soko.setBullet(null);

			} else {/* area */
				g.drawImage(item.getImage(), item.x(), item.y(), this);
			}

			if (isCompleted) {
				g.setColor(new Color(0, 0, 0));
				g.drawString("Completed", 25, 20);
			}

		}
		if (forbutton == 1)
			forbutton = 0;// prevent repeated execution when bottom is clicked
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		buildWorld(g);
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (isCompleted) {
				return;
			}

			int key = e.getKeyCode();

			switch (key) {
				case KeyEvent.VK_LEFT:
					if (checkCollisions(soko, LEFT_COLLISION)) {
						return;
					}
					if (!cops.isEmpty()) {
						for(int i=0;i<cops.size();i++){
							Police cop=cops.get(i);
							if (checkPersonAndPersonCollision(soko, cop, LEFT_COLLISION)) {
								playerLoss();
							}
						}
						
					}
					soko.move(-SPACE, 0);
					soko.setPlayerImage(faceLeft);
					currentlyFacing = faceLeft;
					break;

				case KeyEvent.VK_RIGHT:
					if (checkCollisions(soko, RIGHT_COLLISION)) {
						return;
					}
					if (!cops.isEmpty()) {
						for(int i=0;i<cops.size();i++){
							Police cop=cops.get(i);
							if (checkPersonAndPersonCollision(soko, cop, RIGHT_COLLISION)) {
								playerLoss();
							}
						}
						
					}
					soko.move(SPACE, 0);
					soko.setPlayerImage(faceRight);
					currentlyFacing = faceRight;
					break;

				case KeyEvent.VK_UP:
					if (checkCollisions(soko, TOP_COLLISION)) {
						return;
					}
					if (!cops.isEmpty()) {
						for(int i=0;i<cops.size();i++){
							Police cop=cops.get(i);
							if (checkPersonAndPersonCollision(soko, cop, TOP_COLLISION)) {
								playerLoss();
							}
						}
						
					}
					soko.move(0, -SPACE);
					soko.setPlayerImage(faceUp);
					currentlyFacing = faceUp;
					break;

				case KeyEvent.VK_DOWN:
					if (checkCollisions(soko, BOTTOM_COLLISION)) {
						return;
					}
					if (!cops.isEmpty()) {
						for(int i=0;i<cops.size();i++){
							Police cop=cops.get(i);
							if (checkPersonAndPersonCollision(soko, cop, BOTTOM_COLLISION)) {
								playerLoss();
							}
						}
						
					}

					soko.move(0, SPACE);
					soko.setPlayerImage(faceDown);
					currentlyFacing = faceDown;
					break;

				case KeyEvent.VK_R:// restart

					restartLevel();

					break;
				case KeyEvent.VK_Z:// portal
					if (portal.getIsActive() == 1) {
						for (int i = 0; i < baggs.size(); i++) {
							Baggage ref = baggs.get(i);
							if (ref.x() == portal.x() && ref.y() == portal.y()) /* check if portal is blocked by bag */
								return;

						}
						soko.setX(portal.x());
						soko.setY(portal.y());
						portal.setIsActive(0);
					} else {
						if (portal.getAvailability() == 0)
							return;
						portal.setAvailability(portal.getAvailability() - 1);
						portal.setX(soko.x());
						portal.setY(soko.y());
						portal.setIsActive(1);
					}

					break;
				case KeyEvent.VK_SPACE:// bullet
					if (soko.getBullet() != null)
						return;
					else if (soko.getRifleAvailable() == 1 && soko.getAmmo() > 0) {

						soko.setBullet(new Bullet(soko.x(), soko.y(), currentlyFacing));
						soko.setAmmo(soko.getAmmo() - 1);
					}

					break;

				case KeyEvent.VK_X:// penetrate
					if (penetrateNotUsed) {
						// formal version
						/*
						 * collisionIgnore = true; collisionIgnoreTime = new Date().getTime();
						 * penetrateNotUsed = false;
						 */

						// below this is debug version
						penetrateNotUsed = true;
						collisionIgnore = !collisionIgnore;
						// above is debug version
					}
					break;
				default:
					break;
			}
			forbutton = 1;
			System.out.printf("key");

			repaint();
		}
	}

	private boolean checkCollisions(Actor a, int d) {
		// a -> actor, s -> direction
		if (checkHardWallCollision(a, d) || checkWallCollision(a, d) || checkBagCollision(d))
			return true;
		return false;
	}

	private boolean checkHardWallCollision(Actor actor, int type) {
		int i;

		switch (type) {
			case LEFT_COLLISION:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isLeftCollision(hardWall)) {
						return true;
					}
				}
				break;

			case RIGHT_COLLISION:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isRightCollision(hardWall)) {
						return true;
					}
				}
				break;

			case TOP_COLLISION:
				for (i = 0; i < hardWalls.size(); i++) {
					HardWall hardWall = hardWalls.get(i);
					if (actor.isTopCollision(hardWall)) {
						return true;
					}
				}
				break;

			case BOTTOM_COLLISION:
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
			case LEFT_COLLISION:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isLeftCollision(wall)) {
						return true;
					}
				}
				break;

			case RIGHT_COLLISION:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isRightCollision(wall)) {
						return true;
					}
				}
				break;

			case TOP_COLLISION:
				for (int i = 0; i < walls.size(); i++) {
					Wall wall = walls.get(i);
					if (actor.isTopCollision(wall)) {
						return true;
					}
				}
				break;

			case BOTTOM_COLLISION:
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

		switch (type) {

			case LEFT_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (soko.isLeftCollision(bag)) {
						for (int j = 0; j < baggs.size(); j++) {
							Baggage item = baggs.get(j);
							if (!bag.equals(item)) {
								if (bag.isLeftCollision(item)) {
									return true;
								}
							}
							if (checkWallCollision(bag, LEFT_COLLISION)
									|| checkHardWallCollision(bag, LEFT_COLLISION)) {
								return true;
							}
						}

						if (cops != null &&!checkBagCollisiontoPolice(bag.getX() - SPACE, bag.getY())) {
							bag.move(-SPACE, 0);
							System.out.print("fault");
						} else if (cops.isEmpty()) {// when police death ,the way can prevent bug
							bag.move(-SPACE, 0);
						} else
							return true;
						isCompleted();
					}
				}

				return false;

			case RIGHT_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (soko.isRightCollision(bag)) {
						for (int j = 0; j < baggs.size(); j++) {
							Baggage item = baggs.get(j);
							if (!bag.equals(item)) {
								if (bag.isRightCollision(item)) {
									return true;
								}
							}
							if (checkWallCollision(bag, RIGHT_COLLISION)
									|| checkHardWallCollision(bag, RIGHT_COLLISION)) {
								return true;
							}
						}
						if (cops != null &&!checkBagCollisiontoPolice(bag.getX() + SPACE, bag.getY()) ) {
							bag.move(SPACE, 0);
							System.out.printf("falut");
						} else if (cops.isEmpty()) {
							bag.move(SPACE, 0);
						}

						else
							return true;

						isCompleted();
					}
				}
				return false;

			case TOP_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (soko.isTopCollision(bag)) {
						for (int j = 0; j < baggs.size(); j++) {
							Baggage item = baggs.get(j);
							if (!bag.equals(item)) {
								if (bag.isTopCollision(item)) {
									return true;
								}
							}

							if (checkWallCollision(bag, TOP_COLLISION) || checkHardWallCollision(bag, TOP_COLLISION)) {
								return true;
							}
						}

						if (cops != null &&!checkBagCollisiontoPolice(bag.getX(), bag.getY()-SPACE)) {
							bag.move(0, -SPACE);
							System.out.printf("falut");
						} else if (cops.isEmpty()) {
							bag.move(0, -SPACE);
						} else
							return true;

						isCompleted();
					}
				}

				return false;

			case BOTTOM_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (soko.isBottomCollision(bag)) {
						for (int j = 0; j < baggs.size(); j++) {
							Baggage item = baggs.get(j);
							if (!bag.equals(item)) {
								if (bag.isBottomCollision(item)) {
									return true;
								}
							}

							if (checkWallCollision(bag, BOTTOM_COLLISION)
									|| checkHardWallCollision(bag, BOTTOM_COLLISION)) {
								return true;
							}
						}
						if (cops != null &&!checkBagCollisiontoPolice(bag.getX() , bag.getY()+ SPACE)) {
							bag.move(0, SPACE);
							System.out.printf("falut");
						} else if (cops.isEmpty()) {
							bag.move(0, SPACE);
						}

						else
							return true;

						isCompleted();
					}
				}

				break;

			default:
				break;
		}

		return false;
	}
	private boolean checkBagCollisiontoPolice(int bag_x,int bag_y){
		for(int i=0;i<cops.size();i++){
			Police temp= cops.get(i);
			if(judge_XandY_Collision(temp.getx(), temp.gety(), bag_x, bag_y))return true;
			
		}
		return false;
	}
	private boolean judge_XandY_Collision(int x,int y,int x1,int y1){
		if(x1==x&&y1==y)return true;
		else return false;
	}
	private boolean checkBagCollisionforPolice(Actor actor, int type) {

		switch (type) {
			case LEFT_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (actor.isLeftCollision(bag)) {
						return true;
					}
				}
				return false;

			case RIGHT_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (actor.isRightCollision(bag)) {
						return true;
					}
				}
				return false;

			case TOP_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (actor.isTopCollision(bag)) {
						return true;
					}
				}
				return false;

			case BOTTOM_COLLISION:
				for (int i = 0; i < baggs.size(); i++) {
					Baggage bag = baggs.get(i);
					if (actor.isBottomCollision(bag)) {
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
		switch (type) {
			case TOP_COLLISION:
				if (actor.isTopCollision(actor1)) {
					return true;
				}
				return false;
			case BOTTOM_COLLISION:
				if (actor.isBottomCollision(actor1)) {
					return true;
				}
				return false;
			case LEFT_COLLISION:
				if (actor.isLeftCollision(actor1)) {
					return true;
				}
				return false;
			case RIGHT_COLLISION:
				if (actor.isRightCollision(actor1)) {
					return true;
				}
				return false;
			default:
				break;
		}
		return false;
	}

	public void playerLoss() {
		cops = null;
		JOptionPane.showMessageDialog(this, "Game_Over");
		lost = true;
	}

	public void isCompleted() {
		int nOfBags = baggs.size();
		int finishedBags = 0;

		for (int i = 0; i < nOfBags; i++) {
			Baggage bag = baggs.get(i);

			for (int j = 0; j < nOfBags; j++) {
				Area area = areas.get(j);
				if (bag.x() == area.x() && bag.y() == area.y()) {
					finishedBags += 1;
				}
			}
		}

		if (finishedBags == 1) {
			soko.setRifleAvailable(1);
		} else if (finishedBags == nOfBags) {
			JOptionPane.showMessageDialog(this, "win!");
			isCompleted = true;
			System.out.printf("aboutbagger fauult\n");
			repaint();
		}
	}

	private void restartLevel() {

		areas.clear();
		baggs.clear();
		walls.clear();

		isCompleted = false;
		initWorld();
	}

	public boolean isLost() {
		return lost;
	}

	public boolean getIsCompleted() {
		return isCompleted;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}
}