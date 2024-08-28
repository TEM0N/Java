package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SecondCard extends JPanel implements MouseListener{
    private int size = 8;

    public void setSize(int size) {
        this.size = size;
    }

    private String oldText;
    private Color oldColor;

    private final Color NEW_COLOR = Color.RED;

    SecondCard() {
        super();
        setLayout(new GridLayout(size, size));
        initButtons();
        int pixels = 70;
        setPreferredSize(new Dimension(pixels *size, pixels *size));
    }

    private void initButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JButton bn = new JButton(i * size + j + 1 + "");
                bn.addMouseListener(this);
                add(bn);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        oldText = button.getText();
        String NEW_TEXT = "click!";
        button.setText(NEW_TEXT);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setText(oldText);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        oldColor = button.getBackground();
        button.setBackground(NEW_COLOR);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setBackground(oldColor);
    }
}
