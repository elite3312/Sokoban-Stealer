package java2020.finalProject;

import java.util.ArrayList;

import java2020.finalProject.HardWall;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import java.awt.Dimension;

public class CollisionDetector {

    private final double baseWidth = 1536.0;
    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

    private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
    private final int DOWN = 4;
    private final int SPACE = (int) (40 * scale); // actor side length

    private boolean collisionIgnore;

    private BackgroundMP3Player sounds;

    private enum sound {
		bulletSound, bagSound, bombSound
	};

    public CollisionDetector() {
        try {
            sounds = new BackgroundMP3Player();
        } catch (FileNotFoundException | JavaLayerException e1) {
            System.out.println(e1);
        }

        collisionIgnore = false;
    }

    public void setCollisionIgnore(boolean state) {
        collisionIgnore = state;
    }

    public boolean getCollisionIgnore() {
        return collisionIgnore;
    }

    public boolean checkCollisions(Actor a, int d, ArrayList<HardWall> hardWalls, ArrayList<Wall> walls, ArrayList<Treasure> treasures, ArrayList<Police> cops) {
		// a -> actor, d -> direction
		if (checkHardWallCollision(a, d, hardWalls) || checkWallCollision(a, d, walls) || checkBagCollision(d, treasures, a, hardWalls, walls, cops))
			return true;
		return false;
	}

	public boolean checkHardWallCollision(Actor actor, int type, ArrayList<HardWall> hardWalls) {
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

	public boolean checkWallCollision(Actor actor, int type, ArrayList<Wall> walls) {

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

	public boolean checkBagCollision(int type, ArrayList<Treasure> treasures, Actor stealer, ArrayList<HardWall> hardWalls, ArrayList<Wall> walls, ArrayList<Police> cops) {

		sounds.setSound(sound.bagSound.ordinal());

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
							if (checkWallCollision(box, LEFT, walls) || checkHardWallCollision(box, LEFT, hardWalls)) {
								return true;
							}
						}

						if (cops != null && !checkBagCollisiontoPolice(box.getX() - SPACE, box.getY(), cops)) {
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
							if (checkWallCollision(box, RIGHT, walls) || checkHardWallCollision(box, RIGHT, hardWalls)) {
								return true;
							}
						}
						if (cops != null && !checkBagCollisiontoPolice(box.getX() + SPACE, box.getY(), cops)) {
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

							if (checkWallCollision(box, UP, walls) || checkHardWallCollision(box, UP, hardWalls)) {
								return true;
							}
						}

						if (cops != null && !checkBagCollisiontoPolice(box.getX(), box.getY() - SPACE, cops)) {
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

							if (checkWallCollision(box, DOWN, walls) || checkHardWallCollision(box, DOWN, hardWalls)) {
								return true;
							}
						}
						if (cops != null && !checkBagCollisiontoPolice(box.getX(), box.getY() + SPACE, cops)) {
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

	public boolean checkBagCollisiontoPolice(int bag_x, int bag_y, ArrayList<Police> cops) {
		for (int i = 0; i < cops.size(); i++) {
			Police temp = cops.get(i);
			if (judge_XandY_Collision(temp.getx(), temp.gety(), bag_x, bag_y))
				return true;

		}
		return false;
	}

	public boolean judge_XandY_Collision(int x, int y, int x1, int y1) {
		if (x1 == x && y1 == y)
			return true;
		else
			return false;
	}

	public boolean checkBagCollisionforPolice(Actor actor, int type, ArrayList<Treasure> treasures) {

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

	public Boolean checkPersonAndPersonCollision(Actor actor, Actor actor1, int type) {
		if (actor.x() == actor1.x() && actor.y() == actor1.y()) {
			return true;
		}
		return false;
	}
}