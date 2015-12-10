import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anna on 05.12.15.
 */
public class Registration {
   private JFrame frame = new JFrame();
    private JPanel panel;
    private JTextField loginField;
    private JButton registrationButton;
    private JButton entryButton;
    private Controll controll;
    private ServerConnection c;
    public Registration(){
        //panel.setBackground(Color.cyan);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.setContentPane(panel);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controll.registration()){
                    UserWindow userWindow = new  UserWindow(c,controll);
                    userWindow.setControll(controll);
                    controll.setUserWindow(userWindow);
                    frame.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(frame, controll.getTextFromArray(Controll.arrayList));
                }
            }
        });

        entryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controll.entry()){
                    UserWindow userWindow = new  UserWindow(c,controll);
                    userWindow.setControll(controll);
                    controll.setUserWindow(userWindow);
                    //userWindow.setServer(c);
                    frame.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(frame, controll.getTextFromArray(Controll.arrayList));
                }
            }
        });
    }

    public void showWindow(){
        new UserWindow(c,controll);
    }

    public void setControl(Controll control){
        this.controll=control;
    }

    public  String getTextFromField(){
        return loginField.getText();
    }

    public  static void main(String[]args){
        new Registration();
    }

    public void setServerConnection(ServerConnection c){
        this.c=c;
    }

    public void setVisibleFrame(boolean bol){
        frame.setVisible(bol);
    }
}
