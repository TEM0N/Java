package lab5;

import gui.AbstractApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TeleportingButtonApplication extends AbstractApplication {
    private JPanel jPanel;
    private JLabel point;

    public TeleportingButtonApplication() {
        super("Teleporting Button", 400, 600);
        setLayout(new BorderLayout());
        initPoint();
        initPanel();
        setVisible(true);
    }

    private void initPoint() {
        point = new JLabel();
        point.setForeground(Color.RED);
        add(point, BorderLayout.SOUTH);
    }


    private void updatePos(Point p) {
        point.setText("X: " + p.x + " Y: " + p.y);
    }

    private void initPanel() {
        jPanel = new JPanel(null);
        jPanel.setBackground(Color.BLACK);
        JButton button = new JButton("MOVE ME");
        button.setSize(button.getPreferredSize());
        jPanel.add(button, SwingConstants.CENTER);
        add(jPanel);
        addMouseAction(button);
    }

    private void addMouseAction(JButton button) {
        jPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                updatePos(mouseEvent.getPoint());
            }
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                mouseMoved(mouseEvent);
            }
        });
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                button.setLocation(mouseEvent.getPoint());
            }
        });
        ButtonHandler handler = new ButtonHandler(button);
        button.addMouseMotionListener(handler);
        button.addKeyListener(handler);
    }

    private class ButtonHandler extends KeyAdapter implements MouseMotionListener {
        private final JButton button;

        private ButtonHandler(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            int newX = mouseEvent.getX() + button.getX();
            int newY = mouseEvent.getY() + button.getY();
            updatePos(new Point(newX, newY));
            if ((mouseEvent.getModifiers() & ActionEvent.CTRL_MASK) != 0) {
                button.setLocation(newX, newY);
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {}

        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                StringBuilder s = new StringBuilder(button.getText());
                try {
                    s.deleteCharAt(s.length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    return;
                }
                button.setText(String.valueOf(s));
                button.setSize(button.getPreferredSize());
                return;
            }
            button.setText(button.getText() + keyEvent.getKeyChar());
            button.setSize(button.getPreferredSize());
        }
    }
}