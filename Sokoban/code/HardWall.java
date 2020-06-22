package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

import java.io.IOException;
import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import java.awt.Dimension;

public class HardWall extends Actor {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

    public HardWall(int x, int y) {
        super(x, y);
        initHardWall();
    }

    @Override
    public String getActorName(){
        return "hardWall";
    }

    private void initHardWall() {

        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/hardWall.png";
        else
            path = path.replaceAll("code", "pic/hardWall.png");

        BufferedImage image;
        try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            setImage(image);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
