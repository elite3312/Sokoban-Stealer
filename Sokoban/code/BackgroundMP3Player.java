package ntou.cs.finalterm.test.game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.advanced.AdvancedPlayer;
// import javazoom.jl.player.Player;
/* References:
	http://www.javazoom.net/javalayer/sources.html
	https://introcs.cs.princeton.edu/java/15inout/BackgroundMP3.java
*/

public class BackgroundMP3Player {

	private AdvancedPlayer playerr;
	boolean loop = true;
	private File file;
	private int songChoose = 1;

	public BackgroundMP3Player() throws FileNotFoundException, JavaLayerException {
		
		File tempfile = new File("");
		String path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/BGM3.mp3";
		else
			path = path.replaceAll("code", "BGM/BGM3.mp3");

		file = new File(path);
	}

	public void setSong(int songSeq){
		songChoose = songSeq;
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

	
}
