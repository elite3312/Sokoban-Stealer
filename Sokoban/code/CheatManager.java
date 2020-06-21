package java2020.finalProject;

public class CheatManager {

    private int[] buffer;
    private final int[] target = {1, 3, 2, 2, 4, 1, 2, 3};
    private int pivot;
    private boolean activate = false;

    public CheatManager(){
        buffer = new int[8];
        pivot = 0;
    }

    public void pushCommand(int command){
        if(activate)
            return;

        if(command == target[pivot]){
            buffer[pivot++] = command;
            if(pivot == 8)
                activate = true;
        }
        else{
            pivot = 0;
            buffer[pivot++] = command;
        }
    }

    public boolean checkCondition(){
        return activate;
    }

    public void deactivate(){
        activate = false;
        pivot = 0;
    }

}