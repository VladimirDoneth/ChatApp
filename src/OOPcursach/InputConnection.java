package OOPcursach;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputConnection {
    private JFrame frame = new JFrame();
    private JButton acceptButton = new JButton();
    private JButton rejectButton = new JButton();
    private JLabel inputCalling = new JLabel();
    private JPanel panel = new JPanel();
    static boolean flag;
    static boolean isInputConnection = true;

    public static void main(String args []){
        new InputConnection("lala");
    }
    
    public InputConnection(String name){
    	isInputConnection = true;
        frame.setContentPane(panel);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        Box boxh = Box.createHorizontalBox();
        Box boxv = Box.createVerticalBox();
        acceptButton.setText("Accept");
        rejectButton.setText("Reject");
        boxh.add(acceptButton);
        boxh.add(Box.createHorizontalGlue());
        boxh.add(rejectButton);
        inputCalling.setText(name);
        boxv.add(inputCalling);
        boxv.add(boxh);
        
        frame.add(boxv,BorderLayout.CENTER);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             flag=true;
             isInputConnection = false;
             frame.setVisible(false);
            }
        });
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             flag=false;
             isInputConnection = false;
             frame.setVisible(false);
            }
        });
    }
}
