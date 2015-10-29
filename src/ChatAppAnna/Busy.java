import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by anna on 25.10.15.
 */
public class Busy extends JFrame{
    private JPanel panel1;
    private JButton button1;
    private JButton disconnectButton;
     Caller call = new Caller();
    Connection connection = new Connection();

    public  Busy(String ipAddress,int serverPort){
        final String ip = ipAddress;
        final int port = serverPort;
        setContentPane(panel1);
        setSize(200, 100);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Socket socket = new Socket(ip, port);
                }catch (IOException r){
                    //дописать;
                }
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
    }
}
