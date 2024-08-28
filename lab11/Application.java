package lab11;

import gui.AbstractApplication;
import lab11.mvc.Controller;
import lab11.mvc.Set;
import lab11.mvc.View;

import javax.swing.*;
import java.awt.*;

public class Application extends AbstractApplication {
    private View<Export> leftView;
    private View<Export> rightView;
    private View<Export> resultView;
    private Controller controller;
    private Set<Export> leftSet;
    private Set<Export> rightSet;
    private Set<Export> resultSet;

    public static Application create() {
        return new Application();
    }

    private Application() {
        super("Lab 11", 1700, 500);
        setLayout(new GridLayout(0, 1));
        initMVC();
        initListsPanel();
        initGeneralButtons();
        setVisible(true);
    }

    private void initMVC() {
        initDefaultSet();
        leftView = new View<>(leftSet);
        rightView = new View<>(rightSet);
        resultView = new View<>(resultSet);
        controller = new Controller(leftSet, rightSet, resultSet, leftView, rightView, resultView);

    }

    private void initDefaultSet() {
        leftSet = Set.create();
        rightSet = Set.create();
        resultSet = Set.create();
        leftSet.add(new Export("teapot", "Belarus", 200));
        leftSet.add(new Export("teapot", "Russia", 300));
        leftSet.add(new Export("laptop", "Russia", 3000));
        leftSet.add(new Export("laptop", "Serbia", 123));
        leftSet.add(new Export("laptop", "Belarus", 321));

        rightSet.add(new Export("books", "Belarus", 5678));
        rightSet.add(new Export("laptop", "Serbia", 123));
        rightSet.add(new Export("laptop", "Belarus", 321));

    }

    private void initGeneralButtons() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        initUnionButton(panel);
        initIntersectionButton(panel);
        initDifferenceButton(panel);
        initEqualButton(panel);
        initLeftMove(panel);
        initRightMove(panel);
        add(panel);
    }

    private void initEqualButton(JPanel panel) {
        JButton button = new JButton("equals");
        button.addActionListener(actionEvent -> controller.equals(this));
        panel.add(button);
    }

    private void initRightMove(JPanel panel) {
        JButton button = new JButton(">>");
        button.addActionListener(actionEvent -> controller.moveRight());
        panel.add(button);
    }

    private void initLeftMove(JPanel panel) {
        JButton button = new JButton("<<");
        button.addActionListener(actionEvent -> controller.moveLeft());
        panel.add(button);
    }

    private void initDifferenceButton(JPanel panel) {
        JButton button = new JButton("difference");
        button.addActionListener(actionEvent -> controller.difference());
        panel.add(button);
    }

    private void initIntersectionButton(JPanel panel) {
        JButton button = new JButton("intersection");
        button.addActionListener(actionEvent -> controller.intersection());
        panel.add(button);
    }

    private void initUnionButton(JPanel panel) {
        JButton button = new JButton("union");
        button.addActionListener(actionEvent -> controller.union());
        panel.add(button);
    }

    private void initListsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.setBackground(Color.BLACK);
        initPanel(leftSet, leftView, panel);
        initPanel(rightSet, rightView, panel);
        initPanel(resultSet, resultView, panel);
        add(panel);
    }

    private void initPanel(Set<Export> set, View<Export> view, JPanel panel) {
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        JList<Export> list = view.getList();
        initAddButton(set, view, listPanel, c);

        initRemoveButton(set, view, listPanel, c);
        initClearButton(set, view, listPanel, c);
        initSizeButton(set, listPanel);
        list.setBackground(Color.BLACK);
        list.setForeground(Color.CYAN);
        addList(list, listPanel, c);
        panel.add(listPanel);
    }

    private void initAddButton(Set<Export> set, View<Export> view, JPanel panel, GridBagConstraints c) {
        JButton addButton = new JButton("add");
        addButton.addActionListener(actionEvent -> {
            Export export = new Export();
            new AddExportDialog(this, export);
            if (!export.equals(new Export())) {
                controller.add(view, set, export);
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(addButton);
    }

    private void addList(JList<Export> list, JPanel panel, GridBagConstraints c) {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(list, c);
    }

    private void initRemoveButton(Set<Export> set, View<Export> view, JPanel panel, GridBagConstraints c) {
        JButton removeButton = new JButton("remove");
        removeButton.addActionListener(actionEvent -> controller.remove(view, set));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(removeButton, c);
    }

    private void initClearButton(Set<Export> set, View<Export> view, JPanel panel, GridBagConstraints c) {
        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(actionEvent -> controller.clear(view, set));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(clearButton, c);
    }

    private void initSizeButton(Set<Export> set, JPanel panel) {
        JButton sizeButton = new JButton("size");
        sizeButton.addActionListener(actionEvent -> controller.size(this, set));
        panel.add(sizeButton);
    }
}
