import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import java.sql.*;
/**
 * Created by anna on 17.11.15.
 */

public class ChatAppGUI implements Observer {
    private JFrame frame = new JFrame();
    private ArrayList<String> arrayList = new ArrayList<String>();
    private JPanel panel;
    private JTextField myNickNameField;
    private JButton applyButton;
    //private JTextField friendIPField;
    private JTextField friendNickField;
    private JButton disconnectButton;
    private JButton connectButton;
    private JLabel myNickName;
    private JLabel friendNick;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea DialogArea;
   // private JButton contactsButton;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JScrollBar scrollBar1;
    private JButton contactsButton;
    private JButton cancelButton;
    private JButton myFriendsButton;
    private JButton deleteButton;
    private CallWindow callWindow;
    private JScrollBar scrollBar;
    private Controll controll;


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
    private String IP;
    private Contacts contacts;
    ServerConnection c;
    private ArrayList<String> arrayListFriends = new ArrayList<String>();
    private ArrayList<String> serverContacts = new ArrayList<String>();

    public ChatAppGUI(String hi){

    }

    public  void setControl(Controll con){
        controll = con;
    }

    public ChatAppGUI() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        c = new ServerConnection();
        c.setServerAddress("jdbc:mysql://files.litvinov.in.ua/chatapp_server?characterEncoding=utf-8&useUnicode=true");
        c.connect();
        scrollBar1 = scrollPane.createVerticalScrollBar();
        panel.setBackground(Color.GREEN);
        frame.setContentPane(panel);
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
//        contacts = controll.getLocalContacts();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.cancel();
                // Controll.arrayList.clear();
                DialogArea.setText(getTextFromArray(Controll.arrayList));
            }
        });
        myFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contacts = controll.getLocalContacts();
                if (contacts.getSize() == 0) {
                    // arrayList.add();
                    DialogArea.setText("Список контактов пустой");
                } else {
                    DialogArea.setText(getTextFromArray(controll.getArrayListFriends()));
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.delete();
            }
        });

        applyButton.addActionListener(new ActionListener() {   /*Этот обработчик событый готов к использовиниюА*/
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.apply();
                System.out.println(getTextFromArray(Controll.arrayList));
                DialogArea.setText(getTextFromArray(Controll.arrayList));
               /* if (!isHaveNickName) {
                    String nick = myNickNameField.getText();
                    if (ChackGaps(nick)) {
                        arrayList.add("Вы ввели неправильный ник! ");
                        arrayList.add("Введите ник не используя пробелы!");
                    } else {
                        isHaveNickName = true;
                        nickName = nick;
                        arrayList.add("Вы зарегистрированы");
                        c.setLocalNick(nickName);
                    }
                }else{
                     String tmp = myNickNameField.getText();
                    if (tmp.equals("")==false&&tmp.equals(nickName)==false){
                        nickName = myNickNameField.getText();
                        c.setLocalNick(nickName);
                        arrayList.add("Вы зарегистрированы");
                       // arrayList.add("ваши данные успешно изменены");
                    }
                }
                arrayList.add("Вход в программу успешно выполнен");
                c.goOnline();
                DialogArea.setText(getTextFromArray(arrayList));
                /*list1.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {

                    }
                });*/
            }
        });


        contactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] arrayOfContacts = controll.getServerContacts();
                for (int i = 0; i < arrayOfContacts.length; i++) {
                    serverContacts.add(arrayOfContacts[i]);
                }
                DialogArea.setText(getTextFromArray(serverContacts));
            }
        });

        disconnectButton.addActionListener(new ActionListener() {//ok
            @Override
            public void actionPerformed(ActionEvent e) {
                /*if (isHaveSocket) {
                     isHaveSocket = false;
                    arrayList.add("Отключено");
                    try {
                        disconnect();
                        c.goOffline();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    arrayList.add("У Вас нет активного подключения");
                }*/
                controll.disconnect();
                DialogArea.setText(getTextFromArray(arrayList));
                //DialogArea.setText(getTextFromArray(arrayList));
            }
        });

        connectButton.addActionListener(new ActionListener() {//ок
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.connect();
                DialogArea.setText(getTextFromArray(Controll.arrayList));

            }
        });
               /* if (!isHaveSocket) {
                    String IPstr;
                    String friendNickTmp;
                    friendNickTmp = friendNickField.getText();
                    //IPstr = searchClient(friendNickTmp);
                    IPstr=c.getIpForNick(friendNickTmp);
                    //IPstr = friendIPField.getText(
                    //if (isHaveNickName) {
                    if(c.isNickOnline(nickName)&&c.isNickOnline(friendNickTmp)){
                        if (!IPstr.equals("") && !friendNickTmp.equals("")) {
                            //  if (!ChackGaps(friendNickTmp)) {
                            caller.setRemoteAdress(IPstr);
                            try {
                                caller.Call();
                                Socket s = caller.getSocket();
                                connection.setSocket(s);
                                isHaveSocket = true;
                                IP = IPstr;
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
                           /* } else {
                                arrayList.add("Вы ввели неправильный ник друга! ");
                                arrayList.add("Введите ник не используя пробелы!");
                            }
                        } else {
                            arrayList.add("Вы не заполнили все поля!!!");
                        }
                    } else arrayList.add("Вы не зарегистрированы!!!");
                } else arrayList.add("У вас есть активное подключение!!!");
                scrollPane.setToolTipText(getTextFromArray(arrayList));
                // DialogArea.setText(getTextFromArray(arrayList));
            }*/
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* if (isHaveSocket) {
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
                }*/
                controll.send();
                DialogArea.setText(getTextFromArray(arrayList));
                // DialogArea.setText(getTextFromArray(arrayList));
            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> arrayListTmp = controll.search();
                if (!arrayListTmp.get(0).equals("") && !arrayListTmp.get(1).equals("")) {
                    controll.addToContacts(arrayListTmp.get(0), arrayListTmp.get(1));
                    DialogArea.setText(arrayListTmp.get(0) + " " + arrayListTmp.get(1));
                } else {
                    DialogArea.setText(getTextFromArray(arrayList));
                }

               /* if (friendNickField.getText().equals("") == false) {
                    IP = searchClient(friendNickField.getText());
                    friendNickName = friendNickField.getText();
                    if ("".equals(IP) == false) {
                        scrollPane.setToolTipText(friendNickField.getText() + " " + IP);
                    }
                }
            }*/
            }
        });



      /*  myNickNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  nickName = myNickNameField.getText();
            }
        });

        /*friendIPField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caller.setRemoteAdress(friendIPField.getText());
            }
        });
*/
      /*  friendNickField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // caller.setRemoteNick(friendNickField.getText());
            }
        });
    }
       /* messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arrayList.add(messageField.getText());
            }
        });}
*/
        contactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListContact();
            }
        });
        //  }

    }
  public void myNickFieldClear(){
      myNickNameField.setText("");
  }
    private String getTextFromArray(ArrayList<String> arrayList) {
        String str = "";
        // if(arrayList.size()<26) {
        for (int i = 0; i < arrayList.size(); i++) {
            str += arrayList.get(i) + "\n";
        }
        return str;
    }
        /*    }
        }else{
            int index=arrayList.size()-tmpSize;
            for (int i = index; i < arrayList.size(); i++) {
                str += arrayList.get(i) + "\n";
            }
        }
        return str;
    }*/

    public String searchClient(String textFromSearch){
        int size = contacts.getSize();
        for(int i =0;i<size;i++){
            if(contacts.getNick(i)==textFromSearch){
                IP = contacts.getIP(i);
                return IP;
            }
        }
        return null;
    }
    public boolean ChackGaps(String test) {
        for (int j = 0; j < test.length(); j++) {
            if ((test.charAt(j)) == ' ') {
                return true;
            }
        }
        return false;
    }

    public String getTextFromField(String str){
        if (str.equals("friendNickField")){
            return friendNickField.getText();
        }else if(str.equals("myNickNameField")){
            return myNickNameField.getText();
        }else if(str.equals("messageField")){
          return messageField.getText();
        }
   return null ;
    }

    public void setTextToMessageField(){
        messageField.setText("");
    }

   /* public void disconnect() throws IOException {
        socket=commandListenerThread.getSocket();
        commandListenerThread.deleteObserver(guiForm);
        socket.close();
        commandListenerThread.stop();
        isHaveSocket = false;
       // socket.close();
    }
*/
    @Override
    public void update(Observable o, Object arg) {
        String str = (String) arg;
        System.out.println("Good 1 "+isCalling);
        // it is new connection
        if (str.equals(CallListenerThread.itIsCallLisnenerThread)) {
            if (isHaveSocket) {
               // Connection connection1 = new Connection();
                connection = new Connection();
                try {
                    connection.setSocket(callListenerThread.getSocket());
                    connection.sendNickBusy(nickName);
                } catch (IOException e) {
                }
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
                        controll.disconnectClose();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                isNextCallStage = false;
            }
        }

        //it is call user 1 stage
        if (isCalling) {
            if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
               Command command1 = commandListenerThread.getLastCommand();
                if (  command1.getCommand() == null) System.out.println("lalal");
                if(command1.equals(null) & command1.getCommand().equals(NickCommand.helloMessage)){
                    NickCommand nickCommand = (NickCommand)command1;
                    if(nickCommand.isBusy()){
                        friendNickName = nickCommand.getNick();
                        friendNickField.setText(friendNickName);
                        arrayList.add("Абонент "+friendNickName+" занят");
                        isHaveSocket = false;
                        try {
                            controll.disconnectClose();
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
                    controll.disconnectClose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isCalling = false;
        }

        if (str.equals(CommandListenerThread.itIsCommandLisnenerThread) && isHaveSocket) {
            Command command1 = commandListenerThread.getLastCommand();
            if (command1  != null){
                if(command.getCommand() == null) System.out.println("fuck");
                if(command1.getCommand().equals(Command.disconnect)){
                    arrayList.add("Вы были отключены другим юзером");
                    try {
                        controll.disconnectClose();
                    } catch (IOException e) {
                       // e.printStackTrace();
                    }
                }
                if (command1.getCommand().equals(MessageCommand.message)) {
//                    MessageCommand mesCommand = (MessageCommand) command1;
                    /*BufferedReader buffReaderIn = null;
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
                }*/
                    String messageString = connection.readMessage();
                    arrayList.add(messageString);
                   // scrollPane.setToolTipText(getTextFromArray(arrayList));
                    DialogArea.setText(getTextFromArray(arrayList));
            }
        }

    }
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
