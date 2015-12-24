package OOPcursach;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class HelloWindow extends JFrame {
	private String nickName;
	private JFrame frame;
	private JButton okeyButton;
	private JLabel forUser;
	private JTextField myNickNameField;
	private JPanel panel;
	private boolean isMaked;
	public static boolean isMainThreadWait;

	public static void main(String ar[]) {
		new HelloWindow(null, "eng");
	}

	public HelloWindow(MainThread mainThread, String languageCommand) {
		TextFields textFields = new TextFields(languageCommand);
		isMainThreadWait = true;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("not can make theme");
		}
		setTitle("CursachApp");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		Image img = kit.getImage("icon.gif");
		setIconImage(img);
		frame = new JFrame();
		okeyButton = new JButton();
		okeyButton.setText("OK");
		forUser = new JLabel();
		forUser.setText(textFields.getForHelloWindowUserHi());
		myNickNameField = new JTextField();
		panel = new JPanel();

		myNickNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (!isMaked) {
						nickName = myNickNameField.getText();
						if (!nickName.equals("")) {
							if (!ChackGaps(nickName)) {
								isMaked = true;
								setVisible(false);
								isMainThreadWait = false;
								mainThread.notifityThisThread();
							} else {
								JOptionPane.showMessageDialog(frame, textFields.getForHelloWindowLoseWrite());
							}
						} else {
							JOptionPane.showMessageDialog(frame, textFields.getForHelloWindowNoWrite());
						}
					}
				}
			}
		});

		okeyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!isMaked) {
					nickName = myNickNameField.getText();
					if (!nickName.equals("")) {
						if (!ChackGaps(nickName)) {
							isMaked = true;
							setVisible(false);
							isMainThreadWait = false;
							mainThread.notifityThisThread();
						} else {
							JOptionPane.showMessageDialog(frame, textFields.getForHelloWindowLoseWrite());
						}
					} else {
						JOptionPane.showMessageDialog(frame, textFields.getForHelloWindowNoWrite());
					}
				}
			}

		});
		setContentPane(panel);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		Box boxv = Box.createVerticalBox();
		boxv.add(forUser);
		boxv.add(Box.createVerticalStrut(50));
		boxv.add(myNickNameField);
		boxv.add(Box.createVerticalStrut(20));
		boxv.add(okeyButton);
		add(boxv, BorderLayout.CENTER);
		setVisible(true);
	}

	private boolean ChackGaps(String test) {
		for (int j = 0; j < test.length(); j++) {
			if ((test.charAt(j)) == ' ') {
				return true;
			}
		}
		return false;
	}

	public String getNickName() {
		return nickName;
	}
}
