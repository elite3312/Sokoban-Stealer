package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class HardWall extends Actor {

    private Image image;

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
        
        if(!path.contains("code"))path="pic/hardWall.png";
        else path = path.replaceAll("code", "pic/hardWall.png");
        
        ImageIcon iicon = new ImageIcon(path);
        image = iicon.getImage();
        setImage(image);
    }
}
