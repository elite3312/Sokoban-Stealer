package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Wall extends Actor {

    private Image image;

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
        ImageIcon iicon = new ImageIcon(path);
        image = iicon.getImage();
        setImage(image);
    }
}
