package java2020.finalProject;

public class Wall extends Actor {

    @Override
    public String getActorName(){
        return "wall";
    }

    public Wall(int x, int y) {
        super(x, y);
    }

}
