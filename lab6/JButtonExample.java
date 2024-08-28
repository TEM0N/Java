package lab6;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class JButtonExample extends JFrame
        implements ActionListener {
    protected JButton b1;

    public JButtonExample(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b1 = new JButton("Green");

        //Listen for actions on button 1.
        b1.addActionListener(this);

        //Add Components to this container, using the default FlowLayout.
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(b1);

        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            getContentPane().setBackground(Color.GREEN);
        }
    }

    public static void main(String[] args) {
        JButtonExample frame = new JButtonExample("JButtonExample");
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}