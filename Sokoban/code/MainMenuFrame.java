package java2020.finalProject;

import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;

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
	private int levelChosen;

	private JButton levelSelect;
	private JButton exitBtn;
	private JButton launchBtn;
	private JButton back;
	private SavesReader reader;
	private int progress;

	public MainMenuFrame() {
		super("Sokoban Stealer");

		reader=new SavesReader("saves.txt");
		reader.openFile();
		progress=reader.readSaves();
		reader.closeFile();

		Font font = new Font("defalut", Font.PLAIN, 22);

		setLayout(new FlowLayout());
		/* intro */
		topPanel = new JPanel(new BorderLayout());
		picPanel = new JPanel(new BorderLayout());

		File f = new File("");
		String path = f.getAbsolutePath();
		String selectPath = path;
		String selectedPath = path;
		String gameStartPath = path;
		String exitPath = path;

		if (!path.contains("code")) {
			path = "pic/gameTitle2.png";
			selectPath = "pic/select.png";
			selectedPath = "pic/selected.png";
			gameStartPath = "pic/gameStart.png";
			exitPath = "pic/exit.png";
		} else {
			path = path.replaceAll("code", "pic/gameTitle2.png");
			selectPath = selectPath.replaceAll("code", "pic/select.png");
			selectedPath = selectedPath.replaceAll("code", "pic/selected.png");
			gameStartPath = gameStartPath.replaceAll("code", "pic/gameStart.png");
			exitPath = exitPath.replaceAll("code", "pic/exit.png");
		}

		ImageIcon titleImage = new ImageIcon(path);
		JLabel mainImage = new JLabel();
		mainImage.setIcon(titleImage);
		picPanel.add(BorderLayout.CENTER, mainImage);
		add(BorderLayout.NORTH, picPanel);

		String introduction = "偷東西，是一門學問，更是一門藝術。\n在狹小的場地中躲避警衛，並成功將貨物運送到指定地點，是你的目標\n"
				+ "你能否越過重重障礙，並且獲得最終的勝利?\n\n玩法說明：遊戲中按空白鍵可以朝前方發射子彈，並擊倒警衛(每達成一個貨物可加兩發子彈)\n"
				+ "　　　　　按Z鍵，可以設置傳送點或傳送至傳送點(一關限三次)\n　　　　　按X鍵，可以穿牆(一關限一次，三秒)";

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
		bottomPanel = new JPanel(new GridLayout(3, 1));
		JPanel radioBtnPanel1 = new JPanel(new FlowLayout());

		p1 = new JRadioButton("一號小偷", true);
		p1.setFont(font);
		p1.setIcon(new ImageIcon(selectPath));
		p1.setSelectedIcon(new ImageIcon(selectedPath));

		p2 = new JRadioButton("二號小偷", false);
		p2.setFont(font);
		p2.setIcon(new ImageIcon(selectPath));
		p2.setSelectedIcon(new ImageIcon(selectedPath));

		p3 = new JRadioButton("三號小偷", false);
		p3.setFont(font);
		p3.setIcon(new ImageIcon(selectPath));
		p3.setSelectedIcon(new ImageIcon(selectedPath));

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
		bottomPanel.add(levelSelect);
		/* level select panel */
		levelPanel = new JPanel(new GridLayout(5, 2));
		level = new ButtonGroup();
		JLabel label2 = new JLabel("選擇關卡：(目前解鎖進度:第"+progress+"關)");
		label2.setFont(font);

		
		levels=new ArrayList<JRadioButton>();
		for (int i = 1; i <= 6; i++) {
			JRadioButton l1 = new JRadioButton("Level "+i, true);
			l1.setFont(font);
			l1.setIcon(new ImageIcon(selectPath));
			l1.setSelectedIcon(new ImageIcon(selectedPath));
			if(i>progress)l1.setEnabled(false);
			level.add(l1);
			levels.add(l1);
		}

		launchBtn = new JButton(new ImageIcon(gameStartPath));
		launchBtn.setFont(font);
		launchBtn.addActionListener(this);

		back = new JButton("back to menu");
		back.addActionListener(this);
		levelPanel.add(label2);
		levelPanel.add(new JLabel(""));
		for (int i = 0; i < levels.size(); i++) {
			levelPanel.add(levels.get(i));
		}

		levelPanel.add(launchBtn);
		levelPanel.add(back);
		levelPanel.setVisible(false);

		/* exit */
		exitBtn = new JButton(new ImageIcon(exitPath));
		exitBtn.setFont(font);
		exitBtn.addActionListener(this);
		bottomPanel.add(exitBtn);
	}

	public void launch() {

		EventQueue.invokeLater(() -> {
			Sokoban game = new Sokoban(characterChosen, levelChosen);
			updateProgress();
		});

		// music.close();
	}
	public void updateProgress(){
		reader = new SavesReader("saves.txt");
		reader.openFile();
		progress = reader.readSaves();
		for (int i = 0; i < 6; i++) {
				
			if(i<progress)levels.get(i).setEnabled(true);
		
		}
		repaint();
	}
	// handle button events
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == exitBtn) {
			setVisible(false); // you can't see me!
			MainMenuFrame.this.dispose();
		} else if (event.getSource() == levelSelect) {
			MainMenuFrame.this.setSize(1280, 820);
			MainMenuFrame.this.remove(bottomPanel);
			MainMenuFrame.this.add(levelPanel);
			levelPanel.setVisible(true);
			MainMenuFrame.this.updateProgress();
			repaint();

		} else if (event.getSource() == back) {
			MainMenuFrame.this.setSize(1280, 720);
			MainMenuFrame.this.remove(levelPanel);
			MainMenuFrame.this.add(bottomPanel);
			repaint();
		} else {// launch
			if (p1.isSelected())
				characterChosen = playerSkinOne;
			else if(p2.isSelected())
				characterChosen = playerSkinTwo;
			else
				characterChosen = playerSkinThree;

			for (int i = 0; i < levels.size(); i++) {
				if (levels.get(i).isSelected())
					levelChosen = i + 1;
			}

			launch();
			MainMenuFrame.this.setSize(1280, 720);
			MainMenuFrame.this.remove(levelPanel);
			MainMenuFrame.this.add(bottomPanel);
			MainMenuFrame.this.updateProgress();
			repaint();
		}
	}

}
