package java2020.finalProject;

import java.awt.Image;
import java.awt.Dimension;

public class Object {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
    private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size
    
    private final int SPACE = (int)(40 * scale);

    private int x;
    private int y;
    
    private Image image;
    private Image[] imageArray;

    public Object(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }
    public Image getImageArray(int index) {
        return imageArray[index];
    }
    public void setImage(Image img) {
        image = img;
    }
    public void setImageArray(Image[] img) {
        imageArray = img;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // determine the Object's type, the child Class should override this function
    public String getObjectName() {
        return "none";
    }

    public boolean isLeftCollision(Object Object) {
        // you can't move if move side is the wall        or        is in the wall
        return (x() - SPACE == Object.x() && y() == Object.y()) || (x() == Object.x() && y() == Object.y());
    }

    public boolean isRightCollision(Object Object) {
        return (x() + SPACE == Object.x() && y() == Object.y()) || (x() == Object.x() && y() == Object.y());
    }

    public boolean isTopCollision(Object Object) {
        return (y() - SPACE == Object.y() && x() == Object.x()) || (x() == Object.x() && y() == Object.y());
    }

    public boolean isBottomCollision(Object Object) {
        return (y() + SPACE == Object.y() && x() == Object.x()) || (x() == Object.x() && y() == Object.y());
    }
}
