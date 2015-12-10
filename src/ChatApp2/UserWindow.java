import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by anna on 05.12.15.
 */
public class UserWindow{//implements Observer {
    private JPanel panel;
 static JFrame frame;
    private JButton allUserButton;
    private JButton myFriendsButton;
    private JList listOfUser;
    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;
    private Controll controll;
    private Contacts contacts;
    private Vector<String> serverContacts = new Vector<String>();
    private String[] nickAndIP;
    static String serverOrFriends;
    private ServerConnection c;


    public UserWindow(ServerConnection c,final Controll controll){
        this.controll=controll;
        this.c=c;
        frame = new JFrame();
        //panel.setBackground(Color.cyan);
        DefaultListModel defaultListModel = new DefaultListModel();
        listOfUser.setModel(defaultListModel);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.setContentPane(panel);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
//        controll.setUserWindow(this);


        myFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contacts = controll.getLocalContacts();
                if (contacts.getSize()!=0){
                    Vector<String> vector = new Vector<String>();
                    for (int i=0;i<contacts.getSize();i++) {
                        vector.add(contacts.getNick(i)+" "+contacts.getIP(i));
                    }
                    listOfUser.setListData(vector);
                    listOfUser.setVisibleRowCount(contacts.getSize());
                    listOfUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    serverOrFriends = "friend";
                }else{
                    JOptionPane.showMessageDialog(frame, "У вас еще нет ни одного друга");
                }
            }
        });

        allUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] arrayOfContacts = controll.getServerContacts();
                for (int i = 0; i < arrayOfContacts.length-10; i++) {
                    serverContacts.add(arrayOfContacts[i]);
                }
                listOfUser.setListData(serverContacts);
                listOfUser.setVisibleRowCount(serverContacts.size());
                listOfUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                serverOrFriends = "server";
            }
        });

        listOfUser.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               // panel.setBackground(Color.RED);
                Object ob = listOfUser.getSelectedValue();
                String strName = ob.toString();///cтранный эксепшен!!!!!!!!!!!!!!!!!!!!!!
                System.out.println(strName);
                ServerConnection c=getServer();
                String IP =c.getIpForNick(strName);
                //nickAndIP=str.split(" ");
               // System.out.println(str);
                //System.out.println(nickAndIP[1]);
                //for (int i=1;i<2;i++){
                  //  System.out.println(nickAndIP[i]);
                //}
                System.out.println(getServerOrFriends());
                if(getServerOrFriends().equals("server")){
                    PeopleOptionPane peopleOptionPane =  new  PeopleOptionPane(strName,controll,c);
                    frame.setVisible(false);
                    //peopleOptionPane.setControll(controll);
                    peopleOptionPane.setFriendLabel(strName);
                }else{
                    FriendOptionPane friendOptionPane =new FriendOptionPane(strName,controll,c);
                    frame.setVisible(false);
                    //friendOptionPane.setControll(controll);
                    friendOptionPane.setFriendLabel(strName);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> tmpVector = controll.search();
                String[] name = new String[1];
                name[0]= tmpVector.get(0);
                System.out.println(name[0]);
                /*for(int i= 0;i<tmpVector.size();i++){
                    System.out.println(tmpVector.get(i)+"\n");
                }*/
               // if(!tmpVector.get(0).equals("")&&!tmpVector.get(1).equals("")) {
                if(!name[0].equals("")){
                    listOfUser.setListData(name);
                    listOfUser.setVisibleRowCount(1);
                    listOfUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                }else{
                    JOptionPane.showMessageDialog(frame, "Пользователя с таким именем не существует");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                controll.setVisibleFrame("Registration",true);
            }
        });
    }

    public void setVisibleFrame(boolean bol){
        frame.setVisible(bol);
    }
    public String getTextFromField(){
        return searchField.getText();
    }

    public void setControll(Controll controll){
        this.controll = controll;
    }

    public String getServerOrFriends(){
        return serverOrFriends;
    }
public ServerConnection getServer(){
    return c;
}
  //  public void setTextToMessageField
 /* @Override
  public void update(Observable o, Object arg) {
      controll.update(o, arg);
  }
*/
}



