package lab10.first;

import gui.AbstractApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Application extends AbstractApplication {
    private JLabel label;
    private final DefaultListModel<String> pressedKeys = new DefaultListModel<>();

    private final Font font = new Font("Arial", Font.PLAIN, 90);
    private Observable manager;

    public static Application create() {
        return new Application();
    }

    private Application() {
        super("Lab 10", 1200, 400);
        setLayout(new GridLayout(1, 0));
        initLabel();
        initList();
        subscribe();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                manager.notifySubscribers(keyEvent);
            }
        });
        setFocusable(true);
        setVisible(true);
    }

    private void initList() {
        JList<String> history = new JList<>(pressedKeys);
        history.setBackground(Color.BLACK);
        history.setForeground(Color.CYAN);
        history.setFont(font);
        add(new JScrollPane(history));
    }

    private void initLabel() {
        label = new JLabel("No button");
        label.setForeground(Color.CYAN);
        label.setFont(font);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.add(label);
        add(panel);
    }

    private void subscribe() {
        manager = KeyManager.newInstance();
        Observer firstListener = KeyPressedAdaptor.newInstance(label);
        Observer secondListener = KeyLogAdaptor.newInstance(pressedKeys);
        manager.addSubscriber(firstListener);
        manager.addSubscriber(secondListener);
    }
}
