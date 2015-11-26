import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by anna on 17.11.15.
 */
/*public class InputConnection {
    private JButton acceptButton;
    private JButton rejectButton;
    private JLabel InputLabel;

    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    /**
     * Created by anna on 16.11.15.
     */
    public class InputConnection {
        //private JPanel panel;
        private JButton acceptButton;
        private JButton rejectButton;
    private JLabel InputConnectionLabel;
    private JFrame frame;
        private GUIForm guiForm;
        static boolean flag;
        Connection connection = new Connection();

        public InputConnection(){
            flag = false;
            // frame.setContentPane(panel);
            frame.setSize(300, 100);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);


            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flag = true;
                    guiForm.isHaveSocket = true;

                }
            });

            rejectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    connection.sendMessage("Sorry, i don`t want correspond with you ");
                }
            });
        }
    }


