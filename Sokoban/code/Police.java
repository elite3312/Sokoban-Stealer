package java2020.finalProject;

import java.awt.Image;
import java.security.SecureRandom;

import java.awt.Dimension;

public class Police extends Actor {

    private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
    private final int DOWN = 4;
    
    private int toward = 2;
    private int SPACE = 40;
    private int step = 0;
    private int behaveMode = 1;

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

    private Image[] fourDirection = new Image[4];

    //Random random = new Random(new Date().getTime());
    SecureRandom random = new SecureRandom();

    private int dx;
    private int dy;

    public Police(int x, int y) {
        super(x, y);
        initPolice();
        SPACE = (int)(SPACE * scale);
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

    public void getFourDirectionImage(Image[] images){
        fourDirection = images;
    }

    private void initPolice() {
        setBehavior(random.nextInt(4) + 1);
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

        setImage(fourDirection[toward-1]);
        return toward;
    }

    private void setBehavior(int behave){
        switch (behave){
            case 1:
                behaveMode = 1;
                break;
            case 2:
                behaveMode = 2;
                break;
            case 3:
                behaveMode = 3;
                break;
            case 4:
                behaveMode = 4;
                break;
            default:
                behaveMode = 1;
                break;
        }
    }

    private void behavior1(){
        if(step == 3){
            step = 0;
            toward = random.nextInt(100) % 4 + 1;
            return;
        }
        step++;
    }

    private void behavior2(){
        if(step == 3){
            step = 0;
            toward--;

            if(toward < 1)
                toward = 4;
                
            return;
        }
        step++;
    }

    private void behavior3(){
        if(step == 3){
            step = 0;
            toward++;

            if(toward > 4){
                toward = 1;
            }
            return;
        }
        step++;
    }

    private void behavior4(){
        toward = random.nextInt(4) + 1;
    }

}