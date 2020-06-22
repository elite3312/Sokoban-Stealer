package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.security.SecureRandom;

import javax.swing.ImageIcon;

import java.io.IOException;
import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import java.awt.Dimension;

public class Treasure extends Actor {

    private Image[] images = new Image[10];

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

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
                
            BufferedImage image;
            try{
                image = Thumbnails.of(temp).scale(scale).asBufferedImage();
                images[i] = image;
            } catch (IOException e){
                System.out.println(e);
            }
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
