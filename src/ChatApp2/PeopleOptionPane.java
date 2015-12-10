import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by anna on 05.12.15.
 */
public class PeopleOptionPane{//} implements Observer {
    static JFrame frame=new JFrame();
    //private JButton addButton;
    private JButton callButton;
    private JLabel peopleLabel;// = new JLabel("<html><div style=\"text-align: center;\">Hello! Welcome to our program.</html>");
    private Controll controll;
    private JButton addToFriendsButton;
    private JPanel panel;
    private JButton backButton;
    private ServerConnection c;

    public PeopleOptionPane(final String nick,final Controll controll,final ServerConnection c) {
        this.c=c;
        this.controll = controll;
        //frame = new JFrame();
        panel.setBackground(Color.LIGHT_GRAY);
        frame.setContentPane(panel);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);


    addToFriendsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String IP= c.getIpForNick(nick);
            controll.addToContacts(nick,IP);
        }
    });

    callButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controll.connect(getFriendLabel());
            if(!Controll.isCalling){
                JOptionPane.showMessageDialog(frame,controll.getTextFromArray(Controll.arrayList));
            }else{
                DialogWindow dialogWindow = new DialogWindow(getFriendLabel());
                dialogWindow.setControll(controll);
                dialogWindow.setTextOnLabel(nick);
            }
        }
    });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                controll.setVisibleFrame("UserWindow",true);
            }
        });
}

    public void setFriendLabel(String nameText){
       // peopleLabel = new JLabel("<html><div style=\"text-align: center;\">nameText.</html>");
      //  peopleLabel.setLayout(new BorderLayout());
        peopleLabel.setText(nameText);

    }
    public String getFriendLabel(){
        return  peopleLabel.getText();
    }
public  JFrame getFrame(){
    return  frame;
}

 //   public void setControll(Controll controll) {
  //      this.controll = controll;
//    }
/*
    @Override
    public void update(Observable o, Object arg) {
        controll.update(o, arg);
    }*/
}