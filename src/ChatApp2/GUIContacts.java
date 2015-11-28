import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.String;
import java.util.ArrayList;

/**
 * Created by anna on 28.11.15.
 */
public class GUIContacts {
    private  String IP;
    private JTextField searchField;
    private JButton searchButton;
    private JScrollPane scrollPane;
    private JScrollBar scrollBar1;
    private JFrame frame;
    private  JPanel panel;
    private JTextArea textArea;
    private JButton callButton;
    private ArrayList<String> arrayList = new ArrayList<String >();
    private ArrayList <String>arrayListIP = new ArrayList<String>();
    private ChatAppGUI  chatAppGUI;
    private Contacts contacts;

    public  GUIContacts(){
        contacts = new Contacts();
        frame = new JFrame();
        arrayList = createArray();
        arrayListIP = createArrayIP();
        textArea.setText(getTextFromArrays(arrayList,arrayListIP));
        scrollPane = new JScrollPane();
        scrollPane.setToolTipText(getTextFromArrays(arrayList,arrayListIP));
        scrollBar1 = scrollPane.createVerticalScrollBar();
        panel.setBackground(Color.GREEN);
        frame.setContentPane(panel);
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

      searchButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              if(searchField.getText()!=null){
                  IP =searchClient(searchField.getText());
                  if (IP!=null) {
                      scrollPane.setToolTipText(searchField.getText() + " " + IP);
                  }
              }
          }
      });

        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (IP!=null){

                }
            }
        });
    }

    public String searchClient(String textFromSearch){
        int size = contacts.getSize();
        for(int i =0;i<size;i++){
            if(arrayList.get(i)==textFromSearch){
                 IP = arrayListIP.get(i);
                return IP;
            }
        }
       return null;
    }

    public ArrayList<String> createArray(){
        int size = contacts.getSize();
        for (int i=0;i<size;i++){
            arrayList.add(contacts.getNick(i));
        }
        return arrayList;
    }

    public ArrayList<String> createArrayIP(){
        int size = contacts.getSize();
        for (int i=0;i<size;i++){
            arrayListIP.add(contacts.getIP(i));
        }
        return arrayListIP;
    }

    private String getTextFromArrays(ArrayList<String> arrayList,ArrayList<String>arrayListIP) {
        String str = "";
            for (int i = 0; i < arrayList.size(); i++) {
                str += arrayList.get(i) + " "+ arrayListIP.get(i)+"\n";
            }

        return str;
    }

   /* public  static void main(String[]agrs){
        new GUIContacts();
    }
    */
}
