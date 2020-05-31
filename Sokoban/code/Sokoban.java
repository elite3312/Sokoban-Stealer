package java2020.finalProject;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.Timer;
import java.util.TimerTask;

public class Sokoban extends JFrame {

	private final int OFFSET = 30;
	private int player;
	private int level;

	public Sokoban(int player, int level) {
		this.player = player;
		this.level = level;
		initUI();
	}

	private void initUI() {

		Board board = new Board(player, level);
		add(board);

		setTitle("Sokoban-Stealer");

		setSize(board.getBoardWidth() + OFFSET, board.getBoardHeight() + 2 * OFFSET);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		Timer timer = new Timer();
		TimerTask refresh = new TimerTask() {
			@Override
			public void run() {
				board.executetime++;

				board.repaint();
				if (board.isLost()) {
					this.cancel();
					Sokoban.this.dispose();
					setVisible(false);
				}
			}
		};
		timer.schedule(refresh, 0, 50);
		
	}

}
