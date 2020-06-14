package java2020.finalProject;

import java.awt.GridLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class MainMenuFrame extends JFrame implements ActionListener {

	private JTextField textField1;
	private JTextField textField;

	private JPanel topPanel;
	private JPanel picPanel;
	private JPanel bottomPanel;

	private JRadioButton p1;
	private JRadioButton p2;

	private ButtonGroup character;

	private JRadioButton l1;
	private JRadioButton l2;
	private JRadioButton l3;

	private ButtonGroup level;

	private int characterChosen;
	private final int playerSkinOne = 1;
	private final int playerSkinTwo = 2;
	private int levelChosen;

	private JButton levelSelect;
	private JButton exitBtn;
	private JButton launchBtn;
	// no-argument constructor

	public MainMenuFrame() {

		super("Sokoban Stealer");
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

		if (!path.contains("code")){
			path = "pic/gameTitle2.png";
			selectPath = "pic/select.png";
			selectedPath = "pic/selected.png";
			gameStartPath = "pic/gameStart.png";
			exitPath = "pic/exit.png";
		}
		else{
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
		
		String introduction = "偷東西，是一門學問，更是一門藝術。\n在狹小的場地中躲避警衛，並成功將貨物運送到指定地點，是你的目標\n" +
							  "你能否越過重重障礙，並且獲得最終的勝利?\n\n玩法說明：遊戲中按空白鍵可以朝前方發射子彈，並擊倒警衛\n" + 
							  "　　　　　按Z鍵，可以設置傳送點或傳送至傳送點(一關限三次)\n　　　　　按X鍵，可以穿牆(一關限一次)";

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
		p1.setIcon(new ImageIcon(selectPath));
		p1.setSelectedIcon(new ImageIcon(selectedPath));

		p2 = new JRadioButton("二號小偷", false);
		p2.setFont(font);
		p2.setIcon(new ImageIcon(selectPath));
		p2.setSelectedIcon(new ImageIcon(selectedPath));

		character = new ButtonGroup();
		character.add(p1);
		character.add(p2);

		radioBtnPanel1.add(label1);
		radioBtnPanel1.add(p1);
		radioBtnPanel1.add(p2);

		bottomPanel.add(radioBtnPanel1);
		add(bottomPanel);
		/* level select */
		JLabel label2 = new JLabel("選擇關卡：");
		label2.setFont(font);
		JPanel radioBtnPanel2 = new JPanel(new FlowLayout());

		l1 = new JRadioButton("男二舍冰箱大盜(簡單)", true);
		l1.setFont(font);
		l1.setIcon(new ImageIcon(selectPath));
		l1.setSelectedIcon(new ImageIcon(selectedPath));

		l2 = new JRadioButton("左右為難的警衛(困難)", false);
		l2.setFont(font);
		l2.setIcon(new ImageIcon(selectPath));
		l2.setSelectedIcon(new ImageIcon(selectedPath));

		l3 = new JRadioButton("西班牙皇家造幣廠(地獄)", false);
		l3.setFont(font);
		l3.setIcon(new ImageIcon(selectPath));
		l3.setSelectedIcon(new ImageIcon(selectedPath));

		level = new ButtonGroup();
		level.add(l1);
		level.add(l2);
		level.add(l3);

		radioBtnPanel2.add(label2);
		radioBtnPanel2.add(l1);
		radioBtnPanel2.add(l2);
		radioBtnPanel2.add(l3);

		bottomPanel.add(radioBtnPanel2);

		/* launch! */
		launchBtn = new JButton(new ImageIcon(gameStartPath));
		launchBtn.setFont(font);
		launchBtn.addActionListener(this);
		bottomPanel.add(launchBtn);
		
		/* exit */
		exitBtn = new JButton(new ImageIcon(exitPath));
		exitBtn.setFont(font);
		exitBtn.addActionListener(this);
		bottomPanel.add(exitBtn);
	}

	public void launch() {

		EventQueue.invokeLater(() -> {
			Sokoban game = new Sokoban(characterChosen, levelChosen);
			// game.setVisible(true);
		});
	}

	// handle button events
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == exitBtn) {
			setVisible(false); // you can't see me!
			MainMenuFrame.this.dispose();
		} else {
			if (p1.isSelected())
				characterChosen = playerSkinOne;
			else
				characterChosen = playerSkinTwo;

			if (l1.isSelected())
				levelChosen = 1;
			else if (l2.isSelected())
				levelChosen = 2;
			else
				levelChosen = 3;

			launch();
		}
	}
}
//
