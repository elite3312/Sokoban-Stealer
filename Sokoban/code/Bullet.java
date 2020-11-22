package java2020.finalProject;

import java.awt.Dimension;

public class Bullet extends Object {

	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int TOP = 3;
	private final int BOTTOM = 4;

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
		int dx;
		int dy;
		setMaxRange(getMaxRange() - 1);

		switch (dir) {

			case LEFT:
				dx = x() - SPACE;
				dy = y();
				break;
			case RIGHT:
				dx = x() + SPACE;
				dy = y();
				break;
			case TOP:
				dx = x();
				dy = y() - SPACE;
				break;
			case BOTTOM:
				dx = x();
				dy = y() + SPACE;
				break;
			default:
				dx = x();
				dy = y();
				System.out.printf("unexpected direction: %d\n", dir);
				System.exit(0);
				break;

		}
		setX(dx);
		setY(dy);
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}
}
