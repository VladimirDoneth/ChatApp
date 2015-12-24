package OOPcursach;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SecondStageWindow extends JFrame {
	private JPanel panel;
	private String nickName;
	private JFrame frame;
	private JButton gameModeButton;
	private JButton chatAppModeButton;
	private JLabel forUser;
	private JLabel forUser2;
	public static boolean isMainThreadWait;
	public static boolean isUserChoose;

	public static void main(String args[]) {
		new SecondStageWindow("UserName", null, "ua");
	}

	public SecondStageWindow(String nickName, MainThread mainThread, String languageCommand) {
		TextFields textFields = new TextFields(languageCommand);
		this.isMainThreadWait = true;
		this.nickName = nickName;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("not can make theme");
		}
		setTitle("CursachApp");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		Image img = kit.getImage("icon2.gif");
		setIconImage(img);

		panel = new JPanel();
		setContentPane(panel);
		setSize(375, 175);
		setLocationRelativeTo(null);
		setResizable(false);

		forUser = new JLabel();
		forUser2 = new JLabel();
		forUser.setText(textFields.getForSecondStageWindowHiMessage1() + nickName);
		forUser2.setText(textFields.getForSecondStageWindowHiMessage2());
		gameModeButton = new JButton();
		gameModeButton.setText(textFields.getForSecondStageWindowGameModeButton());

		chatAppModeButton = new JButton();
		chatAppModeButton.setText(textFields.getForSecondStageWindowChatAppModeButton());

		Box boxv = Box.createVerticalBox();
		boxv.add(forUser);
		boxv.add(Box.createVerticalStrut(10));
		boxv.add(forUser2);
		boxv.add(Box.createVerticalStrut(20));
		boxv.add(gameModeButton);
		boxv.add(Box.createVerticalStrut(10));
		boxv.add(chatAppModeButton);
		add(boxv, BorderLayout.CENTER);
		setVisible(true);

		frame = new JFrame();
		gameModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isUserChoose = true;
				isMainThreadWait = false;
				JOptionPane.showMessageDialog(frame, textFields.getForSecondStageWindowGameChoose());
				mainThread.notifityThisThread();
				setVisible(false);
			}
		});
		
		chatAppModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isUserChoose = false;
				isMainThreadWait = false;
				JOptionPane.showMessageDialog(frame, textFields.getForSecondStageWindowChatAppChoose());
				mainThread.notifityThisThread();
				setVisible(false);
			}
		});
	}
	
	public void restartThisGUI(){
		isMainThreadWait = true;
		setVisible(true);
	}
}
