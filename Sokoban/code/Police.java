package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Police extends Actor {
    private int toward;
    private int SPACE = 40;
    // private int OFFSET=40;
    Random ran = new Random();
    private int dx;
    private int dy;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    public Police(int x, int y) {
        super(x, y);

        initPolice();

    }

    public void setsituation_change(int toward) {

        if (toward == 1) {
            dx = x() - SPACE;
            // left
            dy = y();

        } else if (toward == 2) {
            dy = y();
            dx = x() + SPACE;

        } else if (toward == 3) {
            dx = x();
            dy = y() - SPACE;

        } else if (toward == 4) {
            dx = x();
            dy = y() + SPACE;
        }

        setX(dx);
        setY(dy);

    }

    @Override
    public String getActorName(){
        return "police";
    }

    public int getx() {
        return x();
    }

    public int gety() {
        return y();
    }

    private void initPolice() {

        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))path="pic/police.png";
        else path = path.replaceAll("code", "pic/police.png");
        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);
    }

    public void visible_range() {

    }
}