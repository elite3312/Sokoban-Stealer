package java2020.finalProject;

public class CheatManager {

    private char[] buffer;
    private final char[] target = {1, 3, 2, 2, 4, 1, 2, 3};

    private short head, head2;
    private boolean activate = false;
    private boolean activate2 = false;

    private final char[] game = {'s', 'o', 'k', 'o', 'b', 'a', 'n'};
    private char[] user;

    public CheatManager() {
        buffer = new char[8];
        user = new char[7];
        head = 0;
        head2 = 0;
    }

    public void pushCommand(int command) {
        if(activate)
            return;

        if(command == target[head]) {
            buffer[head++] = (char)command;
        } else {
            head = 0;
            buffer[head++] = (char)command;
        }

        if(head == target.length)
            activate = true;
    }

    public boolean available() {
        return activate;
    }

    public void deactivate() {
        activate = false;
        activate2 = false;
        head = 0;
        head2 = 0;
    }

    public void pushChar(char character) {
        if(character == game[head2]) {
            user[head2++] = character;
        } else {
            head2 = 0;
            user[head2++] = character;
        }

        if(head2 == game.length) {
            activate2 = !activate2;
            head2 = 0;
        }
    }

    public boolean checkUserCommand() {
        return activate2;
    }

}