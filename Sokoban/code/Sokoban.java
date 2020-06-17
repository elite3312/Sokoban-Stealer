package java2020.finalProject;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import ntou.cs.finalterm.test.game.BackgroundMP3Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import java.util.Timer;
import java.util.TimerTask;

public class Sokoban extends JFrame {
	private BackgroundMP3Player music;
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
		try {
			music = new BackgroundMP3Player();
			music.circularPlay();
		} catch (FileNotFoundException | JavaLayerException e) {
			System.out.printf("music err");
		}
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
					music.close();
					this.cancel();
					JOptionPane.showMessageDialog(null, "Game_Over");

					for(int i=0;i<10000;i++){
						int wait;}
					Sokoban.this.dispose();
					setVisible(false);
				}
				else if(board.getIsCompleted()) {
					this.cancel();
					
					Sokoban.this.dispose();
					setVisible(false);
				}
			}
		};
		timer.schedule(refresh, 0, 50);
		
	}

}
