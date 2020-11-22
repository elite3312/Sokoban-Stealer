package java2020.finalProject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class BackgroundMP3Player {

	private AdvancedPlayer musicPlayer;
	
	private File file;
	private File tempfile = new File("");
	private String path = tempfile.getAbsolutePath();

	private int song = 0;

	private boolean loop = true;

	private String[] songList = new String[] {
		"RepeatedTragedy.mp3",
		"Beyond_My_Beloved_Horizon.mp3",
		"AlanWalkerSpectre.mp3",
		"TragedyFlame.mp3",
		"AllOrNothing.mp3",
		"Splyce - Feel The Drums.mp3",
		"Lovable Clown Sit Com.mp3",
		"Jay.mp3",
		"minotaur_boss_theme.mp3",
		"Spika.mp3"
	};

	private String ending = "APageOfMyStory.mp3";

	private String[] soundList = new String[] {
		"attack1.mp3",
		"movebag.mp3",
		"explosion_.mp3"
	};

	public BackgroundMP3Player() throws FileNotFoundException, JavaLayerException {

		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/";
		else
			path = path.replaceAll("code", "BGM/");
	}

	public void setSong(int songSeq) {
		song = songSeq;
		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/";
		else
			path = path.replaceAll("code", "BGM/");
		
		if(songSeq >= 0 && songSeq < songList.length)
			path += songList[songSeq];
		else
			path += ending;

		file = new File(path);
	}
	public void setSound(int type) {
		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/";
		else
			path = path.replaceAll("code", "BGM/");
		
		if(type >= 0 && type < soundList.length)
			path += soundList[type];
		else
			path += soundList[0];

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
		musicPlayer = new AdvancedPlayer(bis);
		musicPlayer.play();
	}

	// To intentionally terminate the player 
	public void close() {
		loop = false;
		musicPlayer.close();
	}

	public int getCurrentMusic() {
		return song;
	}

}
