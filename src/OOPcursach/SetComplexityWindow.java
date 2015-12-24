package OOPcursach;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class SetComplexityWindow extends JFrame {
	private JPanel panel;
	private JFrame frame;
	private JButton plusButton;
	private JButton minusButton;
	private JButton exitButton;
	private JLabel forUser, forUserStatusComplexity;

	public static void main(String args[]) {
       new SetComplexityWindow("nickName", "ua", null);
	}

	public SetComplexityWindow(String nickName, String languageCommand, SnakeApp snakeApp) {
		TextFieldsSnakeGame textFields = new TextFieldsSnakeGame(languageCommand);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("not can make theme");
		}
		panel = new JPanel();
		setContentPane(panel);
		setSize(400, 225);
		setLocationRelativeTo(null);
		setResizable(false);

		frame = new JFrame();
		forUser = new JLabel();
		forUserStatusComplexity = new JLabel();
		forUserStatusComplexity
				.setText(textFields.getForSetComplexityWindowComplexity() + (SnakeApp.complexity));
		forUser.setText(textFields.getForSetComplexityWindowHiMessage() + nickName);
		plusButton = new JButton(textFields.getForSetComplexityWindowPlusButton());
		minusButton = new JButton(textFields.getForSetComplexityWindowMinusButton());
		exitButton = new JButton(textFields.getForSetComplexityWindowExit());

		plusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (SnakeApp.complexity + 2 < SnakeApp.MAX_COMPLEXITY) {
					SnakeApp.complexity = SnakeApp.complexity + 2;
					forUserStatusComplexity
					.setText(textFields.getForSetComplexityWindowComplexity() + (SnakeApp.complexity));
					repaint();
				} else
					JOptionPane.showMessageDialog(frame, textFields.getForSetComplexityWindowPlusMessage());
			}

		});
		
		minusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (SnakeApp.complexity -2 > SnakeApp.MIN_COMPLEXITY) {
					SnakeApp.complexity = SnakeApp.complexity -2;
					forUserStatusComplexity
					.setText(textFields.getForSetComplexityWindowComplexity() + (SnakeApp.complexity));
					repaint();
				} else
					JOptionPane.showMessageDialog(frame, textFields.getForSetComplexityWindowMinusMessage());
			}

		});
		
		exitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				snakeApp.restartThisGUI();
				setVisible(false);
			}
			
		});
		
		Box vbox = Box.createVerticalBox();
		vbox.add(forUser);
		vbox.add(Box.createVerticalStrut(20));
		vbox.add(forUserStatusComplexity);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(plusButton);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(minusButton);		
		vbox.add(Box.createVerticalStrut(20));
		vbox.add(exitButton);
		
		add(vbox,BorderLayout.CENTER);
		setVisible(true);
	}
	
}
