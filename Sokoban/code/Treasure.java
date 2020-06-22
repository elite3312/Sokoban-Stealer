package java2020.finalProject;

public class Treasure extends Actor {

    public Treasure(int x, int y) {
        super(x, y);
    }

    @Override
    public String getActorName(){
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
}
