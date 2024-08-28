package lab10.second;

import gui.AbstractApplication;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Application extends AbstractApplication implements ActionListener {
    protected JMenuItem menuItem;
    protected JButton edit;
    protected JButton add;
    protected JButton delete;
    private List list;
    private List sortedWithStreamList;
    private List sortedWithLoopList;

    protected JTextField nameFilter;
    protected JLabel totalStreamQuantity;
    protected JLabel totalLoopQuantity;

    private java.util.List<Export> collection;
    private TreeMap<String, Integer> sMap;
    private TreeMap<String, Integer> lMap;

    private FilterExportByNameStrategy sStrategy;
    private FilterExportByNameStrategy lStrategy;

    public static Application create() {
        return new Application();
    }

    private Application() {
        super("Lab 10", 1500, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        initStrategy();
        menuBarInit();
        addNameFilter();
        listsInit();
        buttonsInit();
        setVisible(true);
    }

    private void initStrategy() {
        lStrategy = new FilterByNameStrategyWithLoop();
        sStrategy = new FilterByNameStrategyWithStream();
    }

    private void addNameFilter() {
        Box box = new Box(2);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.RED);

        nameFilterInit();

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setForeground(Color.RED);

        totalLoopQuantity = initTotalQuantityLabel();
        totalStreamQuantity = initTotalQuantityLabel();

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.BLACK);
        filterPanel.add(nameLabel);
        filterPanel.add(nameFilter);
        JPanel totalQuantityPanel = new JPanel();
        totalQuantityPanel.setBackground(Color.BLACK);
        totalQuantityPanel.add(totalLabel);
        totalQuantityPanel.add(totalStreamQuantity);
        totalQuantityPanel.add(totalLoopQuantity);

        box.add(filterPanel);
        box.add(totalQuantityPanel);
        add(box);
    }

    private JLabel initTotalQuantityLabel() {
        JLabel label = new JLabel("0");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.YELLOW);
        return label;
    }

    private void nameFilterInit() {
        nameFilter = new JTextField(15);
        nameFilter.setBackground(Color.BLACK);
        nameFilter.setForeground(Color.GREEN);
        nameFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
                showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
                showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
                showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
            }
        });
    }

    private void menuBarInit() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);
        JMenu menu = new JMenu("File");
        menu.setForeground(Color.CYAN);
        menu.setBackground(Color.BLACK);
        menuItem = new JMenuItem("Open");
        menuItem.setBackground(Color.BLACK);

        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    protected void listsInit() {
        collection = new ArrayList<>();
        sMap = new TreeMap<>();
        lMap = new TreeMap<>();
        list = new List();
        sortedWithStreamList = new List();
        list.setBackground(Color.BLACK);
        sortedWithStreamList.setBackground(Color.BLACK);
        list.setForeground(Color.WHITE);
        sortedWithStreamList.setForeground(Color.WHITE);

        sortedWithLoopList = new List();
        sortedWithLoopList.setBackground(Color.BLACK);
        sortedWithLoopList.setForeground(Color.WHITE);

        Box box = new Box(2);
        box.add(list);
        box.add(sortedWithStreamList);
        box.add(sortedWithLoopList);
        add(box);
    }

    private void buttonsInit() {
        edit = new JButton("Edit");
        add = new JButton("Add");
        delete = new JButton("Delete");
        Box box = new Box(2);
        addButtonToBox(edit, box);
        addButtonToBox(add, box);
        addButtonToBox(delete, box);
        add(box);
        pack();
    }

    private void addButtonToBox(JButton button, Box box) {
        button.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.ORANGE);
        panel.add(button);
        box.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(menuItem)) {
            collection.clear();
            fileChooserInit();
        } else if (actionEvent.getSource().equals(add)) {
            Export export = new Export();
            new AddExportDialog(this, export);
            if (!export.equals(new Export())) {
                collection.add(export);
            }
        } else if (actionEvent.getSource().equals(edit)) {
            int index = list.getSelectedIndex();
            if (index != -1) {
                new AddExportDialog(this, collection.get(index));
            }
        } else if (actionEvent.getSource().equals(delete)) {
            int index = list.getSelectedIndex();
            if (index != -1) {
                collection.remove(index);
            }
        }
        showDataFromCollection();
        showDataFromFilteredCollection(sStrategy, sortedWithStreamList, sMap, totalStreamQuantity);
        showDataFromFilteredCollection(lStrategy, sortedWithLoopList, lMap, totalLoopQuantity);
    }

    private void showDataFromCollection() {
        if (collection != null) {
            list.removeAll();
            for (Export export : collection) {
                list.add(export.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No elements", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDataFromFilteredCollection(FilterExportByNameStrategy strategy, List list, TreeMap<String, Integer> map, JLabel label) {
        list.removeAll();
        ArrayList<Export> sorted = (ArrayList<Export>) strategy.filterByName(collection, nameFilter.getText());
        label.setText(strategy.calculateTotalQuantity(sorted));
        map.clear();
        for (Export export : sorted) {
            if (map.containsKey(export.getCountry())) {
                map.put(export.getCountry(), map.get(export.getCountry()) + export.getQuantity());
            } else {
                map.put(export.getCountry(), export.getQuantity());
            }
        }
        for (String s : map.descendingKeySet()) {
            list.add(s + " : " + map.get(s));
        }
    }

    protected void fileChooserInit() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            Path path = fileChooser.getSelectedFile().toPath();
            parseData(readDataFromFile(path));
        }
    }

    private ArrayList<String> readDataFromFile(Path path) {
        try {
            return (ArrayList<String>) Files.readAllLines(path);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, exception.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return new ArrayList<>();
    }

    protected void parseData(ArrayList<String> lines) {
        String name;
        String country;
        int quantity;
        for (String line : lines) {
            try (Scanner scanner = new Scanner(line)) {
                name = scanner.next();
                country = scanner.next();
                quantity = scanner.nextInt();
                if (scanner.hasNext()) {
                    JOptionPane.showMessageDialog(this, "wrong data", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                collection.add(new Export(name, country, quantity));
            } catch (InputMismatchException exception) {
                JOptionPane.showMessageDialog(this, "wrong data", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
