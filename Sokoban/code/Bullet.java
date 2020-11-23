package java2020.finalProject;

import java.awt.Dimension;

public class Bullet extends Object {

	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWN = 4;

	private final int dir;
	private int maxRange = 40;

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size
	private final int SPACE = (int)(40 * scale); // Object side length

	public Bullet(int x, int y, int initDir) {
		super(x, y);
		dir = initDir;
	}

	@Override
    public String getObjectName() {
        return "Bullet";
    }

	public void updateXY() {
		setMaxRange(getMaxRange() - 1);

		switch (dir) {
			case LEFT:
				setX(x() - SPACE);
				break;
			case RIGHT:
				setX(x() + SPACE);
				break;
			case UP:
				setY(y() - SPACE);
				break;
			case DOWN:
				setY(y() + SPACE);
				break;
			default:
				System.out.printf("unexpected direction: %d\n", dir);
				System.exit(1);
				break;

		}
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}
}
