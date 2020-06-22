package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

import java.io.IOException;
import java.awt.image.BufferedImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import java.awt.Dimension;

public class Wall extends Actor {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

    @Override
    public String getActorName(){
        return "wall";
    }

    public Wall(int x, int y) {
        super(x, y);
        initWall();
    }

    private void initWall() {

        File f = new File("");
        String path = f.getAbsolutePath();
     
        if (!path.contains("code"))
			path = "pic/wall.png";
		else
            path = path.replaceAll("code", "pic/wall.png");
            
        BufferedImage image;
        try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            setImage(image);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
