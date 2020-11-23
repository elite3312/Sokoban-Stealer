package java2020.finalProject;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;

import java.util.Date;

import javax.swing.text.StyleContext.SmallAttributeSet;

public class Panel {

    private FontMetrics metrics;
    private final double scale;

    private int width = 0; // Stage width
    private int height = 0; // Stage height
    
    private Font font;
    private Color color = new Color(0, 0, 0);
    
    public Panel(double sc, int w, int h) {
        this.scale = sc;
        this.width = w;
        this.height = h;

        this.font = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));
    }

    public boolean drawRestart(Graphics g, long restartTime) {
        Long time = new Date().getTime();
        int gap = (int)(time - restartTime);

        String stateNow = "Loading";
        int dot = gap / 200;

        for (int i = 0; i < 5; i++) {
            if (i < dot)
                stateNow += ".";
            else
                stateNow += " ";
        }

        metrics = g.getFontMetrics(font);
        int strWidth = metrics.stringWidth(stateNow);

        g.setColor(color);
        g.setFont(font);

        g.drawString(stateNow, this.width / 2 - strWidth / 2, this.height / 2);

        return gap < 1000;
    }

    public boolean drawLoss(Graphics g, long gap) {
        String infoShow = "關卡失敗 !!!";

        metrics = g.getFontMetrics(font);
        int strWidth = metrics.stringWidth(infoShow);

        g.setColor(color);
        g.setFont(font);
        g.drawString(infoShow, this.width / 2 - strWidth / 2, this.height / 2);

        return gap < 1000;
    }

    public boolean drawWon(Graphics g, long gap) {

        if (gap < 1000) {
            String infoShow = "關卡勝利 !!!";
            metrics = g.getFontMetrics(font);
            int strWidth = metrics.stringWidth(infoShow);

            g.setColor(color);
            g.setFont(font);
            g.drawString(infoShow, this.width / 2 - strWidth / 2, this.height / 2);

            return true;

        } else if (gap > 1000 && gap < 2200) {
            String stateNow = "Loading";
            int dot = (int)((gap-1000) / 200);

            for(int i = 0; i < 5; i++) {
                if (i < dot)
                    stateNow += ".";
                else
                    stateNow += " ";
            }

            metrics = g.getFontMetrics(font);
            int strWidth = metrics.stringWidth(stateNow);

            g.setColor(color);
            g.setFont(font);
            g.drawString(stateNow, this.width / 2 - strWidth / 2, this.height / 2);

            return true;
        }

        return false;
    }

}
