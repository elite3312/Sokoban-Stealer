package java2020.finalProject;

public class CheatManager {

    private int[] buffer;
    private final int[] target = {1, 3, 2, 2, 4, 1, 2, 3};

    private int pivot, pivot2;
    private boolean activate = false;
    private boolean activate2 = false;

    private final char[] game = {'s', 'o', 'k', 'o', 'b', 'a', 'n'};
    private char[] user;

    public CheatManager() {
        buffer = new int[8];
        user = new char[7];
        pivot = 0;
        pivot2 = 0;
    }

    public void pushCommand(int command) {
        if(activate)
            return;

        if(command == target[pivot]) {
            buffer[pivot++] = command;
            if(pivot == 8)
                activate = true;
        } else {
            pivot = 0;
            buffer[pivot++] = command;
        }
    }

    public boolean available() {
        return activate;
    }

    public void deactivate() {
        activate = false;
        activate2 = false;
        pivot = 0;
        pivot2 = 0;
    }

    public void pushChar(char character) {

        if(character == game[pivot2]) {
            user[pivot2++] = character;
            if(pivot2 == 7) {
                activate2 = !activate2;
                pivot2 = 0;
            }
        } else {
            pivot2 = 0;
            user[pivot2++] = character;
        }
    }

    public boolean checkUserCommand() {
        return activate2;
    }

}