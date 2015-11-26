import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;

/**
 * Created by anna on 18.11.15.
 */
public class CallWindow {
    private JFrame frame;
    private JButton rejectButton;
    private JPanel panel;
    // static JTextField nameCallingUserField;
     //JTextField textField1;
     JTextField textField2;
    static String s;


    //static JTextField nameCallingUser;
    static CommandListenerThread commandListenerThread;
    static ChatAppGUI guiForm;
    static Boolean isNextCallStage;
    static Boolean isHaveSocket;

    public CallWindow() {
        frame.setContentPane(panel);
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        //textField1.setText("идет вызов...");


        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField2.setText(s);
                isHaveSocket = false;
                isNextCallStage = false;
                Socket s = commandListenerThread.getSocket();
                commandListenerThread.deleteObserver(guiForm);
                try {
                    s.close();
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
                commandListenerThread.stop();
                stopThisWindow();
            }
        });

    }
    public  void windowClosing(WindowEvent e){//one
        e.getSource();
    }

    public  void stopThisWindow() {///one
        System.exit(0);
    }
}
