package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.security.SecureRandom;

import javax.swing.ImageIcon;

public class Treasure extends Actor {

    private Image[] images = new Image[10];

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
        String temp;
        
        for(int i = 0; i < 10; i++){
            if(!path.contains("code"))
                temp = String.format("pic/treasures/Treasure%d.png", i);
            else{
                temp = path.replaceAll("code", String.format("pic/treasures/Treasure%d.png", i));
            }
                
            ImageIcon iicon = new ImageIcon(temp);
            Image image = iicon.getImage();
            images[i] = image;
        }

        SecureRandom random = new SecureRandom();
        int seq = random.nextInt(10);
        setImage(images[seq]);
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
