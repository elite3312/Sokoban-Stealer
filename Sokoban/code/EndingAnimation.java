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
    private final int finalY;
    private int x, y;

    private Color color;
    private int R, G, B, R2, G2, B2;

    private final String finalWords = "Sokoban - Stealer";
    private final String finalWords2 = "Thanks For Playing";
    private int finalWordsLen;

    private boolean fadeInOver;
    private boolean fadeInOver2;
    private boolean musicPlayed;
    private boolean recorded;

    private int allOver;

    private BackgroundMP3Player music;

    private int[] posRecord;
    private final String[] texts = {
        "- Created By -", // 0
        "",
        "",
        "李佳勳",
        "沈彥昭",
        "吳永璿",
        "",
        "",
        "",
        "",
        "",
        "",
        "- Musics By -", // 12
        "",
        "",
        "Spectre    -    AlanWalker",
        "Jay Jay    -    Kevin MacLeod",
        "Feel The Drums    -    Spycle",
        "Tragedy Flame    -    Raiden II",
        "All Or Nothing    -    Raiden II",
        "Repeated Tragedy    -    Raiden II",
        "Minotaur Boss Theme    -    Toram online",
        "Lovable Clown Sit Com    -    Sir Cubworth",
        "A Page Of My Story    -    Princess Pricipal",
        "SPÏKA 「Rigël Theatre」   -    Remilia Scarlet",
        "Beyond My Beloved Horizon    -    Pirates of the Caribbean",
        "",
        "",
        "",
        "",
        "",
        "",
        "- Pictures By -", // 32
        "",
        "",
        "Police    -    Craftpix.net",
        "Stealer    -    Craftpix.net",
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
        "- Game UI -", // 48
        "",
        "",
        "吳永璿",
        "",
        "",
        "",
        "",
        "",
        "",
        "- Music Design -", // 58
        "",
        "",
        "李佳勳",
        "沈彥昭",
        "吳永璿",
        "",
        "",
        "",
        "",
        "",
        "",
        "- Animation -", // 70
        "",
        "",
        "沈彥昭",
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
        "- Special Thanks -", // 94
        "",
        "",
        "Java    -    Oracle",
        "jiPlayer    -    JavaZOOM",
        "Thumbnailator    -    coobird",
        "",
        "VSCode",
        "Eclipse",
        "Audacity",
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
        "- Special Thanks -", // 124
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
        ""
    };

    
    public EndingAnimation(){

        scale = dimension.getWidth() / baseWidth;

        font = new Font("Microsoft JhengHei", Font.PLAIN, (int) (27 * scale));
        titleFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (55 * scale));
        finalFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (70 * scale));

        x = (int)dimension.getWidth();
        y = (int)dimension.getHeight() + 120;
        finalY = y;

        R2 = R = 230;
        G2 = G = 230;
        B2 = B = 230;

        allOver = 0;

        fadeInOver = false;
        fadeInOver2 = false;
        musicPlayed = false;
        recorded = false;

        try {
            music = new BackgroundMP3Player();
        } catch (FileNotFoundException | JavaLayerException e) {
            System.out.printf("music err");
        }

        color = new Color(0, 0, 0);

        posRecord = new int[texts.length];
    }

    private void recordPosition(Graphics g){

        int strWidth;
        g.setColor(Color.BLACK);

        for (int i = 0; i < texts.length; i++){

            if(i == 0 || i == 12 || i == 32 || i == 48 || i == 58 || i == 70 || i == 94 || i == 124){
                g.setFont(titleFont);
                metrics = g.getFontMetrics(titleFont);     
                strWidth = metrics.stringWidth(texts[i]);
                /*
                    get the length of string that actual on the screen 
                    for the use of centrlize the string
                */
            }
            else{
                g.setFont(font);
                metrics = g.getFontMetrics(font);
                strWidth = metrics.stringWidth(texts[i]);
            }

            posRecord[i] = x / 2 - strWidth / 2;
        }
        recorded = true;
    }

    public void ending(Graphics g){

        int tempY = y;
        int index = -1;

        if(!musicPlayed){
            music.setSong(99);
            music.play();
            musicPlayed = true;
        }

        if(!recorded)
            recordPosition(g);
        
        try{ // needed
            Thread.sleep(7);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        for (String text : texts){

            tempY += Line;
            index++;

            if(tempY < -Line || tempY > finalY + Line) // if not in screen, skip
                continue;

            if(index == 0 || index == 12 || index == 32 || index == 48 || index == 58 || index == 70 || index == 94 || index == 124){
                g.setFont(titleFont);
            }
            else{
                g.setFont(font);
            }

            g.drawString(text, posRecord[index], tempY);

        }

        if(y + Line * texts.length > 0) // the last line disappear from screen
            y -= 1;
        else{
            fadeIn(g);
        }

    }

    private void fadeIn(Graphics g){ // gradually change the color to make fade effect

        if(fadeInOver){
            fadeOut(g);
            return;
        }

        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords);

        g.setFont(finalFont);

        if(R >= 1){
            R -= 1;
            G -= 1;
            B -= 1;
            color = new Color(R, G, B);
        }
        else{
            fadeInOver = true;
            return;
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
        }
        else{
            fadeIn2(g);
            return;
        }
        color = new Color(R, G, B);
        g.setColor(color);
        g.drawString(finalWords, x / 2 - finalWordsLen / 2, finalY / 2);
    }

    private void fadeIn2(Graphics g){

        if(fadeInOver2){
            fadeOut2(g);
            return;
        }

        metrics = g.getFontMetrics(finalFont);     
        finalWordsLen = metrics.stringWidth(finalWords2);

        g.setFont(finalFont);

        if(R2 >= 1){
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
        g.setColor(color);
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