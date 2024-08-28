package lab7;

import gui.AbstractApplication;

import javax.swing.*;

public class Application extends AbstractApplication {
    public Application() {
        super("Lab 7", 800, 600);
        initTabbedPane();
        setVisible(true);
    }

    private void initTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("tab 1", new FirstCard());
        tabbedPane.addTab("tab 2", new SecondCard());
        tabbedPane.addTab("tab 3", new ThirdCard());
        add(tabbedPane);
    }
}
