import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by anna on 25.10.15.
 */
public class GUIUser extends JFrame {
    private JPanel panel1;
    private JTextField applyTextField;
    private JButton applyButton;
    private JTextField textField2;
    private JButton disconnectButton;
    private JTextField textField3;
    private JButton connectionButton;
    private JTextField textField4;
    private JButton sendButton;
    static JTextArea textArea1;
    private String IP;
    private String nickFriend;
    private JFrame frame = new JFrame();
    Scanner sc = new Scanner(System.in);
    Command command = new Command();
    Connection connection = new Connection();

    Caller call = new Caller();
    int port = 28411;


    public GUIUser(){
        setContentPane(panel1);
        this.setSize(800,550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        applyTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command.nick = applyTextField.getText();
            }
        });

        textField2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IP = textField2.getText();
            }
        });

        textField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nickFriend= textField3.getText();
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connection.disconnect();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                call.Connect(IP,port);
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    command.StringMessage(textField4.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}



