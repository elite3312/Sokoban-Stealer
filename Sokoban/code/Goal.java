package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import java.io.IOException;


import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.Dimension;

public class Goal extends Actor {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

    public Goal(int x, int y) {
        super(x, y);
        initGoal();
    }

    @Override
    public String getActorName(){
        return "goal";
    }

    private void initGoal() {

        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/Goal.png";
        else
            path = path.replaceAll("code", "pic/Goal.png");

        BufferedImage image;
        try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            setImage(image);
        } catch (IOException e){
            System.out.println(e);
        }

    }
}
