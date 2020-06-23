package java2020.finalProject;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;

import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;

public class EndingAnimation {

    private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
    private final double scale; // suitable for all screen size
    private Font font, titleFont, finalFont;

    private FontMetrics metrics;

    private final int Line = 40;
    private int x, y;
    private final int finalY;

    private String[] texts;

    private Color color;
    private int R, G, B;

    private String finalWords = "Thanks For Playing";
    private int finalWordsLen;

    private boolean fadeInOver;
    private boolean musicPlayed;

    private int allOver;

    private BackgroundMP3Player music;
    
    public EndingAnimation(){

        scale = dimension.getWidth() / baseWidth;

        font = new Font("Microsoft JhengHei", Font.PLAIN, (int) (24 * scale));
        titleFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (40 * scale));
        finalFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));

        x = (int)dimension.getWidth();
        y = (int)dimension.getHeight() + 30;
        finalY = y;

        R = 230;
        G = 230;
        B = 230;

        allOver = 0;

        fadeInOver = false;
        musicPlayed = false;

        try {
            music = new BackgroundMP3Player();
        } catch (FileNotFoundException | JavaLayerException e) {
            System.out.printf("music err");
        }

        color = new Color(0, 0, 0);

        texts = new String[]{
            "- Presented By -", // 0
            "",
            "吳永璿",
            "沈彥昭",
            "李佳勳",
            "",
            "",
            "",
            "- Musics -", // 8
            "",
            "Spectre    -    AlanWalker",
            "Repeated Tragedy    -    Raiden II",
            "Minotaur Boss Theme    -    Toram online",
            "A Page Of My Story    -    Princess Pricipal",
            "SPÏKA 「Rigël Theatre」   -    Remilia Scarlet",
            "Beyond My Beloved Horizon    -    Pirates of the Caribbean",
            "",
            "",
            "",
            "- Pictures -", // 19
            "",
            "Player    -    Craftpix.net",
            "Police    -    Craftpix.net",
            "Wall    -    OpenGameArt.org",
            "Chest    -    OpenGameArt.org",
            "Portal    -    OpenGameArt.org",
            "Explosion    -    OpenGameArt.org",
            "",
            "",
            "",
            "- Special Thanks -", // 30
            "",
            "馬尚彬 教授",
            "陳俊佑 助教",
            "游婉琳 助教",
            "張瑾瑜 助教",
            "jiPlayer    -    JavaZOOM",
            "Thumbnailator    -    coobird",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };

    }

    public void ending(Graphics g){

        int tempX = x / 2, tempY = y;
        int index = 0;
        int strWidth;

        if(!musicPlayed){
            music.setSong(99);
            music.play();
            musicPlayed = true;
        }
        

        g.setColor(color);

        for (String text : texts){

            if(index == 0 || index == 8 || index == 19 || index == 30){
                g.setFont(titleFont);
                metrics = g.getFontMetrics(titleFont);     
                strWidth = metrics.stringWidth(text);
                /*
                    get the length of string that actual on the screen 
                    for the use of centrlize the string
                */
            }
            else{
                g.setFont(font);
                metrics = g.getFontMetrics(font);
                strWidth = metrics.stringWidth(text);
            }

            int realX = tempX - strWidth / 2;

            g.drawString(text, realX, tempY);
            tempY += Line;
            index++;
        }

        if(y + Line * texts.length > (int)dimension.getHeight() / 2)
            y -= 2;
        else{
            if(!fadeInOver)
                fadeIn(g);
            else
                fadeOut(g);
        }
    }

    private void fadeIn(Graphics g){

        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords);

        g.setFont(finalFont);

        if(R > 3){
            R -= 3;
            G -= 3;
            B -= 3;
            color = new Color(R, G, B);
        }
        else{
            fadeInOver = true;
        }

        g.setColor(color);
        g.drawString(finalWords, x / 2 - finalWordsLen / 2, finalY / 2);
 
    }

    private void fadeOut(Graphics g){

        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords);
        
        g.setFont(finalFont);

        if(R <= 238){
            R += 3;
            G += 3;
            B += 3;
        }
        else
            allOver++;

        color = new Color(R, G, B);
        g.drawString(finalWords, x / 2 - finalWordsLen / 2, finalY / 2);
    }

    public boolean over(){
        if(allOver > 10){
            music.close();
            return true;
        }
        return false;
    }
}