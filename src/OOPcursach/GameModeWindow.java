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
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GameModeWindow extends JFrame {
	private JLabel forUser0;
	private JLabel forUser1;
	private JLabel forUser2;
	private JPanel panel;
	private JFrame frame;
	private JButton gameOfflineSnakePlay;
	private JButton gameOnlineTicTacToePlay;
	private JButton stepBackButton;
	private String nickName;
	private GameModeWindow gameModeWindow;
	private SnakeApp snakeApp;
	private boolean isNoMaked = true;

	public static boolean isMainThreadWait;

	public static void main(String args[]) {
		new GameModeWindow("userName", null, "eng");
	}

	public GameModeWindow(String nickName, MainThread mainThread, String languageCommand) {
		TextFields textFields = new TextFields(languageCommand);
		gameModeWindow = this;
		this.nickName = nickName;
		this.isMainThreadWait = true;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("not can make theme");
		}
		setTitle("CursachApp");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		Image img = kit.getImage("icon3.gif");
		setIconImage(img);

		panel = new JPanel();
		setContentPane(panel);
		setSize(375, 250);
		setLocationRelativeTo(null);
		setResizable(false);

		forUser0 = new JLabel();
		forUser1 = new JLabel();
		forUser2 = new JLabel();
		forUser0.setText(textFields.getForGameModeWindowHiMessage() + nickName);
		forUser1.setText(textFields.getForGameModeWindowOnlineStr());
		forUser2.setText(textFields.getForGameModeWindowOflineStr());

		gameOfflineSnakePlay = new JButton();
		gameOnlineTicTacToePlay = new JButton();
		gameOfflineSnakePlay.setText(textFields.getForGameModeWindowGameOflineSnakePlay());
		gameOnlineTicTacToePlay.setText(textFields.getForGameModeWindowGameOnlineTicTacToePlay());

		stepBackButton = new JButton();
		stepBackButton.setText(textFields.getForGameModeWindowBackButtonText());

		Box vBoxOnline = Box.createVerticalBox();
		Box vBoxOfline = Box.createVerticalBox();

		vBoxOnline.add(forUser1);
		vBoxOnline.add(Box.createVerticalStrut(15));
		vBoxOnline.add(gameOnlineTicTacToePlay);

		vBoxOfline.add(forUser2);
		vBoxOfline.add(Box.createVerticalStrut(15));
		vBoxOfline.add(gameOfflineSnakePlay);

		Box hBox = Box.createHorizontalBox();
		hBox.add(vBoxOfline);
		hBox.add(Box.createHorizontalStrut(10));
		hBox.add(vBoxOnline);

		Box vBox = Box.createVerticalBox();
		vBox.add(forUser0);
		vBox.add(Box.createVerticalStrut(45));
		vBox.add(hBox);
		vBox.add(Box.createVerticalStrut(45));
		vBox.add(stepBackButton);

		stepBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainThread.isAllTimeWork = true;
				isMainThreadWait = false;
				mainThread.notifityThisThread();
				setVisible(false);
			}

		});

		gameOfflineSnakePlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isNoMaked) {
					snakeApp = new SnakeApp(nickName, mainThread, gameModeWindow, languageCommand);
					snakeApp.startGraphicInterface();
				} else
					snakeApp.restartThisGUI();
				setVisible(false);
			}
		});

		gameOnlineTicTacToePlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		add(vBox, BorderLayout.CENTER);
		setVisible(true);
	}

	public void restartThisGUI() {
		isMainThreadWait = true;
		setVisible(true);
	}

}
