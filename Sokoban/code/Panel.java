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
    
    public Panel(double sc, int w, int h) {
        this.scale = sc;
        this.width = w;
        this.height = h;
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

        Font font = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));
        metrics = g.getFontMetrics(font);
        int strWidth = metrics.stringWidth(stateNow);

        g.setColor(new Color(0, 0, 0));
        g.setFont(font);

        g.drawString(stateNow, this.width / 2 - strWidth / 2, this.height / 2);

        if (gap < 1000) {
            return true;
        }
        return false;
    }

    public boolean drawLoss(Graphics g, long gap) {
        String infoShow = "關卡失敗 !!!";

        Font font = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));
        metrics = g.getFontMetrics(font);
        int strWidth = metrics.stringWidth(infoShow);

        g.setColor(new Color(0, 0, 0));
        g.setFont(font);
        g.drawString(infoShow, this.width / 2 - strWidth / 2, this.height / 2);

        if (gap < 1000) {
            return true;
        }
        return false;
    }

    public boolean drawWon(Graphics g, long gap) {

        if (gap < 1000) {
            String infoShow = "關卡勝利 !!!";
            Font font = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));
            metrics = g.getFontMetrics(font);
            int strWidth = metrics.stringWidth(infoShow);

            g.setColor(new Color(0, 0, 0));
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

            Font font = new Font("Microsoft JhengHei", Font.BOLD, (int) (64 * scale));
            metrics = g.getFontMetrics(font);
            int strWidth = metrics.stringWidth(stateNow);

            g.setColor(new Color(0, 0, 0));
            g.setFont(font);
            g.drawString(stateNow, this.width / 2 - strWidth / 2, this.height / 2);

            return true;
        }

        return false;
    }

    public void drawPause(Graphics g, int pauseSelect, int selection) {
        String state = String.format("LEVEL %d", selection);
        String pau = "【 暫停 】";

        Font font = new Font("Microsoft JhengHei", Font.BOLD, (int) (80 * scale));
        metrics = g.getFontMetrics(font);
        int strWidth = metrics.stringWidth(state);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(state, this.width / 2 - strWidth / 2, this.height / 5);

        font = new Font("Microsoft JhengHei", Font.PLAIN, (int) (64 * scale));
        g.setFont(font);

        metrics = g.getFontMetrics(font);
        strWidth = metrics.stringWidth(pau);
        g.drawString(pau, this.width / 2 - strWidth / 2, this.height / 2 - 70);

        String choose[] = new String[]{"繼續遊戲", "重新開始", "回到主畫面"};

        Font bigFont = new Font("Microsoft JhengHei", Font.PLAIN, (int)(40 * scale));
        Font smallFont = new Font("Microsoft JhengHei", Font.PLAIN, (int)(36 * scale));

        for(int i = 0; i < choose.length; i++) {
            if (pauseSelect-1 == i) {
                g.setFont(bigFont);
                metrics = g.getFontMetrics(bigFont);
                g.setColor(Color.RED);
                choose[i] = ">>" + choose[i] + "<<";
            } else {
                g.setFont(smallFont);
                metrics = g.getFontMetrics(smallFont);
                g.setColor(Color.BLACK);
            }
            strWidth = metrics.stringWidth(choose[i]);
            g.drawString(choose[i], this.width / 2 - strWidth / 2, this.height / 2 + 40 + 50*i);
        }
    }
}
