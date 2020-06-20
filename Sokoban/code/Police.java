package java2020.finalProject;

import java.awt.Image;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.ImageIcon;

public class Police extends Actor {

    private int toward = 2;
    private int SPACE = 40;
    private int step = 0;
    private int behaveMode = 1;

    Random random = new Random(new Date().getTime());
    private int dx;
    private int dy;

    public Police(int x, int y) {
        super(x, y);
        initPolice();
    }

    public void setsituation_change(int toward) {

        if (toward == 1) {
            dx = x() - SPACE;
            // left
            dy = y();

        } else if (toward == 2) {
            dy = y();
            dx = x() + SPACE;

        } else if (toward == 3) {
            dx = x();
            dy = y() - SPACE;

        } else if (toward == 4) {
            dx = x();
            dy = y() + SPACE;
        }

        setX(dx);
        setY(dy);
    }

    @Override
    public String getActorName(){
        return "police";
    }

    public int getx() {
        return x();
    }

    public int gety() {
        return y();
    }

    private void initPolice() {

        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/police.png";
        else
            path = path.replaceAll("code", "pic/police.png");
            
        ImageIcon iicon = new ImageIcon(path);
        Image image = iicon.getImage();
        setImage(image);

        setBehavior(random.nextInt(100) % 4 + 1);
    }

    public int nextStep(){
        switch (behaveMode){
            case 1:
                behavior1();
                break;
            case 2:
                behavior2();
                break;
            case 3:
                behavior3();
                break;
            case 4:
                behavior4();
                break;
            default:
                break;
        }

        return toward;
    }

    private void setBehavior(int behave){
        switch (behave){
            case 1:
                behavior1();
                break;
            case 2:
                behavior2();
                break;
            case 3:
                behavior3();
                break;
            case 4:
                behavior4();
                break;
            default:
                behavior1();
                break;
        }
    }

    private void behavior1(){
        behaveMode = 1;
        if(step == 2){
            step = 0;
            toward = random.nextInt(100) % 4 + 1;
            return;
        }
        step++;
    }

    private void behavior2(){
        behaveMode = 2;
        if(step == 3){
            step = 0;
            toward = random.nextInt(100) % 4 + 1;
            return;
        }
        step++;
    }

    private void behavior3(){
        behaveMode = 3;
        if(step == 3){
            step = 0;
            toward = random.nextInt(100) % 3 + 1; // only left, right or up
            return;
        }
        step++;
    }

    private void behavior4(){
        behaveMode = 4;
        toward = random.nextInt(100) % 4 + 1;
    }

}