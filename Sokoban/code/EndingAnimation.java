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

    private final int Line = 45;
    private int x, y;
    private final int finalY;

    private String[] texts;

    private Color color;
    private int R, G, B, R2, G2, B2;

    private String finalWords = "Sokoban - Stealer";
    private String finalWords2 = "Thanks For Playing";
    private int finalWordsLen;

    private boolean fadeInOver;
    private boolean fadeInOver2;
    private boolean musicPlayed;

    private int allOver;

    private BackgroundMP3Player music;
    
    public EndingAnimation(){

        scale = dimension.getWidth() / baseWidth;

        font = new Font("Microsoft JhengHei", Font.PLAIN, (int) (27 * scale));
        titleFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (55 * scale));
        finalFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (70 * scale));

        x = (int)dimension.getWidth();
        y = (int)dimension.getHeight() + 160;
        finalY = y;

        R2 = R = 230;
        G2 = G = 230;
        B2 = B = 230;

        allOver = 0;

        fadeInOver = false;
        fadeInOver2 = false;
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
            "",
            "吳永璿",
            "沈彥昭",
            "李佳勳", // 5
            "",
            "",
            "",
            "",
            "", // 10
            "",
            "- Produced By -", // 12
            "",
            "",
            "吳永璿", // 15
            "沈彥昭",
            "李佳勳",
            "",
            "",
            "", // 20
            "",
            "",
            "",
            "",
            "- Musics -", // 25
            "",
            "",
            "Spectre    -    AlanWalker",
            "Repeated Tragedy    -    Raiden II",
            "Minotaur Boss Theme    -    Toram online", // 30
            "A Page Of My Story    -    Princess Pricipal",
            "SPÏKA 「Rigël Theatre」   -    Remilia Scarlet",
            "Beyond My Beloved Horizon    -    Pirates of the Caribbean",
            "preset 1",
            "preset 2", // 35
            "preset 3",
            "",
            "",
            "",
            "", // 40
            "",
            "",
            "",
            "- Pictures -", // 44
            "", // 45
            "",
            "Player    -    Craftpix.net",
            "Police    -    Craftpix.net",
            "",
            "Wall    -    OpenGameArt.org",
            "Chest    -    OpenGameArt.org",
            "Portal    -    OpenGameArt.org",
            "Explosion    -    OpenGameArt.org",
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
            "",
            "- Special Thanks -", // 75
            "",
            "",
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
            "",
            "- Special Thanks -", // 101
            "",
            "",
            "陳俊佑 助教",
            "游婉琳 助教",
            "",
            "馬尚彬 教授",
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
            "",
            "",
            ""
        };

    }

    public void ending(Graphics g){

        int tempX = x / 2, tempY = y;
        int index = -1;
        int strWidth;

        if(!musicPlayed){
            music.setSong(99);
            music.play();
            musicPlayed = true;
        }
        
        try{ // needed
            Thread.sleep(8);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        g.setColor(color);

        for (String text : texts){

            tempY += Line;
            index++;

            if(tempY < -Line || tempY > finalY + Line) // if not in screen, skip
                continue;

            if(index == 0 || index == 12 || index == 25 || index == 44 || index == 75 || index == 101){
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

        }

        if(y + Line * texts.length > (int)dimension.getHeight() / 2) // the last line disappear from screen
            y -= 1;
        else{
            if(!fadeInOver)
                fadeIn(g);
            else
                fadeOut(g);
        }
    }

    private void fadeIn(Graphics g){ // gradually change the color to make fade effect

        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords);

        g.setFont(finalFont);

        if(R > 1){
            R -= 1;
            G -= 1;
            B -= 1;
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
            R += 1;
            G += 1;
            B += 1;
            color = new Color(R, G, B);
            g.drawString(finalWords, x / 2 - finalWordsLen / 2, finalY / 2);
        }
        else{
            if(!fadeInOver2)
                fadeIn2(g);
            else
                fadeOut2(g);
        }
    }

    private void fadeIn2(Graphics g){
        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords2);

        g.setFont(finalFont);

        if(R2 > 1){
            R2 -= 1;
            G2 -= 1;
            B2 -= 1;
            color = new Color(R2, G2, B2);
        }
        else{
            fadeInOver2 = true;
        }

        g.setColor(color);
        g.drawString(finalWords2, x / 2 - finalWordsLen / 2, finalY / 2);
    }

    private void fadeOut2(Graphics g){
        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords2);
        
        g.setFont(finalFont);

        if(R2 <= 238){
            R2 += 1;
            G2 += 1;
            B2 += 1;
        }
        else
            allOver++;

        color = new Color(R2, G2, B2);
        g.drawString(finalWords2, x / 2 - finalWordsLen / 2, finalY / 2);
    }

    public boolean over(){
        if(allOver > 25){
            music.close();
            return true;
        }
        return false;
    }
}