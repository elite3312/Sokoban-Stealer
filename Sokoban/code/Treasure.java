package java2020.finalProject;

public class Treasure extends Object {

    private boolean isGetAmmo;

    public Treasure(int x, int y) {
        super(x, y);
        isGetAmmo = false;
    }

    @Override
    public String getObjectName(){
        return "Treasure";
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

    public void getAmmo(){
        isGetAmmo = true;
    }

    public boolean canGetAmmo(){
        return !isGetAmmo;
    }
}
