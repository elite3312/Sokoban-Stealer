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
	private final int SPACE = (int)(40 * scale); // actor side length

    private Image bulletImage;
    private Image wallImage;
    private Image hardWallImage;
    private Image goalImage;
    private Image[] treasureImages;
    private Image[] policeImages;
    private Image arrowImage;
    private Image[] bombImage;

    private SecureRandom random = new SecureRandom();
    
    public ImageManager(){
        bulletInit();
        wallInit();
        hardWallInit();
        goalInit();
        treasureInit();
        policeInit();
        arrowInit();
        bombInit();
    }

    public Image getBulletImage(){
        return bulletImage;
    }

    public Image getWallImage(){
        return wallImage;
    }

    public Image getHardWallImage(){
        return hardWallImage;
    }

    public Image getGoalImage(){
        return goalImage;
    }

    public Image getTreasureImage(){
        return treasureImages[random.nextInt(10)];
    }

    public Image[] getPoliceImages(){
        return policeImages;
    }

    public Image getArrowImage(){
        return arrowImage;
    }
    public Image[] getBombImage(){
        return bombImage;
    }
    private void bulletInit(){
        File f = new File("");
        String path = f.getAbsolutePath();
		
		if(!path.contains("code"))
			path = "pic/bullet.png";
		else
			path = path.replaceAll("code", "pic/bullet.png");
	

		BufferedImage image;
		try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            bulletImage = image;
		} catch (IOException e){
			System.out.println(e);
        }        
    }
    private void bombInit(){
        bombImage=new Image[2];
        File f=new File("");
        String path=f.getAbsolutePath();
        String temp;
        for(int i=0;i<2;i++){
            System.out.printf("%d ",i);
            if (!path.contains("code"))
                temp = String.format("pic/bomb%d.png", i);
		    else
                temp = path.replaceAll("code", String.format("pic/bomb%d.png", i));

            BufferedImage image;

            try{
                image = Thumbnails.of(temp).scale(scale).asBufferedImage();
                bombImage[i] = image;
            } catch (IOException e){
                System.out.println(e);
            }
        }
        
    }
    private void wallInit(){
        File f = new File("");
        String path = f.getAbsolutePath();
     
        if (!path.contains("code"))
			path = "pic/wall.png";
		else
            path = path.replaceAll("code", "pic/wall.png");
            
        BufferedImage image;

        try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            wallImage=image;
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private void hardWallInit(){
        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/hardWall.png";
        else
            path = path.replaceAll("code", "pic/hardWall.png");

        BufferedImage image;
        try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            hardWallImage = image;
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private void goalInit(){
        File f = new File("");
        String path = f.getAbsolutePath();
        
        if(!path.contains("code"))
            path = "pic/Goal.png";
        else
            path = path.replaceAll("code", "pic/Goal.png");

        BufferedImage image;
        try{
            image = Thumbnails.of(path).scale(scale).asBufferedImage();
            goalImage = image;
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private void treasureInit(){
        treasureImages = new Image[10];

        File f = new File("");
        String path = f.getAbsolutePath();
        String temp;
        
        for(int i = 0; i < 10; i++){
            if(!path.contains("code"))
                temp = String.format("pic/treasures/Treasure%d.png", i);
            else{
                temp = path.replaceAll("code", String.format("pic/treasures/Treasure%d.png", i));
            }
                
            BufferedImage image;
            try{
                image = Thumbnails.of(temp).scale(scale).asBufferedImage();
                treasureImages[i] = image;
            } catch (IOException e){
                System.out.println(e);
            }
        }
    }

    private void policeInit(){
        File f = new File("");
        String path = f.getAbsolutePath();
        String temp = path;

        policeImages = new Image[4];
        
        if(!path.contains("code")){
            temp = "pic/character/policeLeft.png";
        }
        else{
            temp = path.replaceAll("code", "pic/character/policeLeft.png");
        }
        BufferedImage image;
        try{
            image = Thumbnails.of(temp).scale(scale).asBufferedImage();
            policeImages[0] = image;
        } catch (IOException e){
            System.out.println(e);
        }
        temp = path;


        if(!path.contains("code")){
            temp = "pic/character/policeRight.png";
        }
        else{
            temp = path.replaceAll("code", "pic/character/policeRight.png");
        }
        try{
            image = Thumbnails.of(temp).scale(scale).asBufferedImage();
            policeImages[1] = image;
        } catch (IOException e){
            System.out.println(e);
        }
        temp = path;


        if(!path.contains("code")){
            temp = "pic/character/policeUp.png";
        }
        else{
            temp = path.replaceAll("code", "pic/character/policeUp.png");
        }
        try{
            image = Thumbnails.of(temp).scale(scale).asBufferedImage();
            policeImages[2] = image;
        } catch (IOException e){
            System.out.println(e);
        }
        temp = path;


        if(!path.contains("code")){
            temp = "pic/character/policeDown.png";
        }
        else{
            temp = path.replaceAll("code", "pic/character/policeDown.png");
        }
        try{
            image = Thumbnails.of(temp).scale(scale).asBufferedImage();
            policeImages[3] = image;
        } catch (IOException e){
            System.out.println(e);
        }
    }

    private void arrowInit(){
        File f = new File("");
		String path = f.getAbsolutePath();

		if(!path.contains("code")){
			path = "pic/arrow.png";
		}
		else{
			path = path.replaceAll("code", "pic/arrow.png");
		}
		try{
			arrowImage = Thumbnails.of(path).scale(scale).asBufferedImage();
		} catch (IOException e){
			System.out.println(e);
		}
    }
}