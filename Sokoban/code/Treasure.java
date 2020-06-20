package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Treasure extends Actor {

    public Treasure(int x, int y) {
        super(x, y);
        initTreasure();
    }

    @Override
    public String getActorName(){
        return "Treasure";
    }

    private void initTreasure() {
 
        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/Treasure.png";
        else
            path = path.replaceAll("code", "pic/Treasure.png");
            
        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {
        int dx = x() + x;
        int dy = y() + y;

        setX(dx);
        setY(dy);
    }

    public int getX(){
        return x();
    }

    public int getY(){
        return y();
    }   
}
