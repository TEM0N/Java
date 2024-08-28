package lab9;

import gui.AbstractApplication;
import lab8.AddExportDialog;
import lab8.Export;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application extends AbstractApplication {
    private JList<Export> leftList;
    private JList<Export> rightList;
    private JList<Export> resultList;
    private Set<Export> leftSet;
    private Set<Export> rightSet;
    private Set<Export> resultSet;

    public static Application create() {
        return new Application();
    }

    private Application() {
        super("Lab 9", 1700, 500);
        setLayout(new GridLayout(0, 1));
        initListsPanel();
        initGeneralButtons();
        initDefaultSet();
        setVisible(true);
    }

    private void initDefaultSet() {
        leftSet.add(new Export("teapot", "Belarus", 200));
        leftSet.add(new Export("teapot", "Russia", 300));
        leftSet.add(new Export("laptop", "Russia", 3000));
        leftSet.add(new Export("laptop", "Serbia", 123));
        leftSet.add(new Export("laptop", "Belarus", 321));

        rightSet.add(new Export("books", "Belarus", 5678));
        rightSet.add(new Export("laptop", "Serbia", 123));
        rightSet.add(new Export("laptop", "Belarus", 321));

        leftList.setModel(leftSet.getListModel());
        rightList.setModel(rightSet.getListModel());

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
        button.addActionListener(actionEvent -> {
            String s = leftSet.equals(rightSet) ? "The sets are equals" : "The sets are different";
            JOptionPane.showMessageDialog(this, s, "EQUALS", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(button);
    }

    private void initRightMove(JPanel panel) {
        JButton button = new JButton(">>");
        button.addActionListener(actionEvent -> {
            rightSet.addAll(leftSet);
            rightList.setModel(rightSet.getListModel());
        });
        panel.add(button);
    }

    private void initLeftMove(JPanel panel) {
        JButton button = new JButton("<<");
        button.addActionListener(actionEvent -> {
            leftSet.addAll(rightSet);
            leftList.setModel(leftSet.getListModel());
        });
        panel.add(button);
    }

    private void initDifferenceButton(JPanel panel) {
        JButton button = new JButton("difference");
        button.addActionListener(actionEvent -> {
            resultSet.clear();
            resultSet.addAll(leftSet.difference(rightSet));
            resultList.setModel(resultSet.getListModel());
        });
        panel.add(button);
    }

    private void initIntersectionButton(JPanel panel) {
        JButton button = new JButton("intersection");
        button.addActionListener(actionEvent -> {
            resultSet.clear();
            resultSet.addAll(leftSet.intersection(rightSet));
            resultList.setModel(resultSet.getListModel());
        });
        panel.add(button);
    }

    private void initUnionButton(JPanel panel) {
        JButton button = new JButton("union");
        button.addActionListener(actionEvent -> {
            resultSet.clear();
            resultSet.addAll(leftSet.union(rightSet));
            resultList.setModel(resultSet.getListModel());
        });
        panel.add(button);
    }

    private void initListsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.setBackground(Color.BLACK);
        initLeftPanel(panel);
        initRightPanel(panel);
        initResultPanel(panel);
        add(panel);
    }

    private void initLeftPanel(JPanel panel) {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        leftSet = Set.create();
        leftList = new JList<>(leftSet.getListModel());
        initAddButton(leftList, leftSet, leftPanel, c);
        initRemoveButton(leftList, leftSet, leftPanel, c);
        initClearButton(leftList, leftSet, leftPanel, c);
        initSizeButton(leftSet, leftPanel);
        leftList.setBackground(Color.BLACK);
        leftList.setForeground(Color.CYAN);
        initList(leftList, leftPanel, c);
        panel.add(leftPanel);
    }

    private void initRightPanel(JPanel panel) {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        rightSet = Set.create();
        rightList = new JList<>(rightSet.getListModel());
        rightList.setBackground(Color.BLACK);
        rightList.setForeground(Color.CYAN);
        initAddButton(rightList, rightSet, rightPanel, c);
        initRemoveButton(rightList, rightSet, rightPanel, c);
        initClearButton(rightList, rightSet, rightPanel, c);
        initSizeButton(rightSet, rightPanel);
        initList(rightList, rightPanel, c);
        panel.add(rightPanel);
    }

    private void initResultPanel(JPanel panel) {
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        resultSet = Set.create();
        resultList = new JList<>(resultSet.getListModel());
        resultList.setBackground(Color.BLACK);
        resultList.setForeground(Color.CYAN);
        initAddButton(resultList, resultSet, resultPanel, c);
        initRemoveButton(resultList, resultSet, resultPanel, c);
        initClearButton(resultList, resultSet, resultPanel, c);
        initSizeButton(resultSet, resultPanel);
        initList(resultList, resultPanel, c);
        panel.add(resultPanel);
    }

    private void initList(JList<Export> list, JPanel panel, GridBagConstraints c) {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(list, c);
    }

    private void initAddButton(JList<Export> list, Set<Export> set, JPanel panel, GridBagConstraints c) {
        JButton addButton = new JButton("add");
        addButton.addActionListener(actionEvent -> {
            Export export = new Export();
            new AddExportDialog(this, export);
            if (!export.equals(new Export())) {
                set.add(export);
            }
            list.setModel(set.getListModel());
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(addButton);
    }

    private void initRemoveButton(JList<Export> list, Set<Export> set, JPanel panel, GridBagConstraints c) {
        JButton removeButton = new JButton("remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeFromList();
                list.setModel(set.getListModel());
            }

            private void removeFromList() {
                int[] ints = list.getSelectedIndices();
                for (int i = 0; i < ints.length; i++) {
                    set.remove(set.get(i));
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(removeButton, c);
    }

    private void initClearButton(JList<Export> list, Set<Export> set, JPanel panel, GridBagConstraints c) {
        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(actionEvent -> {
            set.clear();
            list.setModel(set.getListModel());
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(clearButton, c);
    }

    private void initSizeButton(Set<Export> set, JPanel panel) {
        JButton sizeButton = new JButton("size");
        sizeButton.addActionListener(actionEvent -> {
            String s = "Set contains " + set.size() + " element";
            if (set.size() != 1) {
                s += "s";
            }
            JOptionPane.showMessageDialog(this, s, "SIZE", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(sizeButton);
    }
}
