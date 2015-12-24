package OOPcursach;

import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class SnakeApp {

	public static int complexity = 250;
	public static final int MAX_COMPLEXITY = 500;
	public static final int MIN_COMPLEXITY = 0;
	private JFrame myWindow;
	private SetComplexityWindow setComplexityWindow;
	private boolean isNoWorked = true;
	private String nickName = "nickName";
	private MainThread mainThread;
	private String languageCommand = "rus";
	private FileOfHighscore fileOfHighscore;
	GameModeWindow gameModeWindow;

	public SnakeApp() {
		try {
			fileOfHighscore = new FileOfHighscore();
		} catch (FileNotFoundException e) {
			System.out.println("Unpossible exception");
		}
	}

	public SnakeApp(String nickName, MainThread mainThread, GameModeWindow gameModeWindow, String languageCommand) {
		this.nickName = nickName;
		this.mainThread = mainThread;
		this.languageCommand = languageCommand;
		this.gameModeWindow = gameModeWindow;
		try {
			fileOfHighscore = new FileOfHighscore();
		} catch (FileNotFoundException e) {
			System.out.println("Unpossible exception");
		}
	}

	public void restartThisGUI() {
		myWindow.setVisible(true);
	}

	public static void main(String[] args) {
		new SnakeApp().startGraphicInterface();
	}

	public void startGraphicInterface() {
		SnakeApp snakeApp = this;
		myWindow = new JFrame("Snake menu");
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("not can make theme");
		}
		myWindow.setLayout(null);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setSize(270, 290);
		myWindow.setVisible(true);

		final JButton GO = new JButton("Start game");
		GO.setLocation(30, 30);
		GO.setSize(200, 40);
		myWindow.add(GO);

		GO.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {// Start the game
				startGame();// Zavolame SnakeDialog - START GAME!
			}
		});

		final JButton complexity = new JButton("Complexity");
		complexity.setLocation(30, 80);
		complexity.setSize(200, 40);
		myWindow.add(complexity);

		complexity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isNoWorked) {
					isNoWorked = false;
					setComplexityWindow = new SetComplexityWindow(nickName, languageCommand, snakeApp);
				} else
					setComplexityWindow.setVisible(true);
				myWindow.setVisible(false);
			}

		});

		final JButton highscore = new JButton("Highscore");
		highscore.setLocation(30, 130);
		highscore.setSize(200, 40);
		myWindow.add(highscore);
		myWindow.setResizable(false);

		highscore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				try {
					fileOfHighscore.loadInArrayList();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ArrayList<String> arrayList = fileOfHighscore.getArrayList();
				if (arrayList.size() == 0) {
					JOptionPane.showMessageDialog(myWindow,"You don`t have Highscore");
				}else {
					String tmp = "";
					for(int i = 0; i<arrayList.size(); i++){
						tmp = tmp+nickName+" "+arrayList.get(i)+'\n';
					}
					JOptionPane.showMessageDialog(myWindow,tmp,"Highscores",JOptionPane.WARNING_MESSAGE);
					tmp = "";
				}
			}
		});

		final JButton exit = new JButton("Exit");
		exit.setLocation(30, 180);
		exit.setSize(200, 40);
		myWindow.add(exit);

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(exit, "Goodbye!", "OK", JOptionPane.WARNING_MESSAGE);
				myWindow.setVisible(false);
				gameModeWindow.restartThisGUI();
			}
		});
	}

	public void startGame() {
		JDialog dlg = new JDialog((JFrame) null, "Snake v.over9000");// Game as
																		// Dialog
		dlg.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		final SnakeGame sn = new SnakeGame();// If you start the game,
		dlg.getContentPane().add(sn);
		sn.newGame();
		dlg.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent ev) {
				sn.processKey(ev);
			}
		});
		dlg.setVisible(true);
		dlg.pack();
		dlg.setResizable(true);
		dlg.setLocation(300, 200);
		dlg.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent event) {
			}

			public void windowClosed(WindowEvent event) {
			}

			public void windowClosing(WindowEvent event) {
				Object[] options = { "Yes", "No!" };
				int n = JOptionPane.showOptionDialog(event.getWindow(), "Save score?", // You
																						// can
																						// save
																						// the
																						// score
						"Snake", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (n == 0) {
					try {
						fileOfHighscore.writeHighscoreInEnd(sn.getPoints());
					} catch (IOException e) {
						e.printStackTrace();
					}
					event.getWindow().dispose();// Close the snake-game window

				} else {
					event.getWindow().dispose();// Close the snake-game window
				}
			}

			public void windowDeactivated(WindowEvent event) {
			}

			public void windowDeiconified(WindowEvent event) {
			}

			public void windowIconified(WindowEvent event) {
			}

			public void windowOpened(WindowEvent event) {
			}
		});
	}
}
