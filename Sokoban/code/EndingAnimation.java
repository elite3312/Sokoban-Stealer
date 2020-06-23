package java2020.finalProject;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;

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

    private int allOver;
    
    public EndingAnimation(){
        scale = dimension.getWidth() / baseWidth;
        font = new Font("Microsoft JhengHei", Font.PLAIN, (int) (22 * scale));
        titleFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (40 * scale));
        finalFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));

        x = (int)dimension.getWidth();
        y = (int)dimension.getHeight();
        finalY = y;

        System.out.println(y);

        R = 230;
        G = 230;
        B = 230;

        allOver = 0;

        fadeInOver = false;

        color = new Color(0, 0, 0);

        texts = new String[]{
            "Made By :", // 0
            "",
            "吳永璿",
            "沈彥昭",
            "李佳勳",
            "",
            "",
            "",
            "Musics :", // 8
            "",
            "Spectre    -    AlanWalker",
            "Beyond My Beloved Horizon    -    Pirates of the Caribbean",
            "SPÏKA 「Rigël Theatre」   -    Remilia Scarlet",
            "Minotaur Boss Theme    -    Toram online",
            "Repeated Tragedy    -    Raiden II",
            "",
            "",
            "",
            "Pictures :", // 18
            "",
            "Player    -    Craftpix.net",
            "Police    -    Craftpix.net",
            "Wall    -    OpenGameArt.org",
            "Portal    -    OpenGameArt.org",
            "Chest    -    OpenGameArt.org",
            "Explosion    -    OpenGameArt.org",
            "",
            "",
            "",
            "Special Thanks :", // 29
            "",
            "jiPlayer    -    JavaZOOM",
            "Thumbnailator    -    coobird",
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
            "",
            ""
        };

    }

    public void ending(Graphics g){
        int tempX = x / 2, tempY = y;
        int index = 0;
        int strWidth;

        g.setColor(color);

        for (String text : texts){

            if(index == 0 || index == 8 || index == 18 || index == 29){
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

        if(y + Line * 49 > (int)dimension.getHeight() / 2)
            y -= Line / 15;
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

        if(R > 2){
            R -= 2;
            G -= 2;
            B -= 2;
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

        if(R <= 235){
            R += 2;
            G += 2;
            B += 2;
        }
        else
            allOver++;

        color = new Color(R, G, B);
        g.drawString(finalWords, x / 2 - finalWordsLen / 2, finalY / 2);
    }

    public boolean over(){
        if(allOver > 10)
            return true;
        return false;
    }
}