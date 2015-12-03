import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by anna on 02.12.15.
 */
public class Controll {
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
    private boolean isHaveNickName, isNextCallStage;
    private int tmpSize = 25;
    private ChatAppGUI guiForm;
    private IPErrors ipErrors = new IPErrors();
    private String IP;
    private Contacts contacts;
    private WorkWithContactsFile wwcf;
    ServerConnection c;
    static ArrayList<String> arrayList = new ArrayList<String>();
    private ChatAppGUI chatAppGUI;
    private ArrayList<String> arrayListFriends = new ArrayList<String>();

public Controll(){
    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    c = new ServerConnection();
    c.setServerAddress("jdbc:mysql://files.litvinov.in.ua/chatapp_server?characterEncoding=utf-8&useUnicode=true");
    c.connect();
}

    public Contacts getLocalContacts() {
        try {
            wwcf = new WorkWithContactsFile();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wwcf.readContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contacts = wwcf.getContacts();
        return wwcf.getContacts();
    }

    public void setChatAppGUI(ChatAppGUI chat){
      chatAppGUI = chat;
    }

    public String[] getServerContacts(){
        return c.getAllNicks();
    }

    public void addToContacts(String nick, String IP){
        try {
            wwcf = new WorkWithContactsFile();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wwcf.readContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contacts = wwcf.getContacts();
        boolean bool = false;
        for (int i = 0; i<contacts.getSize(); i++)
            if (nick.equals(contacts.getNick(i))) bool = true;
        if (!bool){
            try {
                wwcf.addContactToFile(nick,IP);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        String tmpDelete = chatAppGUI.getTextFromField("friendNickField");
        if(!tmpDelete.equals("")) {
            try {
                wwcf.deleteContacts(chatAppGUI.getTextFromField("friendNickField"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void connect() {
        if (!isHaveSocket) {
            String IPstr;
            String friendNickTmp;
            friendNickTmp = chatAppGUI.getTextFromField("friendNickField");
            if (friendNickTmp.equals("") == false) {
                IPstr = c.getIpForNick(friendNickTmp);
                if (c.isNickOnline(friendNickTmp)) {
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
                } else {
                    arrayList.add("Ваш друг не в онлайне!");
                }
            }else {arrayList.add("Такого друга нет на сервере");}
        } else
        arrayList.add("У вас есть активное подключение!!!");
    }

    public ArrayList<String> getArrayListFriends(){
        arrayListFriends = new ArrayList<String>();
        for(int i=0;i<contacts.getSize();i++){
            arrayListFriends.add(contacts.getNick(i)+" "+contacts.getIP(i)+"\n");
        }
        return arrayListFriends;
    }


    public void apply(){

        String nick = chatAppGUI.getTextFromField("myNickNameField");
         if (!isHaveNickName){
             if(!nick.equals("")) {
                 if (ChackGaps(nick)) {
                     arrayList.add("Вы ввели неправильный ник! ");
                     arrayList.add("Введите ник не используя пробелы!");
                 } else /*if(!compareToServerListContacts(nick)) {
                     isHaveNickName = true;
                     nickName = nick;
                     c.setLocalNick(nickName);
                     arrayList.add("Вы зарегистрированы");
                 }else*/{
                     isHaveNickName = true;
                     nickName = nick;
                     c.setLocalNick(nickName);
                     arrayList.add("Вход в программу успешно выполнен");
                     c.goOnline();
                 }
             }
         } else {
             arrayList.add("Чтобы войти с другого аккаунта или зарегистрироваться, вы должны выйти из текущего аккаунта!");
         }
    }

    public void disconnect(){
        if (isHaveSocket) {
            isHaveSocket = false;
            arrayList.add("Отключено");
            try {
                disconnectClose();
            } catch (IOException e) {
                e.printStackTrace();
            }
            c.goOffline();
        } else {
            arrayList.add("У Вас нет активного подключения");
        }
    }

    public void cancel(){
        isHaveNickName = false;
        chatAppGUI.myNickFieldClear();
        arrayList.clear();
    }

    public void send(){
        if (isHaveSocket) {
            String str =chatAppGUI.getTextFromField("messageField");
            if (!str.equals("")) {
                arrayList.add(str);
                chatAppGUI.setTextToMessageField();
                connection.sendMessage(MessageCommand.message);
                connection.sendMessage(str);
            } else {
                arrayList.add("Вы не ввели сообщение!");
            }
        } else {
            arrayList.add("нет доступного подклчения");
        }
    }

     public ArrayList<String> search(){
         ArrayList<String> arrayListTmp = new ArrayList<String>();
         arrayList.add(0,"");
         arrayList.add(1,"");
         String str = chatAppGUI.getTextFromField("friendNickField");
         if (!str.equals("")){
             if(compareToServerListContacts(str)){
                 arrayListTmp.add(str);
                 arrayListTmp.add(c.getIpForNick(str));
             }else{
                 arrayList.add(0,"Такого пользователя нет на сервере");
             }
         }else{
             arrayList.add(1,"Введите имя друга!");
         }
         return arrayListTmp;
     }

    public boolean compareToServerListContacts(String nick){
        String [] str = c.getAllNicks();
        for (int i=0;i<str.length;i++){
            if(nick.equals(str[i])){
                return true;
            }
        }
        return false;
    }

    public void disconnectClose() throws IOException {
        socket=commandListenerThread.getSocket();
        commandListenerThread.deleteObserver(guiForm);
        socket.close();
        commandListenerThread.stop();
        isHaveSocket = false;
        // socket.close();
    }

    public boolean ChackGaps(String test) {
        for (int j = 0; j < test.length(); j++) {
            if ((test.charAt(j)) == ' ') {
                return true;
            }
        }
        return false;
    }
}


