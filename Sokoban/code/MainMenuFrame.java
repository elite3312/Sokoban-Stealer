package java2020.finalProject;

import java.util.ArrayList;
import java.util.List;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;

import java.net.URISyntaxException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class MainMenuFrame extends JFrame implements ActionListener {

	private JPanel topPanel;
	private JPanel picPanel;
	private JPanel bottomPanel;
	private JPanel levelPanel;

	private JRadioButton p1;
	private JRadioButton p2;
	private JRadioButton p3;

	private ButtonGroup character;

	private ArrayList<JRadioButton> levels;

	private ButtonGroup level;

	private int characterChosen;
	private final int playerSkinOne = 1;
	private final int playerSkinTwo = 2;
	private final int playerSkinThree = 3;
	private final int levelCount = 9;
	private int levelChosen;

	private JButton levelSelect;
	private JButton exitBtn;
	private JButton launchBtn;
	private JButton clearBtn;
	private JButton back;
	private JButton playAnimation;

	private SavesReader reader;
	private SavesWriter writer;
	private int progress;
	private BackgroundMP3Player music;

	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private final double baseWidth = 1536.0;
	private final double scale = dimension.getWidth() / baseWidth; // suitable for all screen size

	private BufferedImage bufImage;
	private Image image;

	private JLabel label2;

	public MainMenuFrame() {
		super("Sokoban Stealer");

		try {
			music = new BackgroundMP3Player();
			music.setSong(0);
			music.circularPlay();

		} catch (FileNotFoundException | JavaLayerException e) {
			System.out.printf("music err");
		}

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);

		reader = new SavesReader("saves.txt");
		reader.openFile();
		progress = reader.readSaves();
		reader.closeFile();

		writer = new SavesWriter("saves.txt");

		Font font = new Font("Microsoft JhengHei", Font.PLAIN, (int) (22 * scale));
		Font btnFont = new Font("Microsoft JhengHei", Font.BOLD, (int) (30 * scale));

		setLayout(new FlowLayout());
		/* intro */
		topPanel = new JPanel(new BorderLayout());
		picPanel = new JPanel(new BorderLayout());

		File f = new File("");
		String path = f.getAbsolutePath();
		String selectPath = path, selectedPath = path;

		if (!path.contains("code")) {
			path = "pic/gameTitle2.png";
			selectPath = "pic/select.png";
			selectedPath = "pic/selected.png";
		} else {
			path = path.replaceAll("code", "pic/gameTitle2.png");
			selectPath = selectPath.replaceAll("code", "pic/select.png");
			selectedPath = selectedPath.replaceAll("code", "pic/selected.png");
		}

		try {
			bufImage = Thumbnails.of(path).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			JLabel mainImage = new JLabel();
			mainImage.setIcon(new ImageIcon(image));
			picPanel.add(BorderLayout.CENTER, mainImage);
		} catch (IOException e) {
			System.out.println(e);
		}

		add(BorderLayout.NORTH, picPanel);

		String introduction = "偷東西，是一門學問，更是一門藝術。\n在狹小的場地中躲避警衛，並成功將貨物運送到指定地點，是你的目標\n"
				+ "你能否越過重重障礙，並且獲得最終的勝利?\n\n玩法說明：按上下左右鍵以移動(請切換成英文輸入法)\n"
				+ "　　　　　遊戲中按空白鍵可以朝人物前方發射子彈，並擊倒警衛(每達成一個貨物可加兩發子彈)\n"
				+ "　　　　　按Z鍵，可以設置傳送點或傳送至傳送點(一關限三次)\n　　　　　按X鍵，可以穿牆(一關限一次，三秒)\n"
				+ "                    等等...記得小心炸彈!!!\n"
				+ "　　　　　也許還有神秘的功能？？  試著發掘看看吧！";

		JTextArea intro = new JTextArea(introduction);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		intro.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		intro.setEditable(false);
		intro.setOpaque(false);
		intro.setFont(font);

		topPanel.add(BorderLayout.CENTER, intro);
		add(BorderLayout.CENTER, topPanel);

		/* character select */
		JLabel label1 = new JLabel("選擇角色：");
		label1.setFont(font);
		bottomPanel = new JPanel(new GridLayout(4, 1));
		JPanel radioBtnPanel1 = new JPanel(new FlowLayout());

		p1 = new JRadioButton("一號小偷", true);
		p1.setFont(font);

		try {
			bufImage = Thumbnails.of(selectPath).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			p1.setIcon(new ImageIcon(image));

			bufImage = Thumbnails.of(selectedPath).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			p1.setSelectedIcon(new ImageIcon(image));
		} catch (IOException e) {
			System.out.println(e);
		}

		p2 = new JRadioButton("二號小偷", false);
		p2.setFont(font);

		try {
			bufImage = Thumbnails.of(selectPath).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			p2.setIcon(new ImageIcon(image));

			bufImage = Thumbnails.of(selectedPath).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			p2.setSelectedIcon(new ImageIcon(image));
		} catch (IOException e) {
			System.out.println(e);
		}

		p3 = new JRadioButton("三號小偷", false);
		p3.setFont(font);

		try {
			bufImage = Thumbnails.of(selectPath).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			p3.setIcon(new ImageIcon(image));

			bufImage = Thumbnails.of(selectedPath).scale(scale).asBufferedImage();
			image = (Image) bufImage;
			p3.setSelectedIcon(new ImageIcon(image));
		} catch (IOException e) {
			System.out.println(e);
		}

		character = new ButtonGroup();
		character.add(p1);
		character.add(p2);
		character.add(p3);

		radioBtnPanel1.add(label1);
		radioBtnPanel1.add(p1);
		radioBtnPanel1.add(p2);
		radioBtnPanel1.add(p3);

		bottomPanel.add(radioBtnPanel1);
		add(bottomPanel);

		/* level select */
		levelSelect = new JButton("選擇關卡");
		levelSelect.addActionListener(this);
		levelSelect.setFont(btnFont);
		bottomPanel.add(levelSelect);
		/* level select panel */
		levelPanel = new JPanel(new GridLayout(6, 3));
		level = new ButtonGroup();

		String tempStr = "播放結尾動畫(未解鎖)";
		if(progress > levelCount){
			tempStr = "播放結尾動畫";
		}
		playAnimation = new JButton(tempStr);
		playAnimation.addActionListener(this);
		playAnimation.setFont(btnFont);
		bottomPanel.add(playAnimation);

		if(progress > levelCount)
			playAnimation.setEnabled(true);
		else
			playAnimation.setEnabled(false);

		String labelText = "選擇關卡：(目前解鎖進度:第" + progress + "關)";
		if (progress > levelCount) {
			labelText = "選擇關卡：(目前解鎖進度：全破)";
		}
		
		label2 = new JLabel(labelText);
		label2.setFont(font);

		levels = new ArrayList<JRadioButton>();
		for (int i = 1; i <= levelCount; i++) {
			JRadioButton l1 = new JRadioButton("Level " + i, true);
			l1.setFont(font);
			l1.setBorderPainted(false);

			try {
				bufImage = Thumbnails.of(selectPath).scale(scale).asBufferedImage();
				image = (Image) bufImage;
				l1.setIcon(new ImageIcon(image));

				bufImage = Thumbnails.of(selectedPath).scale(scale).asBufferedImage();
				image = (Image) bufImage;
				l1.setSelectedIcon(new ImageIcon(image));
			} catch (IOException e) {
				System.out.println(e);
			}
			if (i > progress)
				l1.setEnabled(false);
			level.add(l1);
			levels.add(l1);
		}

		launchBtn = new JButton("遊戲開始");
		launchBtn.setFont(btnFont);
		launchBtn.addActionListener(this);

		back = new JButton("回到上一步");
		back.addActionListener(this);
		back.setFont(btnFont);

		levelPanel.add(label2);
		levelPanel.add(new JLabel(""));
		levelPanel.add(new JLabel(""));

		for (int i = 0; i < levels.size(); i++) {
			levelPanel.add(levels.get(i));
		}

		clearBtn = new JButton("清除紀錄");
		clearBtn.addActionListener(this);
		clearBtn.setFont(btnFont);
		
		levelPanel.add(launchBtn);
		levelPanel.add(back);
		levelPanel.add(clearBtn);

		levelPanel.setVisible(false);

		exitBtn = new JButton("離開");
		exitBtn.setFont(btnFont);
		exitBtn.addActionListener(this);
		bottomPanel.add(exitBtn);
	}

	public void launch() {
		music.close();
		
		EventQueue.invokeLater(() -> {
			Sokoban game = new Sokoban(characterChosen, levelChosen);
		});
	}

	public void updateProgress() {

		reader = new SavesReader("saves.txt");
		reader.openFile();
		progress = reader.readSaves();

		String labelText = "選擇關卡：(目前解鎖進度:第" + progress + "關)";
		if (progress > levelCount) {
			labelText = "選擇關卡：(目前解鎖進度：全破)";
			playAnimation.setText("播放結尾動畫");
			playAnimation.setEnabled(true);
		}
		else{
			playAnimation.setText("播放結尾動畫(未解鎖)");
			playAnimation.setEnabled(false);
		}
		label2.setText(labelText);

		for (int i = 0; i < levelCount; i++) {
			if (i < progress)
				levels.get(i).setEnabled(true);
			else
				levels.get(i).setEnabled(false);
		}
		repaint();
	}

	// handle button events
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == exitBtn) {
			setVisible(false); // you can't see me!
			MainMenuFrame.this.dispose();
			System.exit(0);
			
		} else if (event.getSource() == levelSelect) {
			MainMenuFrame.this.remove(bottomPanel);
			MainMenuFrame.this.add(levelPanel);
			levelPanel.setVisible(true);
			MainMenuFrame.this.updateProgress();
			repaint();

		} else if (event.getSource() == back) {
			MainMenuFrame.this.remove(levelPanel);
			MainMenuFrame.this.add(bottomPanel);
			updateProgress();
			repaint();

		} else if (event.getSource() == playAnimation){
			levelChosen = levelCount + 1;
			launch();
			repaint();

		} else if(event.getSource() == clearBtn){
			writer.openFile();
			writer.upDate(1);
			writer.notStaticCloseFile();

			updateProgress();
			repaint();

		} else { // launch
			if (p1.isSelected())
				characterChosen = playerSkinOne;
			else if (p2.isSelected())
				characterChosen = playerSkinTwo;
			else
				characterChosen = playerSkinThree;

			for (int i = 0; i < levels.size(); i++) {
				if (levels.get(i).isSelected())
					levelChosen = i + 1;
			}

			launch();
			MainMenuFrame.this.remove(levelPanel);
			MainMenuFrame.this.add(bottomPanel);
			MainMenuFrame.this.updateProgress();
			repaint();
		}
	}

}
