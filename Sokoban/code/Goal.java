package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Goal extends Actor {

    public Goal(int x, int y) {
        super(x, y);

        initArea();
    }

    @Override
    public String getActorName(){
        return "area";
    }

    private void initArea() {

        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/area.png";
        else
            path = path.replaceAll("code", "pic/area.png");

        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);
    }
}
