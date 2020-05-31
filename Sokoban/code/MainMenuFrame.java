package java2020.finalProject;

import java.awt.GridLayout;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;

public class MainMenuFrame extends JFrame implements ActionListener {
	private JTextField textField1;
	

	private JTextField textField;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JRadioButton p1;
	private JRadioButton p2;
	private ButtonGroup character;
	private JRadioButton l1;
	private JRadioButton l2;
	private JRadioButton l3;
	private ButtonGroup level;
	private int characterChosen;
	private int levelChosen;
	private JButton levelSelect;
	private JButton exitBtn;
	private JButton launchBtn;
	// no-argument constructor
	public MainMenuFrame() {

		super("Sokoban Stealer");
		Font font = new Font("新細明體", Font.PLAIN, 25);

		setLayout(new BorderLayout());
		/* intro */
		topPanel = new JPanel(new BorderLayout());
		
		JTextArea intro= new JTextArea("歡迎來到Sokoban Stealer!" 
				+"(說明故事大綱，遊戲規則)"
				+ "\n\n\n\n");
		
		intro.setFont(font);
		
		topPanel.add(BorderLayout.CENTER, intro);
		add(BorderLayout.NORTH,topPanel);
		/* character select */
		JLabel label1= new JLabel("選擇角色");
		label1.setFont(font);
		bottomPanel = new JPanel(new GridLayout(4,1));
		JPanel radioBtnPanel1 = new JPanel(new FlowLayout());
		p1 = new JRadioButton("一號小偷", true);p1.setFont(font);
		p2 = new JRadioButton("二號小偷", false);p2.setFont(font);
		character=new ButtonGroup();
		character.add(p1);
		character.add(p2);
		radioBtnPanel1.add(label1);
		radioBtnPanel1.add(p1);
		radioBtnPanel1.add(p2);
		
		bottomPanel.add( radioBtnPanel1);
		add(bottomPanel);
		/* level select */
		JLabel label2= new JLabel("選擇關卡");
		label2.setFont(font);
		JPanel radioBtnPanel2 = new JPanel(new FlowLayout());
		l1 = new JRadioButton("洗劫宿舍冰箱(簡單)", true);l1.setFont(font);
		l2 = new JRadioButton("男二大盜(困難)", false);l2.setFont(font);
		l3 = new JRadioButton("西班牙皇家造幣廠(地獄)", false);l3.setFont(font);
		level =new ButtonGroup();
		level.add(l1);
		level.add(l2);
		level.add(l3);
		radioBtnPanel2.add(label2);
		radioBtnPanel2.add(l1);
		radioBtnPanel2.add(l2);
		radioBtnPanel2.add(l3);
		bottomPanel.add( radioBtnPanel2);
		/*exit*/
		exitBtn = new JButton("exit");exitBtn.setFont(font);
		exitBtn.addActionListener(this);
		bottomPanel.add(exitBtn);
		/* launch! */
		launchBtn=new JButton("開始遊戲");launchBtn.setFont(font);
		launchBtn.addActionListener(this);
		bottomPanel.add(launchBtn);
	}
	public void launch() {

		EventQueue.invokeLater(() -> {
            Sokoban game = new Sokoban(characterChosen,levelChosen);
            // game.setVisible(true);
        });
    }
	// handle button events
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource()==exitBtn){
			setVisible(false); //you can't see me!
			MainMenuFrame.this.dispose();
		}
		else {
			if(p1.isSelected())characterChosen=1;
			else characterChosen=2;
			
			if(l1.isSelected())levelChosen=1;
			else if(l2.isSelected()) levelChosen=2;
			else levelChosen=3;
				
			launch();
		}
	}
}
//
