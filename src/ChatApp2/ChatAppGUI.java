import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by anna on 17.11.15.
 */
public class ChatAppGUI implements Observer {
    private JFrame frame = new JFrame();
    private ArrayList<String> arrayList = new ArrayList<String>();
    private JPanel panel;
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
    private CallWindow callWindow;


    private Connection connection = new Connection();
    private CommandListenerThread commandListenerThread;
    private BufferedReader buffReadIn;
    private CallListenerThread callListenerThread;
    private PrintWriter PWOut;
    private Caller caller = new Caller();
    Socket socket;
    static Boolean isHaveSocket=false, isCalling = false;
    Command command = new Command();
    private String nickName,friendNickName;
    private boolean isHaveNickName, isNextCallStage;
    private int tmpSize=25;
    private ChatAppGUI guiForm=this;
    private IPErrors ipErrors = new IPErrors();


    public ChatAppGUI() {
        panel.setBackground(Color.GREEN);
        frame.setContentPane(panel);
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        /*int port = 28411;
        callListenerThread = new CallListenerThread(28411);
        callListenerThread.addObserver(guiForm);
        callListenerThread.CallListenerStart("lama®");
        */

        applyButton.addActionListener(new ActionListener() {   /*Этот обработчик событый готов к использовиниюА*/
            @Override
            public void actionPerformed(ActionEvent e) {
                String nick = myNickNameField.getText();
                if (ChackGaps(nick)) {
                    arrayList.add("Вы ввели неправильный ник! ");
                    arrayList.add("Введите ник не используя пробелы!");
                } else {
                    isHaveNickName = true;
                    nickName = nick;
                    arrayList.add("Новый ник успешно установлен");
                }
                DialogArea.setText(getTextFromArray(arrayList));
            }
        });

        disconnectButton.addActionListener(new ActionListener() {//ok
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isHaveSocket) {
                    isHaveSocket = false;
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

        connectButton.addActionListener(new ActionListener() {//ок
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isHaveSocket) {
                    String IPstr;
                    String friendNickTmp;
                    IPstr = friendIPField.getText();
                    friendNickTmp = friendNickField.getText();
                    if (isHaveNickName) {
                        if (!IPstr.equals("") && !friendNickTmp.equals("")){
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
                    } else arrayList.add("Вы не заполнили свой НИК!!!");
                } else arrayList.add("У вас есть активное подключение!!!");
                DialogArea.setText(getTextFromArray(arrayList));
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isHaveSocket) {
                    String str = messageField.getText();
                    if(!str.equals("")) {
                        arrayList.add(str);
                        messageField.setText("");
                        connection.sendMessage(MessageCommand.message);
                        connection.sendMessage(str);
                    }else{
                        arrayList.add("Вы не ввели сообщение!");
                    }
                }else{
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
                caller.setRemoteNick(friendNickField.getText());
            }
        });

        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arrayList.add(messageField.getText());
            }
        });


    }



    private String getTextFromArray(ArrayList<String> arrayList) {
        String str = "";
        if(arrayList.size()<26) {
            for (int i = 0; i < arrayList.size(); i++) {
                str += arrayList.get(i) + "\n";
            }
        }else{
            int index=arrayList.size()-tmpSize;
            for (int i = index; i < arrayList.size(); i++) {
                str += arrayList.get(i) + "\n";
            }
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
        socket=commandListenerThread.getSocket();
        commandListenerThread.deleteObserver(guiForm);
        socket.close();
        commandListenerThread.stop();
       // socket.close();
    }

    @Override
    public void update(Observable o, Object arg) {
        String str = (String) arg;
        System.out.println("Good 1 "+isCalling);
        // it is new connection
        if (str.equals(CallListenerThread.itIsCallLisnenerThread)) {
            if (isHaveSocket) {
                Connection connection1 = new Connection();
                try {
                    connection1.setSocket(callListenerThread.getSocket());
                    connection1.sendNickBusy(nickName);
                } catch (IOException e) {
                }
            } else {
              /*  new InputConnection();
                try {
                    InputConnection inputConnection = new InputConnection();
                    if (inputConnection.flag) {
                        connection.setSocket(callListenerThread.getSocket());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        }

        //it is call user 2 stage
        if(isNextCallStage){
            if(str.equals(CommandListenerThread.itIsCommandLisnenerThread)){
                Command command1 = commandListenerThread.getLastCommand();
                if(command1.getCommand().equals(Command.accepted)){
                    arrayList.add("Подключение выполнено");
                    arrayList.add("");
                    callWindow.stopThisWindow();
                }
                if(command1.getCommand().equals(Command.rejected)){
                    callWindow.stopThisWindow();
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

        //it is call user 1 stage
      /*  if (isCalling) {
            if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
               Command command1 = commandListenerThread.getLastCommand();
                if (  command1.getCommand() == null) System.out.println("lalal");
                if(command1.equals(null) & command1.getCommand().equals(NickCommand.helloMessage)){
                    NickCommand nickCommand = (NickCommand)command1;
                    if(nickCommand.isBusy()){
                        friendNickName = nickCommand.getNick();
                        arrayList.add("Абонент "+friendNickName+" занят");
                        isHaveSocket = false;
                        try {
                            disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        CallWindow.guiForm = guiForm;
                        CallWindow.commandListenerThread = commandListenerThread;
                        CallWindow.isHaveSocket = isHaveSocket;
                        CallWindow.isNextCallStage = isNextCallStage;
                        CallWindow.s=nickName;
                       // callWindow.setTextField2();
                     //   CallWindow.textField2.setText(nickName);
                        isHaveSocket = true;
                        isNextCallStage = true;
                        connection.sendNickHello(nickName);
                        //start call window
                        callWindow = new CallWindow();
                    }
                }else {
                    isHaveSocket = false;
                    arrayList.add("Не удалось позвонить //поскольку неверный ответ сервера");
                }
            }else{
                isHaveSocket = false;
               arrayList.add("Не удалось позвонить");
                try {
                    disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isCalling = false;
        }*/

        if (str.equals(CommandListenerThread.itIsCommandLisnenerThread) && isHaveSocket) {
            Command command1 = commandListenerThread.getLastCommand();
            if (command1  != null){
                if(command.getCommand() == null) System.out.println("fuck");
                if(command1.getCommand().equals(Command.disconnect)){
                    arrayList.add("Вы были отключены другим юзером");
                    try {
                        disconnect();
                    } catch (IOException e) {
                       // e.printStackTrace();
                    }
                }
                if (command1.getCommand().equals(MessageCommand.message)) {
//                    MessageCommand mesCommand = (MessageCommand) command1;
                    BufferedReader buffReaderIn = null;
                    socket = commandListenerThread.getSocket();
                    try {
                        buffReaderIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        String messageStr = buffReaderIn.readLine();
                        arrayList.add(messageStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        DialogArea.setText(getTextFromArray(arrayList));
    }
}
/* caller.setRemoteAdress(friendIPField.getText());
                        caller.Call();
                        Socket s = caller.getSocket();
                        connection.sendNickHello(nickName);
                        isHaveSocket = true;
                        commandListenerThread = new CommandListenerThread("lalathread", connection);
                        commandListenerThread.addObserver(guiForm);
                        arrayList.add(String.valueOf(s.getLocalPort()));
                        arrayList.add(String.valueOf(s.getInetAddress()));
                        DialogArea.setText(getTextFromArray(arrayList));
*/
