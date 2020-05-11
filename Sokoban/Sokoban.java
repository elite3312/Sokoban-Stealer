package coding.code;


import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.Timer;
import java.util.TimerTask;
public class Sokoban extends JFrame {

    private final int OFFSET = 30;

    public Sokoban() {

        initUI();
    }

    private void initUI() {
        
        Board board = new Board();
        add(board);

        setTitle("Sokoban");
        
        setSize(board.getBoardWidth() + OFFSET,
                board.getBoardHeight() + 2 * OFFSET);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        Timer timer=new Timer();
        TimerTask refresh= new TimerTask(){
			@Override
			public void run() {
				
				JFrame.getFrames()[0].repaint();
			}	
		};
		timer.schedule(refresh, 0, 200);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            
            Sokoban game = new Sokoban();
            //game.setVisible(true);
            
        });
    }
}
