package java2020.finalProject;

import javax.swing.JFrame;

public class MenuDemo {
	public static void main(String[] args) {
		MainMenuFrame frame = new MainMenuFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
