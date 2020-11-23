package java2020.finalProject;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;

public class PausePanel {

    private FontMetrics metrics;

    private final double scale;

    private int selection;
    private int strWidth;

    private final int width; // Stage width
    private final int height; // Stage height
    
    private String pauseText;
    private String state;
    private String choose[];

    private Font title, subTitle, markedUp, normal;

    public PausePanel(double sc, int w, int h) {
        this.selection = 1;
        this.scale = sc;
        this.width = w;
        this.height = h;

        this.pauseText = "【 暫停 】";
        this.choose = new String[]{"繼續遊戲", "重新開始", "回到主畫面"};

        this.title = new Font("Microsoft JhengHei", Font.BOLD, (int) (80 * scale));
        this.subTitle = new Font("Microsoft JhengHei", Font.PLAIN, (int) (64 * scale));
        this.markedUp = new Font("Microsoft JhengHei", Font.PLAIN, (int)(40 * scale));
        this.normal = new Font("Microsoft JhengHei", Font.PLAIN, (int)(36 * scale));
    }
    
    public int getSelection() {
        return this.selection;
    }

    public void setSelection(int select) {
        this.selection = select;
    }

    public void selectionUp() {
        if(this.selection > 1)
            this.selection--;
    }

    public void selectionDown() {
        if(this.selection < 3)
            this.selection++;
    }
    
    public void draw(Graphics g, int level) {

        g.setColor(Color.BLACK);

        state = String.format("LEVEL %d", level);
        metrics = g.getFontMetrics(title);
        strWidth = metrics.stringWidth(state);
        
        g.setFont(title);
        g.drawString(state, this.width / 2 - strWidth / 2, this.height / 5);

        metrics = g.getFontMetrics(subTitle);
        strWidth = metrics.stringWidth(pauseText);

        g.setFont(subTitle);
        g.drawString(pauseText, this.width / 2 - strWidth / 2, this.height / 2 - 70);

        for(int i = 0; i < choose.length; i++) {
            String temp = choose[i];

            // selected or not
            if (this.selection-1 == i) {
                temp = ">>" + temp + "<<";

                g.setFont(markedUp);
                g.setColor(Color.RED);
                metrics = g.getFontMetrics(markedUp);
            } else {
                g.setFont(normal);
                g.setColor(Color.BLACK);
                metrics = g.getFontMetrics(normal);
            }

            strWidth = metrics.stringWidth(temp);
            g.drawString(temp, this.width / 2 - strWidth / 2, this.height / 2 + 40 + 50*i);
        }
    }
}
