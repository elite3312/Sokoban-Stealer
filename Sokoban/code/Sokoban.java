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
	private final int LevelCount = new Map().getMapCount();

	private int player;
	private int level;
	
	private SavesWriter writer;
	private SavesReader reader;

	private BackgroundMP3Player sounds;
	private enum sound {bulletSound, bagSound, bombSound};

	private Boolean trigger = false;

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

					if(reader.readSaves() == level){
						
						if(level <= LevelCount){
							writer.openFile();
							writer.upDate(level+1); //next level becomes available
							writer.notStaticCloseFile();
						}
						
					}
					reader.closeFile();

					if(level <= LevelCount) {
							music.setSong(++level);
							music.circularPlay();
					}
					stage.setNextStage(false);
					
				}

				if(stage.closeAct()) {
					Sokoban.this.dispose();
					music.close();
					setVisible(false);
				}
				
			}
		};

		timer.schedule(refresh, 0, 33);
	}

}
