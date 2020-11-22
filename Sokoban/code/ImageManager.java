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
            exploInit();
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
        File f = new File("");
        String path = f.getAbsolutePath();

        path = pathConfig(path, "bullet.png");
        bulletImage = getImageFromPath(path);
    }

    private void bombInit() {
        bombImage = new Image[2];
        File f = new File("");
        String path = f.getAbsolutePath();
        String temp;

        for (int i = 0; i < 2; i++) {
            temp = pathConfig(path, String.format("bomb%d.png", i));
            bombImage[i] = getImageFromPath(temp);
        }

    }

    private void exploInit() {
        exploImages = new Image[11];
        File f = new File("");
        String path = f.getAbsolutePath();
        String temp;

        for (int i = 0; i <= 10; i++) {
            temp = pathConfig(path, String.format("explosion/Explosion%d.png", i));
            exploImages[i] = getImageFromPath(temp);
        }
    }

    private void wallInit() {
        File f = new File("");
        String path = f.getAbsolutePath();

        path = pathConfig(path, "wall.png");
        wallImage = getImageFromPath(path);
    }

    private void hardWallInit() {
        File f = new File("");
        String path = f.getAbsolutePath();

        path = pathConfig(path, "hardWall.png");
        hardWallImage = getImageFromPath(path);
    }

    private void goalInit() {
        File f = new File("");
        String path = f.getAbsolutePath();

        path = pathConfig(path, "Goal.png");
        goalImage = getImageFromPath(path);
    }

    private void treasureInit() {
        treasureImages = new Image[10];

        File f = new File("");
        String path = f.getAbsolutePath();
        String temp;

        for (int i = 0; i < 10; i++) {
            temp = pathConfig(path, String.format("treasures/Treasure%d.png", i));
            treasureImages[i] = getImageFromPath(temp);
        }
    }

    private void policeInit() {
        File f = new File("");
        String path = f.getAbsolutePath();
        String temp = path;

        policeImages = new Image[4];

        temp = pathConfig(path, "character/policeLeft.png");
        policeImages[0] = getImageFromPath(temp);

        temp = pathConfig(path, "character/policeRight.png");
        policeImages[1] = getImageFromPath(temp);

        temp = pathConfig(path, "character/policeUp.png");
        policeImages[2] = getImageFromPath(temp);

        temp = pathConfig(path, "character/policeDown.png");
        policeImages[3] = getImageFromPath(temp);
    }

    private void arrowInit() {
        File f = new File("");
        String path = f.getAbsolutePath();

        path = pathConfig(path, "arrow.png");
        arrowImage = getImageFromPath(path);
    }

    public String pathConfig(String path, String picName) {

        String res;

        if (!path.contains("code")) {
            res = "pic/" + picName;
        } else {
            res = path.replaceAll("code", "pic/" + picName);
        }

        return res;
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