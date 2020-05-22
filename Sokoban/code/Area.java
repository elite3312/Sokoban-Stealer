package coding.code;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Area extends Actor {

    public Area(int x, int y) {
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
        path = path.replaceAll("code", "pic/area.png");

        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);
    }
}
