package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Goal extends Actor {

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

        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);
    }
}
