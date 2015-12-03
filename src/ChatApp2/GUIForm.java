import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by anna on 12.11.15.
 */
public class GUIForm implements Observer {
    private ArrayList<String> arrayList = new ArrayList<String>();
    private JFrame frame = new JFrame();
    private JButton applyButton;
    private JButton disconnectionButton;
    private JButton connectionButton;
    private JButton sendButton;
    private JTextField loginField;
    private JTextField friendIPField;
    private JPanel panel;
    private JTextField friendNickNameField;
    private JLabel login;
    private JLabel friendIP;
    private JLabel friendNickName;
    private JScrollPane scrollPanel;
    private JTextField messagePanel;
    private String nickName;
    private Connection connection = new Connection();
    private CommandListenerThread commandListenerThread;
    private BufferedReader buffReadIn;
    private CallListenerThread callListenerThread;
    private PrintWriter PWOut;
    private Caller caller = new Caller();
    Socket socket;
    static boolean isHaveSocket;
    Command command = new Command();
    GUIForm guiForm = new GUIForm();


    public GUIForm() {
        //scrollPanel.createVerticalScrollBar();
        frame.setContentPane(panel);
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        callListenerThread = new CallListenerThread(28411);

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = loginField.getText();
                if (ChackGaps(nick)) {
                    arrayList.add("вы ввели неправильный ник! введите без пробелов!");
                    //DialogArea.setText(getTextFromArray(arrayList));
                   // scrollPanel.setToolTipText(getTextFromArray(arrayList));
                } else {
                    nickName = nick;
                    arrayList.add("nick had set!");
                    // DialogArea.setText(getTextFromArray(arrayList));
                    //scrollPanel.setToolTipText(getTextFromArray(arrayList));
                }
            }
        });

        disconnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isHaveSocket) {
                    isHaveSocket = false;
                    try {
                        disconnect();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    caller.setRemoteAdress(friendIPField.getText());
                    caller.Call();
                    Socket s = caller.getSocket();
                    connection.sendNickHello(nickName);
                    isHaveSocket = true;
                    commandListenerThread = new CommandListenerThread("lalathread", connection);
                    commandListenerThread.addObserver(guiForm);
                    arrayList.add(String.valueOf(s.getLocalPort()));
                    arrayList.add(String.valueOf(s.getInetAddress()));
                    //scrollPanel.setToolTipText(getTextFromArray(arrayList));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    caller.Call();
                } catch (IOException io) {
                    arrayList.add("Error");
                    //DialogArea.setText(getTextFromArray(arrayList));
                   // scrollPanel.setToolTipText(getTextFromArray(arrayList));
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //DialogArea.setText(getTextFromArray(arrayList));
               // scrollPanel.setToolTipText(getTextFromArray(arrayList));
            }
        });

        loginField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickName = loginField.getText();
            }
        });

        friendIPField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caller.setRemoteAdress(friendIPField.getText());
            }
        });

        friendNickNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caller.setRemoteNick(friendNickNameField.getText());
            }
        });

        messagePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arrayList.add(messagePanel.getText());
            }
        });


    }

    private String getTextFromArray(ArrayList<String> arrayList) {
        String str = "";
        for (int i = 0; i < arrayList.size(); i++) {
            str += arrayList.get(i) + "\n";
        }
        return str;
    }

    private boolean ChackGaps(String test) {
        for (int j = 0; j < test.length(); j++) {
            if ((test.charAt(j)) == ' ') {
                return true;
            }
        }
        return false;
    }

    public void disconnect() throws IOException {
        commandListenerThread.stop();
        commandListenerThread.deleteObserver(guiForm);
        socket.close();
    }

    @Override
    public void update(Observable o, Object arg) {
        String str = (String) arg;
        if (str.equals(CallListenerThread.itIsCallLisnenerThread)) {
            if (isHaveSocket) {
                connection.sendNickBusy(nickName);
            } else {
                new InputConnection();
                try {
                    InputConnection inputConnection = new InputConnection();
                    if (inputConnection.flag) {
                        connection.setSocket(callListenerThread.getSocket());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
                NickCommand nickCommand = new NickCommand();
                Command command1 = commandListenerThread.getLastCommand();
                if (isHaveSocket) {
                    if (command1.getCommand().equals(MessageCommand.message)) {

                    } else if (command1.equals(nickCommand.getNick())) {

                    } else if (command1.equals(Command.disconnect)) {
                        try {
                            isHaveSocket = false;
                            disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (command1.equals(Command.accepted)) {

                    } else if (command1.equals(Command.rejected)) {
                    } else {
                    }

                }
            }
        }

    }
    public  void createUIComponents(){

    }
}

