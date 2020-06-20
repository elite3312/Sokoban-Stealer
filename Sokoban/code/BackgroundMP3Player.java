package java2020.finalProject;

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
	private File tempfile = new File("");
	private String path = tempfile.getAbsolutePath();

	public BackgroundMP3Player() throws FileNotFoundException, JavaLayerException {

		tempfile = new File("");
		path = tempfile.getAbsolutePath();

		if(!path.contains("code"))
			path = "BGM/rep.mp3";
		else
			path = path.replaceAll("code", "BGM/rep.mp3");
	}

	public void setSong(int songSeq){

		switch (songSeq) {
			case 1:
				path = path.replaceAll("rep", "Beyond_My_Beloved_Horizon");
				break;
			case 2:
				path = path.replaceAll("rep", "Spika");
				break;
			case 3:
				path = path.replaceAll("rep", "AlanWalkerSpectre");
				break;
			default:
				path = path.replaceAll("rep", "AlanWalkerSpectre");
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

}
