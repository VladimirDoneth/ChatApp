import javafx.beans.InvalidationListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by anna on 05.12.15.
 */
public class DialogWindow{//implements Observer {
    static JFrame frame;
    private JScrollPane scrollPanel;
    private JTextField messageField;
    private JTextArea DialogArea;
    private JLabel nameLabel;
    private JPanel panel;
    private JButton sendButton;
    private JButton disconnectButton;
    private Controll controll;
    String message = "";
    private Vector<String> vectorMessage = new Vector<String>();

    public static void main(String a[]){
        DialogWindow dw =new DialogWindow("lapochka");
    }

    public DialogWindow(String name){
        // scrollBar1 = new JScrollBar();
        frame = new JFrame();
        //panel = new JPanel();
        //scrollBar1 = scrollPane.createVerticalScrollBar();
        panel.setBackground(Color.LIGHT_GRAY);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.setVisible(true);
        nameLabel.setText("    " + name);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.send();
                DialogArea.setText(controll.getTextFromArray(Controll.arrayListMessage));
                messageField.setText("");
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.disconnect();
                //формочка  "закрыть окно?" (нужна ли?)
            }
        });
    }

    public void setTextOnLabel(String name){
        nameLabel.setText(name);
    }
    public void setControll(Controll controll){
        this.controll = controll;
    }

    public String getTextFromField(){
        return messageField.getText();
    }
    public void setTextToMessageField(){
        messageField.setText("");
    }

    public void addMessage(String str){
    vectorMessage.add(str);
  }

    public String  arrayToString(Vector<String>vectorMessage){
        for(int i=0;i<vectorMessage.size();i++){
            message+=vectorMessage.get(i)+"\n";
        }
        return message;
    }
public void setMessage(String message){
    addMessage(message);
DialogArea.setText(arrayToString(vectorMessage));
}

   /* @Override
    public void update(Observable o, Object arg) {
        controll.update(o, arg);
    }*/
  /*  public void setMessage(){

        DialogArea.setText(message);
    }*/
}
