package coding.code;

import java.awt.Image;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Actor {
	
	private Bullet bullet=null;
	private int ammo=5;
	private int rifleAvailable=1;
    public Player(int x, int y) {
        super(x, y);

        initPlayer();
    }

    private void initPlayer() {

        ImageIcon iicon = new ImageIcon("pic/sokoban.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {

        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }

	public int getRifleAvailable() {
		return rifleAvailable;
	}

	public void setRifleAvailable(int rifleAvailable) {
		this.rifleAvailable = rifleAvailable;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
}
