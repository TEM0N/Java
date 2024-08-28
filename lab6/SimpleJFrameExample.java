package lab6;

import java.awt.*;
import javax.swing.*;

public class SimpleJFrameExample {
    public static void main(String[] args) {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleJFrameExample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}