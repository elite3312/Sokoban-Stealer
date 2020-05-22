package coding.code;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Baggage extends Actor {

    public Baggage(int x, int y) {

        super(x, y);
        initBaggage();
    }

    private void initBaggage() {
 
        File f = new File("");
        String path = f.getAbsolutePath();
        path = path.replaceAll("code", "pic/baggage.png");

        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);
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
