package coding.code;

import java.awt.Image;
import javax.swing.ImageIcon;

public class HardWall extends Actor {

    private Image image;

    public HardWall(int x, int y) {
        super(x, y);
        
        initHardWall();
    }
    
    private void initHardWall() {
        
        ImageIcon iicon = new ImageIcon("pic/hardWall.png");
        image = iicon.getImage();
        setImage(image);
    }
}
