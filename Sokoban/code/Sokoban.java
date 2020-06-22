package java2020.finalProject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.util.Timer;
import java.util.TimerTask;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.EventQueue;

public class Sokoban extends JFrame {

	private BackgroundMP3Player music;

	private final int MARGIN = 40;
	private final int LevelCount = 6;

	private int player;
	private int level;

	private SavesWriter writer;
	private SavesReader reader;

	private JPanel panel;

	public Sokoban(int player, int level) {
		super();

		this.player = player;
		this.level = level;
		writer = new SavesWriter("saves.txt");
		reader = new SavesReader("saves.txt");
		
		initUI();
	}

	private void initUI() {

		Stage stage = new Stage(player, level);

		try {
			music = new BackgroundMP3Player();
			music.setSong(level);
			music.circularPlay();
		} catch (FileNotFoundException | JavaLayerException e) {
			System.out.printf("music err");
		}

		add(stage);

		setTitle("Sokoban-Stealer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		setAlwaysOnTop(true);

		Timer timer = new Timer();
		TimerTask refresh = new TimerTask() {
			@Override
			public void run() {

				stage.executetime++;
				stage.repaint();

				if(stage.goNextStage()) {
					music.close();
					
					reader.openFile();
					if(reader.readSaves()==level){
						writer.openFile();
						writer.upDate(level+1); //next level becomes available
						SavesWriter.closeFile();
					}
					reader.closeFile();
					if(level < 6)
						level++;
					else if(level == 6){
						gameOverThanks();
					}

					music.setSong(level);
					music.circularPlay();
				}
				if(stage.closeAct()){
					music.close();
					Sokoban.this.dispose();
					setVisible(false);
				}
				
			}
		};

		timer.schedule(refresh, 0, 30);
	}

	private void gameOverThanks() {

		music.setSong(99);

		setLayout(new FlowLayout());
		panel = new JPanel(new BorderLayout());

		String specialThanks = "製作人員:\n" + "吳永璿\n沈彥昭\n李佳勳\n\n\n" + "Musics:\n" + "Spectre - AlanWalker\n"
				+ "Beyond My Beloved Horizon - Pirates of the Caribbean\n"
				+ "SPÏKA 「Rigël Theatre」 - Remilia Scarlet\n\n\n" + "Special Thanks:\n" + "馬尚彬 教授\n\n\n";

		Font font = new Font("Microsoft JhengHei", Font.PLAIN, 22);
		JTextArea texts = new JTextArea(specialThanks);
		texts.setOpaque(false);
		texts.setFont(font);
		texts.setEditable(false);

		panel.add(BorderLayout.CENTER, texts);
		add(BorderLayout.CENTER, panel);

		launch();
	}

	public void launch() {

		EventQueue.invokeLater(() -> {
			panel.setVisible(true);
			Sokoban.this.add(panel);
			repaint();
		});

	}
}
