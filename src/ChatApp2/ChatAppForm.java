package Laba4_plus;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import ChatApp2.*;

public class ChatAppForm {

	public static void main(String[] args) {
		ChatFrame frame = new ChatFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

class ChatFrame extends JFrame implements Observer {
	private ArrayList<String> arrayList = new ArrayList<String>();
	private JTextField myNickNameField;
	private JButton applyButton;
	private JTextField friendIPField;
	private JTextField friendNickField;
	private JButton disconnectButton;
	private JButton connectButton;
	private JLabel myNickName;
	private JLabel friendIP;
	private JLabel friendNick;
	private JTextField messageField;
	private JButton sendButton;
	private JTextArea DialogArea;
	private JScrollPane scrollPane;
	// private CallWindow callWindow;

	private Connection connection = new Connection();
	private CommandListenerThread commandListenerThread;
	private BufferedReader buffReadIn;
	private CallListenerThread callListenerThread;
	private PrintWriter PWOut;
	private Caller caller = new Caller();
	Socket socket;
	static Boolean isHaveSocket = false, isCalling = false;
	Command command = new Command();
	private String nickName, friendNickName;
	private boolean isHaveNickName, isNextCallStage, isWaitNextStageCalling;
	private int tmpSize = 14;
	private ChatFrame guiForm = this;
	private IPErrors ipErrors = new IPErrors();

	public ChatFrame() {
		setTitle("ChatApp");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setSize(screenSize.width / 2, screenSize.height / 2);
		Image img = kit.getImage("D:/icon.gif");
		setIconImage(img);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		myNickName = new JLabel("  You nick");
		myNickNameField = new JTextField(10);
		myNickNameField.setMaximumSize(myNickNameField.getPreferredSize());
		applyButton = new JButton("OK");
		Box you_box = Box.createHorizontalBox();
		you_box.add(myNickName);
		you_box.add(Box.createHorizontalStrut(10));
		you_box.add(myNickNameField);
		you_box.add(Box.createHorizontalStrut(10));
		you_box.add(applyButton);

		friendNick = new JLabel("Friend Nick");
		friendNickField = new JTextField(10);
		friendNickField.setMaximumSize(friendNickField.getPreferredSize());
		connectButton = new JButton("Connect");
		Box him_box = Box.createHorizontalBox();
		him_box.add(friendNick);
		him_box.add(Box.createHorizontalStrut(10));
		him_box.add(friendNickField);
		him_box.add(Box.createHorizontalStrut(10));
		him_box.add(connectButton);

		friendIP = new JLabel("Friend IP");
		friendIPField = new JTextField(10);
		friendIPField.setMaximumSize(friendIPField.getPreferredSize());
		disconnectButton = new JButton("Disconnect");
		Box him_IP_box = Box.createHorizontalBox();
		him_IP_box.add(friendIP);
		him_IP_box.add(Box.createHorizontalStrut(10));
		him_IP_box.add(friendIPField);
		him_IP_box.add(Box.createHorizontalStrut(10));
		him_IP_box.add(disconnectButton);

		Box him_super_box = Box.createVerticalBox();
		him_super_box.add(him_box);
		him_super_box.add(him_IP_box);

		Box up_super_box = Box.createHorizontalBox();
		up_super_box.add(you_box);
		up_super_box.add(Box.createGlue());
		up_super_box.add(him_super_box);

		messageField = new JTextField(100);
		messageField.setMaximumSize(messageField.getPreferredSize());
		sendButton = new JButton("Sent");
		Box message_box = Box.createHorizontalBox();
		message_box.add(messageField);
		message_box.add(Box.createHorizontalStrut(10));
		message_box.add(sendButton);

		// JTextField messages = new JTextField(100);
		scrollPane = new JScrollPane();
		DialogArea = new JTextArea();
		scrollPane.setMaximumSize(DialogArea.getMaximumSize());
		scrollPane.add(DialogArea);
		/// scrollPane.setMaximumSize(null);

		Box super_box = Box.createVerticalBox();
		super_box.add(up_super_box);
		super_box.add(Box.createVerticalGlue());
		super_box.add(DialogArea);
		//super_box.add(scrollPane);
		super_box.add(Box.createVerticalGlue());
		super_box.add(message_box);
		add(super_box, BorderLayout.CENTER);

		
		  int port = 28411; callListenerThread = new CallListenerThread(28411);
		  callListenerThread.addObserver(guiForm);
		  callListenerThread.CallListenerStart("lama®");
		 

		applyButton.addActionListener(
				new ActionListener() { /*
										 * Этот обработчик событый готов к
										 * использовиниюА
										 */
					@Override
					public void actionPerformed(ActionEvent e) {
						String nick = myNickNameField.getText();
						if (!nick.equals("")) {
							if (ChackGaps(nick)) {
								arrayList.add("Вы ввели неправильный ник! ");
								arrayList.add("Введите ник не используя пробелы!");
							} else {
								isHaveNickName = true;
								nickName = nick;
								arrayList.add("Новый ник: " + nick + " успешно установлен");
							}
						} else
							arrayList.add("Вы должны заполнить поле для ввода");
						DialogArea.setText(getTextFromArray(arrayList));
					}
				});

		disconnectButton.addActionListener(new ActionListener() {// ok
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHaveSocket) {
					arrayList.add("Отключено");
					try {
						disconnect();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					arrayList.add("У Вас нет активного подключения");
				}
				DialogArea.setText(getTextFromArray(arrayList));
			}
		});

		connectButton.addActionListener(new ActionListener() {// ок
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isHaveSocket) {
					String IPstr;
					String friendNickTmp;
					IPstr = friendIPField.getText();
					friendNickTmp = friendNickField.getText();
					if (isHaveNickName) {
						if (!IPstr.equals("") && !friendNickTmp.equals("")) {
							if (!ChackGaps(friendNickTmp)) {
								caller.setRemoteAdress(IPstr);
								try {
									caller.Call();
									Socket s = caller.getSocket();
									connection.setSocket(s);
									isHaveSocket = true;
									commandListenerThread = new CommandListenerThread("lalathread", connection);
									commandListenerThread.addObserver(guiForm);
									commandListenerThread.CommandListenerThreadStart();
									arrayList.add(String.valueOf(s.getLocalPort()));
									arrayList.add(String.valueOf(s.getInetAddress()));
									isCalling = true;
								} catch (IOException e1) {
									ipErrors.addErrorIP(IPstr);
									arrayList.add("Вызов по данному адресу не удался");
								}
								/// call window
							} else {
								arrayList.add("Вы ввели неправильный ник друга! ");
								arrayList.add("Введите ник не используя пробелы!");
							}
						} else {
							arrayList.add("Вы не заполнили все поля!!!");
						}
					} else
						arrayList.add("Вы не заполнили свой НИК!!!");
				} else
					arrayList.add("У вас есть активное подключение!!!");
				DialogArea.setText(getTextFromArray(arrayList));
			}
		});

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHaveSocket) {
					String str = messageField.getText();
					if (!str.equals("")) {
						arrayList.add(str);
						messageField.setText("");
						connection.sendMessage(MessageCommand.message);
						connection.sendMessage(str);
					} else {
						arrayList.add("Вы не ввели сообщение!");
					}
				} else {
					arrayList.add("нет доступного подклчения");
				}
				DialogArea.setText(getTextFromArray(arrayList));
			}
		});

		myNickNameField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nickName = myNickNameField.getText();
			}
		});

		friendIPField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				caller.setRemoteAdress(friendIPField.getText());
			}
		});

		friendNickField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// caller.setRemoteNick(friendNickField.getText());
			}
		});

		messageField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				arrayList.add(messageField.getText());
			}
		});
	}

	public void disconnect() throws IOException {
		socket = commandListenerThread.getSocket();
		commandListenerThread.deleteObserver(guiForm);
		socket.close();
		commandListenerThread.stop();
		isHaveSocket = false;
	}

	private boolean ChackGaps(String test) {
		for (int j = 0; j < test.length(); j++) {
			if ((test.charAt(j)) == ' ') {
				return true;
			}
		}
		return false;
	}

	private String getTextFromArray(ArrayList<String> arrayList) {
		String str = "";
		if (arrayList.size() < tmpSize) {
			for (int i = 0; i < arrayList.size(); i++) {
				str += arrayList.get(i) + "\n";
			}
		} else {
			int index = arrayList.size() - tmpSize;
			for (int i = index; i < arrayList.size(); i++) {
				str += arrayList.get(i) + "\n";
			}
		}
		return str;
	}

	//////////////////
	public void update(Observable o, Object args) {
		boolean isNotMaked = true;
		String str = (String) args;
		//System.out.println("Good 1 " + isCalling);

		if (str.equals(CallListenerThread.itIsCallLisnenerThread)) {
			Connection connection1 = new Connection();
			isNotMaked = false;
			if (isHaveSocket) {
				try {
					connection1.setSocket(callListenerThread.getSocket());
					connection1.sendNickBusy(nickName);
					connection1.getSocket().close();
				} catch (IOException e) {
				}
			} else
				try {
					try {
						disconnect();
					} catch (Exception e) {
					}
					connection1.setSocket(callListenerThread.getSocket());
					connection1.sendNickHello(nickName);
					commandListenerThread = new CommandListenerThread("lala1", connection1);
					commandListenerThread.addObserver(this);
					commandListenerThread.CommandListenerThreadStart();
					isWaitNextStageCalling = true;
				} catch (IOException e) {
				}
		}

		if (isNotMaked && isWaitNextStageCalling) {
			if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
				isWaitNextStageCalling = false;
				isNotMaked = false;
				try {
					Command command1 = commandListenerThread.getLastCommand();
					if (command1.getCommand() == null) System.out.println("null");
					if (command1 != null && command1.getCommand().equals(NickCommand.helloMessage)) {
						NickCommand nickCommand = (NickCommand) command1;
						InputConnection inputConnection = new InputConnection(nickCommand.getNick());
						InputConnection.isInputConnection = true;
						while (InputConnection.isInputConnection) {
							System.out.println(InputConnection.isInputConnection+ "  " + InputConnection.flag);
						}
						if (InputConnection.flag) {
							System.out.println("Подключение выполнено");
							connection.setSocket(commandListenerThread.getSocket());
							connection.accept();
							arrayList.add("Подключение выполнено");
							isHaveSocket = true;
						} else {
							connection.setSocket(commandListenerThread.getSocket());
							connection.reject();
							System.out.println("Подключение не выполнено");
							arrayList.add("Подключение НЕ выполнено, Вы отказались");
							try {
								disconnect();
							} catch (IOException ee) {
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (isNotMaked && isCalling) {
			if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
				isCalling = false;
				isNotMaked = false;
				Command command1 = commandListenerThread.getLastCommand();

				if (command1 == null)
					System.out.println("holy shit");
				if (command1.getCommand() == null)
					System.out.println("lalal holy shit");

				if (command1 != null && command1.getCommand().equals(NickCommand.helloMessage)) {
					NickCommand nickCommand = (NickCommand) command1;
					if (nickCommand.isBusy()) {
						String friendNickNameTmp = nickCommand.getNick();
						friendNickField.setText(friendNickNameTmp);
						arrayList.add("Абонент " + friendNickNameTmp + " занят");
						isHaveSocket = false;
						try {
							disconnect();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {

						// CallWindow.guiForm = guiForm;
						/*
						 * CallWindow.commandListenerThread =
						 * commandListenerThread; CallWindow.isHaveSocket =
						 * isHaveSocket; CallWindow.isNextCallStage =
						 * isNextCallStage; CallWindow.s=nickName;
						 */
						// callWindow.setTextField2();
						// CallWindow.textField2.setText(nickName);
						isHaveSocket = true;
						isNextCallStage = true;
						connection.sendNickHello(nickName);

						// start call window
						// callWindow = new CallWindow();
					}
				} else {
					isHaveSocket = false;
					arrayList.add("Не удалось позвонить //поскольку неверный ответ сервера");
				}
			} else {
				isHaveSocket = false;
				arrayList.add("Не удалось позвонить");
				try {
					disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			isCalling = false;
		}

		// it is call user 2 stage
		if (isNotMaked && isNextCallStage) {
			if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
				isNotMaked = false;
				Command command1 = commandListenerThread.getLastCommand();
				if (command1.getCommand().equals(Command.accepted)) {
					arrayList.add("Подключение выполнено");
					arrayList.add("");
					// callWindow.stopThisWindow();
				}
				if (command1.getCommand().equals(Command.rejected)) {
					// callWindow.stopThisWindow();
					arrayList.add("Клиент отказался подключаться");
					try {
						disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				isNextCallStage = false;
			}
		}

		if (isNotMaked && isNextCallStage) {
			if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
				isNotMaked = false;
				Command command1 = commandListenerThread.getLastCommand();
				if (command1.getCommand().equals(Command.accepted)) {
					arrayList.add("Подключение выполнено");
					arrayList.add("");
					// callWindow.stopThisWindow();
				}
				if (command1.getCommand().equals(Command.rejected)) {
					// callWindow.stopThisWindow();
					arrayList.add("Товарищ отказался подключаться");
					try {
						disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				isNextCallStage = false;
			}
		}

		if (isNotMaked && str.equals(CommandListenerThread.itIsCommandLisnenerThread) && isHaveSocket) {
			Command command1 = commandListenerThread.getLastCommand();
			if (command1 != null) {
				isNotMaked = false;
				if (command.getCommand() == null)
					System.out.println("fuck");
				if (command1.getCommand().equals(Command.disconnect)) {
					arrayList.add("Вы были отключены другим юзером");
					try {
						disconnect();
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
				if (command1.getCommand().equals(MessageCommand.message)) {
					MessageCommand messageCommand = (MessageCommand) command1;
					arrayList.add(messageCommand.getMessage());

				}
			}

		}
		DialogArea.setText(getTextFromArray(arrayList));
	}

}
