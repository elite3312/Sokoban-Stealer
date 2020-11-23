package java2020.finalProject;

import java.io.FileNotFoundException;

import java.awt.Image;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Bomb extends Object {

    BackgroundMP3Player sounds;

    private Image[] images;
    private int indx;

    private enum sound {
		bulletSound, bagSound, bombSound
	};

    public Bomb(int x, int y) {
        super(x, y);

        try {
            sounds = new BackgroundMP3Player();
            sounds.setSound(sound.bombSound.ordinal());
        } catch (FileNotFoundException | JavaLayerException e1) {
            System.out.printf("music err");
        }

        ImageManager iManager = new ImageManager(false);
        iManager.bombInit();

        this.images = iManager.getBombImage();
        indx = 0;
        setImage(this.images[0]);
    }

    @Override
    public String getObjectName() {
        return "bomb";
    }

    public void playExplosionSound() {
        sounds.play();
    }

    public void nextFrame() {
        if(indx+1 < this.images.length)
            indx++;
            setImage(this.images[indx]);
        }

    public Image getImage2() {
        return this.images[1];
    }
}