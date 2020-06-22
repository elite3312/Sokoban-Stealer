package java2020.finalProject;

import java.awt.Image;
import java.awt.Dimension;

public class Actor {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
    private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size
    
    private final int SPACE = (int)(40 * scale);

    private int x;
    private int y;
    
    private Image image;

    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
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

    public String getActorName(){ // for the use of determine the actor's type, the child Class should override this func
        return "none";
    }

    public boolean isLeftCollision(Actor actor) {
        return (x() - SPACE == actor.x() && y() == actor.y()) || (x() == actor.x() && y() == actor.y());
    } // you can't move if move side is the wall               or is in the wall

    public boolean isRightCollision(Actor actor) {
        return (x() + SPACE == actor.x() && y() == actor.y()) || (x() == actor.x() && y() == actor.y());
    }

    public boolean isTopCollision(Actor actor) {
        return (y() - SPACE == actor.y() && x() == actor.x()) || (x() == actor.x() && y() == actor.y());
    }

    public boolean isBottomCollision(Actor actor) {
        return (y() + SPACE == actor.y() && x() == actor.x()) || (x() == actor.x() && y() == actor.y());
    }
}
