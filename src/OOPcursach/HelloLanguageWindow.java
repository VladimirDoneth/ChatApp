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
import javax.swing.JTextField;
import javax.swing.UIManager;

public class HelloLanguageWindow extends JFrame{
	private JFrame frame;
	private JButton rusButton, engButton, uaButton;
	private JLabel forUser;
	private JPanel panel;
	private String languageStatus;
	public static boolean isMainThreadWait;

	public static void main(String ar[]) {
		new HelloLanguageWindow(null);
	}
	
	public HelloLanguageWindow(MainThread mainThread){
		isMainThreadWait = true;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("not can make theme");
		}
		setTitle("CursachApp");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		Image img = kit.getImage("icon4.gif");
		setIconImage(img);
		frame = new JFrame();
		forUser = new JLabel();
		panel = new JPanel();
		rusButton = new JButton();
		engButton = new JButton();
		uaButton = new JButton();
		
		rusButton.setText("Русский");
		engButton.setText("English");
		uaButton.setText("Українська");
		
		
		
		rusButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "Вы выбрали русский язык");
				languageStatus = TextFields.RUS_LANGUAGE;
				isMainThreadWait = false;
				mainThread.notifityThisThread();
				setVisible(false);
			}
			
		});
		
		engButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "You choose English language");
				languageStatus = TextFields.ENG_LANGUAGE;
				isMainThreadWait = false;
				mainThread.notifityThisThread();
				setVisible(false);
			}
			
		});
		
		uaButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "Ви обрали українську мову");
				languageStatus = TextFields.UA_LANGUAGE;
				isMainThreadWait = false;
				mainThread.notifityThisThread();
				setVisible(false);
			}
			
		});
		
		setContentPane(panel);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		Box boxv = Box.createVerticalBox();
		forUser = new JLabel();
		forUser.setText("Язык / Language / Мова");
		boxv.add(forUser);
		boxv.add(Box.createVerticalStrut(20));
		boxv.add(rusButton);
		boxv.add(Box.createVerticalStrut(10));
		boxv.add(engButton);
		boxv.add(Box.createVerticalStrut(10));
		boxv.add(uaButton);
		
		add(boxv,BorderLayout.CENTER);
		setVisible(true);
	}
	
	public String getLanguageStatus(){
		return languageStatus;
	}
}
