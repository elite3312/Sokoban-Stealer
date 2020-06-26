package java2020.finalProject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class BackgroundMP3Player {

	private AdvancedPlayer playerr;
	boolean loop = true;
	private File file;
	private File tempfile = new File("");
	private String path = tempfile.getAbsolutePath();

	private int song = 0;

	public BackgroundMP3Player() throws FileNotFoundException, JavaLayerException {

		tempfile = new File("");
		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/";
		else
			path = path.replaceAll("code", "BGM/");
	}

	public void setSong(int songSeq){
		song = songSeq;
		tempfile = new File("");
		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/";
		else
			path = path.replaceAll("code", "BGM/");
			
		switch (songSeq) {
			case 0:
				path += "RepeatedTragedy.mp3";
				break;
			case 1:
				path += "Beyond_My_Beloved_Horizon.mp3";
				break;
			case 2:
				path += "AlanWalkerSpectre.mp3";
				break;
			case 3:
				path += "TragedyFlame.mp3";
				break;
			case 4:
				path += "AllOrNothing.mp3";
				break;
			case 5:
				path += "Splyce - Feel The Drums.mp3";
				break;
			case 6:
				path += "Lovable Clown Sit Com.mp3";
				break;
			case 7:
				path += "Jay.mp3";
				break;
			case 8:
				path += "minotaur_boss_theme.mp3";
				break;
			case 9:
				path += "Spika.mp3";
				break;
			case 99: // gameover thanks music
				path += "APageOfMyStory.mp3";
				break;
			default:
				path += "AlanWalkerSpectre.mp3";
				break;
		}

		file = new File(path);
	}
	public void setSound(int type){
		tempfile = new File("");
		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/";
		else
			path = path.replaceAll("code", "BGM/");
			
		switch (type) {
			case 0:
				path += "attack1.mp3";
				break;
			case 1:
				path+= "movebag.mp3";
				break;
			case 2:
				path+= "explosion_.mp3";
				break;
			default:
				path += "attack1.mp3";
				break;
		}

		file = new File(path);
		
	}
	public void circularPlay() {
		Thread currentThread;
		// continuously run in new thread to play in background
		currentThread = new Thread() {
			@Override
			public void run() {
				try {
					do {
						playOnce();
					} while (loop);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		};
		currentThread.start();
	}

	public void play() {
		Thread currentThread;
		// run in new thread to play in background
		currentThread = new Thread() {
			@Override
			public void run() {
				try {
					playOnce();
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		};
		currentThread.start();
	}

	private void playOnce() throws FileNotFoundException, JavaLayerException {
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		playerr = new AdvancedPlayer(bis);
		playerr.play();
	}

	// To intentionally terminate the player 
	public void close() {
		loop = false;
		
		playerr.close();
	}

	public int getCurrentMusic(){
		return song;
	}

}
