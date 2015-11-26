package ChatApp2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;


public class GUIForm implements Observer {
	public static void main(String args[]){
		GUIForm g = new GUIForm();
	}
	private JFrame frame = new JFrame();
	private JButton applyButton;
	private JButton disconnectionButton;
	private JButton connectionButton;
	private JButton sendButton;
	private JLabel login;
	private JTextField loginField;
	private JTextField friendIPField;
	private JTextField friendNameField;
	private JTextField MessageField;
	private JTextArea DialogArea;
	private JPanel panel;
	private String nickName;
	CommandListenerThread commandListenerThread;// = new
												// CommandListenerThread();
	private BufferedReader buffReadIn;
	CallListenerThread callListenerThread;
	private PrintWriter PWOut;
	Caller caller = new Caller();
	Socket socket;

	public GUIForm() {
		frame.setContentPane(panel);
		frame.setSize(800, 550);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		callListenerThread = new CallListenerThread(28411);
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		disconnectionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// connection.disconnect();

			}
		});

		connectionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callListenerThread.CallListenerStart("CallListenerThread");// вернули
																			// сокет
				caller.setRemoteAdress(callListenerThread.getSocket().toString());
				try {
					caller.Call();
				} catch (IOException io) {
					DialogArea.setText("Error");
				}
			}
		});

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		loginField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nick = loginField.getText();
				if (ChackGaps(nick)) {
					DialogArea.setText("вы ввели неправильный ник! введите без пробелов!");
				} else {
					nickName = nick;
				}
			}
		});

		friendIPField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = friendIPField.getText();
			}
		});

		friendNameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		MessageField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// PWOut = new PrintWriter(socket.getOutputStream(), true);
				// buffReaderIn = new BufferedReader(new
				// InputStreamReader(socket.getInputStream()));
			}
		});

	}

	private boolean ChackGaps(String test) {
		for (int j = 0; j < test.length(); j++) {
			if ((test.charAt(j)) == ' ') {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		/*if (arg.equals(CallListenerThread.itIsCallLisnenerThread)) {
			socket = callListenerThread.getSocket();
		}
		if (arg.equals(CommandListenerThread.itIsCommandLisnenerThread)) {

		}*/
	}
}