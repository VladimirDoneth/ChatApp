import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by anna on 02.12.15.
 */
public class Controll implements Observer {
    private String friendNickNameTmp;
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
    //  private ChatAppGUI guiForm;
   // private IPErrors ipErrors = new IPErrors();
    private String IP;
    private Contacts contacts;
    private WorkWithContactsFile wwcf;
    ServerConnection c;
    static ArrayList<String> arrayList = new ArrayList<String>();
    //   private ChatAppGUI chatAppGUI;
    private ArrayList<String> arrayListFriends = new ArrayList<String>();
    private Registration registration;
    static boolean isflagEntry;
    private UserWindow userWindow;
    private DialogWindow dialogWindow;
    private boolean isWaitNextStageCalling;
    static ArrayList<String>arrayListMessage= new ArrayList<String>();

    public Controll(){
        callListenerThread = new CallListenerThread(28411);
        callListenerThread.addObserver(this);
        callListenerThread.CallListenerStart("loloshka");
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
    public void setRegistration(Registration registration){
        this.registration = registration;
    }
/*public void setDialogWindow(DialogWindow dialogWindow){
    this.dialogWindow = dialogWindow;
}
    *//*public void setChatAppGUI(ChatAppGUI chat){
      chatAppGUI = chat;
    }*/
    public void setUserWindow(UserWindow userWindow){
        this.userWindow = userWindow;
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
    public void delete(String deleteFriend) {
        if (!deleteFriend.equals("")) {
            try {
                wwcf.deleteContacts(deleteFriend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  /* public void delete(){
        String tmpDelete = chatAppGUI.getTextFromField("friendNickField");
        if(!tmpDelete.equals("")) {
            try {
                wwcf.deleteContacts(chatAppGUI.getTextFromField("friendNickField"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public void connect(String friendNickTmp){
        if (!isHaveSocket){
            arrayList.clear();
           if (!friendNickTmp.equals("")){
               Vector<String> nickVector =search(friendNickTmp);
               if(!nickVector.get(0).equals("")){
                   if (!nickVector.get(1).equals("")) {
                       if (c.isNickOnline(friendNickTmp)) {
                           caller.setRemoteAdress(nickVector.get(1));
                           try {
                               caller.Call();
                               Socket s = caller.getSocket();
                               connection.setSocket(s);
                               IP = nickVector.get(1);
                               commandListenerThread = new CommandListenerThread("lalathread", connection);
                               commandListenerThread.addObserver(this);//? так?
                               commandListenerThread.CommandListenerThreadStart();
                               arrayList.add(String.valueOf(s.getLocalPort()));
                               arrayList.add(String.valueOf(s.getInetAddress()));
                               isCalling = true;
                               isHaveSocket = true;
                               arrayList.add("Вызов по данному адресу выполнен");
                           } catch (IOException e1) {
                               // ipErrors.addErrorIP(IPstr);
                               arrayList.add("Вызов по данному адресу не удался");
                           }
                       } else {
                           arrayList.add("Ваш друг не в онлайне!");
                       }
                   }
                   else {
                       arrayList.add("Ваш друг не в онлайне!");
                   }
               } else {
                   arrayList.add("Такого друга нет на сервере");
               }
           } else {
               arrayList.add("Такого друга нет на сервере");
           }
        } else {
            arrayList.add("У вас есть активное подключение!!!");
        }
        if(UserWindow.serverOrFriends.equals("server")) {
            JOptionPane.showMessageDialog(PeopleOptionPane.frame, getTextFromArray(arrayList));
        } else {
            JOptionPane.showMessageDialog(FriendOptionPane.frame,getTextFromArray(arrayList));
        }
    }

    /*
    public void connect(String friendNickTmp) {
        if (!isHaveSocket) {
            arrayList.clear();
            String IPstr;
            //String friendNickTmp;
           // friendNickTmp = chatAppGUI.getTextFromField("friendNickField");
            if (friendNickTmp.equals("") == false) {
                Vector nickVector = search(friendNickTmp);
                if(!nickVector.get(1).equals("")){
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
                            commandListenerThread.addObserver(this);//? так?
                            commandListenerThread.CommandListenerThreadStart();
                            arrayList.add(String.valueOf(s.getLocalPort()));
                            arrayList.add(String.valueOf(s.getInetAddress()));
                            isCalling = true;
                        } catch (IOException e1) {
                            // ipErrors.addErrorIP(IPstr);
                            arrayList.add("Вызов по данному адресу не удался");
                        }
                    } else {
                        arrayList.add("Ваш друг не в онлайне!");
                    }
                }
            }else {arrayList.add("Такого друга нет на сервере");}
        } else
            arrayList.add("У вас есть активное подключение!!!");
       // JOptionPane.showMessageDialog();
        if(UserWindow.serverOrFriends.equals("server")) {
            JOptionPane.showMessageDialog(PeopleOptionPane.frame,getTextFromArray(arrayList));
        } else {
            JOptionPane.showMessageDialog(FriendOptionPane.frame,getTextFromArray(arrayList));
        }
    }
    */

    public ArrayList<String> getArrayListFriends(){
        arrayListFriends = new ArrayList<String>();
        for(int i=0;i<contacts.getSize();i++){
            arrayListFriends.add(contacts.getNick(i)+" "+contacts.getIP(i)+"\n");
        }
        return arrayListFriends;
    }

    public boolean registration(){
        isflagEntry=false;
        arrayList.clear();
        String nick = registration.getTextFromField();
        //System.out.println(nick);
        if (!isHaveNickName) {
            if (!nick.equals("")){
                if (!ChackGaps(nick)){
                    if(!compareToServerListContacts(nick)){
                        isHaveNickName = true;
                        nickName = nick;
                        c.setLocalNick(nickName);
                        c.goOnline();
                       // arrayList.add("Вы зарегистрированы");
                        isflagEntry=true;
                    }else{
                        arrayList.add("Такой ник уже занят! Пожалуйста, выберите другой!");
                    }
                }else{
                    arrayList.add("Вы ввели неправильный ник! ");
                    arrayList.add("Введите ник не используя пробелы!");
                }
            }else{
                arrayList.add("Введите ник!");
            }
        }else{
            arrayList.add("Error"); //????
        }
       /* if(UserWindow.serverOrFriends.equals("server")) {
            JOptionPane.showMessageDialog(PeopleOptionPane.frame,getTextFromArray(arrayList));
        } else {
            JOptionPane.showMessageDialog(FriendOptionPane.frame,getTextFromArray(arrayList));
        }*/
        return isflagEntry;
    }

    public boolean entry(){
        isflagEntry=false;
        arrayList.clear();
        String nick = registration.getTextFromField();
        if (!isHaveNickName) {
            if (!nick.equals("")){
                if (!ChackGaps(nick)){
                    if(compareToServerListContacts(nick)){
                        isHaveNickName = true;
                        nickName = nick;
                        c.setLocalNick(nickName);
                        c.goOnline();
                        arrayList.add("Вход успешно выполнен!");
                        isflagEntry=true;
                    }else{
                        arrayList.add("Вы не зарегистрированы!!");
                    }
                }else{
                    arrayList.add("Вы ввели неправильный ник! ");
                    arrayList.add("Введите ник не используя пробелы!");
                }
            }else{
                arrayList.add("Введите ник!");
            }
        }else{
            arrayList.add("Error"); //????
        }
       /* if(UserWindow.serverOrFriends.equals("server")) {
            JOptionPane.showMessageDialog(PeopleOptionPane.frame,getTextFromArray(arrayList));
        } else {
            JOptionPane.showMessageDialog(FriendOptionPane.frame,getTextFromArray(arrayList));
        }*/
        return isflagEntry;
    }
/*    public void apply(String str){

        String nick = chatAppGUI.getTextFromField("myNickNameField");
        if (!isHaveNickName){
            if(!nick.equals("")) {
                if (ChackGaps(nick)) {
                    arrayList.add("Вы ввели неправильный ник! ");
                    arrayList.add("Введите ник не используя пробелы!");
                } else if(!compareToServerListContacts(nick)) {//если нет в списке контактов
                    if(str.equals("Registration")) {
                        isHaveNickName = true;
                        nickName = nick;
                        c.setLocalNick(nickName);
                        arrayList.add("Вы зарегистрированы");
                    }else{

                    }
                }else{
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
    }*/

    public void disconnect(){
        if (isHaveSocket) {
            isHaveSocket = false;
          //  arrayList.add("Отключено");
            try {
                disconnectClose();
            } catch (IOException e) {
                e.printStackTrace();
            }
            c.goOffline();
        } else {
            JOptionPane.showMessageDialog(DialogWindow.frame,"У Вас нет активного подключения");
           // arrayList.add("У Вас нет активного подключения");
        }
    }


   /* public void cancel(){
        isHaveNickName = false;
        chatAppGUI.myNickFieldClear();
        arrayList.clear();
    }
*/

    public void setDialogWindow(DialogWindow dialogWindow){
        this.dialogWindow = dialogWindow;
    }

    public void setVisibleFrame(String str,boolean bol){
        if(str.equals("Registration")) {
            registration.setVisibleFrame(true);
        }
        if(str.equals("UserWindow")) {
            userWindow.setVisibleFrame(true);
        }
       /* if(str.equals("Registration")) {
            registration.setVisibleFrame(true);
        }*/
    }

    public void send(){
        arrayList.clear();
        if (isHaveSocket) {
            String str =dialogWindow.getTextFromField();
            if (!str.equals("")) {
                //arrayList.add(str);
               // dialogWindow.addMessage(str);
                dialogWindow.setMessage(str);
                connection.sendMessage(MessageCommand.message);
                connection.sendMessage(str);
                arrayListMessage.add(str);
            } else {
                JOptionPane.showMessageDialog(DialogWindow.frame,"Вы не ввели сообщение!");
             //   arrayList.add("Вы не ввели сообщение!");
            }
        } else {
            JOptionPane.showMessageDialog(DialogWindow.frame,"нет доступного подклчения");
           // arrayList.add("нет доступного подклчения");
        }
    }

    public Vector<String> search(String friendNickNameTmp){
        Vector<String> arrayListTmp = new Vector<String>();
        arrayListTmp.add(0,"");
        arrayListTmp.add(1,"");
        //String str = chatAppGUI.getTextFromField("friendNickField");
        if (!friendNickNameTmp.equals("")){
            arrayListTmp.add(0, friendNickNameTmp);
            if (compareToServerListContacts(friendNickNameTmp)) {
                arrayListTmp.add(1, c.getIpForNick(friendNickNameTmp));
            }
        }
        return arrayListTmp;
    }

    public Vector<String> search(){
        arrayList.clear();
        Vector<String> arrayListTmp = new Vector<String>();
        arrayListTmp.add(0,"");
        arrayListTmp.add(1,"");
        //String str = chatAppGUI.getTextFromField("friendNickField");
        String str = userWindow.getTextFromField();
        if (!str.equals("")){
            if(compareToServerListContacts(str)){
                arrayListTmp.add(0,str);
                arrayListTmp.add(1,c.getIpForNick(str));
            }else{
                arrayList.add(0,"Такого пользователя нет на сервере");
            }
        }else{
            arrayList.add(1,"Введите имя друга!");
        }

            JOptionPane.showMessageDialog(UserWindow.frame,getTextFromArray(arrayList));

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
       commandListenerThread.deleteObservers();
      //  commandListenerThread.deleteObserver(guiForm);//???подумать
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

    public  String getTextFromArray(ArrayList<String> arrayList) {
        String str = "";
        // if(arrayList.size()<26) {
        for (int i = 0; i < arrayList.size(); i++) {
            str += arrayList.get(i) + "\n";
        }
        return str;
    }



    public void update666(Observable o, Object arg) {
        String str = (String) arg;
        System.out.println("Good 1 "+isCalling);
        // it is new connection

        //it is work with new connection
        if (str.equals(CallListenerThread.itIsCallLisnenerThread)) {
            Connection connection1 = new Connection();
            if (isHaveSocket) {
                try {
                    connection1.setSocket(callListenerThread.getSocket());
                    connection1.sendNickBusy(nickName);
                    connection1.getSocket().close();
                } catch (IOException e) {
                }
            } else try {
                disconnectClose();
                connection1.setSocket(callListenerThread.getSocket());
                connection1.sendNickHello(nickName);
                commandListenerThread = new CommandListenerThread("lala1",connection1);
                commandListenerThread.addObserver(this);
                commandListenerThread.CommandListenerThreadStart();
                isWaitNextStageCalling = true;
            } catch (IOException e) {
            }
        }

        if(isWaitNextStageCalling) {
            if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
                isWaitNextStageCalling = false;
                try {
                    Command command1 = commandListenerThread.getLastCommand();
                    if (command1 != null && command.getCommand().equals(NickCommand.helloMessage)) {
                        NickCommand nickCommand = (NickCommand) command1;
                        InputConnection inputConnection = new InputConnection(nickCommand.getNick());
                        while (!InputConnection.isInputConnection) {
                        }
                        if (InputConnection.flag) {
                            connection.setSocket(callListenerThread.getSocket());
                            new DialogWindow(nickCommand.getNick());  //имя друга, который звонит
                        } else {
                            try {
                                disconnectClose();
                            } catch (IOException ee) {
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (isCalling) {
            if (str.equals(CommandListenerThread.itIsCommandLisnenerThread)) {
                isCalling = false;
                Command command1 = commandListenerThread.getLastCommand();
                if (command1 == null) System.out.println("holy shit");
                //  if (command1.getCommand() == null) System.out.println("lalal");
                if(command1 != null && command1.getCommand().equals(NickCommand.helloMessage)){
                    NickCommand nickCommand = (NickCommand)command1;
                    //if(nickCommand.isBusy()){
                    String friendNickNameTmp = nickCommand.getNick();
                    if(nickCommand.isBusy()){
                        if(UserWindow.serverOrFriends.equals("server")) {
                            JOptionPane.showMessageDialog(PeopleOptionPane.frame,friendNickName+" занят");
                        } else {
                            JOptionPane.showMessageDialog(FriendOptionPane.frame,friendNickName+" занят");
                        }
                        // friendNickField.setText(friendNickName);
                        // arrayList.add("Абонент "+friendNickName+" занят");
                        isHaveSocket = false;
                        try {
                            disconnectClose();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{

                        // CallWindow.guiForm = guiForm;
                            /* CallWindow.commandListenerThread = commandListenerThread;
                        CallWindow.isHaveSocket = isHaveSocket;
                        CallWindow.isNextCallStage = isNextCallStage;
                        CallWindow.s=nickName;*/
                        // callWindow.setTextField2();
                        //   CallWindow.textField2.setText(nickName);
                        isHaveSocket = true;
                        isNextCallStage = true;
                        connection.sendNickHello(nickName);
                        DialogWindow dialogWindow = new DialogWindow(friendNickNameTmp);//имя друга которому звоним
                        //start call window
                        // callWindow = new CallWindow();
                    }
                }else {
                    isHaveSocket = false;
                    if(UserWindow.serverOrFriends.equals("server")) {
                        JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Не удалось позвонить //поскольку неверный ответ сервера");
                    } else {
                        JOptionPane.showMessageDialog(FriendOptionPane.frame,"Не удалось позвонить //поскольку неверный ответ сервера");
                    }
                    //arrayList.add("Не удалось позвонить //поскольку неверный ответ сервера");
                }
            }else{
                isHaveSocket = false;
                if(UserWindow.serverOrFriends.equals("server")) {
                    JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Не удалось позвонить");
                } else {
                    JOptionPane.showMessageDialog(FriendOptionPane.frame,"Не удалось позвонить");
                }
                //   arrayList.add("Не удалось позвонить");
                try {
                    disconnectClose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isCalling = false;
        }
        //it is call user 2 stage
        if(isNextCallStage){
            if(str.equals(CommandListenerThread.itIsCommandLisnenerThread)){
                Command command1 = commandListenerThread.getLastCommand();
                if(command1.getCommand().equals(Command.accepted)){
                   dialogWindow =  new DialogWindow(friendNickName);//имя друга, которому звоним
                    //JOptionPane.showMessageDialog();
                    //arrayList.add("Подключение выполнено");
                    //arrayList.add("");
                    //callWindow.stopThisWindow();
                }
                if(command1.getCommand().equals(Command.rejected)){
                    arrayList.clear();
                    if(UserWindow.serverOrFriends.equals("server")) {
                        JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Aбонент отклонил вызов");
                    } else {
                        JOptionPane.showMessageDialog(FriendOptionPane.frame,"Aбонент отклонил вызов");
                    }
                    //callWindow.stopThisWindow();
                    //arrayList.add("Клиент отказался подключаться");
                    try {
                        disconnectClose();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                isNextCallStage = false;
            }
        }
        //it is call user 1 stage


        if (str.equals(CommandListenerThread.itIsCommandLisnenerThread) && isHaveSocket) {
            Command command1 = commandListenerThread.getLastCommand();
            if (command1  != null){
                //if(command.getCommand() == null) System.out.println("fuck");
                if(command1.getCommand().equals(Command.disconnect)){
                   // arrayList.add("Вы были отключены другим юзером");///////????????????
                    JOptionPane.showMessageDialog(DialogWindow.frame,"Вы были отключены другим юзером");
                    try {
                        disconnectClose();
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
                    MessageCommand messageCommand = (MessageCommand) command1;
                  //  String messageString = me;
                    dialogWindow.setMessage(messageCommand.getMessage());
                    //arrayList.add(messageString);
                    // scrollPane.setToolTipText(getTextFromArray(arrayList));
                    //DialogArea.setText(getTextFromArray(arrayList));
                }
            }

        }
    }


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

                            connection.setSocket(commandListenerThread.getSocket());
                            connection.accept();
                            new DialogWindow(nickCommand.getNick()); //имя друга, который звонит
                            isHaveSocket = true;
                        } else {
                            connection.setSocket(commandListenerThread.getSocket());
                            connection.reject();
                            try {
                                disconnectClose();
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
                        if(UserWindow.serverOrFriends.equals("server")) {
                            JOptionPane.showMessageDialog(PeopleOptionPane.frame,friendNickName+" занят");
                        } else {
                            JOptionPane.showMessageDialog(FriendOptionPane.frame,friendNickName+" занят");
                        }
                        isHaveSocket = false;
                        try {
                            disconnectClose();
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
                        DialogWindow dialogWindow = new DialogWindow(friendNickNameTmp);
                        // start call window
                        // callWindow = new CallWindow();
                    }
                } else {
                    isHaveSocket = false;
                    if(UserWindow.serverOrFriends.equals("server")) {
                        JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Не удалось позвонить //поскольку неверный ответ сервера");
                    } else {
                        JOptionPane.showMessageDialog(FriendOptionPane.frame,"Не удалось позвонить //поскольку неверный ответ сервера");
                    }
                }
            } else {
                isHaveSocket = false;
                if(UserWindow.serverOrFriends.equals("server")) {
                    JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Не удалось позвонить");
                } else {
                    JOptionPane.showMessageDialog(FriendOptionPane.frame,"Не удалось позвонить");
                }
                try {
                    disconnectClose();
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
                    dialogWindow =  new DialogWindow(friendNickName);
                }
                if (command1.getCommand().equals(Command.rejected)) {
                    arrayList.clear();
                    if(UserWindow.serverOrFriends.equals("server")) {
                        JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Aбонент отклонил вызов");
                    } else {
                        JOptionPane.showMessageDialog(FriendOptionPane.frame,"Aбонент отклонил вызов");
                    }
                    try {
                        disconnectClose();
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
                    dialogWindow =  new DialogWindow(friendNickName);
                }
                if (command1.getCommand().equals(Command.rejected)) {
                    arrayList.clear();
                    if(UserWindow.serverOrFriends.equals("server")) {
                        JOptionPane.showMessageDialog(PeopleOptionPane.frame,"Aбонент отклонил вызов");
                    } else {
                        JOptionPane.showMessageDialog(FriendOptionPane.frame,"Aбонент отклонил вызов");
                    }
                    try {
                        disconnectClose();
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
                    arrayList.add("¬˚ ·˚ÎË ÓÚÍÎ˛˜ÂÌ˚ ‰Û„ËÏ ˛ÁÂÓÏ");
                    try {
                        disconnectClose();
                    } catch (IOException e) {
                        // e.printStackTrace();
                    }
                }
                if (command1.getCommand().equals(MessageCommand.message)) {
                    MessageCommand messageCommand = (MessageCommand) command1;
                    dialogWindow.setMessage(messageCommand.getMessage());
                }
            }

        }
        //DialogArea.setText(getTextFromArray(arrayList));
    }



}



