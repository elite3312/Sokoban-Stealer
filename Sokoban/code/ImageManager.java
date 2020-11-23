package java2020.finalProject;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import java2020.finalProject.Bullet;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.security.SecureRandom;

public class ImageManager {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    private final double baseWidth = 1536.0;
    private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

    private Image bulletImage;
    private Image wallImage;
    private Image hardWallImage;
    private Image goalImage;
    private Image arrowImage;
    private Image[] treasureImages;
    private Image[] policeImages;
    private Image[] bombImage;
    private Image[] exploImages;

    private File f = new File("");

    private SecureRandom random = new SecureRandom();

    public ImageManager(boolean init) {
        if(init) {
            bulletInit();
            wallInit();
            hardWallInit();
            goalInit();
            treasureInit();
            policeInit();
            arrowInit();
            bombInit();
            explosionInit();
        }
    }

    public Image getBulletImage() {
        return bulletImage;
    }

    public Image getWallImage() {
        return wallImage;
    }

    public Image getHardWallImage() {
        return hardWallImage;
    }

    public Image getGoalImage() {
        return goalImage;
    }

    public Image getTreasureImage() {
        return treasureImages[random.nextInt(10)];
    }

    public Image[] getPoliceImages() {
        return policeImages;
    }

    public Image getArrowImage() {
        return arrowImage;
    }

    public Image[] getBombImage() {
        return bombImage;
    }

    public Image[] getExplodeImage() {
        return exploImages;
    }

    private void bulletInit() {
        String path = f.getAbsolutePath();

        path = pathConfig(path, "bullet.png");
        bulletImage = getImageFromPath(path);
    }

    private void bombInit() {
        bombImage = new Image[2];
        String path = f.getAbsolutePath();
        String temp;

        for (int i = 0; i < 2; i++) {
            temp = pathConfig(path, String.format("bomb%d.png", i));
            bombImage[i] = getImageFromPath(temp);
        }

    }

    private void explosionInit() {
        exploImages = new Image[11];
        String path = f.getAbsolutePath();
        String temp;

        for (int i = 0; i <= 10; i++) {
            temp = pathConfig(path, String.format("explosion/Explosion%d.png", i));
            exploImages[i] = getImageFromPath(temp);
        }
    }

    private void wallInit() {
        String path = f.getAbsolutePath();

        path = pathConfig(path, "wall.png");
        wallImage = getImageFromPath(path);
    }

    private void hardWallInit() {
        String path = f.getAbsolutePath();

        path = pathConfig(path, "hardWall.png");
        hardWallImage = getImageFromPath(path);
    }

    private void goalInit() {
        String path = f.getAbsolutePath();

        path = pathConfig(path, "Goal.png");
        goalImage = getImageFromPath(path);
    }

    private void treasureInit() {
        treasureImages = new Image[10];

        String path = f.getAbsolutePath();
        String temp;

        for (int i = 0; i < 10; i++) {
            temp = pathConfig(path, String.format("treasures/Treasure%d.png", i));
            treasureImages[i] = getImageFromPath(temp);
        }
    }

    private void policeInit() {
        String path = f.getAbsolutePath();
        String temp = path;
        String[] directions = new String[]{"Left", "Right", "Up", "Down"};

        policeImages = new Image[4];

        for(int i = 0; i < 4; i++) {
            temp = pathConfig(path, "character/police" + directions[i] + ".png");
            policeImages[i] = getImageFromPath(temp);
        }
    }

    private void arrowInit() {
        String path = f.getAbsolutePath();

        path = pathConfig(path, "arrow.png");
        arrowImage = getImageFromPath(path);
    }

    public String pathConfig(String path, String picName) {
        return path.replaceAll("code", "pic/" + picName);
    }

    public Image getImageFromPath(String path) {
        Image result;

        try {
            result = Thumbnails.of(path).scale(scale).asBufferedImage();
            return result;
        } catch (IOException e) {
            System.out.println(e);
        }

        return null;
    }
}